package com.itmo.backend.controllers;


import com.itmo.backend.exceptions.IncorrectJWTException;
import com.itmo.backend.exceptions.ValidationException;
import com.itmo.backend.model.dao.ShotDAO;
import com.itmo.backend.model.dao.UserDAO;
import com.itmo.backend.model.dto.ShotDTO;
import com.itmo.backend.model.dto.UserDTO;
import com.itmo.backend.model.entity.Shot;
import com.itmo.backend.model.entity.User;
import com.itmo.backend.services.AreaCheckService;
import com.itmo.backend.utils.JSONUtil;
import com.itmo.backend.utils.JWTUtil;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

@Path("/shots")
public class ShotsController {
    @EJB
    private JWTUtil jwtUtil;

    @EJB
    private UserDAO userDAO;
    @EJB
    private ShotDAO shotDAO;

    @Context
    private SecurityContext securityContext;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getUserShots() {
        String username = securityContext.getUserPrincipal().getName();
        User user = userDAO.getUserByUsername(username);
        List<Shot> shots = shotDAO.getUserShots(user);
        return Response.ok().entity(JSONUtil.convertShotListToJSON(shots)).build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addShot(ShotDTO shotDTO) {
        try{
            String username = securityContext.getUserPrincipal().getName();
            User user = userDAO.getUserByUsername(username);
            Shot shot = new Shot();
            boolean result = AreaCheckService.calculateResult(shotDTO.getX(), shotDTO.getY(), shotDTO.getR(), shotDTO.isByAreaClick());
            shot.setX(shotDTO.getX());
            shot.setY(shotDTO.getY());
            shot.setR(shotDTO.getR());
            shot.setByAreaClick(shotDTO.isByAreaClick());
            shot.setUser(user);
            shot.setResult(result);
            shotDAO.addShot(shot);
            return Response.ok().entity(JSONUtil.generateShotResultJSON(shot)).build();
        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(JSONUtil.generateResponseMessage(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/clear")
    public Response clearShots() {
        String username = securityContext.getUserPrincipal().getName();
        User user = userDAO.getUserByUsername(username);
        shotDAO.clearShots(user);
        return Response.ok().entity(JSONUtil.generateResponseMessage("Shots were cleared")).build();
    }
}

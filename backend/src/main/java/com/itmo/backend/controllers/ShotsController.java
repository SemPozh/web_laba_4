package com.itmo.backend.controllers;

import com.itmo.backend.exceptions.IncorrectJWTException;
import com.itmo.backend.model.dao.ShotDAO;
import com.itmo.backend.model.dao.UserDAO;
import com.itmo.backend.model.dto.ShotDTO;
import com.itmo.backend.model.dto.UserDTO;
import com.itmo.backend.model.entity.Shot;
import com.itmo.backend.model.entity.User;
import com.itmo.backend.utils.JWTUtil;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/shots")
public class ShotsController {
    @EJB
    private JWTUtil jwtUtil;

    @EJB
    private UserDAO userDAO;
    @EJB
    private ShotDAO shotDAO;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getUserShots(@CookieParam("jwt_auth") Cookie jwtCookie){
        try {
            String username = jwtUtil.parseUsernameFromJWT(jwtCookie.getValue());
            User user = userDAO.getUserByUsername(username);
            if (user==null){
                return Response.status(Response.Status.FORBIDDEN).entity("{'success': false, 'message':'Can't find your user, try to logout and sign in again'}").build();
            }
            List<Shot> shots = shotDAO.getUserShots(user);
            return Response.ok().entity("{'success':true, 'points':[]}").build();
        } catch (IncorrectJWTException e) {
            return Response.status(Response.Status.FORBIDDEN).entity("{'success': false, 'message':'"+e.getMessage()+"'}").build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addShot(ShotDTO shotDTO){
        return null;
    }
}

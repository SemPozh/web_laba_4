package com.itmo.backend.controllers;

import com.itmo.backend.model.dao.UserDAO;
import com.itmo.backend.model.dto.UserDTO;
import com.itmo.backend.model.entity.User;
import com.itmo.backend.utils.JSONUtil;
import com.itmo.backend.utils.JWTUtil;
import com.itmo.backend.utils.PasswordManager;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthController {
    @EJB
    private UserDAO userDAO;

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPasswordHash(PasswordManager.hashPassword(userDTO.getPassword()));
        if (userDAO.getUserByUsername(userDTO.getUsername())!=null){
            return Response.status(Response.Status.CONFLICT).entity(JSONUtil.generateResponseMessage("User with this username already exists")).build();
        }
        userDAO.addUser(user);
        return Response.ok().entity(JSONUtil.generateResponseMessage("User created")).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserDTO userDTO){
        User user = userDAO.getUserByUsername(userDTO.getUsername());
        if (user==null){
            return Response.status(Response.Status.FORBIDDEN).entity(JSONUtil.generateResponseMessage("No user with such username")).build();
        }
        if (!PasswordManager.checkPasswordHash(userDTO.getPassword(), user.getPasswordHash())){
            return Response.status(Response.Status.FORBIDDEN).entity(JSONUtil.generateResponseMessage("Incorrect password")).build();
        }
        NewCookie jwtCookie = new NewCookie("jwt_auth", JWTUtil.generateJWT(userDTO.getUsername()), "/", null, null, -1, false);
        return Response.ok().entity(JSONUtil.generateResponseMessage("You were successfully authorized!")).cookie(jwtCookie).build();
    }
}

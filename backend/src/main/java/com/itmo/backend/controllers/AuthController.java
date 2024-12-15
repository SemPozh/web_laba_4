package com.itmo.backend.controllers;

import com.itmo.backend.model.dao.UserDAO;
import com.itmo.backend.model.dto.UserDTO;
import com.itmo.backend.model.entity.User;
import com.itmo.backend.utils.JWTUtil;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthController {
    @EJB
    private UserDAO userDAO;

    @EJB
    private JWTUtil jwtUtil;

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPasswordHash(userDTO.getPassword());
        if (userDAO.getUserByUsername(userDTO.getUsername())!=null){
            return Response.status(Response.Status.CONFLICT).entity("{'success': false, 'message':'User with this username already exists'}").build();
        }
        userDAO.addUser(user);
        return Response.ok().entity("{\"success\": true, \"message\":\"User created\"}").build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserDTO userDTO){
        User user = userDAO.getUserByUsername(userDTO.getUsername());
        if (user==null){
            return Response.status(Response.Status.FORBIDDEN).entity("{'success': false, 'message':'No user with such username'}").build();
        }
        if (!user.getPasswordHash().equals(userDTO.getPassword())){
            return Response.status(Response.Status.FORBIDDEN).entity("{'success': false, 'message':'Incorrect password'}").build();
        }
        NewCookie jwtCookie = new NewCookie("jwt_auth", jwtUtil.generateJWT(userDTO.getUsername()), "/", null, null, -1, false);
        return Response.ok().entity("{'success': true, 'message':'You were successfully authorized!'}").cookie(jwtCookie).build();
    }
}

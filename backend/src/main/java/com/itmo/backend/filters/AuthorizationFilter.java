package com.itmo.backend.filters;

import com.itmo.backend.utils.JWTUtil;
import jakarta.annotation.Priority;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {
    @EJB
    private JWTUtil jwtUtil;


    @Override
    public void filter(ContainerRequestContext containerRequestContext){

    }
}

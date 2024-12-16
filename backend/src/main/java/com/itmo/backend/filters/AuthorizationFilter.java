package com.itmo.backend.filters;

import com.itmo.backend.conf.AuthConfig;
import com.itmo.backend.exceptions.IncorrectJWTException;
import com.itmo.backend.model.dao.UserDAO;
import com.itmo.backend.model.dto.UserDTO;
import com.itmo.backend.model.entity.User;
import com.itmo.backend.utils.JSONUtil;
import com.itmo.backend.utils.JWTUtil;
import jakarta.annotation.Priority;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {
    @EJB
    private JWTUtil jwtUtil;
    @EJB
    private UserDAO userDAO;


    @Override
    public void filter(ContainerRequestContext containerRequestContext){
        if (AuthConfig.AUTH_FREE_PATHS.contains(containerRequestContext.getUriInfo().getPath())){
            return;
        }

        Cookie authorizationCookie = containerRequestContext.getCookies().get("jwt_auth");
        if (authorizationCookie == null || authorizationCookie.getValue() == null
                || authorizationCookie.getValue().isBlank()) {
            containerRequestContext.abortWith(Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(JSONUtil.generateResponseMessage("JWT required"))
                    .build());
            return;
        }

        String token = authorizationCookie.getValue();
        try {
            String username = jwtUtil.parseUsernameFromJWT(token);
            User user = userDAO.getUserByUsername(username);
            if (user==null){
                throw new IncorrectJWTException("Incorrect JWT!");
            }

            containerRequestContext.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return new UserDTO(username);
                }

                @Override
                public boolean isUserInRole(String s) {
                    return false;
                }

                @Override
                public boolean isSecure() {
                    return false;
                }

                @Override
                public String getAuthenticationScheme() {
                    return null;
                }
            });
        } catch (IncorrectJWTException e) {
            containerRequestContext.abortWith(Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(JSONUtil.generateResponseMessage(e.getMessage()))
                    .build());
        }


    }
}

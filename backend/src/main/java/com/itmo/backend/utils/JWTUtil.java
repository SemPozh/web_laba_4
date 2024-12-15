package com.itmo.backend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.itmo.backend.conf.AuthConfig;
import com.itmo.backend.exceptions.IncorrectJWTException;
import jakarta.ejb.Singleton;

@Singleton
public class JWTUtil {
    public String generateJWT(String username){
        return JWT.create().withSubject(username).sign(Algorithm.HMAC256(AuthConfig.SECRET_JWT_KEY));
    }

    public String parseUsernameFromJWT(String jwt) throws IncorrectJWTException {
        try {
            return JWT.require(Algorithm.HMAC256(AuthConfig.SECRET_JWT_KEY)).build().verify(jwt).getSubject();
        } catch (JWTVerificationException e){
            throw new IncorrectJWTException("Incorrect auth token!");
        }
    }
}

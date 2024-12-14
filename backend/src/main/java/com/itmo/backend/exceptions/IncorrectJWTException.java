package com.itmo.backend.exceptions;

public class IncorrectJWTException extends Exception{
    public IncorrectJWTException(String message){
        super(message);
    }
}

package com.itmo.backend.exceptions;

public class ValidationException extends Exception{
    public ValidationException(String message) {
        super(message);
    }
}
package com.itmo.backend.model.dto;

public class UserDTO {
    private String username;
    private String password;

    public UserDTO(){

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
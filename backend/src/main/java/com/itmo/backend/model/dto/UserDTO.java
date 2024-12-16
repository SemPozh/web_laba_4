package com.itmo.backend.model.dto;

import java.security.Principal;

public class UserDTO implements Principal {
    private String username;
    private String password;

    public UserDTO(){

    }

    public UserDTO(String username){
        this.username = username;
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

    @Override
    public String getName() {
        return username;
    }
}

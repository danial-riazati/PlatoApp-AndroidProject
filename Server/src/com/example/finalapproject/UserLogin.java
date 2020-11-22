package com.example.finalapproject;

import java.io.Serializable;

public class UserLogin implements Serializable {
    private static final long serialVersionUID = -565170801344232014L;
    private String username;
    private String password;

    public UserLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

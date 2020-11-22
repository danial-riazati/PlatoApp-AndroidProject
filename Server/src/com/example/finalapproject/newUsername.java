package com.example.finalapproject;

import java.io.Serializable;

public class newUsername implements Serializable {
    private static final long serialVersionUID = -565170801344232014L;
    String theUsername;
    String newUsername;

    public newUsername(String theUsername, String newUsername) {
        this.theUsername = theUsername;
        this.newUsername = newUsername;
    }

    public String getTheUsername() {
        return theUsername;
    }

    public String getNewUsername() {
        return newUsername;
    }
}

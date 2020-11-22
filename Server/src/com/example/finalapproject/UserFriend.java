package com.example.finalapproject;

import java.io.Serializable;

public class UserFriend implements Serializable {
    private static final long serialVersionUID = -565170801344232014L;
    String theUser;
    String friendUsername;
    public UserFriend(String theUser,String friendUsername){
        this.theUser =theUser;
        this.friendUsername = friendUsername;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public String getTheUser() {
        return theUser;
    }
}

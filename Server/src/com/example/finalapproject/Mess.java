package com.example.finalapproject;

import com.example.finalapproject.User;

import java.awt.*;
import java.io.Serializable;

public class Mess implements Serializable {
    private static final long serialVersionUID = -565170801344232014L;
    String data;
    User sender;
    User receiver;
    String time;

    public Mess(String data, User sender, User receiver, String time) {
        this.data = data;
        this.sender = sender;
        this.time = time;
        this.receiver = receiver;
    }

    public String getData() {
        return data;
    }

    public String getTime() {
        return time;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }
}

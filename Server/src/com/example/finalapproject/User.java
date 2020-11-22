package com.example.finalapproject;

import java.io.Serializable;
import java.util.ArrayList;
public class User implements Serializable {
    private static final long serialVersionUID = -565170801344232014L;

    private String username = "";
    private String password = "";
    private ArrayList<Integer> scores;
    public ArrayList<User> friends;
    private ArrayList<Mess> messages;
    public boolean isLoggedIn = false;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.friends = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.scores = new ArrayList<>();
        this.scores.add(0);
        this.scores.add(0);
        this.scores.add(0);
    }

    public User(String username) {
        this.username = username;
        this.messages = new ArrayList<>();
        this.friends = new ArrayList<>();
    }

    public ArrayList<Mess> getMessages() {
        return messages;
    }

    public ArrayList<Mess> getSpecificMessages(User friend) {
        ArrayList<Mess> mess = new ArrayList<>();
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getSender().getUsername().equals(friend.getUsername()) || messages.get(i).getReceiver().getUsername().equals(friend.getUsername())) {
                mess.add(messages.get(i));
            }
        }
        return mess;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Integer> scores) {
        this.scores = scores;
    }
}

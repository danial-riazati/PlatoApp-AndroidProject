package com.example.finalapproject;

import java.io.Serializable;

public class OthelloPlayer implements Serializable {
    private static final long serialVersionUID = -565170801344232014L;
    User player;
    // int buttonID;
    public boolean isStarter = false;

    public OthelloPlayer(User player) {
        this.player = player;
    }

    /*public int getButtonID() {
        return buttonID;
    }

    public void setButtonID(int buttonID) {
        this.buttonID = buttonID;
    }*/

    public User getPlayer() {
        return player;
    }
}

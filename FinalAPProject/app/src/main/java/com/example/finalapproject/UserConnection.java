/*
package com.example.finalapproject;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class UserConnection extends Thread {
    DataOutputStream dos;
    ObjectInputStream ois;
    String tmp_user, tmp_pass;
    User user;

    UserConnection(String tmp_user, String tmp_pass) throws IOException {
        Socket socket = new Socket("172.20.10.2", 3000);
        ois = new ObjectInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
        this.tmp_user = tmp_user;
        this.tmp_pass = tmp_pass;
    }

    @Override
    public void run() {
        try {
            while (true) {
                dos.writeUTF(tmp_user);
                dos.writeUTF(tmp_pass);
                user = (User) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }



    public User getUser() {
        return user;
    }
}
*/

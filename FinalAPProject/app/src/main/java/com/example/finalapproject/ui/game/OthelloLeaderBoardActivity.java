package com.example.finalapproject.ui.game;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalapproject.R;
import com.example.finalapproject.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class OthelloLeaderBoardActivity extends AppCompatActivity {
    LinkedList<User> lb = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);
        Comparator<User> comparator = new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if (o1.getScores().get(1) < o2.getScores().get(1)) {
                    return 1;
                } else if (o1.getScores().get(1) > o2.getScores().get(1)) {
                    return -1;
                }
                return 0;
            }
        };


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("172.20.10.2", 6000);
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    dos.writeUTF("LeaderBoard");
                    oos.writeObject(null);
                    Object object = ois.readObject();
                    lb = (LinkedList<User>) object;

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Collections.sort(lb, comparator);
        RecyclerView recyclerView = findViewById(R.id.lb_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        OthelloLeaderboardAdapter othelloLeaderboardAdapter = new OthelloLeaderboardAdapter(lb);
        recyclerView.setAdapter(othelloLeaderboardAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }
}

package com.example.finalapproject.ui.game;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Chronometer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalapproject.R;
import com.example.finalapproject.XOPlayer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static com.example.finalapproject.LogIn.user;

public class XOLobby extends AppCompatActivity {
    Object object;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xo_lobby);
        Chronometer simpleChronometer = (Chronometer) findViewById(R.id.chronometer);
        simpleChronometer.start();
        final XOPlayer xoPlayer = new XOPlayer(user);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("172.20.10.2", 6000);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF("XOWaiter");
                    oos.writeObject(xoPlayer);
                    object = ois.readObject();
                    Intent intent = new Intent(getApplicationContext(),XOActivity.class);
                    intent.putExtra("opponent",(XOPlayer)object);
                    startActivity(intent);
                    socket.close();

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }
}

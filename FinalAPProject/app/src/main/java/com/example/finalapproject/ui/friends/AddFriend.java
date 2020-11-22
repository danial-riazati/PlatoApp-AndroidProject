package com.example.finalapproject.ui.friends;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;

import com.example.finalapproject.Main;
import com.example.finalapproject.R;
import com.example.finalapproject.User;
import com.example.finalapproject.UserFriend;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static com.example.finalapproject.LogIn.user;


public class AddFriend extends AppCompatActivity {
    Object object = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend);

        Button button = findViewById(R.id.add_submit);
        final TextView username = findViewById(R.id.friend_user);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click", "Submit and go to main page !");
                String userString;
                boolean founded = false;

                userString = username.getText().toString();
                final UserFriend userFriend = new UserFriend(user.getUsername(), userString);
                final Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Socket socket = new Socket("172.20.10.2", 6000);
                            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                            dos.writeUTF("AddFriend");
                            oos.writeObject(userFriend);
                            object = ois.readObject();
                            socket.close();

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
                if (object instanceof String) {
                    Toast.makeText(getApplicationContext(), (String) object, Toast.LENGTH_LONG).show();
                } else {
                    user = (User) object;
                    friendsFragment.fla.notifyDataSetChanged();
                    Intent intent = new Intent(getApplicationContext(), Main.class);
                    startActivity(intent);
                    finish();
                }


            }
        });
    }
}

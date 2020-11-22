package com.example.finalapproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static com.example.finalapproject.LogIn.user;

public class SignUp extends AppCompatActivity {
    String done = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        final EditText username = findViewById(R.id.signup_username);
        final EditText password = findViewById(R.id.signup_password);
        Button signUp_button = findViewById(R.id.final_signUp_button);
        signUp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().length() < 5) {
                    Toast.makeText(getApplicationContext(), "your password length is less than 5 characters , try a new one !", Toast.LENGTH_LONG).show();
                } else {
                    final User newUser = new User(username.getText().toString(), password.getText().toString());

                    final Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                Socket socket = new Socket("172.20.10.2", 6000);
                                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                                DataInputStream dis = new DataInputStream(socket.getInputStream());
                                dos.writeUTF("SignUp");
                                oos.writeObject(newUser);
                                done = dis.readUTF();
                                socket.close();

                            } catch (IOException e) {
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
                    if (done.equals("done")) {
                        Intent intent = new Intent(getApplicationContext(), Main.class);
                        user = newUser;
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "Your username is taken before , try a new one !", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });


    }

}

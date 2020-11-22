package com.example.finalapproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class LogIn extends AppCompatActivity {

    public static User user = null;
    String tmp_user, tmp_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        final Context context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        final Button login_button = findViewById(R.id.login_button);
        final Button signUp_button = findViewById(R.id.signUp_button);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tmp_user = username.getText().toString();
                tmp_pass = password.getText().toString();

                final Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Socket socket = new Socket("172.20.10.2", 6000);
                            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                            dos.writeUTF("LogIn");
                            dos.writeUTF(tmp_user);
                            dos.writeUTF(tmp_pass);
                           // oos.writeObject(new UserLogin(tmp_user,tmp_pass));
                            user = (User) ois.readObject();
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
                if (user == null) {
                    Toast.makeText(context, "your username or password is wrong", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(context, Main.class);
                    context.startActivity(intent);
                    finish();

                }


            }
        });

        signUp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SignUp.class);
                context.startActivity(intent);
            }
        });


    }
}

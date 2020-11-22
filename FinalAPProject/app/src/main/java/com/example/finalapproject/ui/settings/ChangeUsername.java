package com.example.finalapproject.ui.settings;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalapproject.R;
import com.example.finalapproject.User;
import com.example.finalapproject.UserFriend;
import com.example.finalapproject.newUsername;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static com.example.finalapproject.LogIn.user;

public class ChangeUsername extends AppCompatActivity {
    Object object;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_username);
        final EditText newUsernameEditText = findViewById(R.id.newUsername);
        final Button submit = findViewById(R.id.changeUser_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userString;
                userString = newUsernameEditText.getText().toString();
                if (userString.equals(user.getUsername())) {
                    Toast.makeText(getApplicationContext(), "Your new Username is equal to your last Username , change it !", Toast.LENGTH_LONG).show();
                } else {


                   // final newUsername newUsername = new newUsername(user.getUsername(), userString);
                    final Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                Socket socket = new Socket("172.20.10.2", 6000);
                                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                                dos.writeUTF("ChangeUsername");
                                dos.writeUTF(userString);
                                dos.writeUTF(user.getUsername());
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
                        finish();
                    }


                }


            }
        });

    }
}

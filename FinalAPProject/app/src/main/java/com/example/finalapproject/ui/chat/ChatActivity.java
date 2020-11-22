package com.example.finalapproject.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalapproject.Mess;
import com.example.finalapproject.R;
import com.example.finalapproject.User;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.finalapproject.LogIn.user;

public class ChatActivity extends AppCompatActivity {
   /* public void updateYourSelf() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("172.20.10.2", 6000);
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF("UpdateUser");
                    dos.writeUTF(user.getUsername());
                    boolean flag =false;
                    while (*//*user.isLoggedIn =*//* true) {
                        //flag = !flag;
                        //  thread.sleep(3000);
                        user = (User) ois.readObject();
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }*/
    MessageListAdapter mla;
    String time = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        final Intent intent2 = new Intent(this,ChatActivity.class);
        //updateYourSelf();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list);
        Intent intent = getIntent();
        final User friend = (User) intent.getSerializableExtra("UserChat");

        Button sendButton = findViewById(R.id.send_button);
        final EditText newMessage = findViewById(R.id.new_messege);
        final RecyclerView recyclerView = findViewById(R.id.recyclerview_message_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mla = new MessageListAdapter(getApplicationContext(), user.getSpecificMessages(friend));
        recyclerView.setAdapter(mla);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tmp = newMessage.getText().toString();
                Calendar now = Calendar.getInstance();
                time = Integer.toString(now.get(Calendar.HOUR_OF_DAY));
                time += ":";
                time += Integer.toString(now.get(Calendar.MINUTE));
                Mess mess = new Mess(tmp, user, friend, time);
                user.getMessages().add(mess);
                mla.notifyItemInserted(user.getSpecificMessages(friend).size());

                final Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            Socket socket = new Socket("172.20.10.2", 6000);
                            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                            dos.writeUTF("newMessage");
                            Mess outMess = new Mess(tmp, user, friend, time);
                            oos.writeObject(outMess);
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

                intent2.putExtra("UserChat",friend);
                startActivity(intent2);
                finish();


            }
        });

    }
}

package com.example.finalapproject.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalapproject.R;
import com.example.finalapproject.User;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static com.example.finalapproject.LogIn.user;

public class chatFragment extends Fragment {
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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chat,container,false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
       // updateYourSelf();
        RecyclerView recyclerView = view.findViewById(R.id.chat_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ChatListAdapter cla = new ChatListAdapter(user.friends);
        recyclerView.setAdapter(cla);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}

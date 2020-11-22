package com.example.finalapproject.ui.friends;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalapproject.Main;
import com.example.finalapproject.R;
import com.example.finalapproject.User;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static com.example.finalapproject.LogIn.user;

public class friendsFragment extends Fragment {
  /*  public void updateYourSelf() {
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
    static FriendListAdapter fla;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_friends,container,false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
       // updateYourSelf();
        RecyclerView recyclerView = view.findViewById(R.id.friend_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
         fla = new FriendListAdapter(user.friends);
        recyclerView.setAdapter(fla);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        final Intent intent = new Intent(getContext(), AddFriend.class);
        Button button = view.findViewById(R.id.add_friend_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click","Going to add page !");
                startActivity(intent);
            }
        });

    }
}

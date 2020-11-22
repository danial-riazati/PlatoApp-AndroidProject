package com.example.finalapproject.ui.chat;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalapproject.R;
import com.example.finalapproject.User;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter {
    List<User> chatList;
    public ChatListAdapter (List<User> cList) {
        this.chatList = cList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item,parent,false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final int p = position;
        ChatViewHolder fvh = (ChatViewHolder) holder;
        fvh.bind(chatList.get(position));
        fvh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                intent.putExtra("UserChat",chatList.get(p));
                v.getContext().startActivity(intent);
                Log.i("Click","Clicked successful");
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
    private static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        ImageView image;

        public ChatViewHolder (@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.friend_name);
            image = itemView.findViewById(R.id.friend_image);
        }
        void bind(User friend) {
            nameView.setText((friend.getUsername()));
        }
    }
}

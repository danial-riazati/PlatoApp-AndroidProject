package com.example.finalapproject.ui.friends;

import android.content.Intent;
import android.os.Parcelable;
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

public class FriendListAdapter extends RecyclerView.Adapter {
    List<User> friendsList;
    public FriendListAdapter(List<User> fList) {
        this.friendsList = fList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item,parent,false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final int p = position;
        FriendViewHolder fvh = (FriendViewHolder) holder;
        fvh.bind(friendsList.get(position));
        fvh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FriendDetails.class);
                intent.putExtra("UserFriend",  friendsList.get(position));
                v.getContext().startActivity(intent);
                Log.i("Click","Clicked successful");
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }
    private static class FriendViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        ImageView image;
        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.friend_name);
            image = itemView.findViewById(R.id.friend_image);
        }
        void bind(User friend) {
            nameView.setText((friend.getUsername()));
        }
    }
}



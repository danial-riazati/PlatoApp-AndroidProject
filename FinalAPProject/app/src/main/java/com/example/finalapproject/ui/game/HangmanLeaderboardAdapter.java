package com.example.finalapproject.ui.game;

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

class HangmanLeaderboardAdapter extends RecyclerView.Adapter {
    List<User> leaderboard;
    public HangmanLeaderboardAdapter(List<User> list) {
        this.leaderboard = list;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_item,parent,false);
        return new HangmanLeaderboardAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HangmanLeaderboardAdapter.UserViewHolder uvh = (HangmanLeaderboardAdapter.UserViewHolder) holder;
        uvh.bind(leaderboard.get(position));
    }

    @Override
    public int getItemCount() {
        return leaderboard.size();
    }
    private static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        TextView scoreView;
        ImageView image;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.user_name_lb);
            scoreView = itemView.findViewById(R.id.user_score_lb);
            image = itemView.findViewById(R.id.user_image_lb);
        }
        void bind(User user) {
            nameView.setText((user.getUsername()));
            scoreView.setText(Integer.toString(user.getScores().get(2)));
        }
    }
}


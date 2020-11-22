package com.example.finalapproject.ui.friends;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalapproject.R;
import com.example.finalapproject.User;

import java.util.Objects;

import static com.example.finalapproject.LogIn.user;

public class FriendDetails extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("UserFriend");
        TextView profileName = findViewById(R.id.pro_name);
        profileName.setText(user.getUsername());
        TextView score1 = findViewById(R.id.score1);
        TextView score2 = findViewById(R.id.score2);
        TextView score3 = findViewById(R.id.score3);
        score1.setText("Your score in XO : " + user.getScores().get(0));
        score2.setText("Your score in Hangman : " + user.getScores().get(1));
        score3.setText("Your score in Dots and Boxes : " + user.getScores().get(2));
    }
}

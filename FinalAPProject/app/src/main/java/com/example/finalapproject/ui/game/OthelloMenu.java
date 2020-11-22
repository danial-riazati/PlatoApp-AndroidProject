package com.example.finalapproject.ui.game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalapproject.R;

public class OthelloMenu extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_menu);
        TextView gameName = findViewById(R.id.game_name_menu);
        gameName.setText("Othello");
        Button casual = findViewById(R.id.game_casual);
        Button ranked = findViewById(R.id.game_ranked);
        Button leader = findViewById(R.id.game_lb);
        final Intent casualLobby = new Intent(this, OthelloCasualLobbyActivity.class);
        final Intent lb = new Intent(this, OthelloLeaderBoardActivity.class);
        final Intent rank = new Intent(this, OthelloLobby.class);
        casual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click", "Go to casual lobby !");
                startActivity(casualLobby);
            }
        });
        leader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click", "Go to leaderboard !");
                startActivity(lb);
            }
        });
        ranked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(rank);
            }
        });
    }

}

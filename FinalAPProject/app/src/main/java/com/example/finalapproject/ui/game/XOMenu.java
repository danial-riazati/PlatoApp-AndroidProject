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

public class XOMenu extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_menu);
        TextView gameName = findViewById(R.id.game_name_menu);
        gameName.setText("Tic Tac Toe");
        Button casual = findViewById(R.id.game_casual);
        Button ranked = findViewById(R.id.game_ranked);
        Button leader = findViewById(R.id.game_lb);
        final Intent casualLobby = new Intent(this, XOCasualLobbyActivity.class);
        final Intent lb = new Intent(this, XOLeaderBoardActivity.class);
        final Intent rank = new Intent(this, XOLobby.class);
        casual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click","Go to casual lobby !");
                startActivity(casualLobby);
            }
        });
        leader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Click","Go to leaderboard !");
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

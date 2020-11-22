package com.example.finalapproject.ui.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finalapproject.R;

public class gameFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_game,container,false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LinearLayout xo = view.findViewById(R.id.xo);
        LinearLayout hangman = view.findViewById(R.id.hangman);
        LinearLayout dotsdAndBoxes = view.findViewById(R.id.dotsandboxes);
        LinearLayout othello = view.findViewById(R.id.othello);
        othello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),OthelloMenu.class);
                startActivity(intent);
            }
        });
        xo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),XOMenu.class);
                startActivity(intent);
            }
        });
        hangman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),HangmanMenu.class);
                startActivity(intent);
            }
        });
        dotsdAndBoxes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DotsAndBoxesActivity.class);
                startActivity(intent);
            }
        });
    }
}

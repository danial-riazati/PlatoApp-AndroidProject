package com.example.finalapproject.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.finalapproject.R;

import static com.example.finalapproject.LogIn.user;

public class profileFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView profileName = view.findViewById(R.id.pro_name);
        profileName.setText(user.getUsername());
        TextView score1 = view.findViewById(R.id.score1);
        TextView score2 = view.findViewById(R.id.score2);
        TextView score3 = view.findViewById(R.id.score3);
        score1.setText("Your score in XO : " + user.getScores().get(0));
        score2.setText("Your score in Hangman : " + user.getScores().get(1));
        score3.setText("Your score in Dots and Boxes : " + user.getScores().get(2));
    }
}

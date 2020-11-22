package com.example.finalapproject.ui.settings;

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
import androidx.fragment.app.FragmentTransaction;

import com.example.finalapproject.AboutUs;
import com.example.finalapproject.LogIn;
import com.example.finalapproject.R;
import com.example.finalapproject.ui.profile.profileFragment;

import static com.example.finalapproject.LogIn.user;

public class settingsFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button logout = (Button) view.findViewById(R.id.log_out_button);
        Button aboutUs = (Button) view.findViewById(R.id.about_us_button);
        Button changeUsername = (Button) view.findViewById(R.id.change_username);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AboutUs.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.isLoggedIn = false;
                Intent intent = new Intent(getContext(), LogIn.class);
                startActivity(intent);
            }
        });
        changeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangeUsername.class);
                startActivity(intent);
            }
        });
    }
}


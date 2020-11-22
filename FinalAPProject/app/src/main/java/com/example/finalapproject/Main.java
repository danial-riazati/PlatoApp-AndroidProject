package com.example.finalapproject;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.finalapproject.ui.chat.chatFragment;
import com.example.finalapproject.ui.settings.settingsFragment;
import com.example.finalapproject.ui.game.gameFragment;
import com.example.finalapproject.ui.profile.profileFragment;
import com.example.finalapproject.ui.friends.friendsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static com.example.finalapproject.LogIn.user;

public class Main extends AppCompatActivity {



    public void openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    BottomNavigationView bottomNavigation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
      // updateYourSelf();

        bottomNavigation = findViewById(R.id.button_nav);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_settings:
                        settingsFragment homeFragment = new settingsFragment();
                        openFragment(homeFragment);
                        break;
                    case R.id.nav_chat:
                        chatFragment chatFragment = new chatFragment();
                        openFragment(chatFragment);
                        break;
                    case R.id.nav_friend:
                        friendsFragment friendsFragment = new friendsFragment();
                        openFragment(friendsFragment);
                        break;
                    case R.id.nav_game:
                        gameFragment gameFragment = new gameFragment();
                        openFragment(gameFragment);
                        break;
                    case R.id.nav_profile:
                        profileFragment profileFragment = new profileFragment();
                        openFragment(profileFragment);
                        break;
                }
                return true;
            }

        });
    }

}

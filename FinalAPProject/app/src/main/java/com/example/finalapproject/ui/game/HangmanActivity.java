package com.example.finalapproject.ui.game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalapproject.HangmanPlayer;
import com.example.finalapproject.OthelloPlayer;
import com.example.finalapproject.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static com.example.finalapproject.LogIn.user;

public class HangmanActivity extends AppCompatActivity {
    static String answer;
    static int attempt;
    HangmanPlayer opponent;
    boolean isQuestioner = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hangman_word_input);
        final EditText editText = findViewById(R.id.first_word);
        Button button = findViewById(R.id.hangman_submit);
        //TextView textView = findViewById(R.id.round_number);
        final Intent intent = getIntent();
        opponent = (HangmanPlayer) intent.getSerializableExtra("opponent");
        final Intent intent2 = new Intent(this, HangmanActivity2.class);
        final Intent intent3 = new Intent(this, HangmanActivity2.class);
        if (opponent.isStarter) {
            isQuestioner = false;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket socket = new Socket("172.20.10.2", 6000);
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        DataInputStream dis = new DataInputStream(socket.getInputStream());
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        dos.writeUTF("HangmanGamerReceiver");
                        dos.writeUTF(user.getUsername());
                        boolean flag = false;
                        String s = (String) ois.readObject();
                        intent3.putExtra("opponent", (HangmanPlayer) opponent);
                        startActivity(intent3);

                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            });
            thread.start();

        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isQuestioner) {
                    answer = editText.getText().toString();
                    attempt = answer.length();
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Socket socket = new Socket("172.20.10.2", 6000);
                                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                                dos.writeUTF("HangmanQuestioner");
                                oos.writeObject(null);
                                dos.writeUTF(opponent.getPlayer().getUsername());
                                intent2.putExtra("opponent", (HangmanPlayer) opponent);
                                startActivity(intent2);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    });
                    thread.start();

                } else {
                    Toast.makeText(getApplicationContext(), "You are not Questioner!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}

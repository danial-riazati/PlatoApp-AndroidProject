package com.example.finalapproject.ui.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalapproject.HangmanPlayer;
import com.example.finalapproject.Main;
import com.example.finalapproject.OthelloPlayer;
import com.example.finalapproject.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static com.example.finalapproject.LogIn.user;

public class HangmanActivity2 extends AppCompatActivity {
    HangmanPlayer opponent;
    static int p1Score = 0;
    static int p2Score = 0;
    boolean isQuestioner = false;
    char guess;
    StringBuilder showInView = new StringBuilder("");
    TextView attemptView;
    TextView ans;
    Button button;
    Intent intent2;
    int counter;
    boolean flag =false;
    String str = HangmanActivity.answer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        opponent = (HangmanPlayer) intent.getSerializableExtra("opponent");
        if (!opponent.isStarter) {
            isQuestioner = true;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket socket = new Socket("172.20.10.2", 6000);
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        DataInputStream dis = new DataInputStream(socket.getInputStream());
                        dos.writeUTF("HangmanGamerReceiver");
                        oos.writeObject(user);
                        while (!flag) {
                            String a = dis.readUTF();
                            char b = a.charAt(0);
                            if (str.indexOf(b) == -1) {
                                counter++;
                            } else {
                                for (int i = 0; i < str.length(); i++) {
                                    if (str.charAt(i) == b) {
                                        showInView.setCharAt(i * 2, b);
                                    }
                                }
                            }
                            ans.setText(showInView);
                            attemptView.setText("Your Chances : " + Integer.toString(HangmanActivity.attempt - counter));


                            if (showInView.indexOf("_") == -1) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "you win", Toast.LENGTH_LONG).show();
                                    }
                                });
                                intent2 = new Intent(getApplicationContext(), Main.class);
                                startActivity(intent2);
                                flag=true;
                            } else if (counter == HangmanActivity.attempt) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "you loose", Toast.LENGTH_LONG).show();
                                    }
                                });
                                intent2 = new Intent(getApplicationContext(), Main.class);
                                startActivity(intent2);
                                flag=true;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hangman_guess_word);
        attemptView = findViewById(R.id.remain_hangman);
        ans = findViewById(R.id.ans_hangman);
        button = findViewById(R.id.submit_word);
        counter = 0;
        for (int q = 0; q < HangmanActivity.attempt * 2; q++) {
            if (q % 2 == 0)
                showInView.append("_");
            else
                showInView.append(" ");
        }
        attemptView.setText("Your Chances : " + Integer.toString(HangmanActivity.attempt - counter));
        ans.setText(showInView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.guess_hangman);
                if (!isQuestioner) {
                    try {

                        guess = editText.getText().charAt(0);

                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Socket socket = new Socket("172.20.10.2", 6000);
                                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                                    dos.writeUTF("HangmanGameSender");
                                    oos.writeObject(user);
                                    dos.writeUTF(String.valueOf(guess));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        thread.start();
                        thread.join();


                        if (str.indexOf(guess) == -1) {
                            counter++;
                        } else {
                            for (int i = 0; i < str.length(); i++) {
                                if (str.charAt(i) == guess) {
                                    showInView.setCharAt(i * 2, guess);
                                }
                            }
                        }
                        ans.setText(showInView);
                        attemptView.setText("Your Chances : " + Integer.toString(HangmanActivity.attempt - counter));


                        if (showInView.indexOf("_") == -1) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "you win", Toast.LENGTH_LONG).show();
                                }
                            });
                            intent2 = new Intent(getApplicationContext(), Main.class);
                            startActivity(intent2);
                        } else if (counter == HangmanActivity.attempt) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "you loose", Toast.LENGTH_LONG).show();
                                }
                            });
                            intent2 = new Intent(getApplicationContext(), Main.class);
                            startActivity(intent2);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "you are Questioner", Toast.LENGTH_LONG).show();
                }

            }


        });
    }
}


   /* private void alertDialog(String str) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HangmanSecondActivity.this);
        final View customLayout = getLayoutInflater().inflate(R.layout.alert_dialog_layout, null);
        alertDialog.setView(customLayout);
        alertDialog.setTitle(str);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Hn.this, "Ok", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }*/




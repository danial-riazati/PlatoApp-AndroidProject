package com.example.finalapproject.ui.game;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalapproject.Main;
import com.example.finalapproject.R;
import com.example.finalapproject.XOPlayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.example.finalapproject.LogIn.user;

public class XOActivity extends AppCompatActivity implements View.OnClickListener {
    public Button[][] buttons = new Button[3][3];
    private int round;
    private boolean p1Turn = true;
    XOPlayer opponent;
    Object object;
    String myCharacter = "X";
    String OppCharacter = "O";
    Intent intent1;
    boolean flag = false;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        intent1 = new Intent(this, Main.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xo_layout);
        Intent intent = getIntent();
        opponent = (XOPlayer) intent.getSerializableExtra("opponent");
        Log.i("opponent", opponent.getPlayer().getUsername());
        if (opponent.isStarter) {
            p1Turn = false;
            myCharacter = "O";
            OppCharacter = "X";
        }
        round = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String bId = "xo_button_" + i + j;
                int res = getResources().getIdentifier(bId, "id", getPackageName());
                buttons[i][j] = findViewById(res);
                buttons[i][j].setOnClickListener(this);
            }
        }
        final Thread ReceiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("172.20.10.2", 6000);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    dos.writeUTF("XOGamerReceiver");
                    oos.writeObject(user);
                    while (!flag) {
                        final int x = Integer.parseInt(dis.readUTF());
                        final int y = Integer.parseInt(dis.readUTF());
                        Log.i("id", String.valueOf(x));
                        Log.i("id", String.valueOf(y));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                buttons[x][y].setText(OppCharacter);
                                round++;
                            }
                        });
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (checkForWin()) {


                                    String p1w = "Opponent Won !";
                                    Toast.makeText(getApplicationContext(), p1w, Toast.LENGTH_LONG).show();
                                    completeBoard(p1w);
                                    startActivity(intent1);
                                    flag = true;

                                }
                                if (round == 9) {

                                    String draw = "Draw !";
                                    Toast.makeText(getApplicationContext(), draw, Toast.LENGTH_LONG).show();
                                    completeBoard(draw);
                                    startActivity(intent1);
                                    flag =true;
                                }
                            }
                        });

                        p1Turn = !p1Turn;


                    }
                    dis.close();
                    dos.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        ReceiveThread.start();

    }


    @Override
    public void onClick(View v) {
        int x = 0, y = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String bId = "xo_button_" + i + j;
                int res = getResources().getIdentifier(bId, "id", getPackageName());
                if (v.getId() == res) {
                    x = i;
                    y = j;
                    Log.i("Click", "X , Y founded !");
                }
            }
        }
        final int finalX = x, finalY = y;
        if (!((Button) v).getText().equals("")) {
            return;
        }
        if (p1Turn) {
            ((Button) v).setText(myCharacter);

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket socket = new Socket("172.20.10.2", 6000);
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        dos.writeUTF("XOGamerSender");
                        Log.i("id", String.valueOf(finalX));
                        Log.i("id", String.valueOf(finalY));
                        dos.writeUTF(String.valueOf(finalX));
                        dos.writeUTF(String.valueOf(finalY));
                        dos.writeUTF(opponent.getPlayer().getUsername());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            round++;
            if (checkForWin()) {

                String p2w = "You Won !";
                int score = user.scores.get(0);
                score++;
                user.scores.remove(0);
                user.scores.add(0, score);
                Thread thread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Socket socket = new Socket("172.20.10.2", 6000);
                            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                            dos.writeUTF("scoreChanged");
                            oos.writeObject(user);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
                thread1.start();
                Toast.makeText(this, p2w, Toast.LENGTH_LONG).show();
                completeBoard(p2w);
                startActivity(intent1);
            }
            if (round == 9) {
                String draw = "Draw !";
                Toast.makeText(this, draw, Toast.LENGTH_LONG).show();
                completeBoard(draw);
                startActivity(intent1);
            }
            p1Turn = !p1Turn;


        } else {
            Toast.makeText(getApplicationContext(), "It's not your turn !", Toast.LENGTH_LONG).show();
        }


    }

    boolean checkForWin() {
        String[][] str = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                str[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (str[i][0].equals(str[i][1]) && str[i][0].equals(str[i][2])
                    && !str[i][0].equals("") && !str[2][0].equals("-")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (str[0][i].equals(str[1][i]) && str[0][i].equals(str[2][i])
                    && !str[0][i].equals("") && !str[2][0].equals("-")) {
                return true;
            }
        }
        if (str[0][0].equals(str[1][1]) && str[0][0].equals(str[2][2])
                && !str[0][0].equals("") && !str[2][0].equals("-"))
            return true;
        if (str[2][0].equals(str[1][1]) && str[2][0].equals(str[0][2])
                && !str[2][0].equals("") && !str[2][0].equals("-"))
            return true;
        return false;
    }

    void completeBoard(String str) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("-");
            }
        }
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(XOActivity.this);
        final View customLayout = getLayoutInflater().inflate(R.layout.alert_dialog_layout, null);
        alertDialog.setView(customLayout);
        alertDialog.setTitle(str);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(XOActivity.this, "Ok", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }
}

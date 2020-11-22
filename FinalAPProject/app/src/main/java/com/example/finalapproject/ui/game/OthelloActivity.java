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
import com.example.finalapproject.OthelloPlayer;
import com.example.finalapproject.R;
import com.example.finalapproject.XOPlayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static com.example.finalapproject.LogIn.user;


public class OthelloActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[8][8];
    private char[][] board = new char[8][8];
    private int p1Score = 0;
    private int p2Score = 0;
    private int round;
    boolean p1Turn = true;
    TextView p1ScoreView;
    TextView p2ScoreView;
    char myCharacter = 'O';
    char OppCharacter = 'I';
    Intent intent1;
    OthelloPlayer opponent;
    boolean flag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        intent1 = new Intent(getApplicationContext(), Main.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.othello_layout);
        final Intent intent = getIntent();
        opponent = (OthelloPlayer) intent.getSerializableExtra("opponent");
        if (opponent.isStarter) {
            p1Turn = false;
            myCharacter = 'I';
            OppCharacter = 'O';
        }
        round = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String bId = "othello_button_" + i + j;
                int res = getResources().getIdentifier(bId, "id", getPackageName());
                buttons[i][j] = findViewById(res);
                buttons[i][j].setOnClickListener(this);
            }
        }
        p1ScoreView = findViewById(R.id.p1_score_othello);
        p2ScoreView = findViewById(R.id.p2_score_othello);
        buttons[3][4].setText("I");
        buttons[4][3].setText("I");
        buttons[3][3].setText("O");
        buttons[4][4].setText("O");
        board[3][4] = board[4][3] = 'I';
        board[4][4] = board[3][3] = 'O';

        final Thread ReceiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("172.20.10.2", 6000);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    dos.writeUTF("OthelloGamerReceiver");
                    oos.writeObject(user);
                    while (!flag) {
                        board = (char[][]) ois.readObject();
                        Log.i("id", String.valueOf(board.length));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                p1Score = 0;
                                p2Score = 0;
                                for (int i = 0; i < 8; i++) {
                                    for (int j = 0; j < 8; j++) {
                                        buttons[i][j].setText(Character.toString(board[i][j]));
                                        if (board[i][j] == 'O') {
                                            p1Score++;
                                        } else if (board[i][j] == 'I') {
                                            p2Score++;
                                        }
                                    }
                                }
                                round++;
                            }
                        });
                        /*runOnUiThread(new Runnable() {
                            @Override
                            public void run() {*/
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                p1ScoreView.setText("Player 1 : " + Integer.toString(p1Score));
                                p2ScoreView.setText("Player 2 : " + Integer.toString(p2Score));
                            }
                        });
                        if (round == 60) {
                            if (p1Score > p2Score) {
                                completeBoard("O Wins !");
                            } else if (p1Score < p2Score) {
                                completeBoard("I Wins !");
                            } else {
                                completeBoard("Draw !");
                            }
                            startActivity(intent);
                            flag = true;
                        }


                        // }

                        //  });

                        p1Turn = !p1Turn;


                    }
                    dis.close();
                    dos.close();
                    socket.close();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
        ReceiveThread.start();

    }

    @Override
    public void onClick(View v) {
        if (((Button) v).getText().equals('O') || ((Button) v).getText().equals('I')) {
            Log.i("Click", "Return !");
            return;
        }
        int x = 0, y = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String bId = "othello_button_" + i + j;
                int res = getResources().getIdentifier(bId, "id", getPackageName());
                if (v.getId() == res) {
                    x = i;
                    y = j;
                    Log.i("Click", "X , Y founded !");
                }
            }
        }
        boolean a;
        if (myCharacter == 'O') {
            a = p1Turn;
        } else {
            a = !p1Turn;
        }
        if (p1Turn && reverse(x, y, board, a)) {
            board[x][y] = myCharacter;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket socket = new Socket("172.20.10.2", 6000);
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        dos.writeUTF("OthelloGameSender");
                      /*  Log.i("id", String.valueOf(finalX));
                        Log.i("id", String.valueOf(finalY));*/
                        oos.writeObject(board);
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

        } else {
            return;
        }
        round++;
        p1Turn = !p1Turn;
        p1Score = 0;
        p2Score = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j].setText(Character.toString(board[i][j]));
                if (board[i][j] == 'O') {
                    p1Score++;
                } else if (board[i][j] == 'I') {
                    p2Score++;
                }
            }
        }
        if (round == 60) {
            if (p1Score > p2Score) {
                completeBoard("O Wins !");
            } else if (p1Score < p2Score) {
                completeBoard("I Wins !");
            } else {
                completeBoard("Draw !");
            }
            if (opponent.isStarter && (p2Score > p1Score) ||!opponent.isStarter && (p2Score < p1Score) ){
                int score = user.scores.get(1);
                score++;
                user.scores.remove(1);
                user.scores.add(1, score);
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
            }
            startActivity(intent1);
        }
        p1ScoreView.setText("Player 1 : " + Integer.toString(p1Score));
        p2ScoreView.setText("Player 2 : " + Integer.toString(p2Score));

    }

    static boolean reverse(int x, int y, char[][] b, boolean o) {
        boolean rev = false, isOpp = false;
        if (o) {
            for (int i = x + 1; i < 8; i++) {
                if (b[i][y] == 'I') {
                    isOpp = true;
                } else if (b[i][y] == 'O' && isOpp) {
                    for (int j = i; j > x; j--) {
                        b[j][y] = 'O';
                    }
                    rev = true;
                    break;
                } else {
                    isOpp = false;
                    break;
                }
            }
            for (int i = x - 1; i > -1; i--) {
                if (b[i][y] == 'I') {
                    isOpp = true;
                } else if (b[i][y] == 'O' && isOpp) {
                    for (int j = i; j < x; j++) {
                        b[j][y] = 'O';
                    }
                    rev = true;
                    break;
                } else {
                    isOpp = false;
                    break;
                }
            }
            for (int i = y + 1; i < 8; i++) {
                if (b[x][i] == 'I') {
                    isOpp = true;
                } else if (b[x][i] == 'O' && isOpp) {
                    for (int j = i; j > y; j--) {
                        b[x][j] = 'O';
                    }
                    rev = true;
                    break;
                } else {
                    isOpp = false;
                    break;
                }
            }
            for (int i = y - 1; i > -1; i--) {
                if (b[x][i] == 'I') {
                    isOpp = true;
                } else if (b[x][i] == 'O' && isOpp) {
                    for (int j = i; j < y; j++) {
                        b[x][j] = 'O';
                    }
                    rev = true;
                    break;
                } else {
                    isOpp = false;
                    break;
                }
            }
            for (int i = x + 1, j = y + 1; i < 8 && j < 8; j++, i++) {
                if (b[i][j] == 'I') {
                    isOpp = true;
                } else if (b[i][j] == 'O' && isOpp) {
                    for (int k = i, t = j; k > x && t > y; k--, t--) {
                        b[k][t] = 'O';
                    }
                    rev = true;
                    break;
                } else {
                    isOpp = false;
                    break;
                }
            }
            for (int i = x - 1, j = y - 1; i > -1 && j > -1; j--, i--) {
                if (b[i][j] == 'I') {
                    isOpp = true;
                } else if (b[i][j] == 'O' && isOpp) {
                    for (int k = i, t = j; k < x && t < y; k++, t++) {
                        b[k][t] = 'O';
                    }
                    rev = true;
                    break;
                } else {
                    isOpp = false;
                    break;
                }
            }
            for (int i = x + 1, j = y - 1; i < 8 && j > -1; j--, i++) {
                if (b[i][j] == 'I') {
                    isOpp = true;
                } else if (b[i][j] == 'O' && isOpp) {
                    for (int k = i, t = j; k > x && t < y; k--, t++) {
                        b[k][t] = 'O';
                    }
                    rev = true;
                    break;
                } else {
                    isOpp = false;
                    break;
                }
            }
            for (int i = x - 1, j = y + 1; i > -1 && j < 8; j++, i--) {
                if (b[i][j] == 'I') {
                    isOpp = true;
                } else if (b[i][j] == 'O' && isOpp) {
                    for (int k = i, t = j; k < x && t > y; k++, t--) {
                        b[k][t] = 'O';
                    }
                    rev = true;
                    break;
                } else {
                    isOpp = false;
                    break;
                }
            }
        } else {
            for (int i = x + 1; i < 8; i++) {
                if (b[i][y] == 'O') {
                    isOpp = true;
                } else if (b[i][y] == 'I' && isOpp) {
                    for (int j = i; j > x; j--) {
                        b[j][y] = 'I';
                    }
                    rev = true;
                    break;
                } else {
                    isOpp = false;
                    break;
                }
            }
            for (int i = x - 1; i > -1; i--) {
                if (b[i][y] == 'O') {
                    isOpp = true;
                } else if (b[i][y] == 'I' && isOpp) {
                    for (int j = i; j < x; j++) {
                        b[j][y] = 'I';
                    }
                    rev = true;
                    break;
                } else {
                    isOpp = false;
                    break;
                }
            }
            for (int i = y + 1; i < 8; i++) {
                if (b[x][i] == 'O') {
                    isOpp = true;
                } else if (b[x][i] == 'I' && isOpp) {
                    for (int j = i; j > y; j--) {
                        b[x][j] = 'I';
                    }
                    rev = true;
                    break;
                } else {
                    isOpp = false;
                    break;
                }
            }
            for (int i = y - 1; i > -1; i--) {
                if (b[x][i] == 'O') {
                    isOpp = true;
                } else if (b[x][i] == 'I' && isOpp) {
                    for (int j = i; j < y; j++) {
                        b[x][j] = 'I';
                    }
                    rev = true;
                    break;
                } else {
                    isOpp = false;
                    break;
                }
            }
            for (int i = x + 1, j = y + 1; i < 8 && j < 8; j++, i++) {
                if (b[i][j] == 'O') {
                    isOpp = true;
                } else if (b[i][j] == 'I' && isOpp) {
                    for (int k = i, t = j; k > x && t > y; k--, t--) {
                        b[k][t] = 'I';
                    }
                    rev = true;
                    break;
                } else {
                    isOpp = false;
                    break;
                }
            }
            for (int i = x - 1, j = y - 1; i > -1 && j > -1; j--, i--) {
                if (b[i][j] == 'O') {
                    isOpp = true;
                } else if (b[i][j] == 'I' && isOpp) {
                    for (int k = i, t = j; k < x && t < y; k++, t++) {
                        b[k][t] = 'I';
                    }
                    rev = true;
                    break;
                } else {
                    isOpp = false;
                    break;
                }
            }
            for (int i = x + 1, j = y - 1; i < 8 && j > -1; j--, i++) {
                if (b[i][j] == 'O') {
                    isOpp = true;
                } else if (b[i][j] == 'I' && isOpp) {
                    for (int k = i, t = j; k > x && t < y; k--, t++) {
                        b[k][t] = 'I';
                    }
                    rev = true;
                    break;
                } else {
                    isOpp = false;
                    break;
                }
            }
            for (int i = x - 1, j = y + 1; i > -1 && j < 8; j++, i--) {
                if (b[i][j] == 'O') {
                    isOpp = true;
                } else if (b[i][j] == 'I' && isOpp) {
                    for (int k = i, t = j; k < x && t > y; k++, t--) {
                        b[k][t] = 'I';
                    }
                    rev = true;
                    break;
                } else {
                    isOpp = false;
                    break;
                }
            }
        }
        return rev;
    }

    void completeBoard(String str) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(OthelloActivity.this);
        final View customLayout = getLayoutInflater().inflate(R.layout.alert_dialog_layout, null);
        alertDialog.setView(customLayout);
        alertDialog.setTitle(str);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(OthelloActivity.this, "Ok", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }
}

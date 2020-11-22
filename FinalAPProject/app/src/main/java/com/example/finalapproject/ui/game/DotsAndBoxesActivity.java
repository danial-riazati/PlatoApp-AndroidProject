package com.example.finalapproject.ui.game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.finalapproject.R;

public class DotsAndBoxesActivity extends AppCompatActivity {
    ImageView[][] images = new ImageView[13][13];
    boolean[][] board = new boolean[13][13];
    boolean p1turn = true;
    int round = 0;
    int p1score = 0;
    int p2score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridLayout gridLayout = findViewById(R.id.gridlayout);
        for(int i = 0 ; i < 13 ;i++){
            for(int j = 0; j < 13; j++){
                if((j % 2 == 0) && (i % 2 == 0)) {
                    images[i][j] = new ImageView(this);
                    images[i][j].setImageResource(R.drawable.o);
                    gridLayout.addView(images[i][j]);
                }
                if((j % 2 == 1)&& (i % 2 == 0)){
                    images[i][j] = new ImageView(this);
                    images[i][j].setImageResource(R.drawable.d);
                    final int p = i;
                    final int q = j;
                    images[i][j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(board[p][q])
                                return;
                            if(p1turn) {
                                ((ImageView) view).setColorFilter(Color.RED);
                                board[p][q] = true;
                                if(p > 1 && p < 11) {
                                    if((board[p - 2][q]) && (board[p - 1][q - 1]) && (board[p - 1][q + 1])) {
                                        images[p - 1][q].setColorFilter(Color.RED);
                                        p1score++;
                                        round++;
                                    }
                                    if((board[p + 2][q]) && (board[p + 1][q - 1]) && (board[p + 1][q + 1])) {
                                        images[p + 1][q].setColorFilter(Color.RED);
                                        p1score++;
                                        round++;
                                    }
                                } else if(p >= 11) {
                                    if((board[p - 2][q]) && (board[p - 1][q - 1]) && (board[p - 1][q + 1])) {
                                        images[p - 1][q].setColorFilter(Color.RED);
                                        p1score++;
                                        round++;
                                    }
                                } else {
                                    if((board[p + 2][q]) && (board[p + 1][q - 1]) && (board[p + 1][q + 1])) {
                                        images[p + 1][q].setColorFilter(Color.RED);
                                        p1score++;
                                        round++;
                                    }
                                }
                                p1turn = false;
                            }
                            else {
                                ((ImageView) view).setColorFilter(Color.BLUE);
                                board[p][q] = true;
                                if(p > 1 && p < 11) {
                                    if((board[p - 2][q]) && (board[p - 1][q - 1]) && (board[p - 1][q + 1])) {
                                        images[p - 1][q].setColorFilter(Color.BLUE);
                                        p2score++;
                                        round++;
                                    }
                                    if((board[p + 2][q]) && (board[p + 1][q - 1]) && (board[p + 1][q + 1])) {
                                        images[p + 1][q].setColorFilter(Color.BLUE);
                                        p2score++;
                                        round++;
                                    }
                                } else if(p >= 11) {
                                    if((board[p - 2][q]) && (board[p - 1][q - 1]) && (board[p - 1][q + 1])) {
                                        images[p - 1][q].setColorFilter(Color.BLUE);
                                        p2score++;
                                        round++;
                                    }
                                } else {
                                    if((board[p + 2][q]) && (board[p + 1][q - 1]) && (board[p + 1][q + 1])) {
                                        images[p + 1][q].setColorFilter(Color.BLUE);
                                        p2score++;
                                        round++;
                                    }
                                }
                                p1turn = true;
                            }
                            if(round == 36) {
                                if(p1score > p2score) {
                                    completeBoard("P1 Won !");
                                } else if(p1score < p2score) {
                                    completeBoard("P2 Won !");
                                } else {
                                    completeBoard("Draw !");
                                }
                            }
                        }
                    });
                    gridLayout.addView(images[i][j]);

                }
                if((j % 2 == 0)&& (i % 2 == 1)){
                    images[i][j] = new ImageView(this);
                    images[i][j].setImageResource(R.drawable.d2);
                    final int p = i;
                    final int q = j;
                    images[i][j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(board[p][q])
                                return;
                            if(p1turn) {
                                ((ImageView) view).setColorFilter(Color.RED);
                                board[p][q] = true;
                                if(q > 1 && q < 11) {
                                    if((board[p][q - 2]) && (board[p - 1][q - 1]) && (board[p + 1][q - 1])) {
                                        images[p][q - 1].setColorFilter(Color.RED);
                                        p1score++;
                                        round++;
                                    }
                                    if((board[p][q + 2]) && (board[p - 1][q + 1]) && (board[p + 1][q + 1])) {
                                        images[p][q + 1].setColorFilter(Color.RED);
                                        p1score++;
                                        round++;
                                    }
                                } else if(q >= 11) {
                                    if((board[p][q - 2]) && (board[p - 1][q - 1]) && (board[p + 1][q - 1])) {
                                        images[p][q - 1].setColorFilter(Color.RED);
                                        p1score++;
                                        round++;
                                    }
                                } else {
                                    if((board[p][q + 2]) && (board[p - 1][q + 1]) && (board[p + 1][q + 1])) {
                                        images[p][q + 1].setColorFilter(Color.RED);
                                        p1score++;
                                        round++;
                                    }
                                }
                                p1turn = false;
                            }
                            else {
                                ((ImageView) view).setColorFilter(Color.BLUE);
                                board[p][q] = true;
                                if(q > 1 && q < 11) {
                                    if((board[p][q - 2]) && (board[p - 1][q - 1]) && (board[p + 1][q - 1])) {
                                        images[p][q - 1].setColorFilter(Color.BLUE);
                                        p2score++;
                                        round++;
                                    }
                                    if((board[p][q + 2]) && (board[p - 1][q + 1]) && (board[p + 1][q + 1])) {
                                        images[p][q + 1].setColorFilter(Color.BLUE);
                                        p2score++;
                                        round++;
                                    }
                                } else if(q >= 11) {
                                    if((board[p][q - 2]) && (board[p - 1][q - 1]) && (board[p + 1][q - 1])) {
                                        images[p][q - 1].setColorFilter(Color.BLUE);
                                        p2score++;
                                        round++;
                                    }
                                } else {
                                    if((board[p][q + 2]) && (board[p - 1][q + 1]) && (board[p + 1][q + 1])) {
                                        images[p][q + 1].setColorFilter(Color.BLUE);
                                        p2score++;
                                        round++;
                                    }
                                }
                                p1turn = true;
                            }
                            if(round == 36) {
                                if(p1score > p2score) {
                                    completeBoard("P1 Won !");
                                } else if(p1score < p2score) {
                                    completeBoard("P2 Won !");
                                } else {
                                    completeBoard("Draw !");
                                }
                            }
                        }
                    });
                    gridLayout.addView(images[i][j]);

                }
                if((j %2 ==1 ) && (i % 2 ==1)){
                    images[i][j] = new ImageView(this);
                    images[i][j].setImageResource(R.drawable.ba);
                    gridLayout.addView(images[i][j]);
                }

            }
        }

    }
    void completeBoard(String str) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DotsAndBoxesActivity.this);
        final View customLayout = getLayoutInflater().inflate(R.layout.alert_dialog_layout, null);
        alertDialog.setView(customLayout);
        alertDialog.setTitle(str);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DotsAndBoxesActivity.this,"Ok",Toast.LENGTH_SHORT).show();

            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

}

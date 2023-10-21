package com.example.tictactoe;

import android.animation.AnimatorSet;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import com.example.tictactoe.R;

public class GameActivity extends AppCompatActivity {

    private final TextView[] textViews = new TextView[9];
    private TextView playerOneTextView;
    private TextView playerTwoTextView;

    // Variables for game logic
    private final char[] board = new char[9];
    private char currentPlayer = 'X';
    private boolean gameEnded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        MultiDex.install(this);

        //hiding toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize UI elements
        textViews[0] = findViewById(R.id.BoxOne);
        textViews[1] = findViewById(R.id.BoxTwo);
        textViews[2] = findViewById(R.id.BoxThree);
        textViews[3] = findViewById(R.id.BoxFour);
        textViews[4] = findViewById(R.id.BoxFive);
        textViews[5] = findViewById(R.id.BoxSix);
        textViews[6] = findViewById(R.id.BoxSeven);
        textViews[7] = findViewById(R.id.BoxEight);
        textViews[8] = findViewById(R.id.BoxNine);


        LinearLayout linearLayout;
        linearLayout = findViewById(R.id.layoutBottom);

        playerOneTextView = findViewById(R.id.playerOne);
        playerTwoTextView = findViewById(R.id.playerTwo);

        Button restartButton = findViewById(R.id.RestartNow);
        Button MainMenu = findViewById(R.id.MainMenu);

        //declaration of the arrow that will animate after the every click of player

//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in_and_translate);
//        linearLayout.startAnimation(animation);


        MainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_out_to_right));
                for (int i = 0; i < 9; i++) {
                    textViews[i].setText("");
                    board[i] = ' ';
                }
                startAgainGame();
            }
        });
        // Set player names in the TextViews
        String player1Name = getIntent().getStringExtra("player1");
        String player2Name = getIntent().getStringExtra("player2");

        playerOneTextView.setText(player1Name);
        playerTwoTextView.setText(player2Name);

        // Initialize the game board
        for (int i = 0; i < 9; i++) {
            board[i] = ' ';
        }

        // Set click listeners for each text view
        for (int i = 0; i < 9; i++) {
            final int index = i;
            textViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBoxClick(index);
                }
            });
        }

        // Set click listener for the restart button
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.animate().rotationBy(360).setDuration(500).start();
                restartGame();
            }
        });
    }

    private void onBoxClick(int index) {
        if (board[index] == ' ' && !gameEnded) {
            textViews[index].setText(String.valueOf(currentPlayer));
            board[index] = currentPlayer;

            if (checkForWin()) {
                String winner = (currentPlayer == 'X') ? getIntent().getStringExtra("player1") : getIntent().getStringExtra("player2");
                gameEnded = true;

                // Show the ResultActivity dialog with the winner's name
                ResultActivity resultDialog = new ResultActivity(this, winner + " wins!", winner, this);
                resultDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                resultDialog.setCancelable(false);
                resultDialog.show();
            } else if (isBoardFull()) {
                showToast("It's a draw!");

                // Show the "Draw" dialog
                ResultActivity drawDialog = new ResultActivity(this, "Draw !", null, this);
                drawDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                drawDialog.setCancelable(false);
                drawDialog.show();

                gameEnded = true;
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                updatePlayerTurn();
            }
        }
    }



    private boolean checkForWin() {
        // Check rows
        for (int i = 0; i < 9; i += 3) {
            if (board[i] != ' ' && board[i] == board[i + 1] && board[i] == board[i + 2]) {
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (board[i] != ' ' && board[i] == board[i + 3] && board[i] == board[i + 6]) {
                return true;
            }
        }

        // Check diagonals
        if (board[0] != ' ' && board[0] == board[4] && board[0] == board[8]) {
            return true;
        }
        return board[2] != ' ' && board[2] == board[4] && board[2] == board[6];
    }


    private boolean isBoardFull() {
        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') {
                return false;
            }
        }
        return true;
    }

    private void updatePlayerTurn() {
        Intent intent = getIntent();
        if (intent.hasExtra("player1")) {
            //move to left animation

            String textReceived = intent.getStringExtra("player1");
            playerOneTextView.setText(textReceived);
        }
        if(intent.hasExtra("player2")) {
            String textRecievedTwo = intent.getStringExtra("player2");
            playerTwoTextView.setText(textRecievedTwo);
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public void startAgainGame() {
        Intent intent = new Intent(GameActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void restartGame() {
        for (int i = 0; i < 9; i++) {
            textViews[i].setText("");
            board[i] = ' ';
        }

        currentPlayer = 'X';
        updatePlayerTurn();
        gameEnded = false;
    }
}

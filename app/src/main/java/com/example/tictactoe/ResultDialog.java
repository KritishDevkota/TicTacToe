package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class ResultDialog extends Dialog {
    private final String winnerName;
    private final GameActivity gameActivity;

    public ResultDialog(@NonNull Context context, String message, String winnerName, GameActivity gameActivity) {
        super(context);
        this.winnerName = winnerName;
        this.gameActivity = gameActivity;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView messageText = findViewById(R.id.resultView);
        Button startAgain = findViewById(R.id.StartAgain);
        Button mainMenu = findViewById(R.id.MainMenu);

        if (winnerName != null) {
            messageText.setText(winnerName + " wins!");
        } else {
            messageText.setText("Draw!");
        }

        mainMenu.setOnClickListener(v -> gameActivity.startAgainGame());

        startAgain.setOnClickListener(v -> {
            gameActivity.restartGame();
            dismiss();
        });
    }
}

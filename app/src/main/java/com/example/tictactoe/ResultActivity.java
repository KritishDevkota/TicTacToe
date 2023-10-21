package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends Dialog {
    private final String message;
    private final String winnerName; // Add a winner name
    private final GameActivity gameActivity;

    public ResultActivity(@NonNull Context context, String message, String winnerName, GameActivity gameActivity) {
        super(context);
        this.message = message;
        this.winnerName = winnerName;
        this.gameActivity = gameActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);// Set the layout

        TextView messageText = findViewById(R.id.resultView);
        Button startAgain = findViewById(R.id.StartAgain);
        Button mainMenu = findViewById(R.id.MainMenu);

        if (winnerName != null) {
            messageText.setText(winnerName + " wins!");
        } else {
            messageText.setText("Draw!");
        }
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameActivity.startAgainGame();
            }
        });

        startAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameActivity.restartGame();
                dismiss();
            }
        });
    }
}

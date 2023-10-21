package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MultiDex.install(this);

        EditText editTextOne, editText2;
        Button button;

        button = findViewById(R.id.button);
        editTextOne = findViewById(R.id.editTextText);
        editText2 = findViewById(R.id.editTextText2);
        TextView textView = findViewById(R.id.TicTacToe);
        TextView HelloUser = findViewById(R.id.HelloUser);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Animation animationOne = AnimationUtils.loadAnimation(this, R.anim.fade_in_and_translate);
        HelloUser.startAnimation(animationOne);

        Animation animationTwo = AnimationUtils.loadAnimation(this, R.anim.fade_in_and_translate);
        textView.startAnimation(animationTwo);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userOne = editTextOne.getText().toString();
                String userTwo = editText2.getText().toString();
                if (userOne.isEmpty() || userTwo.isEmpty())  {
                    editTextOne.setError("Empty");
                }
                else if (userOne.length() > 8 || userTwo.length() > 8){
                    editTextOne.setError("Less Than 8 Character");
                }
                else if (userTwo.equals(userOne)){
                    editText2.setError("Name must be different");
                }
                else {
                    //declaring the user input to submit to another intent
                   String textToPassOne = editTextOne.getText().toString();
                   String textToPassTwo = editText2.getText().toString();

                   //opening to another intent and showing the data of the first activity
                   Intent intent = new Intent(MainActivity.this, GameActivity.class);
                   intent.putExtra("player1", textToPassOne);
                   intent.putExtra("player2", textToPassTwo);
                   startActivity(intent);
                }
            }
        });

    }
}
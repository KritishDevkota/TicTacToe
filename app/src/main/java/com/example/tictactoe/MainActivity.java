package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class MainActivity extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
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

        MobileAds.initialize(MainActivity.this, initializationStatus -> {
        });
        AdView mAd_view = findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAd_view.loadAd(adRequest);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Animation animationOne = AnimationUtils.loadAnimation(this, R.anim.fade_in_and_translate);
        HelloUser.startAnimation(animationOne);

        Animation animationTwo = AnimationUtils.loadAnimation(this, R.anim.fade_in_and_translate);
        textView.startAnimation(animationTwo);

        button.setOnClickListener(v -> {

            //loading the ads for the advertisement in another View
            AdRequest adRequest1 = new AdRequest.Builder().build();


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
                InterstitialAd.load(MainActivity.this,"ca-app-pub-6209002206896630/6987704196", adRequest1, new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        mInterstitialAd = null;
                    }
                });
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MainActivity.this);
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }
        });

    }
}
package com.example.canteendeliveryservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;

public class Splash_screen extends AppCompatActivity {
LottieAnimationView lottie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        lottie = findViewById(R.id.lottie);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                Intent i =  new Intent(Splash_screen.this , login_page.class);
                startActivity(i);
            }
        },4000);


    }
}
package com.essbidali.pantrywatcher.ui.starteractivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.essbidali.pantrywatcher.R;
import com.essbidali.pantrywatcher.ui.authentication.Login;

public class SplashScreenActivity extends AppCompatActivity {
    //Variables
    Animation topAnim, bottomAnim;
    ImageView logoImageView;
    TextView titleTextView, taglineTextView;
    SharedPreferences onBoarding;

    public static int SPLASH_SCREEN = 1200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_splash_screen);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        //Hooks
        logoImageView = findViewById(R.id.logoImageView);
        titleTextView = findViewById(R.id.titleTextView);
        taglineTextView = findViewById(R.id.taglineTextView);

        //Set Animation
        logoImageView.setAnimation(topAnim);
        titleTextView.setAnimation(bottomAnim);
        taglineTextView.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onBoarding = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
                boolean isFirstTime = onBoarding.getBoolean("firstTime", true);

                if(isFirstTime){
                    SharedPreferences.Editor editor = onBoarding.edit();
                    editor.putBoolean("firstTime", false);
                    editor.apply();

                    Intent intent = new Intent(SplashScreenActivity.this, OnBoardingActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(SplashScreenActivity.this, Login.class);
                    startActivity(intent);
                }
                finish();
            }
        }, SPLASH_SCREEN);
    }
}

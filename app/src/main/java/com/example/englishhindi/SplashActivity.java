package com.example.englishhindi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.englishhindi.base.BaseActivity;
import com.example.englishhindi.model.AppSettings;

public class SplashActivity extends BaseActivity {
    
    private static final long SPLASH_DELAY = 2000; // 2 seconds
    
    private TextView titleTextView;
    private TextView subtitleTextView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        // Initialize views
        titleTextView = findViewById(R.id.splash_title);
        subtitleTextView = findViewById(R.id.splash_subtitle);
        
        // Update UI based on language
        updateUILanguage();
        
        // Schedule transition to main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start main activity
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY);
    }
    
    @Override
    protected void updateUILanguage() {
        // Update texts based on current language
        if (isHindiActive()) {
            titleTextView.setText(R.string.app_name);
            subtitleTextView.setText(R.string.welcome_message);
        } else {
            titleTextView.setText(R.string.app_name);
            subtitleTextView.setText(R.string.welcome_message);
        }
    }
}
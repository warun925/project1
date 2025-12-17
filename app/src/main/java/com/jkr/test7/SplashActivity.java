package com.jkr.test7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 1. Initialize Shared Preferences
        sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);

        // 2. Wait for 2 seconds (2000ms), then decide where to go
        new Handler().postDelayed(() -> {
            checkLoginStatus();
        }, 2000);
    }

    private void checkLoginStatus() {
        boolean isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false);

        if (isLoggedIn) {
            // User is remembered -> Go to Dashboard (Main)
            String email = sharedPreferences.getString("email", "User");
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.putExtra("USER_EMAIL", email);
            startActivity(intent);
        } else {
            // User is new -> Go to Login
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        finish(); // Close Splash so user can't go back to it
    }
}

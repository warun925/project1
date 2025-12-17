package com.jkr.test7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView tvWelcomeName;
    Button btnLogout;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvWelcomeName = findViewById(R.id.tvWelcomeName);
        btnLogout = findViewById(R.id.btnLogout);

        // Initialize Shared Preferences
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        // Get email passed from Login OR saved preferences
        String userEmail = getIntent().getStringExtra("USER_EMAIL");
        if (userEmail == null) {
            // If intent is null, grab it from storage
            userEmail = sharedPreferences.getString("email", "User");
        }
        tvWelcomeName.setText(userEmail);

        // LOGOUT LOGIC
        btnLogout.setOnClickListener(v -> {
            // 1. Clear the data
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear(); // Deletes all saved data
            editor.apply();

            // 2. Go back to Login Screen
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}

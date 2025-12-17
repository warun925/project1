package com.jkr.test7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    // --- FIX 1: DECLARE VARIABLES HERE (So they are visible to the whole class) ---
    EditText etEmail, etPassword;
    Button btnLogin;             // Fixed "Cannot resolve symbol 'btnLogin'"
    TextView tvRegisterLink;
    ApiService apiService;       // Fixed "Cannot resolve symbol 'apiService'"

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check login status before setting content view
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)) {
            String savedEmail = sharedPreferences.getString(KEY_EMAIL, "User");
            goToDashboard(savedEmail);
        }

        setContentView(R.layout.activity_login);

        // --- FIX 2: INITIALIZE VIEWS ---
        etEmail = findViewById(R.id.etLoginEmail);
        etPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegisterLink = findViewById(R.id.tvRegisterLink);

        // Initialize Retrofit
        // REMEMBER: Use 10.0.2.2 for Emulator, or your IP (e.g., 192.168.1.5) for physical phone
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.48.36.140:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Button Click Listener
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            // Call the method to login
            loginUser(email, password);
        });

        // Register Link Listener
        tvRegisterLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser(String email, String password) {
        // --- FIX 3: CREATE THE USER OBJECT ---
        // Fixed "Cannot resolve symbol 'user'" - You must create it before passing it!
        UserModel user = new UserModel(null, email, password);

        apiService.loginUser(user).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                    // Save session
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(KEY_IS_LOGGED_IN, true);
                    editor.putString(KEY_EMAIL, email);
                    editor.apply();

                    goToDashboard(email);
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }

            // --- FIX 4: IMPLEMENT onFailure ---
            // Fixed "Class must implement abstract method onFailure"
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToDashboard(String email) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("USER_EMAIL", email);
        startActivity(intent);
        finish();
    }
}

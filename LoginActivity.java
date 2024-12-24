package com.droiddevhub.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.droiddevhub.notesapp.Database.AppDatabase;
import com.droiddevhub.notesapp.Database.User;
import com.droiddevhub.notesapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    private static final String SHARED_PREFS_NAME = "MyPrefs";
    private static final String KEY_IS_SAVED = "isSaved";

    private AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = AppDatabase.getDatabase(this);         //singleton


        binding.createAccount.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });

        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.emailLogin.getText().toString();
            String password = binding.passLogin.getText().toString();

            User user = db.userDao().getUser(email, password);      //factory

            if (user != null) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(KEY_IS_SAVED, true);  // Save the value as true
                editor.apply();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Invalid credentials. Register first.", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }
}
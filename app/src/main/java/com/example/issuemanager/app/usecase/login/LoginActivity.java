package com.example.issuemanager.app.usecase.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.issuemanager.app.MainActivity;
import com.example.issuemanager.R;
import com.example.issuemanager.app.usecase.menu.fragment.SettingsFragment;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    Configuration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Loads stored language (if any)
        sharedPref = this.getSharedPreferences("APP_SETTINGS", Context.MODE_PRIVATE);
        config = new Configuration(getResources().getConfiguration());
        config.locale = new Locale(sharedPref.getString("LANGUAGE", ""));
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Auto-Login if there is a stored user
        if (sharedPref.getBoolean("STORED_USER", false)) {
            goToMenu();
        }

        // Loads View
        setContentView(R.layout.activity_login);

        // Login
        final EditText username = findViewById(R.id.field_username);
        final EditText password = findViewById(R.id.field_password);

        final Button button = findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("admin") && pass.equals("admin")) {
                    sharedPref.edit().putBoolean("STORED_USER", true).commit();
                    sharedPref.edit().putString("USER_NAME", user).commit();
                    sharedPref.edit().putString("USER_PASS", pass).commit();
                    goToMenu();
                } else  {
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void goToMenu() {
        Intent sendIntent = new Intent(this, MainActivity.class);
        startActivity(sendIntent);
    }
}
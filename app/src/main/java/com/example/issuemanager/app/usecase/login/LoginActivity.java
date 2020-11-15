package com.example.issuemanager.app.usecase.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.issuemanager.app.MainActivity;
import com.example.issuemanager.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText username = findViewById(R.id.field_username);
        final EditText password = findViewById(R.id.field_password);

        final Button button = findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /*String user = username.getText().toString();
                String pass = password.getText().toString();*/

                String user = "admin";
                String pass = "admin";

                if (user.equals("admin") && pass.equals("admin")) {
                    Log.i("Status", "Login: Success!");
                    goToMenu();
                } else  {
                    Log.i("Status", "Login: Incorrect Password or Username");
                }
            }
        });
    }

    public void goToMenu() {
        Intent sendIntent = new Intent(this, MainActivity.class);
        startActivity(sendIntent);
    }
}
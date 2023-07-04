package com.example.expendituremanagementapp.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expendituremanagementapp.MainActivity;
import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister, textViewForgotPassword;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewRegister = findViewById(R.id.textViewRegister);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        databaseHelper = new DatabaseHelper(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                if (checkValidate(username, password)) {
                    if (databaseHelper.checkUser(username, password)) {
                        Toast.makeText(LoginActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
//                      startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("username", username);
                        int userID = databaseHelper.getUserId(username);
                        intent.putExtra("userID", userID);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login information is incorrect!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
                finish();
            }
        });
    }

    private boolean checkValidate(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter your account");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            return false;
        }
        return true;
    }
}
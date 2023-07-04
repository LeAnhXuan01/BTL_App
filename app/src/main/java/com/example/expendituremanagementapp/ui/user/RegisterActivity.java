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

import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.database.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword, editTextConfirmPassword;
    private Button buttonRegister;
    private TextView textViewLogin;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        textViewLogin = findViewById(R.id.textViewLogin);
        databaseHelper = new DatabaseHelper(this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();

                if (checkValidate(username, password, confirmPassword)) {
                    long userId = databaseHelper.insertUser(username, password);
                    if (userId != -1) {
                        Toast.makeText(RegisterActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration failed! Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private boolean checkValidate(String username, String password, String confirmPassword) {
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter your account");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            return false;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            editTextConfirmPassword.setError("Please confirm password");
            return false;
        }

        if (password.length() < 5) {
            editTextPassword.setError("Password must be at least 5 characters");
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            editTextPassword.setError("Password must contain at least one capital letter");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            editTextConfirmPassword.setError("Confirm password does not match");
            return false;
        }

        if (databaseHelper.checkUsername(username)) {
            editTextUsername.setError("That username is already in use");
            return false;
        }
        return true;
    }
}
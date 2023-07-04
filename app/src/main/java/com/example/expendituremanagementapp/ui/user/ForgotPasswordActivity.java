package com.example.expendituremanagementapp.ui.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.database.DatabaseHelper;

import java.util.Random;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private Button buttonConfirm;
    private Button buttonCancel;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextUsername = findViewById(R.id.editTextUsername);
        buttonConfirm = findViewById(R.id.buttonConfirm);
        buttonCancel = findViewById(R.id.buttonCancel);

        databaseHelper = new DatabaseHelper(this);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleForgotPassword();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToLoginActivity();
            }
        });
    }

    private void handleForgotPassword() {
        String username = editTextUsername.getText().toString();

        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter your account name");
            return;
        }
        // Kiểm tra xem tài khoản có tồn tại trong cơ sở dữ liệu hay không
        if (databaseHelper.checkUsername(username)) {
            // Thực hiện các xử lý cần thiết để lấy lại mật khẩu
            String newPassword = generateNewPassword(); // Hàm generateNewPassword() tạo mật khẩu mới

            // Cập nhật mật khẩu mới vào cơ sở dữ liệu
            if (databaseHelper.updatePassword(username, newPassword)) {
                // Hiển thị mật khẩu mới cho người dùng
                showPasswordAlertDialog(newPassword);
            } else {
                // Hiển thị thông báo lỗi khi cập nhật mật khẩu mới
                Toast.makeText(ForgotPasswordActivity.this, "An error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Hiển thị thông báo tài khoản không tồn tại
            Toast.makeText(ForgotPasswordActivity.this, "Account does not exist.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPasswordAlertDialog(String newPassword) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New password");
        builder.setMessage("Your new password is: " + newPassword);
        builder.setCancelable(false); // Không cho phép đóng dialog bằng cách nhấn nút Back hoặc bấm bên ngoài dialog
        builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // Chuyển người dùng về trang đăng nhập sau khi dialog đã đóng
                redirectToLoginActivity();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void redirectToLoginActivity() {
        Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    private String generateNewPassword() {
        // Logic để tạo mật khẩu ngẫu nhiên
        // Ví dụ: Tạo mật khẩu gồm 5 ký tự từ các chữ cái và số
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder newPassword = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(characters.length());
            newPassword.append(characters.charAt(index));
        }
        return newPassword.toString();
    }
}
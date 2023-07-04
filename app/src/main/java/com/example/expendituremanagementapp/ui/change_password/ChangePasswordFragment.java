package com.example.expendituremanagementapp.ui.change_password;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.expendituremanagementapp.MainActivity;
import com.example.expendituremanagementapp.R;
import com.example.expendituremanagementapp.database.DatabaseHelper;
import com.example.expendituremanagementapp.ui.expense.ExpenseDetailViewModel;

public class ChangePasswordFragment extends Fragment {

    private EditText editOldPassword, editNewPassword, editConfirmPassword;
    private Button btnSavePassword;

    private String username;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        editOldPassword = view.findViewById(R.id.edit_old_password);
        editNewPassword = view.findViewById(R.id.edit_new_password);
        editConfirmPassword = view.findViewById(R.id.edit_confirm_password);
        btnSavePassword = view.findViewById(R.id.btn_save_password);
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());

        username = databaseHelper.getUsernameId(((MainActivity) getActivity()).userID());

        btnSavePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = editOldPassword.getText().toString();
                String newPassword = editNewPassword.getText().toString();
                String confirmPassword = editConfirmPassword.getText().toString();

                // Validate input fields
                if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(getActivity(), "New password and confirm password do not match", Toast.LENGTH_SHORT).show();
                } else if (newPassword.length() < 5) {
                    Toast.makeText(getActivity(), "New password must be at least 5 characters long", Toast.LENGTH_SHORT).show();
                } else if (!newPassword.matches(".*[A-Z].*")) {
                    Toast.makeText(getActivity(), "New password must contain at least one uppercase letter", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if old password matches


                    if (checkOldPassword(username, oldPassword)) {
                        // Perform password change operation
                        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                        boolean passwordUpdated = databaseHelper.updatePassword(username, newPassword);
                        databaseHelper.close();

                        if (passwordUpdated) {
                            Toast.makeText(getActivity(), "Password changed successfully", Toast.LENGTH_SHORT).show();
//                            // Clear input fields
//                            editOldPassword.setText("");
//                            editNewPassword.setText("");
//                            editConfirmPassword.setText("");
                            MainActivity mainActivity = (MainActivity) requireActivity();
                            mainActivity.navigateToHome();
                        } else {
                            Toast.makeText(getActivity(), "Failed to update password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Old password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private boolean checkOldPassword(String username, String oldPassword) {
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        return databaseHelper.checkUser(username, oldPassword);
    }
}


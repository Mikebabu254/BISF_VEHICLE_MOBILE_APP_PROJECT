package com.example.bisfproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class userDeatails extends AppCompatActivity {
    TextView email;
    FirebaseAuth auth;
    FirebaseUser user;
    EditText oldPassword, newPassword, confirmNewPassword;
    Button changePasswordBtn;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_deatails);

        oldPassword = findViewById(R.id.oldPassword);
        newPassword = findViewById(R.id.newPassword);
        confirmNewPassword = findViewById(R.id.confirmNewPassword);
        changePasswordBtn = findViewById(R.id.changePasswordBtn);
        progress = findViewById(R.id.changePasswordProgress);
        email = findViewById(R.id.detailsEmail);
        auth = FirebaseAuth.getInstance();

        user = auth.getCurrentUser();
        if (user== null){
            Intent intent = new Intent(userDeatails.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            email.setText(user.getEmail());
        }

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
    }

    private void changePassword() {
        String oldPasswordTxt = oldPassword.getText().toString().trim();
        String newPasswordTxt = newPassword.getText().toString().trim();
        String confirmNewPasswordTxt = confirmNewPassword.getText().toString().trim();

        if (TextUtils.isEmpty(oldPasswordTxt) || TextUtils.isEmpty(newPasswordTxt) || TextUtils.isEmpty(confirmNewPasswordTxt)) {
            Toast.makeText(this, "Fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPasswordTxt.equals(confirmNewPasswordTxt)) {
            Toast.makeText(this, "New password and Confirm password do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        progress.setVisibility(View.VISIBLE);

        // Reauthenticate the user to ensure the old password is correct
        auth.getCurrentUser().reauthenticate(com.google.firebase.auth.EmailAuthProvider.getCredential(auth.getCurrentUser().getEmail(), oldPasswordTxt))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // If reauthentication is successful, update the password
                            auth.getCurrentUser().updatePassword(newPasswordTxt)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progress.setVisibility(View.GONE);
                                            if (task.isSuccessful()) {
                                                Toast.makeText(userDeatails.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                Toast.makeText(userDeatails.this, "Failed to change password, make sure its more than 6 characters", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            progress.setVisibility(View.GONE);
                            Toast.makeText(userDeatails.this, "Incorrect old password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
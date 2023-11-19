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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registration extends AppCompatActivity {
    EditText username, email, password, rePassword;
    Button register;
    FirebaseAuth mAuth;
    ProgressBar progress;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), dashboard.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        username = findViewById(R.id.regUsername);
        email = findViewById(R.id.regEmail);
        password = findViewById(R.id.regPassword);
        rePassword = findViewById(R.id.regRePassword);
        register = findViewById(R.id.regBtnRegister);
        mAuth = FirebaseAuth.getInstance();
        progress = findViewById(R.id.bookProgress);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setVisibility(View.VISIBLE);
                try {
                    String usernameTxt = String.valueOf(username.getText());
                    String emailTxt = String.valueOf(email.getText());
                    String passwordTxt = String.valueOf(password.getText());
                    String rePasswordTxt = String.valueOf(rePassword.getText());

                    if (TextUtils.isEmpty(usernameTxt)) {
                        Toast.makeText(registration.this, "enter username", Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.GONE);
                    } else if (TextUtils.isEmpty(emailTxt)) {
                        Toast.makeText(registration.this, "enter email", Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.GONE);
                    } else if (TextUtils.isEmpty(passwordTxt)) {
                        Toast.makeText(registration.this, "enter password", Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.GONE);
                    } else if (passwordTxt.length() < 6) {
                        Toast.makeText(registration.this, "password should be of more than 6 words", Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.GONE);
                    } else if (TextUtils.isEmpty(rePasswordTxt)) {
                        Toast.makeText(registration.this, "re-write password", Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.GONE);
                    } else if (!passwordTxt.equals(rePasswordTxt)) {
                        Toast.makeText(registration.this, "password and re-password should be equal", Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.GONE);
                    } else {
                        mAuth.createUserWithEmailAndPassword(emailTxt, passwordTxt)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progress.setVisibility(View.GONE);
                                        if (task.isSuccessful()) {
                                            Toast.makeText(registration.this, "you have successful registered", Toast.LENGTH_SHORT).show();
                                            onStart();
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Toast.makeText(registration.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                            progress.setVisibility(View.GONE);
                                        }
                                    }
                                });
                    }
                } catch (Exception e) {
                    Toast.makeText(registration.this, "please register", Toast.LENGTH_SHORT).show();
                    progress.setVisibility(View.GONE);
                }
            }
        });




    }
}
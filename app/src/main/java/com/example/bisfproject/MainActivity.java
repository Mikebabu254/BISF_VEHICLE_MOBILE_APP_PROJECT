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

public class MainActivity extends AppCompatActivity {
    Button loginBtn, registerBtn;
    EditText email, password;
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
        setContentView(R.layout.activity_main);

        progress = findViewById(R.id.mainProgressBar);

        mAuth = FirebaseAuth.getInstance();
        loginBtn = findViewById(R.id.loginBtnLogin);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setVisibility(View.VISIBLE);
                try {
                    email = findViewById(R.id.loginEmail);
                    password= findViewById(R.id.loginPassword);

                    String emailTxt = String.valueOf(email.getText());
                    String passwordTxt = String.valueOf(password.getText());

                    if (TextUtils.isEmpty(emailTxt)) {
                        Toast.makeText(MainActivity.this, "please enter the email", Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.GONE);
                        return;
                    }
                    if (TextUtils.isEmpty(passwordTxt)) {
                        Toast.makeText(MainActivity.this, "please enter the password", Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.GONE);
                        return;
                    }

                    mAuth.signInWithEmailAndPassword(emailTxt, passwordTxt)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progress.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "login successful", Toast.LENGTH_SHORT).show();
                                        Intent login = new Intent(getApplicationContext(), dashboard.class);
                                        startActivity(login);
                                        finish();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        progress.setVisibility(View.GONE);
                                    }
                                }
                            });
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "please enter your credentials", Toast.LENGTH_SHORT).show();
                    progress.setVisibility(View.GONE);
                }
            }
        });

        registerBtn = findViewById(R.id.loginBtnRegister);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, registration.class);
                startActivity(intent);
            }
        });


    }


}
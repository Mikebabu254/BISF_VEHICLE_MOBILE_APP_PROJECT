package com.example.bisfproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class dashboard extends AppCompatActivity {
    ImageButton bookingParkingBtn,userDetailsBtn, parkingRecordsBtn;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView email;
    Button logout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.textDashboardEmail);
        logout = findViewById(R.id.dashboardBtnLogout);

        user = auth.getCurrentUser();
        if (user== null){
            Intent intent = new Intent(dashboard.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            email.setText(user.getEmail());
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(dashboard.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        bookingParkingBtn = findViewById(R.id.dashboardBtnBookParking);
        bookingParkingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dashboard.this, bookParking.class);
                startActivity(intent);
            }
        });



        userDetailsBtn = findViewById(R.id.dashboardBtnUserDetails);
        userDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dashboard.this, userDeatails.class);
                startActivity(intent);
            }
        });


        parkingRecordsBtn = findViewById(R.id.dashboardBtnParkingRecords);
        parkingRecordsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dashboard.this, ViewBookingsActivity.class);
                startActivity(intent);
            }
        });

    }
}
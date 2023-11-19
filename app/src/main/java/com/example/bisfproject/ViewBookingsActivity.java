package com.example.bisfproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import java.util.List;

public class ViewBookingsActivity extends AppCompatActivity {

    private ListView listViewBookings;
    private List<String> bookingsList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookings);

        listViewBookings = findViewById(R.id.listViewBookings);
        bookingsList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookingsList);
        listViewBookings.setAdapter(arrayAdapter);

        // Retrieve bookings from the database
        retrieveBookings();
    }

    private void retrieveBookings() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            String userEmail = user.getEmail();

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("booking");

            reference.orderByChild("mail").equalTo(userEmail)/*.limitToLast(10)*/.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        HelperClass helperClass = snapshot.getValue(HelperClass.class);
                        if (helperClass != null) {
                            String bookingInfo = "venue: " + helperClass.getVenue() + "\n" +
                                    "slot: " + helperClass.getSlot() + "\n" +
                                    "carRegNo: " + helperClass.getCarRegNo() + "\n" +
                                    "date: " + helperClass.getDate() + "\n" +
                                    "timeIn: " + helperClass.getTimeIn() + "\n" +
                                    "timeOut: " + helperClass.getTimeOut() + "\n" +
                                    "duration: " + helperClass.getDuration() + " hours\n" +
                                    "payment: " + helperClass.getPayment() + "\n" +
                                    "receiptNumber: " + helperClass.getRecieptNumber();

                            bookingsList.add(bookingInfo);
                        }
                    }

                    arrayAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error, if necessary
                }
            });
        }
    }
}
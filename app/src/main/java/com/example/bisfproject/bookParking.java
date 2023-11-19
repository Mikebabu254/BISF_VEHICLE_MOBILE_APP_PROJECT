package com.example.bisfproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class bookParking extends AppCompatActivity {
    private Spinner parkingVenue, parkingSlot, paymentMethod;
    private TextView selectDate, selectTime, selectTimeOut,selectCash, selectReciept, selectUserMail;
    EditText selectDuration, selectCarRegNo;
    FirebaseDatabase database;
    DatabaseReference reference;
    private Button datePicker, timePicker, confirm, done;
    private int year, month, day, hour, minute;
    ProgressBar progress;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_parking);

        progress = findViewById(R.id.bookProgress);
        auth = FirebaseAuth.getInstance();
        selectUserMail = findViewById(R.id.textViewUser);
        selectCarRegNo = findViewById(R.id.carRegNo);

        user = auth.getCurrentUser();
        if (user== null){
            Intent intent = new Intent(bookParking.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            selectUserMail.setText(user.getEmail());
        }


        selectReciept = findViewById(R.id.recieptNo);
        selectCash = findViewById(R.id.bookingCash);
        done = findViewById(R.id.bookingBtnConfirmParking);


        parkingVenue = findViewById(R.id.bookingParkingVenue);
        String[] optionVenue = {"PICK A VENUE","KICC parking", "Skate park", "Parliament road"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, optionVenue);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        parkingVenue.setAdapter(adapter);
        parkingVenue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedOption = optionVenue[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here, but you can add handling if needed.
            }
        });

        parkingSlot = findViewById(R.id.bookingParkingSlot);
        String[] optionSlot = {"PICK A SLOT","A1", "A2", "A3", "A4", "A5"};
        ArrayAdapter<String> adapt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, optionSlot);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        parkingSlot.setAdapter(adapt);
        parkingSlot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedOption = optionSlot[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here, but you can add handling if needed.
            }
        });

        paymentMethod = findViewById(R.id.bookingParkingPaymentMethod);
        String[] optionPayment = {"PICK PAYMENT METHOD","m-pesa", "debit/credit card", "cash"};
        ArrayAdapter<String> adaptt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, optionPayment);
        adaptt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMethod.setAdapter(adaptt);
        paymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedOption = optionPayment[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here, but you can add handling if needed.
            }
        });


        selectDate = findViewById(R.id.bookingDatePicker);
        datePicker = findViewById(R.id.bookingBtnDatePicker);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        selectTime = findViewById(R.id.bookingTimePicker);
        timePicker = findViewById(R.id.bookingBtnTimePicker);
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });


        done.setEnabled(false);

        confirm = findViewById(R.id.bookingBtnConfirm);
        selectDuration = findViewById(R.id.bookingDurationPicker);
        selectTimeOut = findViewById(R.id.bookingTimeOut);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String duration = selectDuration.getText().toString();
                    int num = Integer.parseInt(duration);
                    String selectedVenue = parkingVenue.getSelectedItem().toString();
                    String selectedSlot = parkingSlot.getSelectedItem().toString();
                    String selectedPayment = paymentMethod.getSelectedItem().toString();
                    String carRegNo = selectCarRegNo.getText().toString();

                    if(carRegNo.isEmpty()){
                        Toast.makeText(bookParking.this, "Please enter car registration number", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (selectedVenue.equals("PICK A VENUE")) {
                        Toast.makeText(bookParking.this, "pick a venue", Toast.LENGTH_SHORT).show();
                    } else if (selectedSlot.equals("PICK A SLOT")) {
                        Toast.makeText(bookParking.this, "please pick parking slot", Toast.LENGTH_SHORT).show();
                    } else if (selectedPayment.equals("PICK PAYMENT METHOD")) {
                        Toast.makeText(bookParking.this, "please pick payment method", Toast.LENGTH_SHORT).show();
                    } else if (num < 1) {
                        Toast.makeText(bookParking.this, "you cannot stay in the parking less than 1 hr" , Toast.LENGTH_SHORT).show();
                    }else if(num < 25){
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, hour);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.add(Calendar.HOUR, num);

                        int updatedHour = calendar.get(Calendar.HOUR_OF_DAY);
                        int updatedMinute = calendar.get(Calendar.MINUTE);

                        String timeOut = updatedHour + ":" + updatedMinute;

                        // Display the updated time in selectTimeOut
                        selectTimeOut.setText(" " + timeOut);

                        //String duration = selectDuration.getText().toString();
                        int cash = Integer.parseInt(duration);
                        int total = cash * 100;
                        // = findViewById(R.id.bookingCash);
                        selectCash.setText("ksh " + total);


                        String t = selectTime.getText().toString();
                        String d = selectDate.getText().toString();


                        selectReciept.setText(""+ d.substring(8, 10) + t.substring(0, 2) + selectedSlot  + selectedVenue.substring(0, 1)+ carRegNo);
                        done.setEnabled(true);

                    }  else {
                        done.setEnabled(false);
                        Toast.makeText(bookParking.this, "you cannot park more than 24 hrs" , Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception E){

                    Toast.makeText(bookParking.this, "make sure you have field the form" , Toast.LENGTH_SHORT).show();
                    done.setEnabled(false);
                }



            }
        });










        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setVisibility(View.VISIBLE);
                try {
                    selectCarRegNo = findViewById(R.id.carRegNo);
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("booking");

                    String duration = selectDuration.getText().toString();
                    String timeOut = selectTimeOut.getText().toString();
                    String carRegNo = selectCarRegNo.getText().toString();
                    String cash = selectCash.getText().toString();
                    String timeIn = selectTime.getText().toString();
                    String date = selectDate.getText().toString();
                    String venue = parkingVenue.getSelectedItem().toString();
                    String slot = parkingSlot.getSelectedItem().toString();
                    String payment = paymentMethod.getSelectedItem().toString();
                    String recieptNumber = selectReciept.getText().toString();
                    String mail = selectUserMail.getText().toString();

                    // Check if the receipt number exists in the database
                    DatabaseReference receiptReference = database.getReference("booking").child(recieptNumber);
                    receiptReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // Receipt number already exists in the database
                                Toast.makeText(bookParking.this, "parking already booked", Toast.LENGTH_SHORT).show();
                                progress.setVisibility(View.GONE);
                            } else{
                                // Receipt number doesn't exist, allow the booking
                                // ... (your existing code to set up the booking)
                                HelperClass helperClass = new HelperClass(duration,carRegNo,timeOut,cash,timeIn,date,venue,slot,payment,recieptNumber,mail);
                                reference.child(recieptNumber).setValue(helperClass);


                                // Get the current time in milliseconds using ServerValue.TIMESTAMP
                                Object timestamp = ServerValue.TIMESTAMP;

                                // Create a map to update both time and date in the database
                                Map<String, Object> timestampUpdate = new HashMap<>();
                                timestampUpdate.put("timestamp", timestamp);

                                // Set the timestamp in the database
                                reference.child(recieptNumber).updateChildren(timestampUpdate);


                                Toast.makeText(bookParking.this, "successful", Toast.LENGTH_SHORT).show();
                                progress.setVisibility(View.GONE);
                                done.setEnabled(true);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            progress.setVisibility(View.GONE);
                            // Handle database error, if necessary
                        }
                    });







                }catch (Exception e){
                        Toast.makeText(bookParking.this, "cannot register", Toast.LENGTH_SHORT).show();
                        progress.setVisibility(View.GONE);
                }
            }
        });




    }


    public void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Format the day and month with two digits
                SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
                SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
                String formattedDay = dayFormat.format(new Date(year - 1900, month, dayOfMonth)); // Subtract 1900 from year
                String formattedMonth = monthFormat.format(new Date(year - 1900, month, dayOfMonth)); // Subtract 1900 from year

                selectDate.setText("" + year + "/" + formattedMonth + "/" + formattedDay);
            }
        }, year, month, day);

        datePickerDialog.show();
    }


    public void showTimePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                bookParking.this.hour = hourOfDay;
                bookParking.this.minute = minute;
                updateSelectedTimeText();
            }
        }, hour, minute, true);

        timePickerDialog.show();
    }

    private void updateSelectedTimeText() {
        selectTime.setText("" + hour + ":" + minute);
    }

}
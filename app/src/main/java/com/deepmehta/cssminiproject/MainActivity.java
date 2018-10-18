package com.deepmehta.cssminiproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("User Verified!! Welcome");

        TextView mobile = (TextView) findViewById(R.id.user_details);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String mobileNumber = mAuth.getCurrentUser().getPhoneNumber();
        mobile.setText("Mobile: "+mobileNumber);
    }
}

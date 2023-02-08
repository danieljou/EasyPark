package com.developer.easypark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profile extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView display_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profil");
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser;
        display_email = findViewById(R.id.display_email);

        currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(getApplicationContext(), login.class);
            startActivity(intent);
            finish();

        }
        String currentUserEmail = currentUser.getEmail().toString();
        display_email.setText(currentUserEmail);


    }
}
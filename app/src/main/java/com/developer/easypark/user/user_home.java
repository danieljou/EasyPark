package com.developer.easypark.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.developer.easypark.Home;
import com.developer.easypark.MapsFragment;
import com.developer.easypark.Parkings;
import com.developer.easypark.R;
import com.developer.easypark.UserProfile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class user_home extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    Button user_logout;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        bottomNavigationView = findViewById(R.id.userbottomNavView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.profile_page);




    }

    MapsFragment mapsFragment = new MapsFragment();
    Parkings parkings = new Parkings();
    UserProfile userProfile = new UserProfile();





    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.profile_page:
                getSupportFragmentManager().beginTransaction().replace(R.id.lay, userProfile).commit();
                return true;

            case R.id.map:
                getSupportFragmentManager().beginTransaction().replace(R.id.lay, mapsFragment).commit();
                return true;

            case R.id.parking_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.lay, parkings).commit();
                return true;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


}
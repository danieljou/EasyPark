package com.developer.easypark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;


import com.developer.easypark.Parking.create_parking;
import com.developer.easypark.Parking.parking_list;
import com.developer.easypark.user.user_home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Home extends AppCompatActivity {
    Button start;
    Button to_profile;
    ImageView voiture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        start = (Button) findViewById(R.id.start);
        to_profile = (Button) findViewById(R.id.to_profile);

        to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(Home.this, create_parking.class);
                startActivity(next);


            }
        });
        animationStart();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }
    private void animationStart(){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation1);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.animation2);

        voiture = findViewById(R.id.voiture);
        start = findViewById(R.id.start);
        animation.reset();
        animation2.reset();

        // Aanimation de la voiture
        voiture.clearAnimation();
        voiture.startAnimation(animation);

        // animation du bouton

        start.clearAnimation();
        start.setAnimation(animation2);

    }

    public  void check(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null){
            Intent next = new Intent(getApplicationContext(),register.class);
            startActivity(next);

            return;
        }
        else {
            FirebaseFirestore.getInstance().collection("user")
                    .document(currentUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot user = task.getResult();
                                String is_admin = (String) user.get("is_admin");
                                Intent intent;
                                assert is_admin != null;
                                if (is_admin.equals("true")) {
                                    intent = new Intent(getApplicationContext(), MainActivity.class);
                                } else {
                                    intent = new Intent(getApplicationContext(), profile.class);
                                }
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        }
    }
}
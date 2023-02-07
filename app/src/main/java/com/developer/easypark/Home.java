package com.developer.easypark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;


public class Home extends AppCompatActivity {
    Button start;
    ImageView voiture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        start = (Button) findViewById(R.id.start);
        animationStart();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(getApplicationContext(),register.class);
                startActivity(next);


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
}
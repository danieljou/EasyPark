package com.developer.easypark.Parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.easypark.Modele.Parking;
import com.developer.easypark.R;
import com.developer.easypark.login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class create_parking extends AppCompatActivity {
    EditText parkingName;
    EditText parkingPlace;
    Button submitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_parking);
        setTitle("Création d'un parking");
        parkingPlace = (EditText) findViewById(R.id.edit_parking_place);
        parkingName = (EditText) findViewById(R.id.edit_parking_name);
        submitBtn = (Button) findViewById(R.id.parking_add_btn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(parkingPlace.getText().toString() != null && parkingName.getText() != null){
                String parkID = "" + new Date().getTime() / 1000;
                int parking_place = new Integer(parkingPlace.getText().toString());
                Parking parking = new Parking(parkingName.getText().toString(), parking_place);
                System.out.println("Nombre de place " + parking.getNbrPlace());
                    FirebaseFirestore.getInstance().collection("parking")
                            .document()
                            .set(parking)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(), "Enregistrement effectué", Toast.LENGTH_LONG).show();

                                }
                            });
                }
            }
        });
    }
}
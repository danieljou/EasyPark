package com.developer.easypark.Parking;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.easypark.Modele.Parking;
import com.developer.easypark.R;
import com.developer.easypark.login;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class create_parking extends AppCompatActivity {
    EditText parkingName;
    EditText parkingPlace;
    EditText latitude;
    EditText longitude;
    Button getCoords;
    Button submitBtn;
    client = LocationServices.getFusedLocationProviderClient(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_parking);
        setTitle("Création d'un parking");
        parkingPlace = (EditText) findViewById(R.id.edit_parking_place);
        parkingName = (EditText) findViewById(R.id.edit_parking_name);
        submitBtn = (Button) findViewById(R.id.parking_add_btn);
        latitude = (EditText) findViewById(R.id.edit_latitude);
        longitude = (EditText) findViewById(R.id.edit_longitude);
        getCoords = (Button) findViewById(R.id.get_coords);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parkingPlace.getText().toString();
                if(parkingName.getText() != null){
                String parkID = "" + new Date().getTime();
                int parking_place = new Integer(parkingPlace.getText().toString());
                Parking parking = new Parking(parkID, parkingName.getText().toString(), parking_place);
                System.out.println("Nombre de place " + parking.getNbrPlace());
                    FirebaseFirestore.getInstance().collection("parking")
                            .document()
                            .set(parking)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(), "Enregistrement effectué", Toast.LENGTH_LONG).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(getApplicationContext(), "une erreur s'est produite", Toast.LENGTH_LONG).show();
                                }
                            }).addOnCanceledListener(new OnCanceledListener() {
                                @Override
                                public void onCanceled() {
                                    Toast.makeText(getApplicationContext(), "une erreur s'est produite", Toast.LENGTH_LONG).show();

                                }
                            });
                }
            }
        });
        getCoords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });
    }


    public void getLocation(){
        if (ActivityCompat.checkSelfPermission(create_parking.this, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }

    }
}
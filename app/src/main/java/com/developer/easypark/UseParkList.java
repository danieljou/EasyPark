package com.developer.easypark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.developer.easypark.Modele.Parking;
import com.developer.easypark.Modele.UsePark;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UseParkList extends AppCompatActivity {
    ListView useList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_park_list);
        setTitle("Liste des utlisations");

        useList = (ListView) findViewById(R.id.use_list);
        FirebaseFirestore.getInstance()
                .collection("usepark")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<UsePark> useParks = new ArrayList<>();
                        for(DocumentSnapshot doc: task.getResult()){

                            UsePark use = new UsePark();
                            use.setId(doc.getId());
                            Date dateF = doc.getDate("dateFin");
                            Date dateD = doc.getDate("dateDebut");
                            System.out.println(doc.getString("parkID"));
                            use.setParkID(doc.getString("parkID"));

                            use.setDateFin(dateF);
                            use.setDateDebut(dateD);

                            useParks.add(use);
                        }
                        //if (!task.getResult().isEmpty()) {
                        //List<Parking> querySnapshot = task.getResult().toObjects(Parking.class);
                        UseParkAdapter customList = new UseParkAdapter(getApplicationContext(),useParks);
                        useList.setAdapter(customList);
                    }
                });

    }
}
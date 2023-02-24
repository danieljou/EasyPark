package com.developer.easypark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.developer.easypark.Modele.Park;
import com.developer.easypark.Modele.Parking;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ParkDetails extends AppCompatActivity {
    ListView parklist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_details);
        setTitle("Détals sur le parking");

        Bundle b = getIntent().getExtras();
        String id = b.getString("id");
        System.out.println(" Id passé en parametre : " + id);
        parklist = (ListView) findViewById(R.id.park_list);

        FirebaseFirestore.getInstance().collection("parking")
                .document(id)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot parking = task.getResult();
                            System.out.println(parking.get("nom"));

                            FirebaseFirestore.getInstance()
                                    .collection("park")
                                    .whereEqualTo("parkingID", parking.get("id"))
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            List<Park> parkings = new ArrayList<>();
                                            if(task.isSuccessful()){
                                                for (DocumentSnapshot doc: task.getResult()){
                                                    Park park = new Park(
                                                            doc.get("id").toString(),
                                                            doc.get("parkingID").toString(),
                                                            (boolean) doc.get("state")
                                                    );
                                                    parkings.add(park);
                                                }

                                                SingleParkAdapter customList = new SingleParkAdapter(getApplicationContext(),parkings);
                                                parklist.setAdapter(customList);
                                            }
                                        }
                                    });
                        }
                    }
                });

    }
}
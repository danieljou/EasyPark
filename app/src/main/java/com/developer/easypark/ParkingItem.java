package com.developer.easypark;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.developer.easypark.Modele.Parking;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ParkingItem extends BaseAdapter {
    private Context context;
    private List<Parking> parkings;
    private TextView tt1, tt2;

    public ParkingItem(Context context, List<Parking> parkings) {
        this.context = context;
        this.parkings = parkings;

    }

    @Override
    public int getCount() {
        return parkings.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        currentItemView = LayoutInflater.from(context).inflate(R.layout.parking_adapter, parent, false);


         tt1 = (TextView) currentItemView.findViewById(R.id.item_parkning_name);
         tt2 = (TextView) currentItemView.findViewById(R.id.item_parkning_place);



        tt1.setText(parkings.get(position).getNom());
        tt2.setText("Nombre de parking : " + parkings.get(position).getNbrPlace());


        ImageButton view, delete;
        view = (ImageButton) currentItemView.findViewById(R.id.item_park_view);
        delete = (ImageButton) currentItemView.findViewById(R.id.item_park_delete);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ParkDetails.class);
                intent.putExtra("id",parkings.get(position).getId());
                context.startActivity(intent);
            }
        });

        View finalCurrentItemView = currentItemView;
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Voullez vous vraiment supprimer le parking : " +
                        parkings.get(position).getNom() + " ?");
                builder.setTitle("Validation");
                builder.setCancelable(false);

                builder.setPositiveButton("Oui", (DialogInterface.OnClickListener) (dialog,which) ->{
                    FirebaseFirestore.getInstance().collection("parking")
                            .document(parkings.get(position).id)
                            .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    FirebaseFirestore.getInstance().collection("park")
                                            .whereEqualTo("parkingID",parkings.get(position).getId())
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()){
                                                        for(DocumentSnapshot doc: task.getResult()){
                                                            FirebaseFirestore.getInstance().collection("park")
                                                                    .document(doc.getId())
                                                                    .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {

                                                                        }
                                                                    });
                                                        }

                                                    }
                                                }
                                            });



                                }
                            });
                });

                builder.setNegativeButton("Non", (DialogInterface.OnClickListener) (dialog,which) ->{

                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


        return currentItemView;
    }
}

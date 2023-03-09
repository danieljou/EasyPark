package com.developer.easypark;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.developer.easypark.Modele.Park;
import com.developer.easypark.Modele.Parking;
import com.developer.easypark.Modele.UsePark;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SingleParkAdapter extends BaseAdapter {
    private Context context;
    private List<Park> parkings;
    private TextView tt1, tt2;
   UsePark usePark = new UsePark();
    boolean check = false;
    String id;

    public static final String IS_SELECTED = "is_selected";
    public static final String SHARED_PREFS = "is_selected";
    public static final String USE_PARK_ID= "useparkid";
    public static final String USE_PARK_SART= "start";
    public static final String PARK_ID= "PARK_ID";


    boolean is_selected;
    String park_id;
    String start_date;



    public SingleParkAdapter(Context context, List<Park> parkings) {
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

        currentItemView = LayoutInflater.from(context).inflate(R.layout.single_park_adpter, parent, false);


         tt1 = (TextView) currentItemView.findViewById(R.id.single_park_code);
         tt2 = (TextView) currentItemView.findViewById(R.id.park_state);

         Button userUseParkButton;
         userUseParkButton = (Button) currentItemView.findViewById(R.id.user_use_park);


        tt1.setText(parkings.get(position).getId());
        boolean state = parkings.get(position).state;

        if (state){
            tt2.setText("Occupé");
            tt2.setBackgroundColor(context.getResources().getColor(R.color.red));
            userUseParkButton.setText("Cliquez pour libérer le parking");
        }else {
            tt2.setText("Vide");
            tt2.setBackgroundColor(context.getResources().getColor(R.color.green2));
            userUseParkButton.setText("Cliquez pour utiliser le parking");
        }

        userUseParkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!state){

                   /* if (!check && parkings.get(position).id != id){
                        check = true;
                        id = parkings.get(position).id;
                    }
                    else {
                        Toast.makeText(context, "Erreur veillez liberer l'autre parking", Toast.LENGTH_LONG).show();
                        return;
                    }*/
                    //usePark.setParkID("usepark-" + new Date().toString());
                    usePark.setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    usePark.setDateDebut(new Date());
                    usePark.setParkID(parkings.get(position).getId());
                    saveData();
                    FirebaseFirestore.getInstance()
                            .collection("park")
                            .document(parkings.get(position).getId())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    Park park = new Park(task.getResult().get("id").toString(),
                                            task.getResult().get("parkingID").toString(),
                                            (boolean) task.getResult().get("state")
                                    );
                                    park.setState(true);
                                    FirebaseFirestore.getInstance()
                                            .collection("park")
                                            .document(parkings.get(position).getId())
                                            .set(park).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    tt2.setText("Occupé");
                                                    tt2.setBackgroundColor(context.getResources().getColor(R.color.red));
                                                    userUseParkButton.setText("Cliquez pour libérer le parking");
                                                    Toast.makeText(context, "Effectué", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                }
                            });
                }
                else{

                    loadDATA();
                    if (!is_selected){
                        Toast.makeText(context, "Vous n'este pas autorisé à agir sur ce parking", Toast.LENGTH_LONG).show();
                        return;
                    }
                    SimpleDateFormat formatter6=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                    try {
                        usePark.setDateDebut(formatter6.parse(start_date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    usePark.setParkID(park_id);
                    usePark.setId("usepark-" + new Date().toString());
                    usePark.setDateFin(new Date());
                    usePark.setUserID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    FirebaseFirestore.getInstance()
                                    .collection("usepark")
                                            .document()
                                                    .set(usePark).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                    FirebaseFirestore.getInstance()
                            .collection("park")
                            .document(parkings.get(position).getId())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    Park park = new Park(task.getResult().get("id").toString(),
                                            task.getResult().get("parkingID").toString(),
                                            (boolean) task.getResult().get("state")
                                    );
                                    park.setState(false);
                                    FirebaseFirestore.getInstance()
                                            .collection("park")
                                            .document(parkings.get(position).getId())
                                            .set(park).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    tt2.setText("Vide");
                                                    tt2.setBackgroundColor(context.getResources().getColor(R.color.green2));
                                                    userUseParkButton.setText("Cliquez pour utiliser le parking");
                                                    Toast.makeText(context, "Utilisation Terminée", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                }
                            });
                }
            }
        });



        return currentItemView;
    }
    public void saveData(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USE_PARK_ID, usePark.getParkID());
        editor.putBoolean(IS_SELECTED, true);
        editor.putString(USE_PARK_SART, new Date().toString());
        editor.putString(PARK_ID, usePark.getParkID());


    }
    public void loadDATA(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        is_selected = sharedPreferences.getBoolean(IS_SELECTED, false);
        park_id = sharedPreferences.getString(USE_PARK_ID, "");
        start_date =  sharedPreferences.getString(USE_PARK_SART, "");


    }
}

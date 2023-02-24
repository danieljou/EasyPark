package com.developer.easypark;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.developer.easypark.Modele.Park;
import com.developer.easypark.Modele.Parking;

import java.util.List;

public class SingleParkAdapter extends BaseAdapter {
    private Context context;
    private List<Park> parkings;
    private TextView tt1, tt2;

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



        tt1.setText(parkings.get(position).getId());
        boolean state = parkings.get(position).state;

        if (state == true){
            tt2.setText("Occupé");
            tt2.setBackgroundColor(context.getResources().getColor(R.color.red));
        }else {
            tt2.setText("Vide");
            tt2.setBackgroundColor(context.getResources().getColor(R.color.green2));
        }



        return currentItemView;
    }
}

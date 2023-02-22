package com.developer.easypark;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.developer.easypark.Modele.Parking;

import java.util.List;

public class UserParkAdapter extends BaseAdapter {
    private Context context;
    private List<Parking> parkings;
    private TextView tt1, tt2;

    public UserParkAdapter(Context context, List<Parking> parkings) {
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


        return currentItemView;
    }
}

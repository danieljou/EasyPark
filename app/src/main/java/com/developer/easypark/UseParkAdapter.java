package com.developer.easypark;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.developer.easypark.Modele.UsePark;

import java.util.List;

public class UseParkAdapter extends BaseAdapter {
    private Context context;
    private List<UsePark> use;
    private TextView tt1, tt2, tt3;

    public UseParkAdapter(Context context, List<UsePark> use) {
        this.context = context;
        this.use = use;

    }

    @Override
    public int getCount() {
        return use.size();
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

        currentItemView = LayoutInflater.from(context).inflate(R.layout.use_park_adrapter, parent, false);

        UsePark usePark = new UsePark();
         tt1 = (TextView) currentItemView.findViewById(R.id.user_name);
         tt2 = (TextView) currentItemView.findViewById(R.id.start_at);
         tt3 = (TextView) currentItemView.findViewById(R.id.finish_at);

         tt1.setText(use.get(position).getUserID());
         tt2.setText(use.get(position).getDateDebut().toString());
         tt2.setText(use.get(position).getDateFin().toString());


        return currentItemView;
    }
}

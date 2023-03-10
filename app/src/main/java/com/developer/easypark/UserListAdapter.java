package com.developer.easypark;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.developer.easypark.Modele.UsePark;
import com.developer.easypark.Modele.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserListAdapter extends BaseAdapter {
    private Context context;
    private List<User> use;
    private TextView tt1, tt2, tt3;

    public UserListAdapter(Context context, List<User> use) {
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


         tt1 = (TextView) currentItemView.findViewById(R.id.user_name);
         tt2 = (TextView) currentItemView.findViewById(R.id.start_at);
         tt3 = (TextView) currentItemView.findViewById(R.id.finish_at);

         tt1.setText(use.get(position).getMail());
         tt2.setText(use.get(position).getNom());



        return currentItemView;
    }
}

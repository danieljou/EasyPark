package com.developer.easypark;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.developer.easypark.Modele.Parking;
import com.developer.easypark.Modele.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Parkings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Parkings extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Parkings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Parkings.
     */
    // TODO: Rename and change types and number of parameters
    public static Parkings newInstance(String param1, String param2) {
        Parkings fragment = new Parkings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_parkings, container, false);
        ListView parkList;
        parkList = (ListView) view.findViewById(R.id.user_parking_list);


        FirebaseFirestore.getInstance().collection("parking")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Parking> parkings = new ArrayList<>();
                        if(task.isSuccessful()){
                            for(DocumentSnapshot doc: task.getResult()){
                                LatLng latLng = new LatLng( (double) doc.get("latLng.latitude"), (double) doc.get("latLng.longitude"));
                                Parking park = new Parking(doc.get("id").toString(), doc.get("nom").toString(),
                                        Integer.parseInt( doc.get("nbrPlace").toString()),
                                        latLng
                                );
                                System.out.println(latLng.latitude);
                                parkings.add(park);
                            }
                            //if (!task.getResult().isEmpty()) {
                            //List<Parking> querySnapshot = task.getResult().toObjects(Parking.class);
                            UserParkAdapter customList = new UserParkAdapter(getContext(),parkings);
                            parkList.setAdapter(customList);
                            //}
                        }
                    }
                });

        return view ;

    }
}
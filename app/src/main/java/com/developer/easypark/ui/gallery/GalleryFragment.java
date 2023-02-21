package com.developer.easypark.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.developer.easypark.Modele.Parking;
import com.developer.easypark.Parking.create_parking;
import com.developer.easypark.ParkingItem;
import com.developer.easypark.databinding.FragmentGalleryBinding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView parkList = binding.lsitParking;
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
                            ParkingItem customList = new ParkingItem(getContext(),parkings);
                            parkList.setAdapter(customList);
                            //}
                        }
                    }
                });





        binding.btnCreerPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), create_parking.class);
                startActivity(intent);
            }
        });
        return root;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.developer.easypark.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.developer.easypark.Modele.UsePark;
import com.developer.easypark.Modele.User;
import com.developer.easypark.UseParkAdapter;
import com.developer.easypark.UserListAdapter;
import com.developer.easypark.databinding.FragmentSlideshowBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SlideshowFragment extends Fragment {
    ListView user_list;
    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        user_list = binding.userList;

        FirebaseFirestore.getInstance()
                .collection("user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<User> users = new ArrayList<>();
                        for(DocumentSnapshot doc: task.getResult()){

                            User user = new User();

                            user.setMail(doc.getString("mail"));
                            user.setNom(doc.getString("nom"));

                            users.add(user);


                        }
                        //if (!task.getResult().isEmpty()) {
                        //List<Parking> querySnapshot = task.getResult().toObjects(Parking.class);
                        UserListAdapter customList = new UserListAdapter(getContext(),users);
                        user_list.setAdapter(customList);
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
package com.superfinishing.ui.bookings;

import static com.superfinishing.Workspace.currentUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.superfinishing.R;
import com.superfinishing.databinding.FragmentBookingBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class BookingFragment extends Fragment {

//    private DashboardViewModel dashboardViewModel;
    private FragmentBookingBinding binding;
    ArrayList<HashMap<String,String>> list=new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBookingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = root.findViewById(R.id.recyclerView12);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        AdapterBooking adapterBooking=new AdapterBooking(getContext(),list);
        recyclerView.setAdapter(adapterBooking);

        ImageView imageViewNoData = root.findViewById(R.id.imageView64);
        imageViewNoData.setVisibility(View.GONE);
        FirebaseDatabase.getInstance().getReference().child("booking").child(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String name = dataSnapshot.child("name").getValue().toString();
                    String image = dataSnapshot.child("image").getValue().toString();
                    String describe = dataSnapshot.child("describe").getValue().toString();
                    String category = dataSnapshot.child("category").getValue().toString();
                    String rate = dataSnapshot.child("rate").getValue().toString();
                    String provider = dataSnapshot.child("provider").getValue().toString();
                    String key = dataSnapshot.getKey();
                    String status = dataSnapshot.child("status").getValue().toString();
                    String bookedBy = dataSnapshot.child("bookedBy").getValue().toString();
                    String bookingKey = dataSnapshot.child("bookingKey").getValue().toString();

                    HashMap<String,String> map=new HashMap<>();
                    map.put("key",key);
                    map.put("name",name);
                    map.put("image",image);
                    map.put("describe",describe);
                    map.put("category",category);
                    map.put("rate",rate);
                    map.put("provider",provider);
                    map.put("status",status);
                    map.put("bookedBy",bookedBy);
                    map.put("bookingKey",bookingKey);
                    list.add(map);
                    adapterBooking.notifyItemInserted(list.size());
                }
                if(list.size()==0){
                    imageViewNoData.setVisibility(View.VISIBLE);
                }else {
                    imageViewNoData.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
package com.superfinishing.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.superfinishing.R;
import com.superfinishing.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationsFragment extends Fragment {

//    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    ArrayList<HashMap<String,String>> list=new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        notificationsViewModel =
//                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        RecyclerView recyclerView=root.findViewById(R.id.recyclerView11);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        AdadpterNotifications adadpterNotifications = new AdadpterNotifications(getContext(), list);
        recyclerView.setAdapter(adadpterNotifications);

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        FirebaseDatabase.getInstance().getReference().child("notifications").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String title = dataSnapshot.child("title").getValue().toString();
                    String desc = dataSnapshot.child("desc").getValue().toString();
                    String image = dataSnapshot.child("image").getValue().toString();
                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("title",title);
                    hashMap.put("desc",desc);
                    hashMap.put("image",image);
                    list.add(hashMap);
                    adadpterNotifications.notifyItemInserted(list.size());
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
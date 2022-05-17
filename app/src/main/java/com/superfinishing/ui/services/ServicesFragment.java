package com.superfinishing.ui.services;

import static com.superfinishing.Workspace.currentUser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.superfinishing.R;
import com.superfinishing.search.SearchActivity;
import com.superfinishing.ui.products.AdapterCategory;
import com.superfinishing.ui.products.AdapterPopularService;
import com.superfinishing.ui.products.AdapterTopUsers;

import java.util.ArrayList;
import java.util.HashMap;

public class ServicesFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_services, container, false);

        TextView textLocation = view.findViewById(R.id.textView150);
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("location", Context.MODE_PRIVATE);
        String locality = sharedPreferences.getString("locality", "");
        String address = sharedPreferences.getString("address", "");
        if (locality!=null){
            if (!locality.isEmpty()){
//                textLocation.setText(locality);
            }
        }


        return view;
    }
}
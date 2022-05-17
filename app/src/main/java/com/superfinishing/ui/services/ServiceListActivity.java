package com.superfinishing.ui.services;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.superfinishing.R;
import com.superfinishing.model.ServiceCategoryModel;
import com.superfinishing.ui.products.AdapterCategory;

import java.util.ArrayList;
import java.util.HashMap;

public class ServiceListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        ArrayList<HashMap<String,String>> listServices=new ArrayList<>();


        RecyclerView recyclerViewOurServices = findViewById(R.id.recyclerView7);
        recyclerViewOurServices.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
//        recyclerViewOurServices.setLayoutManager(new LinearLayoutManager(ServiceListActivity.this,RecyclerView.HORIZONTAL,false));
        AdapterCategory adapterServiceCategory = new AdapterCategory(ServiceListActivity.this, listServices, false,true);
        recyclerViewOurServices.setAdapter(adapterServiceCategory);

        FirebaseDatabase.getInstance().getReference().child("serviceCategory").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("Dataaaaaaaaaaaaaaaaaaaaaa");
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    ServiceCategoryModel serviceCategoryModel= dataSnapshot.getValue(ServiceCategoryModel.class);
                    System.out.println("serviceModel.. "+serviceCategoryModel.getName());
                    String name = dataSnapshot.child("name").getValue().toString();
                    String image = dataSnapshot.child("image").getValue().toString();
                    HashMap<String,String> map=new HashMap<>();
                    map.put("name",name);
                    map.put("image",image);
                    listServices.add(map);
                    adapterServiceCategory.notifyItemInserted(listServices.size());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}
package com.superfinishing.subItemDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.superfinishing.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SubItemDetailsActivity extends AppCompatActivity {

    ArrayList<HashMap<String,String>> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_item_details);

        RecyclerView recyclerView = findViewById(R.id.recyclerView16);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SubItemAdapter subItemAdapter=new SubItemAdapter(this,list);
        recyclerView.setAdapter(subItemAdapter);

        HashMap<String, String> data = (HashMap<String, String>) getIntent().getSerializableExtra("data");

        System.out.println("name.. "+data);
        FirebaseDatabase.getInstance().getReference().child("items").orderByChild("subCategory").equalTo(data.get("name")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String name = dataSnapshot.child("name").getValue().toString();
                    String image = dataSnapshot.child("image").getValue().toString();
                    String details = dataSnapshot.child("details").getValue().toString();
                    String mrp = dataSnapshot.child("mrp").getValue().toString();
                    String discount = dataSnapshot.child("discount").getValue().toString();
                    String qty = dataSnapshot.child("qty").getValue().toString();
                    String key = dataSnapshot.getKey();

                    HashMap<String,String> map=new HashMap<>();
                    map.put("name",name);
                    map.put("image",image);
                    map.put("details",details);
                    map.put("mrp",mrp);
                    map.put("discount",discount);
                    map.put("qty",qty);
                    map.put("key",key);
                    list.add(map);
                    subItemAdapter.notifyItemInserted(list.size());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}
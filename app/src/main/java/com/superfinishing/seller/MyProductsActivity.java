package com.superfinishing.seller;

import static com.superfinishing.Workspace.currentUser;

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
import com.superfinishing.subItemDetails.SubItemAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MyProductsActivity extends AppCompatActivity {

    ArrayList<HashMap<String,String>> list=new ArrayList<>();
    private MyProductsAdapter myProductsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products);
        RecyclerView recyclerView = findViewById(R.id.recyclerView27);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myProductsAdapter=new MyProductsAdapter(this,list);
        recyclerView.setAdapter(myProductsAdapter);

        FirebaseDatabase.getInstance().getReference().child("items").orderByChild("seller").equalTo(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String key = dataSnapshot.getKey();
                    String name = dataSnapshot.child("name").getValue().toString();
                    String image = dataSnapshot.child("image").getValue().toString();
                    String details = dataSnapshot.child("details").getValue().toString();
                    String mrp = dataSnapshot.child("mrp").getValue().toString();
                    String discount = dataSnapshot.child("discount").getValue().toString();
                    String qty = dataSnapshot.child("qty").getValue().toString();
                    HashMap<String,String> map=new HashMap<>();
                    map.put("key",key);
                    map.put("name",name);
                    map.put("image",image);
                    map.put("details",details);
                    map.put("mrp",mrp);
                    map.put("discount",discount);
                    map.put("qty",qty);
                    list.add(map);
                    myProductsAdapter.notifyItemInserted(list.size());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
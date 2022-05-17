package com.superfinishing.ui.products;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.superfinishing.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ArrayList<HashMap<String,String>> listCategory=new ArrayList<>();


        RecyclerView recyclerViewCategory = findViewById(R.id.recyclerView3);
        recyclerViewCategory.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
//        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(ProductListActivity.this,RecyclerView.HORIZONTAL,false));
        AdapterCategory adapterCategory = new AdapterCategory(ProductListActivity.this, listCategory,true,false);
        recyclerViewCategory.setAdapter(adapterCategory);
        PagerSnapHelper pagerSnapHelper3=new PagerSnapHelper();
        pagerSnapHelper3.attachToRecyclerView(recyclerViewCategory);

        FirebaseDatabase.getInstance().getReference().child("category").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String name = dataSnapshot.child("name").getValue().toString();
                    String image = dataSnapshot.child("image").getValue().toString();
                    String banner = dataSnapshot.child("banner").getValue().toString();
                    String details = dataSnapshot.child("details").getValue().toString();
                    HashMap<String,String> map=new HashMap<>();
                    map.put("name",name);
                    map.put("image",image);
                    map.put("banner",banner);
                    map.put("details",details);
                    listCategory.add(map);
                    adapterCategory.notifyItemInserted(listCategory.size());
                    System.out.println("LIST SIZE...... "+listCategory.size());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}
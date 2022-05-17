package com.superfinishing.categoryDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.superfinishing.R;
import com.superfinishing.ui.products.AdapterCategory;
import com.superfinishing.ui.products.AdapterFresh;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoryDetailsctivity extends AppCompatActivity {

    ArrayList<HashMap<String,String>> listCategory=new ArrayList<>();
    ArrayList<HashMap<String,String>> listBestValue=new ArrayList<>();
    ArrayList<HashMap<String,String>> listBlockBuster=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detailsctivity);

        RecyclerView recyclerViewBlockBuster = findViewById(R.id.recyclerView18);
        recyclerViewBlockBuster.setLayoutManager(new LinearLayoutManager(CategoryDetailsctivity.this,LinearLayoutManager.HORIZONTAL,false));
        AdapterFresh adapterBlockBuster = new AdapterFresh(CategoryDetailsctivity.this, listBlockBuster);
        recyclerViewBlockBuster.setAdapter(adapterBlockBuster);

        RecyclerView recyclerViewBestValue = findViewById(R.id.recyclerView19);
        recyclerViewBestValue.setLayoutManager(new StaggeredGridLayoutManager(2,LinearLayoutManager.HORIZONTAL));
        AdapterFresh adapterFresh = new AdapterFresh(CategoryDetailsctivity.this, listBestValue);
        recyclerViewBestValue.setAdapter(adapterFresh);

        RecyclerView recyclerViewSubCategory = findViewById(R.id.recyclerView15);
        recyclerViewSubCategory.setLayoutManager(new StaggeredGridLayoutManager(3,LinearLayoutManager.VERTICAL));
        AdapterCategory adapterCategory = new AdapterCategory(CategoryDetailsctivity.this, listCategory,false,false);
        recyclerViewSubCategory.setAdapter(adapterCategory);

        HashMap<String, String> data = (HashMap<String, String>) getIntent().getSerializableExtra("data");

        System.out.println("data .... "+data);
        ImageView imageViewLogo = findViewById(R.id.imageView25);
        ImageView imageViewBanner = findViewById(R.id.imageView24);
        TextView textViewMain = findViewById(R.id.textView80);
        TextView textViewHeader = findViewById(R.id.textView47);
        TextView textViewDetails = findViewById(R.id.textView83);

        Glide.with(this).load(data.get("banner")).into(imageViewBanner);
        Glide.with(this).load(data.get("image")).into(imageViewLogo);
        textViewMain.setText(data.get("name"));
        textViewHeader.setText(data.get("name"));
        textViewDetails.setText(data.get("details"));

        findViewById(R.id.imageView23).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        System.out.println("SUBCATEGORY >>>> "+data.get("name"));
        FirebaseDatabase.getInstance().getReference().child("subCategory").orderByChild("category").equalTo(data.get("name")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    System.out.println("DataSUBBBBB "+dataSnapshot);
                    String name = dataSnapshot.child("name").getValue().toString();
                    String image = dataSnapshot.child("image").getValue().toString();
                    HashMap<String,String> map=new HashMap<>();
                    map.put("name",name);
                    map.put("image",image);
                    listCategory.add(map);
                    adapterCategory.notifyItemInserted(listCategory.size());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        FirebaseDatabase.getInstance().getReference().child("items").orderByChild("blockBuster").equalTo("blockBuster").addListenerForSingleValueEvent(new ValueEventListener() {
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
                    listBlockBuster.add(map);
                    System.out.println("listBestValue... "+listBestValue);
                    adapterBlockBuster.notifyItemInserted(listBestValue.size());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        FirebaseDatabase.getInstance().getReference().child("items").orderByChild("bestValue").equalTo("bestValue").addListenerForSingleValueEvent(new ValueEventListener() {
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
                    listBestValue.add(map);
                    System.out.println("listBestValue... "+listBestValue);
                    adapterFresh.notifyItemInserted(listBestValue.size());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
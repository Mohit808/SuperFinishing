package com.superfinishing.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.superfinishing.R;
import com.superfinishing.ui.products.AdapterFresh;
import com.superfinishing.ui.services.AdapterServiceProvider;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {

    private TextWatcher textWatcher;
    private EditText editText;
    private ArrayList<HashMap<String,String>> list=new ArrayList<>();
    private ArrayList<HashMap<String,String>> listTopRated=new ArrayList<>();
    private RecyclerView recyclerView;
//    private AdapterSearch adapterSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String data = getIntent().getStringExtra("data");

        recyclerView = findViewById(R.id.recyclerView25);

        editText=findViewById(R.id.editTextTextPersonName21);
        textWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()){
                    if (data.equals("product")){
                        searchProduct(s.toString());
                    }else {
                        searchService(s.toString());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editText.addTextChangedListener(textWatcher);
    }

    private void searchProduct(String s){
        FirebaseDatabase.getInstance().getReference().child("items").orderByChild("name").startAt(s.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String name = dataSnapshot.child("name").getValue().toString();
                    String category = dataSnapshot.child("category").getValue().toString();
                    String details = dataSnapshot.child("details").getValue().toString();
                    String discount = dataSnapshot.child("discount").getValue().toString();
                    String image = dataSnapshot.child("image").getValue().toString();
                    String mrp = dataSnapshot.child("mrp").getValue().toString();
                    String qty = dataSnapshot.child("qty").getValue().toString();
                    String subCategory = dataSnapshot.child("subCategory").getValue().toString();
                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("name",name);
                    hashMap.put("category",category);
                    hashMap.put("details",details);
                    hashMap.put("discount",discount);
                    hashMap.put("image",image);
                    hashMap.put("mrp",mrp);
                    hashMap.put("qty",qty);
                    hashMap.put("subCategory",subCategory);
                    list.add(hashMap);
                }
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
                AdapterFresh adapterFresh = new AdapterFresh(SearchActivity.this, list);
                recyclerView.setAdapter(adapterFresh);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void searchService(String s){
        System.out.println("Service called...");
        FirebaseDatabase.getInstance().getReference().child("service").orderByChild("name").startAt(s).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String name = dataSnapshot.child("name").getValue().toString();
                    String image = dataSnapshot.child("image").getValue().toString();
                    String describe = dataSnapshot.child("describe").getValue().toString();
                    String rate = dataSnapshot.child("rate").getValue().toString();
                    String provider = dataSnapshot.child("provider").getValue().toString();
                    String key = dataSnapshot.getKey();

                    HashMap<String,String> map=new HashMap<>();
                    map.put("key",key);
                    map.put("name",name);
                    map.put("image",image);
                    map.put("describe",describe);
                    map.put("rate",rate);
                    map.put("provider",provider);
                    listTopRated.add(map);
                    System.out.println("Listtt service... "+listTopRated);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                AdapterServiceProvider adapterServiceProvider =new AdapterServiceProvider(SearchActivity.this,listTopRated);
                recyclerView.setAdapter(adapterServiceProvider);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editText.removeTextChangedListener(textWatcher);
    }
}
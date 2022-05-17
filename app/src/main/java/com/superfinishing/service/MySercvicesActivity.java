package com.superfinishing.service;

import static com.superfinishing.Workspace.currentUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.superfinishing.R;
import com.superfinishing.ui.services.AdapterServiceProvider;

import java.util.ArrayList;
import java.util.HashMap;

public class MySercvicesActivity extends AppCompatActivity {

    ArrayList<HashMap<String,String>> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sercvices);

        RecyclerView recyclerView = findViewById(R.id.recyclerView26);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.imageView56).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.button18).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MySercvicesActivity.this, ServiceRegisterActivity.class);
                intent.putExtra("data","data");
                startActivity(intent);
                finish();
            }
        });

        FirebaseDatabase.getInstance().getReference().child("service").orderByChild("provider").equalTo(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String provider = dataSnapshot.child("provider").getValue().toString();
                    String category = dataSnapshot.child("category").getValue().toString();
                    String name = dataSnapshot.child("name").getValue().toString();
                    String phone = dataSnapshot.child("phone").getValue().toString();
                    String image = dataSnapshot.child("image").getValue().toString();
                    String describe = dataSnapshot.child("describe").getValue().toString();
                    String rate = dataSnapshot.child("rate").getValue().toString();
                    String key = dataSnapshot.getKey();
                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("category",category);
                    hashMap.put("name",name);
                    hashMap.put("phone",phone);
                    hashMap.put("image",image);
                    hashMap.put("describe",describe);
                    hashMap.put("provider",provider);
                    hashMap.put("rate",rate);
                    hashMap.put("key",key);
                    list.add(hashMap);
                }
                recyclerView.setAdapter(new AdapterMyService(MySercvicesActivity.this,list));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
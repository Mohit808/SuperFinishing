package com.superfinishing.ui.accounts.myAddress;

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

import java.util.ArrayList;
import java.util.HashMap;

public class MyAddressActivity extends AppCompatActivity {

    ArrayList<HashMap<String,String>> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        RecyclerView recyclerView=findViewById(R.id.recyclerView20);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyAddressActivity.this));

        String fromAddress = getIntent().getStringExtra("fromAddress");



        findViewById(R.id.textView91).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyAddressActivity.this,AddNewAddressActivity.class);
                startActivity(intent);
            }
        });

        FirebaseDatabase.getInstance().getReference().child("myAddress").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String address = String.valueOf(dataSnapshot.child("address").getValue());
                    String type = String.valueOf(dataSnapshot.child("type").getValue());
                    String name = String.valueOf(dataSnapshot.child("name").getValue());
                    String key = dataSnapshot.getKey();
                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("address",address);
                    hashMap.put("type",type);
                    hashMap.put("name",name);
                    hashMap.put("key",key);
                    list.add(hashMap);
                }
                if(fromAddress!=null){
                    recyclerView.setAdapter(new AdapterMyAddress(MyAddressActivity.this,list,false));
                }else {
                    recyclerView.setAdapter(new AdapterMyAddress(MyAddressActivity.this,list,true));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
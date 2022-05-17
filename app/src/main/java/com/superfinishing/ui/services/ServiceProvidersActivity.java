package com.superfinishing.ui.services;

import static com.superfinishing.Workspace.currentUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.superfinishing.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ServiceProvidersActivity extends AppCompatActivity {

    ArrayList<HashMap<String,String>> list=new ArrayList<>();
    ArrayList<String> listAlreadyKey=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_providers);

        HashMap<String,String> hashMap = (HashMap<String, String>) getIntent().getSerializableExtra("data");

        TextView textView =findViewById(R.id.textView142);
        textView.setText(hashMap.get("name"));

        RecyclerView recyclerView =findViewById(R.id.recyclerView24);
        recyclerView.setLayoutManager(new LinearLayoutManager(ServiceProvidersActivity.this));
        AdapterServiceProvider adapterServiceProvider=new AdapterServiceProvider(this,list);
        recyclerView.setAdapter(adapterServiceProvider);

        FirebaseDatabase.getInstance().getReference().child("booking").child(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    listAlreadyKey.add(dataSnapshot.child("bookingKey").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("service").orderByChild("category").equalTo(hashMap.get("name")).addListenerForSingleValueEvent(new ValueEventListener() {
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
                    String ratings = String.valueOf(dataSnapshot.child("ratings").getValue());
                    String key = dataSnapshot.getKey();
                    if (!provider.equals(currentUser)){
                        HashMap<String,String> hashMap=new HashMap<>();
                        hashMap.put("category",category);
                        hashMap.put("name",name);
                        hashMap.put("phone",phone);
                        hashMap.put("image",image);
                        hashMap.put("describe",describe);
                        hashMap.put("provider",provider);
                        hashMap.put("rate",rate);
                        hashMap.put("key",key);
                        hashMap.put("ratings",ratings);
                        if (!listAlreadyKey.contains(key)){
                            list.add(hashMap);
                            adapterServiceProvider.notifyItemInserted(list.size());
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}
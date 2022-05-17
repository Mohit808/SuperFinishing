package com.superfinishing.ui.accounts.myOrders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.superfinishing.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyOrdersActivity extends AppCompatActivity {

    ArrayList<HashMap<String,String>> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        RecyclerView recyclerView=findViewById(R.id.recyclerView21);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyOrdersActivity.this));
        AdapterMyOrders adapterMyOrders = new AdapterMyOrders(MyOrdersActivity.this, list);

        recyclerView.setAdapter(adapterMyOrders);


        SharedPreferences sharedPreferences= getSharedPreferences("cart", Context.MODE_PRIVATE);
        Map<String, ?> map = sharedPreferences.getAll();
        Iterator<String> keys = map.keySet().iterator();

        while (keys.hasNext()){
            Object string = map.get(keys.next());
            HashMap maping = new Gson().fromJson(string.toString(), HashMap.class);
            System.out.println("Maping..... "+maping);
//            list.add(maping);
//            adapterCart.notifyItemInserted(list.size());
//            String Count = maping.get("Count").toString();
//            int mrp = Integer.parseInt(maping.get("mrp").toString().replaceAll("[\\D]", ""));
//            int total = Integer.valueOf(Count) * mrp;
//            allTotal=allTotal+total;
        }



        FirebaseDatabase.getInstance().getReference().child("myOrders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                System.out.println("snapshot... "+snapshot);
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
//                    System.out.println("datasnapshot... "+dataSnapshot.child("total").getValue());
                    String count = String.valueOf(dataSnapshot.child("count").getValue());
                    String total = String.valueOf(dataSnapshot.child("total").getValue());
                    String orderDate = String.valueOf(dataSnapshot.child("orderDate").getValue());
                    String deliveryTime = String.valueOf(dataSnapshot.child("deliveryTime").getValue());
                    String status = String.valueOf(dataSnapshot.child("status").getValue());
                    String orderId = String.valueOf(dataSnapshot.child("orderId").getValue());
                    String address = String.valueOf(dataSnapshot.child("address").getValue());
                    String addressType = String.valueOf(dataSnapshot.child("addressType").getValue());
                    String addressName = String.valueOf(dataSnapshot.child("addressName").getValue());

                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("count",count);
                    hashMap.put("total",total);
                    hashMap.put("orderDate",orderDate);
                    hashMap.put("deliveryTime",deliveryTime);
                    hashMap.put("status",status);
                    hashMap.put("orderId",orderId);
                    hashMap.put("address",address);
                    hashMap.put("addressType",addressType);
                    hashMap.put("addressName",addressName);

                    Integer number = Integer.valueOf(count);
                    for (int i=0;i<=number-1;i++){
                        String details = String.valueOf(dataSnapshot.child(String.valueOf(i)).getValue());
                        hashMap.put(String.valueOf(i),details);
                    }
                    list.add(hashMap);
                    adapterMyOrders.notifyItemInserted(list.size());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
package com.superfinishing.ui.accounts.myOrders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.gson.Gson;
import com.superfinishing.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class OrderDetailsActivity extends AppCompatActivity {

    ArrayList<HashMap<String,String>> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        HashMap<String,String> hashMap = (HashMap) getIntent().getSerializableExtra("data");
        System.out.println(hashMap);

        int count = Integer.valueOf(hashMap.get("count"));
        for(int i=0;i<=count-1;i++){
            String data = hashMap.get(String.valueOf(i));
            HashMap maping = new Gson().fromJson(data, HashMap.class);
            System.out.println("mAping... "+maping);
            list.add(maping);
        }

        RecyclerView recyclerView=findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderDetailsActivity.this));
        recyclerView.setAdapter(new AdapterOrderDetails(OrderDetailsActivity.this,list));
    }
}
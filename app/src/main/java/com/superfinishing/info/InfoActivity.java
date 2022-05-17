package com.superfinishing.info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.superfinishing.R;
import com.superfinishing.cart.CartActivity;
import com.superfinishing.ui.products.AdapterFresh;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class InfoActivity extends AppCompatActivity {

    ArrayList<HashMap<String,String>> listSimilar=new ArrayList<>();
    private AdapterFresh adapterSimilar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        HashMap<String,String> hashMap = (HashMap<String, String>) getIntent().getSerializableExtra("data");
        System.out.println("hashh "+hashMap);
        String from = getIntent().getStringExtra("from");

        if (from!=null){
            if (from.equals("cart")){
                findViewById(R.id.bestConstr).setVisibility(View.GONE);
            }

        }

        findViewById(R.id.imageView19).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView imageView = findViewById(R.id.imageView21);
        TextView textViewName = findViewById(R.id.textView64);
        TextView textViewMrp = findViewById(R.id.textView65);
        TextView textViewDiscount = findViewById(R.id.textView63);
        TextView textViewQty = findViewById(R.id.textView73);
        TextView textViewDetails = findViewById(R.id.textView76);
        Glide.with(InfoActivity.this).load(hashMap.get("image")).into(imageView);
        textViewName.setText(hashMap.get("name"));
        textViewMrp.setText(hashMap.get("mrp"));
        if (!hashMap.get("discount").equals("0")){
            textViewDiscount.setText(hashMap.get("discount"));
        }else {
            textViewDiscount.setVisibility(View.GONE);
        }
        textViewQty.setText(hashMap.get("qty"));
        textViewDetails.setText(hashMap.get("details"));


        RecyclerView recyclerView = findViewById(R.id.recyclerView16);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        adapterSimilar= new AdapterFresh(InfoActivity.this, listSimilar);
        recyclerView.setAdapter(adapterSimilar);

        searchSimilar();

        CardView cardViewPlus = findViewById(R.id.cardView2xyz);
        CardView cardViewMinus = findViewById(R.id.cardView2x);
        CardView cardViewFull = findViewById(R.id.cardView2y);
        TextView textViewItemCount = findViewById(R.id.textView115);
        TextView button=findViewById(R.id.textView71);

        if (hashMap.get("Count")!=null){
            if (!hashMap.get("Count").equals("0")){
                cardViewPlus.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);
                cardViewMinus.setVisibility(View.VISIBLE);
                cardViewFull.setVisibility(View.VISIBLE);
                cardViewMinus.setCardBackgroundColor(Color.YELLOW);
                cardViewFull.setCardBackgroundColor(Color.YELLOW);
                cardViewPlus.setCardBackgroundColor(Color.YELLOW);
                textViewItemCount.setText(hashMap.get("Count"));

            }
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                cardViewMinus.setVisibility(View.VISIBLE);
                cardViewPlus.setVisibility(View.VISIBLE);
//                cardViewFull.setVisibility(View.VISIBLE);
//                cardViewPlus.setCardBackgroundColor(Color.YELLOW);
//                cardViewMinus.setCardBackgroundColor(Color.YELLOW);
//                cardViewFull.setCardBackgroundColor(Color.YELLOW);
                button.setVisibility(View.GONE);


                cardViewMinus.setVisibility(View.VISIBLE);
                cardViewFull.setVisibility(View.VISIBLE);
                cardViewMinus.setCardBackgroundColor(Color.YELLOW);
                cardViewFull.setCardBackgroundColor(Color.YELLOW);
                cardViewPlus.setCardBackgroundColor(Color.YELLOW);

                Snackbar snackbar = Snackbar.make(cardViewPlus, "", Snackbar.LENGTH_LONG);
                Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
                TextView textView = (TextView) layout.findViewById(R.id.snackbar_text);
                textView.setVisibility(View.INVISIBLE);



                Integer count = Integer.valueOf(textViewItemCount.getText().toString());
                count=count+1;
                textViewItemCount.setText(count.toString());

//                    System.out.println("list ......... "+listBestValue.get(getLayoutPosition()));
                SharedPreferences sharedPreferences= getSharedPreferences("cart", Context.MODE_PRIVATE);
//                HashMap<String, String> hashMap = list.get(getLayoutPosition());
                hashMap.put("Count",count.toString());
                JSONObject json = new JSONObject(hashMap);
                sharedPreferences.edit().putString(hashMap.get("key"), String.valueOf(json)).apply();

                View snackView = LayoutInflater.from(InfoActivity.this).inflate(R.layout.my_snackbar, null);
                TextView billText = snackView.findViewById(R.id.textView113);
                billText.setText(count.toString()+" Items ");
                layout.setPadding(0,0,0,0);
                layout.addView(snackView, 0);
//                    if (count==1){
                snackbar.show();
//                    }
                layout.findViewById(R.id.cons).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(InfoActivity.this, CartActivity.class));
                    }
                });
            }
        });
        cardViewPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardViewMinus.setVisibility(View.VISIBLE);
                cardViewFull.setVisibility(View.VISIBLE);
                cardViewMinus.setCardBackgroundColor(Color.YELLOW);
                cardViewFull.setCardBackgroundColor(Color.YELLOW);
                cardViewPlus.setCardBackgroundColor(Color.YELLOW);

                Snackbar snackbar = Snackbar.make(cardViewPlus, "", Snackbar.LENGTH_LONG);
                Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
                TextView textView = (TextView) layout.findViewById(R.id.snackbar_text);
                textView.setVisibility(View.INVISIBLE);



                Integer count = Integer.valueOf(textViewItemCount.getText().toString());
                count=count+1;
                textViewItemCount.setText(count.toString());

//                    System.out.println("list ......... "+listBestValue.get(getLayoutPosition()));
                SharedPreferences sharedPreferences= getSharedPreferences("cart", Context.MODE_PRIVATE);
//                HashMap<String, String> hashMap = list.get(getLayoutPosition());
                hashMap.put("Count",count.toString());
                JSONObject json = new JSONObject(hashMap);
                sharedPreferences.edit().putString(hashMap.get("key"), String.valueOf(json)).apply();

//                layout.findViewById(R.id.cons).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startActivity(new Intent(InfoActivity.this, CartActivity.class));
//                    }
//                });

                Intent intent = new Intent("cartUpdate");intent.putExtra("hello","hello");
                sendBroadcast(intent);
            }
        });

        cardViewMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Integer.valueOf(textViewItemCount.getText().toString())>1){
                    Integer count = Integer.valueOf(textViewItemCount.getText().toString());
                    count=count-1;
                    textViewItemCount.setText(count.toString());

                    SharedPreferences sharedPreferences= getSharedPreferences("cart",Context.MODE_PRIVATE);
                    hashMap.put("Count",count.toString());
                    JSONObject json = new JSONObject(hashMap);
                    sharedPreferences.edit().putString(hashMap.get("key"), String.valueOf(json)).apply();

                }else {
                    cardViewMinus.setVisibility(View.GONE);
                    cardViewFull.setVisibility(View.GONE);
                    cardViewPlus.setCardBackgroundColor(Color.WHITE);
                    cardViewPlus.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);

                    Integer count = Integer.valueOf(textViewItemCount.getText().toString());
                    count=count-1;
                    textViewItemCount.setText(count.toString());

                    if (count==0){
                        SharedPreferences sharedPreferences= getSharedPreferences("cart",Context.MODE_PRIVATE);
                        sharedPreferences.edit().remove(hashMap.get("key")).apply();
                    }
//                        cardViewMinus.setVisibility(View.GONE);
//                        cardViewPlus.setVisibility(View.GONE);
//                        cardViewFull.setVisibility(View.GONE);
                }
                Intent intent = new Intent("cartUpdate");intent.putExtra("hello","hello");
                sendBroadcast(intent);
            }
        });
    }

    private void searchSimilar(){
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
                    String key=dataSnapshot.getKey();
                    HashMap<String,String> map=new HashMap<>();
                    map.put("name",name);
                    map.put("image",image);
                    map.put("details",details);
                    map.put("mrp",mrp);
                    map.put("discount",discount);
                    map.put("qty",qty);
                    map.put("key",key);
                    listSimilar.add(map);
                    adapterSimilar.notifyItemInserted(listSimilar.size());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}
package com.superfinishing.ui.products;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.internal.LinkedTreeMap;
import com.superfinishing.R;
import com.superfinishing.cart.CartActivity;
import com.superfinishing.info.InfoActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterFresh extends RecyclerView.Adapter<AdapterFresh.HolderFresh> {
    Context context;
    ArrayList<HashMap<String, String>> listBestValue;

    public AdapterFresh(Context context, ArrayList<HashMap<String, String>> listBestValue) {
        this.context = context;
        this.listBestValue = listBestValue;
    }

    @NonNull
    @Override
    public HolderFresh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_fresh, parent, false);
        return new HolderFresh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderFresh holder, int position) {

        holder.textView.setText(listBestValue.get(position).get("name"));
        holder.textViewQty.setText(listBestValue.get(position).get("qty"));
        holder.textViewMrp.setText(listBestValue.get(position).get("mrp"));
        Glide.with(context).load(listBestValue.get(position).get("image")).into(holder.imageView);

        if (listBestValue.get(position).get("discount").equals("0")){
            holder.cardOff.setVisibility(View.GONE);
        }else {
            holder.cardOff.setVisibility(View.VISIBLE);
            holder.textViewOff.setText(listBestValue.get(position).get("discount"));
        }
    }

    @Override
    public int getItemCount() {
        return listBestValue.size();
    }

    public class HolderFresh extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView,textViewOff,textViewQty,textViewMrp;
        CardView cardOff;
        public HolderFresh(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageViewxyz);
            textView=itemView.findViewById(R.id.textView12xyz);
            CardView cardViewPlus = itemView.findViewById(R.id.cardView2xyz);
            CardView cardViewMinus = itemView.findViewById(R.id.cardView2x);
            CardView cardViewFull = itemView.findViewById(R.id.cardView2y);
            textViewOff = itemView.findViewById(R.id.textView21);
            cardOff = itemView.findViewById(R.id.cardOff);
            textViewQty = itemView.findViewById(R.id.textView16);
            textViewMrp = itemView.findViewById(R.id.textView17);


            TextView textViewItemCount = itemView.findViewById(R.id.textView115);
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

                    System.out.println("list ......... "+listBestValue.get(getLayoutPosition()));
                    SharedPreferences sharedPreferences= context.getSharedPreferences("cart",Context.MODE_PRIVATE);
                    HashMap<String, String> hashMap = listBestValue.get(getLayoutPosition());
                    hashMap.put("Count",count.toString());
                    listBestValue.set(getLayoutPosition(),hashMap);
                    JSONObject json = new JSONObject(hashMap);
                    System.out.println("key is=== "+listBestValue.get(getLayoutPosition()).get("key"));
                    sharedPreferences.edit().putString(listBestValue.get(getLayoutPosition()).get("key"), String.valueOf(json)).apply();

                    View snackView = LayoutInflater.from(context).inflate(R.layout.my_snackbar, null);
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
                            context.startActivity(new Intent(context, CartActivity.class));
                        }
                    });
                }
            });
            cardViewMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Integer.valueOf(textViewItemCount.getText().toString())>1){
                        Integer count = Integer.valueOf(textViewItemCount.getText().toString());
                        count=count-1;
                        textViewItemCount.setText(count.toString());

                        SharedPreferences sharedPreferences= context.getSharedPreferences("cart",Context.MODE_PRIVATE);
                        HashMap<String, String> hashMap = listBestValue.get(getLayoutPosition());
                        hashMap.put("Count",count.toString());
                        JSONObject json = new JSONObject(listBestValue.get(getLayoutPosition()));
                        sharedPreferences.edit().putString(listBestValue.get(getLayoutPosition()).get("key"), String.valueOf(json)).apply();

                        View snackView = LayoutInflater.from(context).inflate(R.layout.my_snackbar, null);
                        TextView billText = snackView.findViewById(R.id.textView113);
                        billText.setText(count.toString()+" Items ");
                        Snackbar snackbar = Snackbar.make(cardViewPlus, "", Snackbar.LENGTH_LONG);
                        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
                        layout.setPadding(0,0,0,0);
                        layout.addView(snackView, 0);
//                    if (count==1){
                        snackbar.show();
                        layout.findViewById(R.id.cons).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                context.startActivity(new Intent(context, CartActivity.class));
                            }
                        });
//                    }
                    }else {
                        cardViewMinus.setVisibility(View.GONE);
                        cardViewFull.setVisibility(View.GONE);
                        cardViewPlus.setCardBackgroundColor(Color.WHITE);

                        Integer count = Integer.valueOf(textViewItemCount.getText().toString());
                        count=count-1;
                        textViewItemCount.setText(count.toString());

                        if (count==0){
                            SharedPreferences sharedPreferences= context.getSharedPreferences("cart",Context.MODE_PRIVATE);
                            sharedPreferences.edit().remove(listBestValue.get(getLayoutPosition()).get("key")).apply();
                        }
                    }
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, InfoActivity.class);
                    intent.putExtra("data",listBestValue.get(getLayoutPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}

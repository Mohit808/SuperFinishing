package com.superfinishing.cart;

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
import com.superfinishing.R;
import com.superfinishing.info.InfoActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.HolderCart> {
    Context context;
    ArrayList<HashMap<String,String>> list;

    public AdapterCart(Context context, ArrayList<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HolderCart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_cart, parent, false);
        return new HolderCart(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderCart holder, int position) {
        HashMap<String, String> hashMap = list.get(position);
        Glide.with(context).load(hashMap.get("image")).into(holder.imageView);
        holder.textViewName.setText(hashMap.get("name"));
        holder.textViewMrp.setText(hashMap.get("mrp"));
        holder.textViewQty.setText(hashMap.get("qty"));
        holder.textViewItemCount.setText(hashMap.get("Count"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderCart extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewName,textViewMrp,textViewQty,textViewItemCount;
        public HolderCart(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView40);
            textViewName=itemView.findViewById(R.id.textView128);
            textViewMrp=itemView.findViewById(R.id.textView129);
            textViewQty=itemView.findViewById(R.id.textView130);

            CardView cardViewPlus = itemView.findViewById(R.id.cardView10);
            CardView cardViewMinus = itemView.findViewById(R.id.cardView11);
//            CardView cardViewFull = itemView.findViewById(R.id.cardView2y);

            textViewItemCount = itemView.findViewById(R.id.textView131);
            cardViewPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    cardViewMinus.setVisibility(View.VISIBLE);
//                    cardViewFull.setVisibility(View.VISIBLE);
                    cardViewMinus.setCardBackgroundColor(Color.YELLOW);
//                    cardViewFull.setCardBackgroundColor(Color.YELLOW);
                    cardViewPlus.setCardBackgroundColor(Color.YELLOW);

                    Integer count = Integer.valueOf(textViewItemCount.getText().toString());
                    count=count+1;
                    textViewItemCount.setText(count.toString());

//                    System.out.println("list ......... "+listBestValue.get(getLayoutPosition()));
                    SharedPreferences sharedPreferences= context.getSharedPreferences("cart",Context.MODE_PRIVATE);
                    HashMap<String, String> hashMap = list.get(getLayoutPosition());
                    hashMap.put("Count",count.toString());
                    JSONObject json = new JSONObject(hashMap);
//                    System.out.println("key is=== "+listBestValue.get(getLayoutPosition()).get("key"));
                    sharedPreferences.edit().putString(list.get(getLayoutPosition()).get("key"), String.valueOf(json)).apply();
                    Intent intent = new Intent("cartUpdate");intent.putExtra("hello","hello");
                    context.sendBroadcast(intent);
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
                        HashMap<String, String> hashMap = list.get(getLayoutPosition());
                        hashMap.put("Count",count.toString());
                        JSONObject json = new JSONObject(list.get(getLayoutPosition()));
                        sharedPreferences.edit().putString(list.get(getLayoutPosition()).get("key"), String.valueOf(json)).apply();

                    }else {
                        cardViewMinus.setVisibility(View.GONE);
                        cardViewPlus.setCardBackgroundColor(Color.WHITE);

                        Integer count = Integer.valueOf(textViewItemCount.getText().toString());
                        count=count-1;
                        textViewItemCount.setText(count.toString());

                        if (count==0){
                            SharedPreferences sharedPreferences= context.getSharedPreferences("cart",Context.MODE_PRIVATE);
                            sharedPreferences.edit().remove(list.get(getLayoutPosition()).get("key")).apply();
                        }
                        list.remove(getLayoutPosition());
                        notifyItemRemoved(getLayoutPosition());
                    }
                    Intent intent = new Intent("cartUpdate");intent.putExtra("hello","hello");
                    context.sendBroadcast(intent);
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, InfoActivity.class);
                    intent.putExtra("data",list.get(getLayoutPosition()));
                    intent.putExtra("from","cart");
                    context.startActivity(intent);
                }
            });

        }
    }
}

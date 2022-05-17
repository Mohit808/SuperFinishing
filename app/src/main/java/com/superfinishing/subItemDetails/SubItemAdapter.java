package com.superfinishing.subItemDetails;

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
import androidx.constraintlayout.widget.ConstraintLayout;
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

public class SubItemAdapter extends RecyclerView.Adapter<SubItemAdapter.SubItemHolder> {
    Context context;
    ArrayList<HashMap<String,String>> list;

    public SubItemAdapter(Context context, ArrayList<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SubItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_sub_item, parent, false);

        return new SubItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubItemHolder holder, int position) {
        holder.textViewName.setText(list.get(position).get("name"));
        holder.textViewQty.setText(list.get(position).get("qty"));
        holder.textViewMrp.setText(list.get(position).get("mrp"));
//        holder.textViewMrp.setText("Rs-"+list.get(position).get("Mrp"));
//        holder.textViewMrp.setText("Rs-"+list.get(position).get("Mrp"));
//        holder.textViewSale.setText("Rs-"+list.get(position).get("Salerate"));
//        holder.textViewSale.setText("Rs-"+list.get(position).get("Salerate"));
//        if (list.get(position).get("Discount").equals("0") || list.get(position).get("Discount").equals("null")){
//            holder.textViewDiscount.setVisibility(View.GONE);
//        }else {
//            holder.textViewDiscount.setVisibility(View.VISIBLE);
//            holder.textViewDiscount.setText("Rs-"+list.get(position).get("Discount"));
//        }
        Glide.with(context).load(list.get(position).get("image"))
                    .into(holder.imageView);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SubItemHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewName,textViewQty,textViewMrp,textViewDiscount,button;
        ConstraintLayout constraintLayout;
        public SubItemHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView41);
            textViewName=itemView.findViewById(R.id.textView85);
            textViewQty=itemView.findViewById(R.id.textView140);
            textViewMrp=itemView.findViewById(R.id.textView86);
            button=itemView.findViewById(R.id.textView141);
//            textViewDiscount=itemView.findViewById(R.id.textView80);
            constraintLayout=itemView.findViewById(R.id.sdafgf);
            CardView cardViewPlus = itemView.findViewById(R.id.cardView2xyz);
            CardView cardViewMinus = itemView.findViewById(R.id.cardView2x);
            CardView cardViewFull = itemView.findViewById(R.id.cardView2y);
            TextView textViewItemCount = itemView.findViewById(R.id.textView115);

            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, InfoActivity.class);
                    intent.putExtra("data",list.get(getLayoutPosition()));
                    context.startActivity(intent);
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardViewMinus.setVisibility(View.VISIBLE);
                    cardViewPlus.setVisibility(View.VISIBLE);
                    cardViewFull.setVisibility(View.VISIBLE);
                    cardViewPlus.setCardBackgroundColor(Color.YELLOW);
                    cardViewMinus.setCardBackgroundColor(Color.YELLOW);
                    cardViewFull.setCardBackgroundColor(Color.YELLOW);
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
                    SharedPreferences sharedPreferences= context.getSharedPreferences("cart",Context.MODE_PRIVATE);
                    HashMap<String, String> hashMap = list.get(getLayoutPosition());
                    hashMap.put("Count",count.toString());
                    JSONObject json = new JSONObject(list.get(getLayoutPosition()));
                    sharedPreferences.edit().putString(list.get(getLayoutPosition()).get("key"), String.valueOf(json)).apply();

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
                    SharedPreferences sharedPreferences= context.getSharedPreferences("cart",Context.MODE_PRIVATE);
                    HashMap<String, String> hashMap = list.get(getLayoutPosition());
                    hashMap.put("Count",count.toString());
                    JSONObject json = new JSONObject(list.get(getLayoutPosition()));
                    sharedPreferences.edit().putString(list.get(getLayoutPosition()).get("key"), String.valueOf(json)).apply();

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
                        HashMap<String, String> hashMap = list.get(getLayoutPosition());
                        hashMap.put("Count",count.toString());
                        JSONObject json = new JSONObject(list.get(getLayoutPosition()));
                        sharedPreferences.edit().putString(list.get(getLayoutPosition()).get("key"), String.valueOf(json)).apply();

                        View snackView = LayoutInflater.from(context).inflate(R.layout.my_snackbar, null);
                        TextView billText = snackView.findViewById(R.id.textView113);
                        billText.setText(count.toString()+" Items ");
                        Snackbar snackbar = Snackbar.make(cardViewPlus, "", Snackbar.LENGTH_LONG);
                        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
                        layout.setPadding(0,0,0,0);
                        layout.addView(snackView, 0);
//                    if (count==1){
                        snackbar.show();
//                    }
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
                            SharedPreferences sharedPreferences= context.getSharedPreferences("cart",Context.MODE_PRIVATE);
                            sharedPreferences.edit().remove(list.get(getLayoutPosition()).get("key")).apply();
                        }
//                        cardViewMinus.setVisibility(View.GONE);
//                        cardViewPlus.setVisibility(View.GONE);
//                        cardViewFull.setVisibility(View.GONE);
                    }
                }
            });


        }
    }
}

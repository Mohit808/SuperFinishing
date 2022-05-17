package com.superfinishing.seller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;
import com.superfinishing.R;
import com.superfinishing.info.InfoActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MyProductsAdapter extends RecyclerView.Adapter<MyProductsAdapter.SubItemHolder> {
    Context context;
    ArrayList<HashMap<String,String>> list;

    public MyProductsAdapter(Context context, ArrayList<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SubItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_my_products, parent, false);

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

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String key = list.get(getLayoutPosition()).get("key");
                    FirebaseDatabase.getInstance().getReference().child("items").child(key).removeValue();
                    list.remove(getLayoutPosition());
                    notifyDataSetChanged();
                }
            });
//            textViewDiscount=itemView.findViewById(R.id.textView80);
            constraintLayout=itemView.findViewById(R.id.vsdasfsadg);
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, InfoActivity.class);
                    intent.putExtra("data",list.get(getLayoutPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}

package com.superfinishing.ui.accounts.myOrders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.superfinishing.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterOrderDetails extends RecyclerView.Adapter<AdapterOrderDetails.HolderOrderDetails> {
    Context context;
    ArrayList<HashMap<String,String>> list;

    public AdapterOrderDetails(Context context, ArrayList<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HolderOrderDetails onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_order_details,parent,false);
        return new HolderOrderDetails(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderOrderDetails holder, int position) {

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

    public class HolderOrderDetails extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewName,textViewMrp,textViewQty,textViewItemCount;
        public HolderOrderDetails(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView40);
            textViewName=itemView.findViewById(R.id.textView128);
            textViewMrp=itemView.findViewById(R.id.textView129);
            textViewQty=itemView.findViewById(R.id.textView130);
            textViewItemCount = itemView.findViewById(R.id.textView131);
        }
    }
}

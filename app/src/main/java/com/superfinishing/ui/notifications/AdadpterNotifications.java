package com.superfinishing.ui.notifications;

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

public class AdadpterNotifications extends RecyclerView.Adapter<AdadpterNotifications.HolderNotifications> {
    Context context;
    ArrayList<HashMap<String,String>> list;

    public AdadpterNotifications(Context context, ArrayList<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HolderNotifications onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_notifications, parent, false);
        return  new HolderNotifications(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderNotifications holder, int position) {

        HashMap<String, String> hashMap = list.get(position);
        holder.textViewTitle.setText(hashMap.get("title"));
        holder.textViewDesc.setText(hashMap.get("desc"));
        Glide.with(context).load(hashMap.get("image")).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderNotifications extends RecyclerView.ViewHolder {
        TextView textViewTitle,textViewDesc;
        ImageView imageView;
        public HolderNotifications(@NonNull View itemView) {
            super(itemView);
            textViewTitle=itemView.findViewById(R.id.textView111);
            textViewDesc=itemView.findViewById(R.id.textView112);
            imageView=itemView.findViewById(R.id.imageView38);
        }
    }
}

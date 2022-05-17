package com.superfinishing.ui.products;

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

public class AdapterSlider extends RecyclerView.Adapter<AdapterSlider.HolderSlider> {
    Context context;
    ArrayList<HashMap<String,String>> list;

    public AdapterSlider(Context context, ArrayList<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HolderSlider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_slider, parent, false);
        return new HolderSlider(view);
    }
    @Override
    public void onBindViewHolder(@NonNull HolderSlider holder, int position) {

        HashMap<String, String> hashMap = list.get(position);
        Glide.with(context).load(hashMap.get("image")).into(holder.imageView);
        holder.textViewLeading.setText(hashMap.get("bigTxt"));
        holder.textViewSubtitle.setText(hashMap.get("smallTxt"));
        holder.textViewButton.setText(hashMap.get("btnText"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderSlider extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewLeading,textViewSubtitle,textViewButton;
        public HolderSlider(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            textViewLeading=itemView.findViewById(R.id.textView28);
            textViewSubtitle=itemView.findViewById(R.id.textView62);
            textViewButton=itemView.findViewById(R.id.textView61);

        }
    }
}

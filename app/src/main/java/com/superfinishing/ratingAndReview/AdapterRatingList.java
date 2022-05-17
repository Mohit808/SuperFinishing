package com.superfinishing.ratingAndReview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;
import com.superfinishing.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterRatingList extends RecyclerView.Adapter<AdapterRatingList.HolderRatingList> {
    Context context;
    ArrayList<HashMap<String,String>> list;
    boolean isRating;

    public AdapterRatingList(Context context, ArrayList<HashMap<String, String>> list,boolean isRating) {
        this.context = context;
        this.list = list;
        this.isRating=isRating;
    }

    @NonNull
    @Override
    public HolderRatingList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_rating_list, parent, false);
        return new HolderRatingList(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HolderRatingList holder, int position) {

        HashMap<String, String> hashMap = list.get(position);
        holder.textViewName.setText(hashMap.get("name"));
        Glide.with(context).load(hashMap.get("image")).into(holder.imageView);

        if (isRating==true){
            holder.ratingBar.setRating(Float.valueOf(hashMap.get("ratings")));
            holder.textViewReview.setVisibility(View.GONE);
        }else {
            holder.textViewReview.setText(hashMap.get("review"));
            holder.ratingBar.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderRatingList extends RecyclerView.ViewHolder {
        TextView textViewName;
        ImageView imageView;
        RatingBar ratingBar;
        TextView textViewReview;
        public HolderRatingList(@NonNull View itemView) {
            super(itemView);
            textViewName=itemView.findViewById(R.id.textView163);
            imageView=itemView.findViewById(R.id.imageView63);
            ratingBar=itemView.findViewById(R.id.ratingBar3);
            textViewReview=itemView.findViewById(R.id.textView164);
            ratingBar.setEnabled(false);
        }
    }
}

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
import com.google.gson.internal.LinkedTreeMap;
import com.superfinishing.R;

import java.util.ArrayList;

public class AdapterPopularService extends RecyclerView.Adapter<AdapterPopularService.HolderPopularService> {
    Context context;
    ArrayList<LinkedTreeMap<String, String>> listCategory;

    public AdapterPopularService(Context context, ArrayList<LinkedTreeMap<String, String>> listCategory) {
        this.context = context;
        this.listCategory = listCategory;
    }

    @NonNull
    @Override
    public HolderPopularService onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_popular_service, parent, false);
        return new HolderPopularService(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPopularService holder, int position) {
        System.out.println("Adadpterrrrr ");
//        holder.textView.setText(listCategory.get(position).get("Name"));
        if (position==0){
            holder.textView.setText("Grab");
            Glide.with(context).load("https://png.pngtree.com/png-clipart/20190906/original/pngtree-construction-vehicle-excavator-png-image_4583103.jpg")
                    .into(holder.imageView);
        }
        if (position==1){
            holder.textView.setText("Roller");
            Glide.with(context).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRheHWX7sNojChnPRyiIyx_ow-dreSbEHl2Ow&usqp=CAU")
                    .into(holder.imageView);
        }
        if (position==2){
            holder.textView.setText("Leader");
            Glide.with(context).load("https://img.favpng.com/14/17/6/caterpillar-inc-heavy-machinery-architectural-engineering-bulldozer-road-roller-png-favpng-Lg7mPswrGWS0ie0zCEqxStXNe.jpg")
                    .into(holder.imageView);
        }

        if (position==3){
            holder.textView.setText("Crane");
            Glide.with(context).load("https://pngimg.com/uploads/crane/crane_PNG8.png")
                    .into(holder.imageView);
        }

        if (position==4){
            holder.textView.setText("Pump truck");
            Glide.with(context).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTpCeD80Y-yL95emh6oh1B5pOgcSwBdPdlgxA&usqp=CAU")
                    .into(holder.imageView);
        }
        if (position==5){
            holder.textView.setText("Bulldozer");
            Glide.with(context).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTzTkiOOR_aUmXHkDKCqjgWFh_np4fl1vCZHQ&usqp=CAU")
                    .into(holder.imageView);
        }
        if (position==6){
            holder.textView.setText("Dump Truck");
            Glide.with(context).load("data:image/jpeg;base64,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")
                    .into(holder.imageView);
        }
    }


    @Override
    public int getItemCount() {
        return listCategory.size()+7;
    }

    public class HolderPopularService extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public HolderPopularService(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView36);
            imageView=itemView.findViewById(R.id.imageView9);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent=new Intent(context, CategoryDetailsActivity.class);
//                    context.startActivity(intent);
                }
            });
        }
    }
}

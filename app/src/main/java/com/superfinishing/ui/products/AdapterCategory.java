package com.superfinishing.ui.products;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.superfinishing.R;
import com.superfinishing.categoryDetails.CategoryDetailsctivity;
import com.superfinishing.subItemDetails.SubItemDetailsActivity;
import com.superfinishing.ui.services.ServiceProvidersActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.HolderCategory> {
    Context context;
    ArrayList<HashMap<String, String>> listCategory;
    boolean b;
    boolean isService;

    public AdapterCategory(Context context, ArrayList<HashMap<String, String>> listCategory, boolean b, boolean isService) {
        this.context = context;
        this.listCategory = listCategory;
        this.b = b;
        this.isService = isService;
    }

    @NonNull
    @Override
    public HolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_category, parent, false);
        return new HolderCategory(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderCategory holder, int position) {
        System.out.println("Adadpterrrrr ");
        holder.textView.setText(listCategory.get(position).get("name"));
        Glide.with(context).load(listCategory.get(position).get("image")).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return listCategory.size();
    }

    public class HolderCategory extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public HolderCategory(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView12);
            imageView=itemView.findViewById(R.id.imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isService){
                        Intent intent = new Intent(context, ServiceProvidersActivity.class);
                        intent.putExtra("data",listCategory.get(getLayoutPosition()));
                        context.startActivity(intent);
                    }else {
                        if (b){
                            Intent intent = new Intent(context, CategoryDetailsctivity.class);
                            intent.putExtra("data",listCategory.get(getLayoutPosition()));
                            context.startActivity(intent);
                        }else {
                            Intent intent = new Intent(context, SubItemDetailsActivity.class);
                            intent.putExtra("data",listCategory.get(getLayoutPosition()));
                            context.startActivity(intent);
                        }
                    }
                }
            });
        }
    }
}

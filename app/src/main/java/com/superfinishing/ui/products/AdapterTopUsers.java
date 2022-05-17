package com.superfinishing.ui.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.superfinishing.R;

public class AdapterTopUsers extends RecyclerView.Adapter<AdapterTopUsers.HolderTopUsers> {
    Context context;

    public AdapterTopUsers(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HolderTopUsers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_top_users, parent, false);
        return new HolderTopUsers(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderTopUsers holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class HolderTopUsers extends RecyclerView.ViewHolder {
        public HolderTopUsers(@NonNull View itemView) {
            super(itemView);
        }
    }
}

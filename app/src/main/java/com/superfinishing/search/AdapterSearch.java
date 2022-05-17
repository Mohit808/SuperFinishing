package com.superfinishing.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.superfinishing.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.HolderSearch> {
    Context context;
    ArrayList<HashMap<String,String>> list;

    public AdapterSearch(Context context, ArrayList<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HolderSearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_search,parent,false);
        return new HolderSearch(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderSearch holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderSearch extends RecyclerView.ViewHolder {
        public HolderSearch(@NonNull View itemView) {
            super(itemView);
        }
    }
}

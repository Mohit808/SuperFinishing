package com.superfinishing.ui.accounts.myAddress;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.superfinishing.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterMyAddress extends RecyclerView.Adapter<AdapterMyAddress.HolderMyAddress> {
    Context context;
    ArrayList<HashMap<String,String>> list;
    boolean fromAddress;

    public AdapterMyAddress(Context context, ArrayList<HashMap<String, String>> list,boolean fromAddress) {
        this.context = context;
        this.list = list;
        this.fromAddress = fromAddress;
    }

    @NonNull
    @Override
    public HolderMyAddress onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_my_address,parent,false);
        return new HolderMyAddress(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMyAddress holder, int position) {

        HashMap<String, String> hashMap = list.get(position);
        holder.textViewName.setText(hashMap.get("name"));
        holder.textViewAddress.setText(hashMap.get("address"));
        holder.textViewType.setText(hashMap.get("type"));

        if (fromAddress){
            holder.button.setBackgroundColor(Color.RED);
            holder.button.setText("Delete");
        }else {
            holder.button.setText("Use this");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderMyAddress extends RecyclerView.ViewHolder {
        TextView textViewType,textViewAddress,textViewName;
        Button button;
        public HolderMyAddress(@NonNull View itemView) {
            super(itemView);

            textViewType=itemView.findViewById(R.id.textView92);
            textViewName=itemView.findViewById(R.id.textView93);
            textViewAddress=itemView.findViewById(R.id.textView94);
            button=itemView.findViewById(R.id.button21);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fromAddress){
                        button.setBackgroundColor(Color.RED);
                        FirebaseDatabase.getInstance().getReference().child("myAddress").child(list.get(getLayoutPosition()).get("key")).removeValue();
                        list.remove(getLayoutPosition());
                    }else {

                        Intent intent=new Intent("addressSelected");
                        intent.putExtra("data",list.get(getLayoutPosition()));
                        context.sendBroadcast(intent);
                        ((Activity)context).finish();
                    }
                }
            });
        }
    }
}

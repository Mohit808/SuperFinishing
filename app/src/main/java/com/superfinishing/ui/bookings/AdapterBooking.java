package com.superfinishing.ui.bookings;

import static com.superfinishing.Workspace.currentUser;
import static com.superfinishing.Workspace.myName;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.superfinishing.R;
import com.superfinishing.Workspace;
import com.superfinishing.ui.services.ServiceDetailsActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterBooking extends RecyclerView.Adapter<AdapterBooking.HolderBooking> {
    Context context;
    ArrayList<HashMap<String,String>> list;

    public AdapterBooking(Context context, ArrayList<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HolderBooking onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_bookings,parent,false);
        return new HolderBooking(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderBooking holder, int position) {
        HashMap<String, String> hashMap = list.get(position);
        holder.textViewName.setText(hashMap.get("name"));
        holder.textViewCategory.setText(hashMap.get("category"));
        holder.textViewRate.setText(hashMap.get("rate"));
        holder.textViewDesc.setText(hashMap.get("describe"));
        holder.textViewStatus.setText(hashMap.get("status"));
        Glide.with(context).load(hashMap.get("image")).into(holder.imageView);

        if (!hashMap.get("provider").equals(currentUser)){
            holder.buttonAccept.setVisibility(View.GONE);
            holder.buttonReject.setVisibility(View.GONE);
            holder.buttonCancel.setVisibility(View.VISIBLE);
            if (hashMap.get("status").equals("booked")){holder.textViewStatus.setTextColor(Color.GREEN);}
            else {holder.textViewStatus.setTextColor(Color.BLACK);}
        }else {
            if (hashMap.get("status").equals("booked")){
                holder.buttonCancel.setVisibility(View.VISIBLE);
                holder.buttonAccept.setVisibility(View.GONE);
                holder.buttonReject.setVisibility(View.GONE);
                holder.textViewStatus.setTextColor(Color.GREEN);
            }else {
                holder.buttonAccept.setVisibility(View.VISIBLE);
                holder.buttonReject.setVisibility(View.VISIBLE);
                holder.buttonCancel.setVisibility(View.GONE);
                holder.textViewStatus.setTextColor(Color.BLACK);
            }
        }
        if (hashMap.get("provider").equals(currentUser)){
            holder.textViewProvider.setVisibility(View.VISIBLE);
            holder.textViewProvider1.setVisibility(View.VISIBLE);
        }else {

            holder.textViewProvider.setVisibility(View.GONE);
            holder.textViewProvider1.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderBooking extends RecyclerView.ViewHolder {
        TextView textViewName,textViewCategory,textViewRate,textViewDesc,textViewStatus;
        ImageView imageView;
        Button buttonCancel,buttonAccept,buttonReject;
        TextView textViewProvider,textViewProvider1;
        public HolderBooking(@NonNull View itemView) {
            super(itemView);
            textViewName=itemView.findViewById(R.id.textView32);
            textViewCategory=itemView.findViewById(R.id.textView37);
            textViewDesc=itemView.findViewById(R.id.textView39);
            textViewRate=itemView.findViewById(R.id.textView41);
            textViewStatus=itemView.findViewById(R.id.textView149);
            imageView=itemView.findViewById(R.id.imageView11);
            buttonCancel=itemView.findViewById(R.id.button14);
            buttonAccept=itemView.findViewById(R.id.button15);
            buttonReject=itemView.findViewById(R.id.button16);
            textViewProvider=itemView.findViewById(R.id.textView155);
            textViewProvider1=itemView.findViewById(R.id.textView156);

            textViewName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ServiceDetailsActivity.class);
                    intent.putExtra("data",list.get(getLayoutPosition()));
                    context.startActivity(intent);
                }
            });

            buttonAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myName=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    HashMap<String, String> hashMap = list.get(getLayoutPosition());
                    if (hashMap.get("provider").equals(currentUser)){
                        hashMap.put("status","booked");
                        FirebaseDatabase.getInstance().getReference().child("booking").child(currentUser).child(hashMap.get("key")).setValue(hashMap);
                        FirebaseDatabase.getInstance().getReference().child("booking").child(hashMap.get("bookedBy")).child(hashMap.get("key")).setValue(hashMap);
                        list.set(getLayoutPosition(),hashMap);
                        notifyItemChanged(getLayoutPosition());
                        Toast.makeText(context, "Booked Service", Toast.LENGTH_SHORT).show();

                        HashMap<String,String> hashMapX=new HashMap<>();
                        hashMapX.put("title",myName+" Accepted booking "+hashMap.get("name"));
                        hashMapX.put("desc","waiting for");
                        hashMapX.put("image","");
                        Workspace.sendMessage(context,hashMap.get("bookedBy"),hashMapX);
                    }
                }
            });
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap<String, String> hashMap = list.get(getLayoutPosition());

                    myName= FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    HashMap<String,String> hashMapX=new HashMap<>();
                    if (hashMap.get("provider").equals(currentUser)){
                        hashMapX.put("title",myName+" Rejected Your Booking "+hashMap.get("name"));
                        hashMapX.put("desc","waiting for");
                        hashMapX.put("image","");
                        Workspace.sendMessage(context,hashMap.get("bookedBy"),hashMapX);

                        FirebaseDatabase.getInstance().getReference().child("booking").child(currentUser).child(hashMap.get("key")).removeValue();
                        FirebaseDatabase.getInstance().getReference().child("booking").child(hashMap.get("bookedBy")).child(hashMap.get("key")).removeValue();
                        list.remove(getLayoutPosition());
                        notifyItemChanged(getLayoutPosition());
                        Toast.makeText(context, "You Rejected", Toast.LENGTH_SHORT).show();
                    }else {
                        hashMapX.put("title",myName+" Canceled Their Booking "+hashMap.get("name"));
                        hashMapX.put("desc","waiting for");
                        hashMapX.put("image","");
                        Workspace.sendMessage(context,hashMap.get("provider"),hashMapX);

                        FirebaseDatabase.getInstance().getReference().child("booking").child(currentUser).child(hashMap.get("key")).removeValue();
                        FirebaseDatabase.getInstance().getReference().child("booking").child(hashMap.get("provider")).child(hashMap.get("key")).removeValue();
                        list.remove(getLayoutPosition());
                        notifyItemChanged(getLayoutPosition());
                        Toast.makeText(context, "canceled", Toast.LENGTH_SHORT).show();
                    }



                }
            });

        }
    }
}

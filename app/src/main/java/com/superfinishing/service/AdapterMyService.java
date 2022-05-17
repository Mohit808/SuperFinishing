package com.superfinishing.service;

import static com.superfinishing.Workspace.currentUser;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;
import com.superfinishing.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterMyService extends RecyclerView.Adapter<AdapterMyService.HolderServiceProvider> {
    Context context;
    ArrayList<HashMap<String,String>> list;

    public AdapterMyService(Context context, ArrayList<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HolderServiceProvider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_my_service, parent, false);
        return new HolderServiceProvider(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HolderServiceProvider holder, int position) {

        HashMap<String, String> hashMap = list.get(position);
        holder.textViewName.setText(hashMap.get("name"));
        holder.textViewDesc.setText(hashMap.get("describe"));
        holder.textViewRate.setText(hashMap.get("rate"));

        Glide.with(context).load(hashMap.get("image")).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderServiceProvider extends RecyclerView.ViewHolder {
        TextView textViewName,textViewDesc,textViewOccupation,textViewRate;
        Button button;
        ImageView imageView;
        ConstraintLayout constraintLayoutCall,constraintLayoutMsg;
        public HolderServiceProvider(@NonNull View itemView) {
            super(itemView);
            textViewName=itemView.findViewById(R.id.textView143);
            textViewDesc=itemView.findViewById(R.id.textView144);
            textViewOccupation=itemView.findViewById(R.id.textView145);
            textViewRate=itemView.findViewById(R.id.textView146);
            button=itemView.findViewById(R.id.button12);
            imageView=itemView.findViewById(R.id.imageView48);
            constraintLayoutCall=itemView.findViewById(R.id.consCall);
            constraintLayoutMsg=itemView.findViewById(R.id.consMsg);

            constraintLayoutCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+list.get(getLayoutPosition()).get("phone")));
                    context.startActivity(intent);
                }
            });
            constraintLayoutMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                    smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
                    smsIntent.setType("vnd.android-dir/mms-sms");
                    smsIntent.setData(Uri.parse("sms:" + list.get(getLayoutPosition()).get("phone")));
                    context.startActivity(smsIntent);

                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap<String, String> hashMapx = list.get(getLayoutPosition());
//                    button.setText("Requested");
//                    String pushKey = FirebaseDatabase.getInstance().getReference().push().getKey();

//                    hashMapx.put("status","requested");
//                    hashMapx.put("bookedBy",currentUser);
//                    hashMapx.put("bookingFor",hashMapx.get("provider"));

                    FirebaseDatabase.getInstance().getReference().child("service").child(hashMapx.get("key")).removeValue();
                    list.remove(getLayoutPosition());
                    notifyDataSetChanged();
//                    FirebaseDatabase.getInstance().getReference().child("booking").child(currentUser).child(pushKey).setValue(hashMapx);
//                    FirebaseDatabase.getInstance().getReference().child("booking").child(hashMapx.get("provider")).child(pushKey).setValue(hashMapx);
                }
            });
        }
    }
}

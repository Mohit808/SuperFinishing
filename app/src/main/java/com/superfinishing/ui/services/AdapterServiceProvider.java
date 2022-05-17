package com.superfinishing.ui.services;

import static com.superfinishing.Workspace.currentUser;
import static com.superfinishing.Workspace.myName;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.superfinishing.R;
import com.superfinishing.Workspace;
import com.superfinishing.admin.AdminActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterServiceProvider extends RecyclerView.Adapter<AdapterServiceProvider.HolderServiceProvider> {
    Context context;
    ArrayList<HashMap<String,String>> list;

    public AdapterServiceProvider(Context context, ArrayList<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HolderServiceProvider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_service_provide, parent, false);
        return new HolderServiceProvider(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HolderServiceProvider holder, int position) {

        HashMap<String, String> hashMap = list.get(position);
        holder.textViewName.setText(hashMap.get("name"));
        holder.textViewDesc.setText(hashMap.get("describe"));
        holder.textViewRate.setText(hashMap.get("rate"));
        holder.textViewOccupation.setText(hashMap.get("category"));

        Glide.with(context).load(hashMap.get("image")).into(holder.imageView);

        if(!hashMap.get("ratings").equals("null")){
            holder.textViewRatings.setText(hashMap.get("ratings")+" person rated");
        }else {
            holder.textViewRatings.setText("no ratings");
        }

        if(hashMap.get("review")!=null){
            if(!hashMap.get("review").equals("null")){
                holder.textViewReview.setText(hashMap.get("review")+" person reviewed");
            }
        }else {
            holder.textViewReview.setText("no review");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderServiceProvider extends RecyclerView.ViewHolder {
        TextView textViewName,textViewDesc,textViewOccupation,textViewRate,textViewRatings,textViewReview;
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
            textViewRatings=itemView.findViewById(R.id.textView24);
            textViewReview=itemView.findViewById(R.id.textView33);

            constraintLayoutCall=itemView.findViewById(R.id.consCall);
            constraintLayoutMsg=itemView.findViewById(R.id.consMsg);

            textViewName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,ServiceDetailsActivity.class);
                    intent.putExtra("data",list.get(getLayoutPosition()));
                    context.startActivity(intent);
                }
            });
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
                    button.setText("Requested");
                    String pushKey = FirebaseDatabase.getInstance().getReference().push().getKey();

                    hashMapx.put("status","requested");
                    hashMapx.put("bookedBy",currentUser);
                    hashMapx.put("bookingKey",hashMapx.get("key"));


                    FirebaseDatabase.getInstance().getReference().child("booking").child(currentUser).child(pushKey).setValue(hashMapx);
                    FirebaseDatabase.getInstance().getReference().child("booking").child(hashMapx.get("provider")).child(pushKey).setValue(hashMapx);
                    button.setEnabled(false);

                    myName=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("title",myName+" booked You service "+hashMapx.get("name"));
                    hashMap.put("desc","waiting for");
                    hashMap.put("image","");
                    Workspace.sendMessage(context,hashMapx.get("provider"),hashMap);
                }
            });
        }
    }
}

package com.superfinishing.ui.accounts.myOrders;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.superfinishing.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterMyOrders extends RecyclerView.Adapter<AdapterMyOrders.HolderMyOrders> {
    Context context;
    ArrayList<HashMap<String,String>> list;

    public AdapterMyOrders(Context context, ArrayList<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HolderMyOrders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_my_orders,parent,false);
        return new HolderMyOrders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMyOrders holder, int position) {
        HashMap<String, String> hashMap = list.get(position);
        holder.textViewCount.setText(hashMap.get("count")+" Items");
        holder.textViewTotal.setText("Rs. "+hashMap.get("total"));
        holder.textViewOrderDate.setText("placed on "+hashMap.get("orderDate"));
        holder.textViewDeliveryDate.setText("scheduled for "+hashMap.get("deliveryTime"));
        holder.textViewStatus.setText(hashMap.get("status"));
        holder.textViewOrderId.setText("order id: "+hashMap.get("orderId"));
        holder.textViewAddress.setText(hashMap.get("address"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderMyOrders extends RecyclerView.ViewHolder {
        TextView textViewOrderDate,textViewCount,textViewTotal,textViewDeliveryDate,textViewOrderId,textViewStatus,textViewAddress;
        public HolderMyOrders(@NonNull View itemView) {
            super(itemView);
            textViewOrderDate=itemView.findViewById(R.id.textView98);
            textViewCount=itemView.findViewById(R.id.textView99);
            textViewTotal=itemView.findViewById(R.id.textView104);
            textViewDeliveryDate=itemView.findViewById(R.id.textView100);
            textViewOrderId=itemView.findViewById(R.id.textView101);
            textViewStatus=itemView.findViewById(R.id.textView103);
            textViewAddress=itemView.findViewById(R.id.textView96);

            itemView.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,OrderDetailsActivity.class);
                    intent.putExtra("data",list.get(getLayoutPosition()));
                    context.startActivity(intent);
//                    FirebaseDatabase.getInstance().getReference().child("myOrders").removeValue();
//                    String details = list.get(getLayoutPosition()).get("details");
//                    System.out.println("details... "+details);
//                    String string = new Gson().toJson(details, HashMap.class);
//                    HashMap maping = new Gson().fromJson(details, HashMap.class);
//                    System.out.println("details... "+maping);

                }
            });
        }
    }
}

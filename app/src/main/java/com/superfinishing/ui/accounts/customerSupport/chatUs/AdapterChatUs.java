package com.superfinishing.ui.accounts.customerSupport.chatUs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.superfinishing.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterChatUs extends RecyclerView.Adapter<AdapterChatUs.HolderChatUs> {
    Context context;
    ArrayList<HashMap<String,String>> list;

    public AdapterChatUs(Context context, ArrayList<HashMap<String, String>> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HolderChatUs onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_chat_us,parent,false);
        return new HolderChatUs(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderChatUs holder, int position) {

        HashMap<String, String> hashMap = list.get(position);
        if (hashMap.get("userKey").equals("1")){
            holder.textViewLeft.setText(hashMap.get("msg"));
            holder.textViewLeft.setVisibility(View.VISIBLE);
            holder.textViewRight.setVisibility(View.GONE);
        }else {
            holder.textViewRight.setText(hashMap.get("msg"));
            holder.textViewRight.setVisibility(View.VISIBLE);
            holder.textViewLeft.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderChatUs extends RecyclerView.ViewHolder {
        TextView textViewLeft,textViewRight;
        public HolderChatUs(@NonNull View itemView) {
            super(itemView);
            textViewLeft=itemView.findViewById(R.id.textView108);
            textViewRight=itemView.findViewById(R.id.textView109);
        }
    }
}

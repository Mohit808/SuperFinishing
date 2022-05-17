package com.superfinishing.admin;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.superfinishing.R;

import java.util.ArrayList;

public class AdapterDialog extends RecyclerView.Adapter<AdapterDialog.HolderDialog> {
    Context context;
    ArrayList<String> list;
    TextView categoryFieldText;
    Dialog dialog;

    public AdapterDialog(Context context, ArrayList<String> list, TextView categoryFieldText, Dialog dialog) {
        this.context = context;
        this.list = list;
        this.categoryFieldText = categoryFieldText;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public HolderDialog onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_dialog, parent, false);
        return new HolderDialog(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderDialog holder, int position) {
        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderDialog extends RecyclerView.ViewHolder {
        TextView textView;
        public HolderDialog(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView76);

            itemView.findViewById(R.id.constraintx).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryFieldText.setText(list.get(getLayoutPosition()));
                    dialog.dismiss();
                }
            });
        }
    }
}

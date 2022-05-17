package com.superfinishing.cities;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.superfinishing.MainActivity;
import com.superfinishing.R;

import java.util.ArrayList;

public class AdapterCity extends RecyclerView.Adapter<AdapterCity.HolderCity> {
    Context context;
    ArrayList<String> list;

    public AdapterCity(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HolderCity onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_city, parent, false);
        return new HolderCity(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderCity holder, int position) {
        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HolderCity extends RecyclerView.ViewHolder {
        TextView textView;
        public HolderCity(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView5);
            CardView cardView = itemView.findViewById(R.id.xzc);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardView.setCardBackgroundColor(Color.CYAN);
                    new AlertDialog.Builder(context)
                            .setTitle("Your city")
                            .setMessage("Are You Sure to continue with "+list.get(getLayoutPosition()))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                    SharedPreferences sharedPreferences=context.getSharedPreferences("location",MODE_PRIVATE);
                                    sharedPreferences.edit().putString("locality", list.get(getLayoutPosition())).apply();
                                    sharedPreferences.edit().putString("address", list.get(getLayoutPosition())).apply();
                                    context.startActivity(new Intent(context, MainActivity.class));
                                    ((Activity)context).finish();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    cardView.setCardBackgroundColor(Color.WHITE);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
        }
    }
}

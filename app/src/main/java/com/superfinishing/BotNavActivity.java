package com.superfinishing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.superfinishing.cart.CartActivity;
import com.superfinishing.databinding.ActivityBotNavBinding;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BotNavActivity extends AppCompatActivity {

    private ActivityBotNavBinding binding;
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBotNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_products, R.id.navigation_bookings, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_bot_nav);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    @Override
    protected void onStart() {
        super.onStart();
//        showSnack(navView,BotNavActivity.this);
    }

//    public static void showSnack(View navView, Context context){
//        SharedPreferences sharedPreferences= context.getSharedPreferences("cart", Context.MODE_PRIVATE);
//        Map<String, ?> map = sharedPreferences.getAll();
//        System.out.println("map.... "+map.keySet());
//        Iterator<String> keys = map.keySet().iterator();
//        System.out.println("map.size().... "+map.size());
//        int allTotal=0;
//        while (keys.hasNext()){
//            Object string = map.get(keys.next());
//            HashMap maping = new Gson().fromJson(string.toString(), HashMap.class);
//            System.out.println("Maping.... "+maping);
//            String Count = maping.get("Count").toString();
//            String Salerate = maping.get("Salerate").toString();
//            int total = Integer.valueOf(Count) * Integer.valueOf(Salerate);
//            allTotal=allTotal+total;
//        }
//        if (map.size()>0){
//            View snackView = LayoutInflater.from(context).inflate(R.layout.my_snackbar, null);
//            TextView textViewProceed = snackView.findViewById(R.id.textView114);
//            TextView textViewAllTotal = snackView.findViewById(R.id.textView113);
//            textViewAllTotal.setText(String.valueOf(map.size())+" item: Rs- "+String.valueOf(allTotal));
//            Snackbar snackbar = Snackbar.make(navView, "", 100000);
//            Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
//            TextView textView = (TextView) layout.findViewById(R.id.snackbar_text);
//            textView.setVisibility(View.INVISIBLE);
//            layout.setPadding(0,0,0,0);
//            layout.addView(snackView, 0);
//            snackbar.show();
//            layout.findViewById(R.id.cons).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    context.startActivity(new Intent(context, CartActivity.class));
//                }
//            });
//        }
//    }

}
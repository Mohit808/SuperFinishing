package com.superfinishing.maps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.gson.Gson;
import com.superfinishing.MainActivity;
import com.superfinishing.R;
import com.superfinishing.cities.AdapterCity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

public class MapsActivity extends AppCompatActivity {

    private BroadcastReceiver broadcastReceiver;
    private String locality;
    private String address;

    private ArrayList<String> listCity=new ArrayList<>();
    private ArrayList<String> listAllCity=new ArrayList<>();
    private TextWatcher watcher;
    private EditText editText;
    private RecyclerView recyclerView;
    private AdapterCity adapterCity;
    private String longitude;
    private String latitude;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        111);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        111);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ccc,new MapsFragment());
        fragmentTransaction.commit();

        ConstraintLayout button = findViewById(R.id.button17);
        ProgressBar progressBar=findViewById(R.id.progressBar2);
        TextView textViewButton=findViewById(R.id.textView3);
        TextView textViewLocation=findViewById(R.id.qwqw);

        checkLocationPermission();
        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                locality = intent.getStringExtra("locality");
                address = intent.getStringExtra("address");
                latitude = intent.getStringExtra("latitude");
                longitude = intent.getStringExtra("longitude");
//                button.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                textViewButton.setText("Use This Address");
                textViewLocation.setText(address);
            }
        };
        IntentFilter intentFilter=new IntentFilter("location");
        registerReceiver(broadcastReceiver,intentFilter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(locality!=null){

                    SharedPreferences sharedPreferences=getSharedPreferences("location",MODE_PRIVATE);
                    SharedPreferences.Editor spEdit = sharedPreferences.edit();
                    spEdit.putString("locality", locality);
                    spEdit.putString("address", address);
                    spEdit.putString("latitude", latitude);
                    spEdit.putString("longitude", longitude);
                    spEdit.apply();
                    startActivity(new Intent(MapsActivity.this, MainActivity.class));
                    finish();

//                    HashMap<String,String> hashMap=new HashMap<>();
//                    hashMap.put("lat",lat);
//                    hashMap.put("lang",lang);
//                    hashMap.put("userKey",currentUser);
//                    FirebaseDatabase.getInstance().getReference().child("locate").child(getPushKey()).setValue(hashMap);
                }

            }
        });

        recyclerView=findViewById(R.id.recyclerView13);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        feedData();

        editText = findViewById(R.id.editTextTextPersonName23);
        watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                }else {
                    recyclerView.setVisibility(View.VISIBLE);
                }
                if (!s.toString().isEmpty()) {
                    listCity.clear();
                    adapterCity.notifyDataSetChanged();
                    for (int i=0;i<listAllCity.size()-1;i++){
                        if (listAllCity.get(i).toLowerCase().contains(s.toString().toLowerCase())) {
                            System.out.println("Containing....... ");
                            listCity.add(listAllCity.get(i));
                            adapterCity.notifyItemInserted(listCity.size());
                        }
                    }
                }else {
                    feedData();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editText.addTextChangedListener(watcher);






        String apiKey = getString(R.string.google_maps_key);
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);


        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.PHOTO_METADATAS));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Toast.makeText(getApplicationContext(), place.getName(), Toast.LENGTH_SHORT).show();

                FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(Objects.requireNonNull(place.getPhotoMetadatas()).get(0))
                        .build();
                placesClient.fetchPhoto(photoRequest).addOnSuccessListener(
                        new OnSuccessListener<FetchPhotoResponse>() {
                            @Override
                            public void onSuccess(FetchPhotoResponse response) {
                                Bitmap bitmap = response.getBitmap();
                                System.out.println("bitmap..... "+bitmap);
//                                ((ImageView)findViewById(R.id.img)).setImageBitmap(bitmap);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                exception.printStackTrace();
                            }
                        });
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void feedData(){
        listCity.clear();
        listAllCity.clear();
        try {
            InputStream inputStream = getAssets().open("countriesToCities.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            System.out.println("json.... "+json);
            HashMap<String, ArrayList<String>> maping = new Gson().fromJson(json, HashMap.class);
            ArrayList<String> abcd = maping.get("India");
            listCity.addAll(abcd);
            listAllCity.addAll(abcd);
            Collections.sort(listCity);
            Collections.sort(listAllCity);
            adapterCity=new AdapterCity(this,listCity);
            recyclerView.setAdapter(adapterCity);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        editText.removeTextChangedListener(watcher);
    }
}
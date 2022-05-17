package com.superfinishing.maps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;
import com.superfinishing.R;

import java.util.List;
import java.util.Locale;

public class MapsFragment extends Fragment {

    int a=0;
    SupportMapFragment mapFragment;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng sydney = new LatLng(28.7041, 77.1025);
            MarkerOptions markerOptions = new MarkerOptions().position(sydney).title("My location");
//            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.redcircle));
//            googleMap.addMarker(markerOptions);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10));
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);


//            LatLng dis2 = new LatLng(28.512936, 77.524522);
//            float[] result = new float[1];
//            Location.distanceBetween(sydney.latitude,sydney.longitude,dis2.latitude,dis2.longitude,result);
//
//            int abbc = Math.round(result[0]);
//            System.out.println("readddddddddddddddddd");
//            System.out.println(result);
//            System.out.println(abbc);

            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull LatLng latLng) {

                    googleMap.clear();
                    Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng).title("Hey! I'm Here"));
                    googleMap.getUiSettings().setMyLocationButtonEnabled(true);
//                    Intent intent=new Intent("location");
//                    intent.putExtra("lat",String.valueOf(latLng.latitude));
//                    intent.putExtra("lang",String.valueOf(latLng.longitude));
//                    getContext().sendBroadcast(intent);
                    getMapData(latLng.latitude,latLng.longitude);
                }
            });

            CancellationToken cancellationToken=new CancellationToken() {
                @Override
                public boolean isCancellationRequested() {
                    return false;
                }

                @NonNull
                @Override
                public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                    return null;
                }
            };
            LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                buildAlertMessageNoGps();
            }
            System.out.println("Helllllllllllllllo44444");
            googleMap.setMyLocationEnabled(true);
            FusedLocationProviderClient fff = LocationServices.getFusedLocationProviderClient(getContext());
            fff.getCurrentLocation(100,cancellationToken).addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    System.out.println("Helllllllllllllllo55555555");

                    LatLng sydney = new LatLng(task.getResult().getLatitude(), task.getResult().getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(sydney).title("Delivery Location"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
                    googleMap.getUiSettings().setZoomControlsEnabled(true);
                    googleMap.getUiSettings().setMapToolbarEnabled(true);
                    googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                    googleMap.getUiSettings().setZoomGesturesEnabled(true);
                    getMapData(task.getResult().getLatitude(),task.getResult().getLongitude());
//                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10));
                }
            });

        }
    };

    private void getMapData(double latitude,double longitude){
        try {
            Geocoder geo = new Geocoder(getContext().getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
            if (addresses.isEmpty()) {
//                yourtextfieldname.setText("Waiting for Location");
            }
            else {
                if (addresses.size() > 0) {
                    System.out.println("Addresss.... "+addresses.get(0).getLocality());
                    System.out.println("Addresss2.... "+addresses.get(0).getAddressLine(0));
                    System.out.println("Addresss3.... "+addresses.get(0).getPhone());
                    System.out.println("Addresss4.... "+addresses.get(0).getSubLocality());
                    System.out.println("Addresss5.... "+addresses.get(0).getPostalCode());
                    Intent intent=new Intent("location");
                    intent.putExtra("locality",addresses.get(0).getLocality());
                    intent.putExtra("address",addresses.get(0).getAddressLine(0));
                    intent.putExtra("latitude",latitude);
                    intent.putExtra("longitude",longitude);
                    getContext().sendBroadcast(intent);

//                    yourtextfieldname.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
//                    Toast.makeText(getContext(), "Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();
//                    Toast.makeText(getContext(), "Address:- " +addresses.get(0).getAddressLine(0), Toast.LENGTH_LONG).show();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapFragment!=null&&a==1){
            mapFragment.getMapAsync(callback);
        }else {
            a=1;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("pauseeeeeee ");
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}
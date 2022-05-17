package com.superfinishing.ui.products;

import static com.superfinishing.Workspace.currentUser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.superfinishing.R;
import com.superfinishing.cart.CartActivity;
import com.superfinishing.categoryDetails.CategoryDetailsctivity;
import com.superfinishing.databinding.FragmentProductsBinding;
import com.superfinishing.maps.MapsActivity;
import com.superfinishing.model.ServiceCategoryModel;
import com.superfinishing.search.SearchActivity;
import com.superfinishing.ui.services.AdapterServiceProvider;
import com.superfinishing.ui.services.ServiceListActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductsFragment extends Fragment {

    private FragmentProductsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ArrayList<HashMap<String,String>> listCategory=new ArrayList<>();
        ArrayList<HashMap<String,String>> listBestValue=new ArrayList<>();
        ArrayList<HashMap<String,String>> listBlockBuster=new ArrayList<>();
        ArrayList<HashMap<String,String>> listAppliance=new ArrayList<>();
        ArrayList<HashMap<String,String>> listMustHave=new ArrayList<>();

        ArrayList<HashMap<String,String>> listServices=new ArrayList<>();
        ArrayList<HashMap<String,String>> listTopRated=new ArrayList<>();
        ArrayList<String> listAlreadyKey=new ArrayList<>();

        binding = FragmentProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences sharedPreferences=getContext().getSharedPreferences("location", Context.MODE_PRIVATE);
        String locality = sharedPreferences.getString("locality", "");
        String address = sharedPreferences.getString("address", "");
        if (locality!=null){
            if (!locality.isEmpty()){
                binding.textView2.setText(locality);
            }
        }

        root.findViewById(R.id.textView23).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ProductListActivity.class);
                startActivity(intent);
            }
        });

        root.findViewById(R.id.textView25).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ServiceListActivity.class);
                startActivity(intent);
            }
        });


        /////////////////////////////////Services Start/////////////////////////////////////////
        RecyclerView recyclerViewOurServices = root.findViewById(R.id.recyclerView9);
//        recyclerViewOurServices.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        recyclerViewOurServices.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        AdapterCategory adapterServiceCategory = new AdapterCategory(getContext(), listServices, false,true);
        recyclerViewOurServices.setAdapter(adapterServiceCategory);

        RecyclerView recyclerViewTopUser = root.findViewById(R.id.recyclerView10);
        recyclerViewTopUser.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        AdapterServiceProvider adapterServiceProvider=new AdapterServiceProvider(getContext(),listTopRated);
        recyclerViewTopUser.setAdapter(adapterServiceProvider);

//        RecyclerView recyclerViewPopularService = view.findViewById(R.id.recyclerView3);
//        recyclerViewPopularService.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
//        recyclerViewPopularService.setAdapter(new AdapterPopularService(getContext(),new ArrayList<>()));

        root.findViewById(R.id.editTextTextPersonName22).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SearchActivity.class);
                intent.putExtra("data","service");
                startActivity(intent);
            }
        });

        FirebaseDatabase.getInstance().getReference().child("serviceCategory").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("Dataaaaaaaaaaaaaaaaaaaaaa");
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    ServiceCategoryModel serviceCategoryModel= dataSnapshot.getValue(ServiceCategoryModel.class);
                    System.out.println("serviceModel.. "+serviceCategoryModel.getName());
                    String name = dataSnapshot.child("name").getValue().toString();
                    String image = dataSnapshot.child("image").getValue().toString();
                    HashMap<String,String> map=new HashMap<>();
                    map.put("name",name);
                    map.put("image",image);
                    listServices.add(map);
                    adapterServiceCategory.notifyItemInserted(listServices.size());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        FirebaseDatabase.getInstance().getReference().child("booking").child(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if (dataSnapshot.child("bookingKey").getValue()!=null){
                        listAlreadyKey.add(dataSnapshot.child("bookingKey").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("service").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String name = String.valueOf(dataSnapshot.child("name").getValue());
                    String image = String.valueOf(dataSnapshot.child("image").getValue());
                    String describe = String.valueOf(dataSnapshot.child("describe").getValue());
                    String category = String.valueOf(dataSnapshot.child("category").getValue());
                    String rate = String.valueOf(dataSnapshot.child("rate").getValue());
                    String provider = String.valueOf(dataSnapshot.child("provider").getValue());
                    String ratings = String.valueOf(dataSnapshot.child("ratings").getValue());
                    String review = String.valueOf(dataSnapshot.child("review").getValue());
                    String key = dataSnapshot.getKey();

                    System.out.println("mmmmmmmm "+ratings);
//                    if(name.equals("null")){
//                        System.out.println("nulllllllllValue "+key);
//                        FirebaseDatabase.getInstance().getReference().child("service").child(key).removeValue();
//                    }

                    if (!provider.equals(currentUser) && !name.equals("null")){
                        HashMap<String,String> map=new HashMap<>();
                        map.put("key",key);
                        map.put("name",name);
                        map.put("image",image);
                        map.put("describe",describe);
                        map.put("category",category);
                        map.put("rate",rate);
                        map.put("provider",provider);
                        map.put("ratings",ratings);
                        map.put("review",review);
                        if (!listAlreadyKey.contains(key)){
                            listTopRated.add(map);
                            adapterServiceProvider.notifyItemInserted(listTopRated.size());
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        /////////////////////////////////Services End/////////////////////////////////////////

        root.findViewById(R.id.textView2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MapsActivity.class);
                startActivity(intent);getActivity().finish();
            }
        });
        root.findViewById(R.id.imageView58).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), CartActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerViewSlider = root.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSlider.setLayoutManager(linearLayoutManager1);
        PagerSnapHelper pagerSnapHelper=new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerViewSlider);

        ArrayList<HashMap<String,String>> listSlider=new ArrayList<>();
        AdapterSlider sliderAdapter=new AdapterSlider(getContext(),listSlider);
        recyclerViewSlider.setAdapter(sliderAdapter);

        RecyclerView recyclerViewCategory = root.findViewById(R.id.recyclerView4);
//        recyclerViewCategory.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        AdapterCategory adapterCategory = new AdapterCategory(getContext(), listCategory,true,false);
        recyclerViewCategory.setAdapter(adapterCategory);
        PagerSnapHelper pagerSnapHelper3=new PagerSnapHelper();
        pagerSnapHelper3.attachToRecyclerView(recyclerViewCategory);

        RecyclerView recyclerViewBlockbuster = root.findViewById(R.id.recyclerView5);
        recyclerViewBlockbuster.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        AdapterFresh adapterBlockBuster = new AdapterFresh(getContext(), listBlockBuster);
        recyclerViewBlockbuster.setAdapter(adapterBlockBuster);

        RecyclerView recyclerViewAppliance = root.findViewById(R.id.recyclerView6);
        recyclerViewAppliance.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        AdapterFresh adapterAppliance = new AdapterFresh(getContext(), listAppliance);
        recyclerViewAppliance.setAdapter(adapterAppliance);


        RecyclerView recyclerViewMustHave = root.findViewById(R.id.recyclerView23);
        recyclerViewMustHave.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        AdapterFresh adapterMustHave = new AdapterFresh(getContext(), listMustHave);
        recyclerViewMustHave.setAdapter(adapterAppliance);

        RecyclerView recyclerViewBestValue = root.findViewById(R.id.recyclerView19x);
        recyclerViewBestValue.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        AdapterFresh adapterFresh = new AdapterFresh(getContext(),listBestValue);
        recyclerViewBestValue.setAdapter(adapterFresh);


        root.findViewById(R.id.editTextTextPersonName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SearchActivity.class);
                intent.putExtra("data","product");
                startActivity(intent);

            }
        });

        FirebaseDatabase.getInstance().getReference().child("slider").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String image = dataSnapshot.child("image").getValue().toString();
                    String btnText = dataSnapshot.child("btnText").getValue().toString();
                    String bigTxt = dataSnapshot.child("bigTxt").getValue().toString();
                    String smallTxt = dataSnapshot.child("smallTxt").getValue().toString();
                    HashMap<String,String> hashMapX=new HashMap<>();
                    hashMapX.put("image",image);
                    hashMapX.put("btnText",btnText);
                    hashMapX.put("bigTxt",bigTxt);
                    hashMapX.put("smallTxt",smallTxt);
                    listSlider.add(hashMapX);
                }
                sliderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("category").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String name = dataSnapshot.child("name").getValue().toString();
                    String image = dataSnapshot.child("image").getValue().toString();
                    String banner = dataSnapshot.child("banner").getValue().toString();
                    String details = dataSnapshot.child("details").getValue().toString();
                    HashMap<String,String> map=new HashMap<>();
                    map.put("name",name);
                    map.put("image",image);
                    map.put("banner",banner);
                    map.put("details",details);
                    listCategory.add(map);
                    adapterCategory.notifyItemInserted(listCategory.size());
                    System.out.println("LIST SIZE...... "+listCategory.size());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        FirebaseDatabase.getInstance().getReference().child("items").orderByChild("blockBuster").equalTo("blockBuster").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String name = dataSnapshot.child("name").getValue().toString();
                    String image = dataSnapshot.child("image").getValue().toString();
                    String details = dataSnapshot.child("details").getValue().toString();
                    String mrp = dataSnapshot.child("mrp").getValue().toString();
                    String discount = dataSnapshot.child("discount").getValue().toString();
                    String qty = dataSnapshot.child("qty").getValue().toString();
                    String key = dataSnapshot.getKey();
                    HashMap<String,String> map=new HashMap<>();
                    map.put("name",name);
                    map.put("image",image);
                    map.put("details",details);
                    map.put("mrp",mrp);
                    map.put("discount",discount);
                    map.put("qty",qty);
                    map.put("key",key);
                    listBlockBuster.add(map);
                    adapterBlockBuster.notifyItemInserted(listBestValue.size());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        FirebaseDatabase.getInstance().getReference().child("items").orderByChild("appliance").equalTo("appliance").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String name = dataSnapshot.child("name").getValue().toString();
                    String image = dataSnapshot.child("image").getValue().toString();
                    String details = dataSnapshot.child("details").getValue().toString();
                    String mrp = dataSnapshot.child("mrp").getValue().toString();
                    String discount = dataSnapshot.child("discount").getValue().toString();
                    String qty = dataSnapshot.child("qty").getValue().toString();
                    String key = dataSnapshot.getKey();
                    HashMap<String,String> map=new HashMap<>();
                    map.put("name",name);
                    map.put("image",image);
                    map.put("details",details);
                    map.put("mrp",mrp);
                    map.put("discount",discount);
                    map.put("qty",qty);
                    map.put("key",key);
                    listAppliance.add(map);
                    adapterAppliance.notifyItemInserted(listBestValue.size());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        FirebaseDatabase.getInstance().getReference().child("items").orderByChild("mustHave").equalTo("mustHave").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String name = dataSnapshot.child("name").getValue().toString();
                    String image = dataSnapshot.child("image").getValue().toString();
                    String details = dataSnapshot.child("details").getValue().toString();
                    String mrp = dataSnapshot.child("mrp").getValue().toString();
                    String discount = dataSnapshot.child("discount").getValue().toString();
                    String qty = dataSnapshot.child("qty").getValue().toString();
                    String key = dataSnapshot.getKey();
                    HashMap<String,String> map=new HashMap<>();
                    map.put("name",name);
                    map.put("image",image);
                    map.put("details",details);
                    map.put("mrp",mrp);
                    map.put("discount",discount);
                    map.put("qty",qty);
                    map.put("key",key);
                    listMustHave.add(map);
                    adapterMustHave.notifyItemInserted(listMustHave.size());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



        FirebaseDatabase.getInstance().getReference().child("items").orderByChild("bestValue").equalTo("bestValue").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String name = dataSnapshot.child("name").getValue().toString();
                    String image = dataSnapshot.child("image").getValue().toString();
                    String details = dataSnapshot.child("details").getValue().toString();
                    String mrp = dataSnapshot.child("mrp").getValue().toString();
                    String discount = dataSnapshot.child("discount").getValue().toString();
                    String qty = dataSnapshot.child("qty").getValue().toString();
                    String key = dataSnapshot.getKey();
                    HashMap<String,String> map=new HashMap<>();
                    map.put("name",name);
                    map.put("image",image);
                    map.put("details",details);
                    map.put("mrp",mrp);
                    map.put("discount",discount);
                    map.put("qty",qty);
                    map.put("key",key);
                    listBestValue.add(map);
                    System.out.println("listBestValue... "+listBestValue);
                    adapterFresh.notifyItemInserted(listBestValue.size());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
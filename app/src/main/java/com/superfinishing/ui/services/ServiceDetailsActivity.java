package com.superfinishing.ui.services;

import static com.superfinishing.Workspace.currentUser;
import static com.superfinishing.Workspace.myName;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.superfinishing.R;
import com.superfinishing.Workspace;
import com.superfinishing.ratingAndReview.AdapterRatingList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ServiceDetailsActivity extends AppCompatActivity {

    TextView textViewName,textViewDesc,textViewOccupation,textViewRate,textViewRatings,textViewReview;
//    Button button;
    ImageView imageView;
    ConstraintLayout constraintLayoutCall,constraintLayoutMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);
        HashMap<String, String> hashMap = (HashMap<String, String>) getIntent().getSerializableExtra("data");
        System.out.println("haasd "+hashMap);

        textViewName=findViewById(R.id.textView143);
        textViewDesc=findViewById(R.id.textView144);
        textViewOccupation=findViewById(R.id.textView145);
        textViewRate=findViewById(R.id.textView146);
//        button=findViewById(R.id.button12);
        imageView=findViewById(R.id.imageView48);
        textViewRatings=findViewById(R.id.textView24);
        textViewReview=findViewById(R.id.textView33);

        constraintLayoutCall=findViewById(R.id.consCall);
        constraintLayoutMsg=findViewById(R.id.consMsg);

        textViewName.setText(hashMap.get("name"));
        textViewDesc.setText(hashMap.get("describe"));
        textViewRate.setText(hashMap.get("rate"));
        textViewOccupation.setText(hashMap.get("category"));
        Glide.with(ServiceDetailsActivity.this).load(hashMap.get("image")).into(imageView);

        if(hashMap.get("bookingKey")!=null){
            FirebaseDatabase.getInstance().getReference().child("service").child(hashMap.get("bookingKey")).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    System.out.println("snaaaaaaaaaaaaaaa "+snapshot);
                    String rating= String.valueOf(snapshot.child("ratings").getValue());
                    String review= String.valueOf(snapshot.child("review").getValue());
                    if(!rating.equals("null")){
                        textViewRatings.setText(rating+" person rated");
                    }
                    if(!review.equals("null")){
                        textViewReview.setText(rating+" person reviewed");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        constraintLayoutCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+hashMap.get("phone")));
                startActivity(intent);
            }
        });

        constraintLayoutMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.setData(Uri.parse("sms:" + hashMap.get("phone")));
                startActivity(smsIntent);

            }
        });
        TextView textviewReview = findViewById(R.id.textView157);
        TextView textviewRating = findViewById(R.id.textView95);
        if(hashMap.get("provider").equals(currentUser)){
            textviewReview.setVisibility(View.GONE);
            textviewRating.setVisibility(View.GONE);
        }

        textviewRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("booking").child(currentUser).child(hashMap.get("key")).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        System.out.println("qwertySnapshot,,, "+snapshot);
                        String status = String.valueOf(snapshot.child("status").getValue());

                        if (status.equals("booked")){
                            final Dialog dialog = new Dialog(ServiceDetailsActivity.this); // Context, this, etc.
                            dialog.setContentView(R.layout.rating_layout);
                            dialog.setTitle("Rating");
                            dialog.show();
                            Button btnDone = dialog.findViewById(R.id.button22);
                            RatingBar ratingBar = dialog.findViewById(R.id.ratingBar2);
                            btnDone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(!hashMap.get("provider").equals(currentUser)){
                                        FirebaseDatabase.getInstance().getReference().child("ratings").child(hashMap.get("provider")).child(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                System.out.println("snaaaaaaaaaa "+snapshot);
                                                if (snapshot.getValue()==null){
                                                    FirebaseDatabase.getInstance().getReference().child("ratings").child(hashMap.get("provider")).child(currentUser).setValue(ratingBar.getRating());
                                                    FirebaseDatabase.getInstance().getReference().child("service").child(hashMap.get("key")).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            System.out.println("daaaaaaaaaaaaaaaaaaaaaaaaaaa "+dataSnapshot);
//                                                            String name = dataSnapshot.child("name").getValue().toString();
//                                                            String image = dataSnapshot.child("image").getValue().toString();
//                                                            String describe = dataSnapshot.child("describe").getValue().toString();
//                                                            String category = dataSnapshot.child("category").getValue().toString();
//                                                            String rate = dataSnapshot.child("rate").getValue().toString();
//                                                            String provider = dataSnapshot.child("provider").getValue().toString();
//                                                            String key = dataSnapshot.getKey();

                                                            String ratings = String.valueOf(dataSnapshot.child("ratings").getValue());
                                                            int ratingssssss=1;
                                                            if(ratings.equals("null")){
                                                                ratingssssss=1;
                                                            }else {
                                                                ratingssssss = Integer.valueOf(ratings) + 1;
                                                            }
                                                            FirebaseDatabase.getInstance().getReference().child("service").child(hashMap.get("bookingKey")).child("ratings").setValue(ratingssssss);
                                                            dialog.dismiss();
                                                            Toast.makeText(ServiceDetailsActivity.this, "Thank you for rating", Toast.LENGTH_SHORT).show();
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });

                                                }else {
                                                    Toast.makeText(ServiceDetailsActivity.this, "You have already rated this service", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(ServiceDetailsActivity.this, "Book service before rate", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        //review
        textviewReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("booking").child(currentUser).child(hashMap.get("key")).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        System.out.println("Snapshot,,, "+snapshot);
                        String status = String.valueOf(snapshot.child("status").getValue());

                        if (status.equals("booked")){

                            final Dialog dialog = new Dialog(ServiceDetailsActivity.this); // Context, this, etc.
                            dialog.setContentView(R.layout.review_layout);
                            dialog.setTitle("Review");
                            dialog.show();
                            Button btnSubmit = dialog.findViewById(R.id.button23);
                            EditText editText = dialog.findViewById(R.id.editTextTextPersonName29);
                            btnSubmit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(!hashMap.get("provider").equals(currentUser)){
                                        FirebaseDatabase.getInstance().getReference().child("review").child(hashMap.get("provider")).child(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                System.out.println("snaaaaaaaaaa "+snapshot);
                                                if (snapshot.getValue()==null){
                                                    FirebaseDatabase.getInstance().getReference().child("review").child(hashMap.get("provider")).child(currentUser).setValue(editText.getText().toString());
                                                    FirebaseDatabase.getInstance().getReference().child("service").child(hashMap.get("key")).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            System.out.println("daaaaaaaaaaaaaaaaaaaaaaaaaaa "+dataSnapshot);
//                                                            String name = dataSnapshot.child("name").getValue().toString();
//                                                            String image = dataSnapshot.child("image").getValue().toString();
//                                                            String describe = dataSnapshot.child("describe").getValue().toString();
//                                                            String category = dataSnapshot.child("category").getValue().toString();
//                                                            String rate = dataSnapshot.child("rate").getValue().toString();
//                                                            String provider = dataSnapshot.child("provider").getValue().toString();
//                                                            String key = dataSnapshot.getKey();

                                                            String ratings = String.valueOf(dataSnapshot.child("review").getValue());
                                                            int reviewsss=1;
                                                            if(ratings.equals("null")){
                                                                reviewsss=1;
                                                            }else {
                                                                reviewsss = Integer.valueOf(ratings) + 1;
                                                            }
                                                            FirebaseDatabase.getInstance().getReference().child("service").child(hashMap.get("bookingKey")).child("review").setValue(reviewsss);
                                                            dialog.dismiss();
                                                            Toast.makeText(ServiceDetailsActivity.this, "Thank you for reviewing", Toast.LENGTH_SHORT).show();
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });

                                                }else {
                                                    Toast.makeText(ServiceDetailsActivity.this, "You have already review this service", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(ServiceDetailsActivity.this, "Book service before rate", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        TextView textViewViewRating=findViewById(R.id.textView161);
        TextView textViewViewReview=findViewById(R.id.textView162);
        textViewViewRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ServiceDetailsActivity.this); // Context, this, etc.
                dialog.setContentView(R.layout.rating_list_layout);
                dialog.setTitle("Review");
                dialog.show();
                RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView8);
                recyclerView.setLayoutManager(new LinearLayoutManager(ServiceDetailsActivity.this));
                ArrayList<HashMap<String,String>> mapArrayList=new ArrayList<>();
                AdapterRatingList adapterRatingList=new AdapterRatingList(ServiceDetailsActivity.this,mapArrayList,true);
                recyclerView.setAdapter(adapterRatingList);
                FirebaseDatabase.getInstance().getReference().child("ratings").child(hashMap.get("provider")).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        System.out.println("sapshhhhh "+snapshot);
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            String userKey = dataSnapshot.getKey();
                            String rateValue = String.valueOf(dataSnapshot.getValue());
                            System.out.println("userkeyyyy "+userKey+"  rate"+rateValue);
                            FirebaseDatabase.getInstance().getReference().child("users").child(userKey).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String name = String.valueOf(snapshot.child("name").getValue());
                                    String image = String.valueOf(snapshot.child("image").getValue());

                                    HashMap<String,String> map=new HashMap<>();
                                    map.put("name",name);
                                    map.put("image",image);
                                    map.put("ratings",rateValue);
                                    mapArrayList.add(map);
                                    adapterRatingList.notifyItemChanged(mapArrayList.size());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
        textViewViewReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ServiceDetailsActivity.this); // Context, this, etc.
                dialog.setContentView(R.layout.rating_list_layout);
                dialog.setTitle("Review");
                dialog.show();
                RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView8);
                recyclerView.setLayoutManager(new LinearLayoutManager(ServiceDetailsActivity.this));
                ArrayList<HashMap<String,String>> mapArrayList=new ArrayList<>();
                AdapterRatingList adapterRatingList=new AdapterRatingList(ServiceDetailsActivity.this,mapArrayList,false);
                recyclerView.setAdapter(adapterRatingList);
                FirebaseDatabase.getInstance().getReference().child("review").child(hashMap.get("provider")).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        System.out.println("sapshhhhh "+snapshot);
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            String userKey = dataSnapshot.getKey();
                            String review = String.valueOf(dataSnapshot.getValue());
                            System.out.println("userkeyyyy "+userKey+"  review"+review);
                            FirebaseDatabase.getInstance().getReference().child("users").child(userKey).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String name = String.valueOf(snapshot.child("name").getValue());
                                    String image = String.valueOf(snapshot.child("image").getValue());

                                    HashMap<String,String> map=new HashMap<>();
                                    map.put("name",name);
                                    map.put("image",image);
                                    map.put("review",review);
                                    mapArrayList.add(map);
                                    adapterRatingList.notifyItemChanged(mapArrayList.size());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                button.setText("Requested");
//                String pushKey = FirebaseDatabase.getInstance().getReference().push().getKey();
//
//                hashMap.put("status","requested");
//                hashMap.put("bookedBy",currentUser);
//                hashMap.put("bookingKey",hashMap.get("key"));
//
//
//                FirebaseDatabase.getInstance().getReference().child("booking").child(currentUser).child(pushKey).setValue(hashMap);
//                FirebaseDatabase.getInstance().getReference().child("booking").child(hashMap.get("provider")).child(pushKey).setValue(hashMap);
//                button.setEnabled(false);
//
//                myName= FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
//                HashMap<String,String> hashMap=new HashMap<>();
//                hashMap.put("title",myName+" booked You service "+hashMap.get("name"));
//                hashMap.put("desc","waiting for");
//                hashMap.put("image","");
//                Workspace.sendMessage(ServiceDetailsActivity.this,hashMap.get("provider"),hashMap);
//            }
//        });
    }
}
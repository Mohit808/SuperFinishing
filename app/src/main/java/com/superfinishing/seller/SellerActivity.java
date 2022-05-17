package com.superfinishing.seller;

import static com.superfinishing.Workspace.currentUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.superfinishing.R;
import com.superfinishing.admin.AdapterDialog;
import com.superfinishing.admin.AdminActivity;
import com.superfinishing.subItemDetails.SubItemDetailsActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class SellerActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 121;
    private Uri imageUri;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        TextView textViewName = findViewById(R.id.textView6);
        TextView textViewAddress = findViewById(R.id.textView68);
        TextView textViewPhoneNumber = findViewById(R.id.textView69);
        TextView textViewEmail = findViewById(R.id.textView70);
        TextView textViewGst = findViewById(R.id.textView71);

        findViewById(R.id.textView152).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellerActivity.this, MyProductsActivity.class));
            }
        });
        FirebaseDatabase.getInstance().getReference().child("business").child(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("name").getValue()!=null){
                    String name = snapshot.child("name").getValue().toString();
                    String address = snapshot.child("address").getValue().toString();
                    String phone = snapshot.child("phone").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    String gst = snapshot.child("gst").getValue().toString();

                    textViewName.setText(name);
                    textViewAddress.setText(address);
                    textViewPhoneNumber.setText(phone);
                    textViewEmail.setText(email);
                    textViewGst.setText(gst);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ConstraintLayout categoryField = findViewById(R.id.constraintLayout15);
        TextView categoryFieldText = findViewById(R.id.textView53);

        categoryField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(SellerActivity.this);
                dialog.setContentView(R.layout.dialog_view);
                RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView14);
                recyclerView.setLayoutManager(new LinearLayoutManager(SellerActivity.this));
                FirebaseDatabase.getInstance().getReference().child("category").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<String> listCategory=new ArrayList<>();
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                            String name = dataSnapshot.child("name").getValue().toString();
                            listCategory.add(dataSnapshot.child("name").getValue().toString());
                            System.out.println("LIST SIZE...... "+listCategory.size());
                        }
                        recyclerView.setAdapter(new AdapterDialog(SellerActivity.this,listCategory,categoryFieldText,dialog));
                        dialog.show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });


        ConstraintLayout subCategoryField = findViewById(R.id.constraintLayout16);
        TextView subCategoryFieldText = findViewById(R.id.textView73);

        subCategoryField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(SellerActivity.this);
                dialog.setContentView(R.layout.dialog_view);
                RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView14);
                recyclerView.setLayoutManager(new LinearLayoutManager(SellerActivity.this));
                FirebaseDatabase.getInstance().getReference().child("subCategory").orderByChild(categoryFieldText.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<String> listCategory=new ArrayList<>();
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                            String name = dataSnapshot.child("name").getValue().toString();
                            listCategory.add(dataSnapshot.child("name").getValue().toString());
                            System.out.println("LIST SIZE...... "+listCategory.size());
                        }
                        recyclerView.setAdapter(new AdapterDialog(SellerActivity.this,listCategory,subCategoryFieldText,dialog));
                        dialog.show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        imageView = findViewById(R.id.imageView4);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        Button button = findViewById(R.id.button10);
        EditText editTextName = findViewById(R.id.editTextTextPersonName10);
        EditText editTextDetails= findViewById(R.id.editTextTextPersonName12);
        EditText editTextMrp= findViewById(R.id.editTextTextPersonName13);
        EditText editTextDiscount= findViewById(R.id.editTextTextPersonName15);
        EditText editTextQty= findViewById(R.id.editTextTextPersonName14);
        CheckBox checkBox= findViewById(R.id.checkBox);
        CheckBox checkBox2= findViewById(R.id.checkBox2);
        CheckBox checkBox3= findViewById(R.id.checkBox3);
        CheckBox checkBox4= findViewById(R.id.checkBox4);
          
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String details = editTextDetails.getText().toString();
                String mrp = editTextMrp.getText().toString();
                String discount = editTextDiscount.getText().toString();
                String qty = editTextQty.getText().toString();

                if (!name.isEmpty()&&!details.isEmpty()&&!mrp.isEmpty()&&!discount.isEmpty()&&!qty.isEmpty()){
                    ProgressDialog progressDialog=new ProgressDialog(SellerActivity.this);
                    progressDialog.show();
                    String pushKey = FirebaseDatabase.getInstance().getReference().push().getKey();

                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("items").child(pushKey);
                    storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        HashMap<String,String> hashMap=new HashMap<>();

                                        if (checkBox.isChecked()){
                                            hashMap.put("bestValue","bestValue");
                                        }
                                        if (checkBox2.isChecked()){
                                            hashMap.put("blockBuster","blockBuster");
                                        }

                                        if (checkBox3.isChecked()){
                                            hashMap.put("appliance","appliance");
                                        }
                                        if (checkBox4.isChecked()){
                                            hashMap.put("mustHave","mustHave");
                                        }

                                        hashMap.put("category",categoryFieldText.getText().toString());
                                        hashMap.put("subCategory",subCategoryFieldText.getText().toString());
                                        hashMap.put("name",name);
                                        hashMap.put("details",details);
                                        hashMap.put("mrp",mrp);
                                        hashMap.put("discount",discount);
                                        hashMap.put("qty",qty);
                                        hashMap.put("image",task.getResult().toString());
                                        hashMap.put("seller",currentUser);

                                        SharedPreferences sharedPreferences=getSharedPreferences("location", Context.MODE_PRIVATE);
                                        String locality = sharedPreferences.getString("locality", "");
                                        hashMap.put("location",locality);

                                        FirebaseDatabase.getInstance().getReference().child("items").child(pushKey).setValue(hashMap);
                                        editTextName.setText("");
                                        editTextDetails.setText("");
                                        editTextDiscount.setText("");
                                        editTextMrp.setText("");
                                        editTextQty.setText("");
                                        imageUri=null;
                                        imageView.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);

                                        checkBox.setChecked(false);
                                        checkBox2.setChecked(false);
                                        checkBox3.setChecked(false);
                                        checkBox4.setChecked(false);
                                        progressDialog.dismiss();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }
}
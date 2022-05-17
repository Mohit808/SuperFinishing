package com.superfinishing.seller;

import static com.superfinishing.Workspace.currentUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.superfinishing.R;

import java.util.HashMap;

public class SellerRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_register);
        EditText editTextName = findViewById(R.id.editTextTextPersonName8);
        EditText editTextAddress = findViewById(R.id.editTextTextPersonName9);
        EditText editTextPhoneNumber = findViewById(R.id.editTextPhone);
        EditText editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        EditText editTextGst = findViewById(R.id.editTextTextPersonName11);
        Button button = findViewById(R.id.button9);

        ConstraintLayout constraintLayout=findViewById(R.id.ccff);
        constraintLayout.setVisibility(View.GONE);
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("getting data");
        progressDialog.setCancelable(false);
        progressDialog.show();

        FirebaseDatabase.getInstance().getReference().child("business").child(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.hide();
                if (snapshot.child("name").getValue()!=null){
                    constraintLayout.setVisibility(View.GONE);
                    String name = snapshot.child("name").getValue().toString();
                    Intent intent=new Intent(SellerRegisterActivity.this,SellerActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    constraintLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextName.getText().toString().isEmpty()){
                    editTextName.setError("Enter name");
                }else
                if (editTextAddress.getText().toString().isEmpty()){
                    editTextAddress.setError("Enter Address");
                }else
                if (editTextPhoneNumber.getText().toString().isEmpty()){
                    editTextPhoneNumber.setError("Enter Phone number");
                }else
                if (editTextEmail.getText().toString().isEmpty()){
                    editTextEmail.setError("Enter Email");
                }else
                if (editTextGst.getText().toString().isEmpty()){
                    editTextGst.setError("Enter Gst");
                }else {
                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("name",editTextName.getText().toString());
                    hashMap.put("address",editTextAddress.getText().toString());
                    hashMap.put("phone",editTextPhoneNumber.getText().toString());
                    hashMap.put("email",editTextEmail.getText().toString());
                    hashMap.put("gst",editTextGst.getText().toString());
                    hashMap.put("provider",currentUser);
                    progressDialog.show();
                    constraintLayout.setVisibility(View.GONE);
                    FirebaseDatabase.getInstance().getReference().child("business").child(currentUser).setValue(hashMap);
                    Intent intent=new Intent(SellerRegisterActivity.this,SellerActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
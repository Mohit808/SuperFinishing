package com.superfinishing.service;

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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
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
import com.superfinishing.admin.AdminServiceActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class ServiceRegisterActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 12;
    private Uri imageUri;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_register);

        TextView categoryFieldText = findViewById(R.id.textView136);
        ConstraintLayout categoryField = findViewById(R.id.constraintx);
        EditText editTextName = findViewById(R.id.editTextTextPersonName17);
        EditText editTextPhoneNumber = findViewById(R.id.editTextTextPersonName26);
        EditText editTextDescribe = findViewById(R.id.editTextTextPersonName18);
        EditText editTextAmount = findViewById(R.id.editTextNumber);
        TextView textViewRsHours = findViewById(R.id.textView138);
        CheckBox checkBoxFixedRate = findViewById(R.id.checkBox5);
        CheckBox checkBoxNegotiable = findViewById(R.id.checkBox6);
        Button button = findViewById(R.id.button11);
        imageView = findViewById(R.id.imageView49);

        ScrollView constraint = findViewById(R.id.cscsc);
        constraint.setVisibility(View.GONE);
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.show();
        FirebaseDatabase.getInstance().getReference().child("service").orderByChild("provider").equalTo(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String provider = null;
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    provider=dataSnapshot.child("provider").getValue().toString();
                }

                if (provider!=null){
                    String data = getIntent().getStringExtra("data");
                    if (data==null){
                        startActivity(new Intent(ServiceRegisterActivity.this,MySercvicesActivity.class));
                        finish();
                    }else {
                        progressDialog.hide();
                        constraint.setVisibility(View.VISIBLE);
                    }
                }else {
                    progressDialog.hide();
                    constraint.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        checkBoxFixedRate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    textViewRsHours.setText("-Rs");
                }else {
                    textViewRsHours.setText("-Rs/Hours");
                }
            }
        });
        checkBoxNegotiable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editTextAmount.setEnabled(false);
                    textViewRsHours.setEnabled(false);
                    checkBoxFixedRate.setEnabled(false);
                }else {
                    editTextAmount.setEnabled(true);
                    textViewRsHours.setEnabled(true);
                    checkBoxFixedRate.setEnabled(true);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = categoryFieldText.getText().toString();
                String name = editTextName.getText().toString();
                String phoneNum = editTextPhoneNumber.getText().toString();
                String describe = editTextDescribe.getText().toString();
                String amount = editTextAmount.getText().toString();
                String rsHours = textViewRsHours.getText().toString();

                ProgressDialog progressDialog=new ProgressDialog(ServiceRegisterActivity.this);
                progressDialog.show();
                String pushKey = FirebaseDatabase.getInstance().getReference().push().getKey();

                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("service").child(pushKey);
                storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()){
                            storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {

                                    HashMap<String,String> hashMap=new HashMap<>();
                                    hashMap.put("category",category);
                                    hashMap.put("name",name);
                                    hashMap.put("phone",phoneNum);
                                    hashMap.put("image",task.getResult().toString());
                                    hashMap.put("describe",describe);
                                    hashMap.put("provider",currentUser);
                                    hashMap.put("ratings","0");

                                    SharedPreferences sharedPreferences=getSharedPreferences("location", Context.MODE_PRIVATE);
                                    String locality = sharedPreferences.getString("locality", "");
                                    hashMap.put("location",locality);

                                    if (!checkBoxNegotiable.isChecked()){
                                        hashMap.put("rate",amount+" "+rsHours);
                                    }else {
                                        hashMap.put("rate","negotiable");
                                    }

                                    progressDialog.dismiss();
                                    FirebaseDatabase.getInstance().getReference().child("service").child(pushKey).setValue(hashMap);

                                    startActivity(new Intent(ServiceRegisterActivity.this,MySercvicesActivity.class));
                                }
                            });
                        }
                    }
                });
            }
        });
        categoryField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ServiceRegisterActivity.this);
                dialog.setContentView(R.layout.dialog_view);
                RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView14);
                recyclerView.setLayoutManager(new LinearLayoutManager(ServiceRegisterActivity.this));
                FirebaseDatabase.getInstance().getReference().child("serviceCategory").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<String> listCategory=new ArrayList<>();
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                            String name = dataSnapshot.child("name").getValue().toString();
                            listCategory.add(dataSnapshot.child("name").getValue().toString());
                            System.out.println("LIST SIZE...... "+listCategory.size());
                        }
                        recyclerView.setAdapter(new AdapterDialog(ServiceRegisterActivity.this,listCategory,categoryFieldText,dialog));
                        dialog.show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

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
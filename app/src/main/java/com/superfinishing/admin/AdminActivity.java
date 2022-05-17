package com.superfinishing.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.superfinishing.Workspace;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 123;
    private static final int PICK_IMAGE_Slider = 100;
    private static final int PICK_IMAGE_BANNER = 111;
    private static final int PICK_IMAGE_SUB_CATEGORY = 101;
    private static final int PICK_IMAGE_NOTIFICATIONS = 112;
    private ImageView imageViewCategory;
    private Uri imageUri;
    private Uri imageUriBanner;
    private Uri imageUriSlider;
    private ImageView imageViewBanner;
    private ImageView imageViewSubCategory;
    private Uri imageUriSubCategory;
    private Uri imageUriNotifications;
    private ImageView imageViewNotifications;
    private ImageView imageViewSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        imageViewCategory=findViewById(R.id.imageView17);
        imageViewSubCategory = findViewById(R.id.imageView21);
        EditText editTextCategory=findViewById(R.id.editTextTextPersonName4);
        EditText editTextCategoryDetails=findViewById(R.id.editTextTextPersonName16);
        EditText editTextSubCategory=findViewById(R.id.editTextTextPersonName6);

        Button buttonSaveCategory=findViewById(R.id.button6);
        Button buttonSaveSubCategory=findViewById(R.id.button8);

        imageViewBanner = findViewById(R.id.imageView42);
        imageViewSlider = findViewById(R.id.imageView57);
        TextView categoryFieldText = findViewById(R.id.textView53);
        EditText editTextBigTextSlider = findViewById(R.id.editTextTextPersonName24);
        EditText editTextSmallTextSlider= findViewById(R.id.editTextTextPersonName25);
        EditText editTextButtonSlider = findViewById(R.id.editTextTextPersonName7);
        Button buttonSlider = findViewById(R.id.button20);

        imageViewBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_BANNER);
            }
        });
        imageViewSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_Slider);
            }
        });

        imageViewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE);
            }
        });
        imageViewSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_SUB_CATEGORY);
            }
        });
        imageViewNotifications=findViewById(R.id.imageView52);
        imageViewNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_NOTIFICATIONS);
            }
        });
        findViewById(R.id.textView139).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this,AdminServiceActivity.class));
            }
        });

        buttonSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonTxt = editTextButtonSlider.getText().toString();
                String bigTxt = editTextBigTextSlider.getText().toString();
                String smallTxt = editTextSmallTextSlider.getText().toString();
                if (!buttonTxt.isEmpty()){
                    if (imageUriSlider!=null){
                        ProgressDialog progressDialog=new ProgressDialog(AdminActivity.this);
                        progressDialog.show();
                        String pushKey = FirebaseDatabase.getInstance().getReference().push().getKey();

                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("slider").child(pushKey);
                        storageReference.putFile(imageUriSlider).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()){
                                    storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            HashMap<String,String> hashMap=new HashMap<>();
                                            hashMap.put("btnText",buttonTxt);
                                            hashMap.put("bigTxt",bigTxt);
                                            hashMap.put("smallTxt",smallTxt);
                                            hashMap.put("image",task.getResult().toString());
                                            FirebaseDatabase.getInstance().getReference().child("slider").child(pushKey).setValue(hashMap);
                                            editTextButtonSlider.setText("");
                                            editTextBigTextSlider.setText("");
                                            editTextSmallTextSlider.setText("");
                                            imageUriSlider=null;
                                            imageViewSlider.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);

                                            progressDialog.dismiss();
                                        }
                                    });
                                }
                            }
                        });
                    }
                }else {
                    Toast.makeText(AdminActivity.this, "Write btn Text", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonSaveCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextCategory.getText().toString();
                String details = editTextCategoryDetails.getText().toString();
                if (!name.isEmpty()&&!details.isEmpty()){
                    if (imageUri!=null&& imageUriBanner!=null){
                        ProgressDialog progressDialog=new ProgressDialog(AdminActivity.this);
                        progressDialog.show();
                        String pushKey = FirebaseDatabase.getInstance().getReference().push().getKey();

                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("category").child(pushKey);
                        storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()){
                                    storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            StorageReference storageReference2 = FirebaseStorage.getInstance().getReference().child("category").child(pushKey+"banner");
                                            storageReference2.putFile(imageUriBanner).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> taskx) {
                                                    if (taskx.isSuccessful()){
                                                        storageReference2.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Uri> taskc) {
                                                                HashMap<String,String> hashMap=new HashMap<>();
                                                                hashMap.put("name",name);
                                                                hashMap.put("details",details);
                                                                hashMap.put("image",task.getResult().toString());
                                                                hashMap.put("banner",taskc.getResult().toString());
                                                                FirebaseDatabase.getInstance().getReference().child("category").child(pushKey).setValue(hashMap);
                                                                editTextCategory.setText("");
                                                                editTextCategoryDetails.setText("");
                                                                imageUri=null;
                                                                imageUriBanner=null;
                                                                imageViewCategory.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
                                                                imageViewBanner.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);

                                                                progressDialog.dismiss();
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }else {
                        Toast.makeText(AdminActivity.this, "Select both image", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AdminActivity.this, "category name and details cant be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonSaveSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextSubCategory.getText().toString();
                if (!name.isEmpty()){
                    if (imageUriSubCategory!=null){
                        ProgressDialog progressDialog=new ProgressDialog(AdminActivity.this);
                        progressDialog.show();
                        String pushKey = FirebaseDatabase.getInstance().getReference().push().getKey();
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("subCategory").child(pushKey);
                        storageReference.putFile(imageUriSubCategory).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()){
                                    storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            HashMap<String,String> hashMap=new HashMap<>();
                                            hashMap.put("category",categoryFieldText.getText().toString());
                                            hashMap.put("name",name);
                                            hashMap.put("image",task.getResult().toString());
                                            FirebaseDatabase.getInstance().getReference().child("subCategory").child(pushKey).setValue(hashMap);
                                            editTextSubCategory.setText("");
                                            imageUriSubCategory=null;
                                            imageViewSubCategory.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);

                                            progressDialog.dismiss();
                                        }
                                    });
                                }
                            }
                        });
                    }
                }else {
                    Toast.makeText(AdminActivity.this, "WRITE SOMETHING....", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button buttonSend = findViewById(R.id.button13);
        EditText editTextTitle=findViewById(R.id.editTextTextPersonName19);
        EditText editTextMsgDesc=findViewById(R.id.editTextTextPersonName20);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String desc = editTextMsgDesc.getText().toString();
                if (!title.isEmpty() && !desc.isEmpty()){

                    ProgressDialog progressDialog=new ProgressDialog(AdminActivity.this);
                    progressDialog.show();
                    String pushKey = FirebaseDatabase.getInstance().getReference().push().getKey();

                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("notifications").child(pushKey);
                    storageReference.putFile(imageUriNotifications).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        HashMap<String,String> hashMap=new HashMap<>();
                                        hashMap.put("title",editTextTitle.getText().toString());
                                        hashMap.put("desc",editTextMsgDesc.getText().toString());
                                        hashMap.put("image",task.getResult().toString());
                                        FirebaseDatabase.getInstance().getReference().child("notifications").child(pushKey).setValue(hashMap);
                                        editTextTitle.setText("");
                                        editTextMsgDesc.setText("");
                                        imageUriNotifications=null;
                                        imageViewNotifications.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);

                                        Workspace.sendMessage(AdminActivity.this,"app",hashMap);
                                        progressDialog.dismiss();
                                    }
                                });
                            }
                        }
                    });

                }
            }
        });

        ConstraintLayout categoryField = findViewById(R.id.constraintLayout15);

        categoryField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(AdminActivity.this);
                dialog.setContentView(R.layout.dialog_view);
                RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView14);
                recyclerView.setLayoutManager(new LinearLayoutManager(AdminActivity.this));
                FirebaseDatabase.getInstance().getReference().child("category").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<String> listCategory=new ArrayList<>();
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                            String name = dataSnapshot.child("name").getValue().toString();
                            listCategory.add(dataSnapshot.child("name").getValue().toString());
                            System.out.println("LIST SIZE...... "+listCategory.size());
                        }
                        recyclerView.setAdapter(new AdapterDialog(AdminActivity.this,listCategory,categoryFieldText,dialog));
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
            imageViewCategory.setImageURI(imageUri);
        }
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_BANNER){
            imageUriBanner = data.getData();
            imageViewBanner.setImageURI(imageUriBanner);
        }
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_Slider){
            imageUriSlider = data.getData();
            imageViewSlider.setImageURI(imageUriSlider);
        }
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_SUB_CATEGORY){
            imageUriSubCategory = data.getData();
            imageViewSubCategory.setImageURI(imageUriSubCategory);
        }
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_NOTIFICATIONS){
            imageUriNotifications= data.getData();
            imageViewNotifications.setImageURI(imageUriNotifications);
        }
    }
}
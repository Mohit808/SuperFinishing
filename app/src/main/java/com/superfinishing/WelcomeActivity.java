package com.superfinishing;

import static com.superfinishing.Workspace.myImage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.superfinishing.maps.MapsActivity;

import java.util.HashMap;

public class WelcomeActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 111;
    private Uri imageUri;
    private ImageView imageView;
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        currentUser=FirebaseAuth.getInstance().getUid();
        System.out.println("current userrrrrrrrrrrr "+currentUser);
        imageView=findViewById(R.id.imageView16);
        EditText editText =findViewById(R.id.editTextTextPersonName2);

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        if (currentUser!=null){
            FirebaseDatabase.getInstance().getReference().child("users").child(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("name").getValue()!=null){
                        String name= String.valueOf(snapshot.child("name").getValue());
                        String image= String.valueOf(snapshot.child("image").getValue());
                        editText.setText(name);
                        Glide.with(WelcomeActivity.this).load(image).into(imageView);

                        Workspace.myName=name;
                        myImage=image;

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                if (!name.isEmpty() && imageUri!=null){

                    ProgressDialog progressDialog=new ProgressDialog(WelcomeActivity.this);
                    progressDialog.show();
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile").child(currentUser);
                    storageReference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        HashMap<String,String> hashMap=new HashMap<>();
                                        hashMap.put("userKey",currentUser);
                                        hashMap.put("name",name);
                                        hashMap.put("image",task.getResult().toString());
                                        FirebaseDatabase.getInstance().getReference().child("users").child(currentUser).setValue(hashMap);
                                        editText.setText("");
                                        imageUri=null;



                                        UserProfileChangeRequest profleUpdate = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(name)
                                                .setPhotoUri(imageUri)
                                                .build();

                                        FirebaseAuth.getInstance().getCurrentUser().updateProfile(profleUpdate);

                                        progressDialog.dismiss();
                                        Intent intent=new Intent(WelcomeActivity.this, MapsActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                        }
                    });
                }else if (imageUri==null && myImage!=null){
                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("userKey",currentUser);
                    hashMap.put("name",name);
                    hashMap.put("image",myImage);
                    FirebaseDatabase.getInstance().getReference().child("users").child(currentUser).setValue(hashMap);

                    UserProfileChangeRequest profleUpdate = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build();

                    FirebaseAuth.getInstance().getCurrentUser().updateProfile(profleUpdate);
                    Intent intent=new Intent(WelcomeActivity.this,MapsActivity.class);
                    startActivity(intent);
                    finish();
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
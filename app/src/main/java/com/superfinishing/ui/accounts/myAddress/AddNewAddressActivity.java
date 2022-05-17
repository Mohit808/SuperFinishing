package com.superfinishing.ui.accounts.myAddress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.database.FirebaseDatabase;
import com.superfinishing.R;

import java.util.HashMap;

public class AddNewAddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_address);

        EditText editTextAddress = findViewById(R.id.editTextTextPersonName28);
        EditText editTextName = findViewById(R.id.editTextTextPersonName27);
        RadioButton radioButtonHome=findViewById(R.id.radioButton);
        RadioButton radioButtonOffice=findViewById(R.id.radioButton2);

        radioButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonOffice.setChecked(false);
            }
        });
        radioButtonOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonHome.setChecked(false);
            }
        });

        findViewById(R.id.sdfasdfs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adddres = editTextAddress.getText().toString();
                String name = editTextName.getText().toString();
                String type = null;
                if (radioButtonHome.isChecked()){
                    type="Home";
                }
                if (radioButtonOffice.isChecked()){
                    type="Office";
                }

                if (!adddres.isEmpty() && type!=null){

                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("address",adddres);
                    hashMap.put("type",type);
                    hashMap.put("name",name);
                    FirebaseDatabase.getInstance().getReference().child("myAddress").push().setValue(hashMap);
                    finish();
                }
            }
        });
    }
}
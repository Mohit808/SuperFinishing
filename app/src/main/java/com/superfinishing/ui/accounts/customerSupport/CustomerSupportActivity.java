package com.superfinishing.ui.accounts.customerSupport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.superfinishing.R;
import com.superfinishing.ui.accounts.customerSupport.chatUs.ChatUsActivity;

public class CustomerSupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_support);

        findViewById(R.id.button2x4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerSupportActivity.this, ChatUsActivity.class));
            }
        });
    }
}
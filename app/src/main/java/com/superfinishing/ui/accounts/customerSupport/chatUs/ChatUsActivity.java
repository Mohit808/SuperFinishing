package com.superfinishing.ui.accounts.customerSupport.chatUs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.superfinishing.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatUsActivity extends AppCompatActivity {

    ArrayList<HashMap<String,String>> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_us);


        RecyclerView recyclerView =findViewById(R.id.recyclerView22);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChatUsActivity.this));

        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("userKey","1");
        hashMap.put("msg","Hello!");
        list.add(hashMap);

        HashMap<String,String> hashMap2=new HashMap<>();
        hashMap2.put("userKey","1");
        hashMap2.put("msg","I'm Freshley, an I'm here to help you! Ask me a question.");
        list.add(hashMap2);

        AdapterChatUs adapterChatUs = new AdapterChatUs(ChatUsActivity.this, list);
        recyclerView.setAdapter(adapterChatUs);
        EditText editText =findViewById(R.id.editTextTextPersonName3);
        findViewById(R.id.cardView8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editText.getText().toString();
                HashMap<String,String> hashMap3=new HashMap<>();
                hashMap3.put("userKey","2");
                hashMap3.put("msg",msg);
                list.add(hashMap3);
                adapterChatUs.notifyItemInserted(list.size());

                editText.setText("");
            }
        });
    }
}
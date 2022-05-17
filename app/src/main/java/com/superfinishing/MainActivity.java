package com.superfinishing;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import static com.superfinishing.Workspace.currentUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentUser=FirebaseAuth.getInstance().getUid();

        if (currentUser==null){
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        } else {
            startActivity(new Intent(MainActivity.this,BotNavActivity.class));
        }
        finish();
    }
}
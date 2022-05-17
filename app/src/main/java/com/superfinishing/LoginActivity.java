package com.superfinishing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1232;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        signIn();
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }
    public void signIn(){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(), RC_SIGN_IN);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            System.out.println("Respinsec... "+response);
            System.out.println("Result code.... "+resultCode);
            System.out.println("data .... "+data);

            if (FirebaseAuth.getInstance().getUid()!=null){
                System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnn ");
                String currentUser = FirebaseAuth.getInstance().getUid();
                FirebaseMessaging.getInstance().subscribeToTopic("app");
                FirebaseMessaging.getInstance().subscribeToTopic(currentUser);
                startActivity(new Intent(LoginActivity.this,WelcomeActivity.class));
                finish();
            }else {
                System.out.println("ooooooooooooooooooooooooooooo ");
            }
        }
    }
}
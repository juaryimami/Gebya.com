package com.example.jewharyimer.gebya;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SystemClock.sleep(3000);

        auth=FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser=FirebaseAuth.getInstance().getCurrentUser();
        if(currentuser==null){
            Intent intent=new Intent(SplashActivity.this,RegisterActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent inten=new Intent(SplashActivity.this,MainActivity.class);
            startActivity(inten);
            finish();
        }
    }
}

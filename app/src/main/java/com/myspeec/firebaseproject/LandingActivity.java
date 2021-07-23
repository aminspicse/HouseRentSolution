package com.myspeec.firebaseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class LandingActivity extends AppCompatActivity {
    ImageView homepage;
    String userId;

    FirebaseAuth fireBaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        getSupportActionBar().hide();

        homepage = findViewById(R.id.homepage);

        try {

            fireBaseAuth = FirebaseAuth.getInstance();
            userId = fireBaseAuth.getCurrentUser().getUid();
        }catch (Exception e){
            Log.d("The",e.getMessage());
        }
/*
        if(userId != null){
            startActivity(new Intent(LandingActivity.this,WelcomeActivity.class));
        }

 */

        homepage.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                //startActivity(new Intent(LandingActivity.this,ScrollingActivity.class));

                if(userId == null){
                    startActivity(new Intent(LandingActivity.this,LoginActivity.class));
                }else{
                    startActivity(new Intent(LandingActivity.this,MainActivity.class));
                }

            }

        });
    }
}
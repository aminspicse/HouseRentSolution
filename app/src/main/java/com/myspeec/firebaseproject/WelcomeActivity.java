package com.myspeec.firebaseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    TextView userEmail, Logoutuser;
    String Email, userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        firebaseAuth = FirebaseAuth.getInstance();
        userEmail = findViewById(R.id.userEmail);

        Email = firebaseAuth.getCurrentUser().getEmail();
        userId = firebaseAuth.getCurrentUser().getUid();
        userEmail.setText(Email+" "+userId);

        Logoutuser = findViewById(R.id.Logout);

        Logoutuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
            }
        });

    }
}
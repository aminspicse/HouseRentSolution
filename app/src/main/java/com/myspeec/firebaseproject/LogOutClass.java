package com.myspeec.firebaseproject;

import android.content.Intent;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class LogOutClass extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    DrawerLayout drawerLayout;  // for toggle button
    ActionBarDrawerToggle toggle; // for toggle button

    int SetStatus(){
        FirebaseAuth.getInstance().signOut();

        return 0;
    }

    void Logout(){
        if(SetStatus() == 0){
            startActivity(new Intent(this,LoginActivity.class));
        }
    }


    void ToggleButton(){



    }
}

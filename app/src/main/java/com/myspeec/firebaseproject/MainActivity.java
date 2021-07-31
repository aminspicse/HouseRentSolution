package com.myspeec.firebaseproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

/*
 Toggle button learning resource
 Link: https://www.youtube.com/watch?v=2aNNoHXOeJs&list=PLgH5QX0i9K3ru-TfN-YsRWKe4EEOLrWjn&index=3
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth firebaseAuth;
    DrawerLayout drawerLayout;  // for toggle button
    ActionBarDrawerToggle toggle; // for toggle button
    NavigationView mNavigationView; // for geting header nav bar
    TextView userEmail;
    String Email, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = findViewById(R.id.navigationId);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = findViewById(R.id.drawerId);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.nav_open,R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        Uri name = firebaseAuth.getCurrentUser().getPhotoUrl();

        //userEmail = findViewById(R.id.userName);
        //userEmail.setText("Helow");

        //System.out.println("Your Name is : "+name);

    }

    // start function for toggle navigation item
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        if(item.getItemId() == R.id.MenuIdView){
            intent = new Intent(this,ViewPost.class);
            startActivity(intent);
        }else if(item.getItemId() == R.id.MenuIdProfile){
            intent = new Intent(this,ProfileActivity.class);
            startActivity(intent);
        }else if(item.getItemId() == R.id.MenuIdLogout){
            intent = new Intent(this,LoginActivity.class);
            FirebaseAuth.getInstance().signOut();
            startActivity(intent);
        }else if(item.getItemId() == R.id.MenuIdCreate){
            intent = new Intent(this,CreatePostActivity.class);
            startActivity(intent);
        }else if(item.getItemId() == R.id.MenuIdMyPost){
            startActivity(new Intent(this, MyPostActivity.class));
        }
        else if(item.getItemId() == R.id.idContact){
            startActivity(new Intent(this, ContactUs.class));
        }
        else if(item.getItemId() == R.id.idAbout){
            startActivity(new Intent(this,AboutUs.class));
        }
        return false;
    }

    // end for toggle navigation
}
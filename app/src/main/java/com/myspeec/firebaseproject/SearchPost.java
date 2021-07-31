package com.myspeec.firebaseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SearchPost extends AppCompatActivity {

    Button btnsearch;
    EditText editTextByLocation, editTextByRoom, editTextByRent;
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post);

        getSupportActionBar().setTitle("Search");

        editTextByLocation = findViewById(R.id.editTextLocation);
        editTextByRent = findViewById(R.id.editTextTaka);
        editTextByRoom = findViewById(R.id.editTextRoom);

        result = findViewById(R.id.result);

        btnsearch = findViewById(R.id.searchButton);

        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    result.setText("Search Feature is under construction");
            }
        });
    }
}
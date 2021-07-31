package com.myspeec.firebaseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class PasswordResetActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button btnPasswordReset;
    EditText editTextEmail;
    ProgressBar progressBar;
    //String email;
    TextView editTextError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        auth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextError = findViewById(R.id.editTextError);
        progressBar = findViewById(R.id.progressBar);

        btnPasswordReset = findViewById(R.id.btnPasswordReset);

        btnPasswordReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
                
            }
        });
    }

    private void resetPassword() {
      String email = editTextEmail.getText().toString();

        if (emailValidation(email) != false){
            progressBar.setVisibility(View.VISIBLE);

            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        editTextError.setText("Please Check Your Email and Click Login");
                        progressBar.setVisibility(View.GONE);
                    }else{
                        editTextError.setText("Try again and provide Valid Email");
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        }


    }

    private boolean emailValidation(String email){
        if(email.isEmpty()){
            editTextError.setText("Email is required");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextError.setText("Please Provide a valid email");
            return false;
        }else{
            return true;
        }
    }
}
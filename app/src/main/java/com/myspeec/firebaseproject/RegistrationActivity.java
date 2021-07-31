package com.myspeec.firebaseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
//https://www.youtube.com/watch?v=exo7NwC2joE&list=PLeSuKd7QCa4bcla2vqbP3D6Uriw1RAvDA&index=6
    EditText editTextEmail, editTextPassword, editTextConfirmPassword;
    Button btnRegister;
    TextView textviewLogin, editTextError;
    String Email, Password;
    FirebaseAuth fireBaseAuth;

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().hide();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextError = findViewById(R.id.editTextError);

        btnRegister = findViewById(R.id.btnRegister);

        textviewLogin = findViewById(R.id.textviewLogin);

        progressBar = findViewById(R.id.progressBar);

        textviewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
            }
        });

        fireBaseAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE); // for progress bar visibility

                if(!EmailValidation() | !PasswordValidation() | !ConfirmPasswordValidation()){
                    progressBar.setVisibility(View.GONE);
                    return;
                }else{
                    Email = editTextEmail.getText().toString();
                    Password = editTextPassword.getText().toString();

                    fireBaseAuth.createUserWithEmailAndPassword(Email,Password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        fireBaseAuth.getCurrentUser().sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(RegistrationActivity.this, "Successfull", Toast.LENGTH_LONG).show();
                                                            startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                                                        }
                                                    }
                                                });
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("This",e.getMessage());
                            if(e.getMessage().equals("The email address is already in use by another account.")){
                                editTextEmail.setError("The email address is already in use by another account.");
                                editTextError.setText("The email address is already in use by another account.");
                            }else if(e.getMessage().equals("The email address is badly formatted.")){
                                editTextError.setText("The email address is badly formatted.");
                                editTextEmail.setError("The email address is badly formatted.");
                            }

                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    boolean EmailValidation(){
        String email = editTextEmail.getText().toString();
        if(email.isEmpty()){
            editTextEmail.setError("Email is Required");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextError.setText("Please Provide a valid email");
            return false;
        }else{
            editTextEmail.setError(null);
            return true;
        }
    }

    boolean PasswordValidation(){
        String password = editTextPassword.getText().toString();
        if(password.isEmpty()){
            editTextPassword.setError("Password is Required");
            return false;
        }else if(password.length() <6 || password.length() >10){
            editTextPassword.setError("Minumum 6 and Maximum 10 character");
            return false;
        }else{
            editTextPassword.setError(null);
            return true;
        }
    }

    boolean ConfirmPasswordValidation(){
        String password = editTextPassword.getText().toString();
        String confirmpassword = editTextConfirmPassword.getText().toString();

        if(confirmpassword.isEmpty()){
            editTextConfirmPassword.setError("Confirm Password is Required");
            return false;
        }else if(confirmpassword.length() <6 || confirmpassword.length() >10){
            editTextConfirmPassword.setError("Minumum 6 and Maximum 10 character");
            return false;
        }else if(!confirmpassword.equals(password)){
            editTextConfirmPassword.setError("Password and confirm password dose not match");
            return false;
        }else{
            editTextConfirmPassword.setError(null);
            return true;
        }
    }
}
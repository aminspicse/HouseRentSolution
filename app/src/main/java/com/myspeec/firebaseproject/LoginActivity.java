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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText editTextEmail, editTextPassword;
    TextView textViewforgotPassword,textViewRegister, editTextError;
    ProgressBar progressBar;

    FirebaseAuth fireBaseAuth;
    public String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide(); // for hiding actionbar

        btnLogin = findViewById(R.id.btnLogin);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextError = findViewById(R.id.editTextError);
        textViewforgotPassword = findViewById(R.id.forgotpassword);
        textViewRegister = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressBar);


        // check user login or not if already login then redirect ViewPost Activity class
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            startActivity(new Intent(this,ViewPost.class));
        }

        // If user is new user then work this code
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
            }
        });

        // if click forgot poassword then work this code
        textViewforgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                startActivity(new Intent(LoginActivity.this,PasswordResetActivity.class));
            }
        });

        fireBaseAuth = FirebaseAuth.getInstance();

        // for login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Login();
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

    // for user login
    public void Login(){

        if(!EmailValidation() | !PasswordValidation()){
            progressBar.setVisibility(View.GONE);
            return;
        }else{
            email = editTextEmail.getText().toString().toLowerCase();
            password = editTextPassword.getText().toString();

            fireBaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                if(fireBaseAuth.getCurrentUser().isEmailVerified()){
                                    startActivity(new Intent(LoginActivity.this,ViewPost.class));
                                    finish();
                                }else{
                                    editTextError.setText("Please Verify your email address! Check Your Email.");
                                    editTextEmail.setError("Please Verify your email address! Check Your Email.");
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("This",e.getMessage());
                    if(e.getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted.")){
                        editTextEmail.setError("This user is not Registered");
                        editTextError.setText("This user is not Registered");
                        progressBar.setVisibility(View.GONE);
                    }else if(e.getMessage().equals("The email address is badly formatted.")){
                        editTextEmail.setError("The email address is badly formatted.");
                        editTextError.setText("The email address is badly formatted.");
                        progressBar.setVisibility(View.GONE);
                    }else if(e.getMessage().equals("The password is invalid or the user does not have a password.")){
                        editTextError.setText("The password is invalid or the user does not have a password.");
                        editTextPassword.setError("The password is invalid or the user does not have a password.");
                        progressBar.setVisibility(View.GONE);
                    }

                }
            });
        }
    }
}
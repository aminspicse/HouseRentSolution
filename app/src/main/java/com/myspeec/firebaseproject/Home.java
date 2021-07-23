package com.myspeec.firebaseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {

 //   public class Home extends MainActivity {
    CardView cardViewsubmit;
    TextInputLayout textInputLayoutusername, textInputLayoutMobile;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFireStroe;

    String UserId, UserName,MobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cardViewsubmit = findViewById(R.id.CardviewSubmit);
        textInputLayoutusername = findViewById(R.id.textInputLayoutUsername);
        textInputLayoutMobile = findViewById(R.id.textInputLayoutMobile);

        // get instance
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFireStroe = FirebaseFirestore.getInstance();

        UserId = firebaseAuth.getCurrentUser().getUid();

        cardViewsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!UserNameValidation() | !MobileValidation()){
                    return;
                }else{
                    UserName = textInputLayoutusername.getEditText().getText().toString();
                    MobileNumber = textInputLayoutMobile.getEditText().getText().toString();

                    Map<String,Object>UserData = new HashMap <>();
                    UserData.put("UserName",UserName);
                    UserData.put("MobileNumber",MobileNumber);

                    firebaseFireStroe.collection(UserId)
                            .document()
                            .set(UserData)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Home.this,"User Data stored in firebase store",Toast.LENGTH_LONG).show();
                                        textInputLayoutusername.clearFocus();
                                    }else{
                                        Log.d("This",task.getException().getMessage());
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("This",e.getMessage());
                        }
                    });
                }
            }
        });
    }

    boolean UserNameValidation(){
        String username = textInputLayoutusername.getEditText().getText().toString();
        if(username.isEmpty()){
            textInputLayoutusername.setError("User Name is required");
            return false;
        }else{
            textInputLayoutusername.setError(null);
            return true;
        }
    }

    boolean MobileValidation(){
        String mobile = textInputLayoutMobile.getEditText().getText().toString();
        if(mobile.isEmpty()){
            textInputLayoutMobile.setError("Mobile Is Required");
            return false;
        }else{
            textInputLayoutMobile.setError(null);
            return true;
        }
    }
}
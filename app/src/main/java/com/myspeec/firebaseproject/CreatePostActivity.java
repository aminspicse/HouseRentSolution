package com.myspeec.firebaseproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.ComponentActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class CreatePostActivity extends AppCompatActivity {

    int Image_Request_Code=7;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;
    ImageButton imageButton;
    EditText editTextTitle, editTextDescription, editTextTaka, editTextLocation,editTextMobile;
    Button postButton;

    Context context;
    private static final int Gallery_Code=1;
    Uri imageUrl = null;
    ProgressDialog progressDialog;

    String Email;

    ImageView homeIcon, createPost, logOut, backIcon, searchIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        getSupportActionBar().hide();

        imageButton = findViewById(R.id.imageButton);
        postButton = findViewById(R.id.postButton);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextTaka = findViewById(R.id.editTextTaka);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextMobile = findViewById(R.id.editTextMobile);

        // Menu icon
        homeIcon = findViewById(R.id.homeIcon);
        createPost = findViewById(R.id.createPost);
        logOut = findViewById(R.id.logOut);
        backIcon = findViewById(R.id.backIcon);
        searchIcon = findViewById(R.id.search_icon);

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreatePostActivity.this,SearchPost.class));
            }
        });
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreatePostActivity.this,ViewPost.class));
            }
        });
        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreatePostActivity.this,CreatePostActivity.class));
            }
        });


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(CreatePostActivity.this,LoginActivity.class));
            }
        });

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreatePostActivity.this, MainActivity.class));
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        Email = firebaseAuth.getCurrentUser().getEmail();

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("posts");
        mStorage = FirebaseStorage.getInstance();

        progressDialog = new ProgressDialog(this);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //intent.setType("image/*");
                //takePhoto(new Activity());
                //CreatePostActivity.this.startActivityForResult(intent,Gallery_Code);
                // https://www.youtube.com/watch?v=KuqLbN41Rag

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imageUrl = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUrl);
                imageButton.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post();
            }
        });
    }

    public void Post(){
        String title = editTextTitle.getText().toString().trim();
        String descripton = editTextDescription.getText().toString().trim();
        String rent = editTextTaka.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();
        String mobile = editTextMobile.getText().toString().trim();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd G 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());


        if(!(title.isEmpty() && descripton.isEmpty() && imageUrl != null)){
            progressDialog.setTitle("Posting....");
            progressDialog.show();

            StorageReference filepath = mStorage.getReference().child("posts").child(imageUrl.getLastPathSegment());
            filepath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            String t = task.getResult().toString();
                            DatabaseReference newPost = mRef.push();
                            newPost.child("imageName").setValue(title);
                            newPost.child("imageURL").setValue(task.getResult().toString());
                            newPost.child("description").setValue(descripton);
                            newPost.child("rentAmount").setValue(rent);
                            newPost.child("location").setValue(location);
                            newPost.child("email").setValue(Email);
                            newPost.child("postTime").setValue(currentDateandTime);
                            newPost.child("mobile").setValue(mobile);
                            progressDialog.dismiss();

                            startActivity(new Intent(CreatePostActivity.this,ViewPost.class));
                        }

                    });
                }
            });
        }
    }
    public static void takePhoto(Activity activity){
        //Activity activity;
        activity.startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), Gallery_Code);
    }

}
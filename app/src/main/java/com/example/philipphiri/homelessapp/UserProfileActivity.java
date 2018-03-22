package com.example.philipphiri.homelessapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

public class UserProfileActivity extends AppCompatActivity {

    private ImageView userPicture;
    private TextView userName;
    private TextView userEmail;
    private TextView residence;
    private TextView curresidence;
    private TextView claims;
    private TextView numClaims;
    private DatabaseReference userData;
    private FirebaseAuth user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //set the values for the user
        userPicture = findViewById(R.id.userImageView);
        userName = findViewById(R.id.nameTextView);
        userEmail = findViewById(R.id.emailTextView);
        residence = findViewById(R.id.residencyTextView);
        curresidence = findViewById(R.id.residencySetTextView);
        claims = findViewById(R.id.numClaims);
        numClaims = findViewById(R.id.claimsSetTextView);
//        String user_id = user.getCurrentUser().getUid();
//        DatabaseReference current_user = userData.child("Users").child(user_id);
    }
}

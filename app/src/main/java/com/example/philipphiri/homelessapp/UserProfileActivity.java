package com.example.philipphiri.homelessapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class UserProfileActivity extends AppCompatActivity {

    private Button claimsButton;
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

        claimsButton = findViewById(R.id.releaseButton);
        userPicture = findViewById(R.id.userImageView); //make button for image setting later
        userName = findViewById(R.id.nameTextView);
        userEmail = findViewById(R.id.emailTextView);
        residence = findViewById(R.id.residencyTextView);
        claims = findViewById(R.id.numClaims);
        curresidence = findViewById(R.id.residencySetTextView);
        numClaims = findViewById(R.id.claimsSetTextView);

        //release claims and set residence back to None
        user = FirebaseAuth.getInstance();
        userData = FirebaseDatabase.getInstance().getReference().child("Users");


        claimsButton.setOnClickListener(new View.OnClickListener() {
            String user_id = user.getCurrentUser().getUid();
            DatabaseReference current_user = userData.child(user_id);
            @Override
            public void onClick(View view) {
                current_user.child("ShelterRegistered").setValue("none");
                current_user.child("NumberClaimed").setValue("0");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String user_id = user.getCurrentUser().getUid();
        final DatabaseReference current_user = userData.child(user_id);
        current_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User u = new User((String)dataSnapshot.child("UserType").getValue(), (String)dataSnapshot.child("PermissionLevel").getValue(),
                        (String)dataSnapshot.child("ShelterRegistered").getValue(), "meep", (String)dataSnapshot.child("NumberClaimed").getValue());
                curresidence.setText(u.getUserResidence());
                //set name
                numClaims.setText(u.getNumClaims());
                //set userEmail.setText(u.getEmail()); also add to constructor^
                userName.setText(u.getUserName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

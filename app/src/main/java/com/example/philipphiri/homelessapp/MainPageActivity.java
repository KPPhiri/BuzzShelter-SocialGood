package com.example.philipphiri.homelessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainPageActivity extends AppCompatActivity {

    private ImageButton userProfileButton;
    private ImageButton mapActivityButton;
    private ImageButton shelterListButton;
    private Button logout;

//    private TextView profileText;
//    private TextView mapText;
//    private TextView shelterText;

    private DatabaseReference userData;
    private static User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        userProfileButton = findViewById(R.id.profileButton);
        mapActivityButton = findViewById(R.id.mapButton);
        shelterListButton = findViewById(R.id.sheltersButton);
//        profileText = findViewById(R.id.profileTextView);
//        mapText = findViewById(R.id.mapTextView);
//        shelterText = findViewById(R.id.shelterTextView);
        logout = findViewById(R.id.logoutButton);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainPageActivity.this, WelcomePageActivity.class ));
            }
        });

        mapActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainPageActivity.this, MapsActivity.class ));
            }
        });

        shelterListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainPageActivity.this, ShelterListActivity.class ));
            }
        });

        userProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainPageActivity.this, UserProfileActivity.class ));
            }
        });

    }

    public static User getCurrentUser() {
        return u;
    }

    @Override
    protected void onStart() {
        super.onStart();
        userData = FirebaseDatabase.getInstance().getReference().child("Users");
        DatabaseReference current_user = userData.child(WelcomePageActivity.getCurrentUser());
        current_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                u = new User((String)dataSnapshot.child("UserType").getValue(), (String)dataSnapshot.child("PermissionLevel").getValue(),
                        (String)dataSnapshot.child("ShelterRegistered").getValue(), (String)dataSnapshot.child("Name").getValue(),
                        (String)dataSnapshot.child("NumberClaimed").getValue(), (String)dataSnapshot.child("Email").getValue(),
                        (String)dataSnapshot.child("Religion").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

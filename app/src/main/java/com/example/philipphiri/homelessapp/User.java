package com.example.philipphiri.homelessapp;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by philipphiri on 2/20/18.
 */

public class User {
    private String userType;
    private String permissionLevel;
    private String residence;
    private String name;
    private String claims;
    private String religion;
    private String email;
    //static DatabaseReference databaseUsers;
    DatabaseReference userData;


    public User(String userType, String permissionLevel, String residence, String name, String claims, String email, String religion) {
        this.userType = userType;
        this.permissionLevel = permissionLevel;
        this.residence = residence;
        this.name = name;
        this.claims = claims;
        this.email = email;
        this.religion = religion;

    }

    public String getUserType(){
        return userType;
    }
    public void setUserType(String type){
        this.userType = type;
    }

    public String getUserResidence() { return residence; }
    public void setUserResidence(String res) { this.residence = res; }

    public String getNumClaims() {

        userData = FirebaseDatabase.getInstance().getReference().child("Users");
        DatabaseReference current_user = userData.child(WelcomePageActivity.getCurrentUser());
        current_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                claims = (String) dataSnapshot.child("NumberClaimed").getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return claims; }
    public void setNumClaims(String num) {this.claims = num; }

    public String getUserName() {return name; }
    public void setUserName(String newName) {this.name = newName; }

    public String getUserEmail() { return email; }
    public void setUserEmail(String newEmail) { this.email = newEmail; }

    public String getUserReligion() { return religion; }
    public void setUserReligion(String newRel) {this.religion = newRel; }

    public String getPermissionLevel(){
        return permissionLevel;
    }
    public void setPermissionLevel(String permlvl){
        this.permissionLevel = permlvl;
    }

}

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
    private final String userType;
    private final String permissionLevel;
    private final String residence;
    private final String name;
    private String claims;
    private final String religion;
    private final String email;
    //static DatabaseReference databaseUsers;
    private DatabaseReference userData;


    public User(String userType, String permissionLevel, String residence, String name, String claims, String email, String religion) {
        this.userType = userType;
        this.permissionLevel = permissionLevel;
        this.residence = residence;
        this.name = name;
        this.claims = claims;
        this.email = email;
        this.religion = religion;

    }
    //UD WARNING
//    public String getUserType(){
//        return userType;
//    }
//    public void setUserType(String type){
//        this.userType = type;
//    }

    public String getUserResidence() { return residence; }
    //UD WARNING
    // public void setUserResidence(String res) { this.residence = res; }

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
    //UD WARNING
    //public void setNumClaims(String num) {this.claims = num; }

    public String getUserName() {return name; }
//    UD WARNING
//    public void setUserName(String newName) {this.name = newName; }

    public String getUserEmail() { return email; }
    //UD WARNING
    //public void setUserEmail(String newEmail) { this.email = newEmail; }

    //UD WARNING
    public String getUserReligion() { return religion; }
    // public void setUserReligion(String newRel) {this.religion = newRel; }

    //UD WARNING
//    public String getPermissionLevel(){
//        return permissionLevel;
//    }
//    public void setPermissionLevel(String permlvl){
//        this.permissionLevel = permlvl;
//    }

}

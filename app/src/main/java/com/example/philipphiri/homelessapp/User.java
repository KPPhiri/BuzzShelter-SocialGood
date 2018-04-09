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


    /**
     * @param userType type of user
     * @param permissionLevel permission level of user
     * @param residence residence of user
     * @param name name of user
     * @param claims claims of user
     * @param email email of user
     * @param religion religion of user
     */
    public User(String userType, String permissionLevel, String residence, String name, String claims, String email, String religion) {
        this.userType = userType;
        this.permissionLevel = permissionLevel;
        this.residence = residence;
        this.name = name;
        this.claims = claims;
        this.email = email;
        this.religion = religion;

    }

    /**
     * @return user type
     */
    public String getUserType(){
        return userType;
    }

    /**
     * @param type new user type
     */
    public void setUserType(String type){
        this.userType = type;
    }

    /**
     * @return residence of user
     */
    public String getUserResidence() { return residence; }

    /**
     * @param res new user residence
     */
    public void setUserResidence(String res) { this.residence = res; }

    /**
     * @return user's number of claims
     */
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

    /**
     * @param num new number of claims
     */
    public void setNumClaims(String num) {this.claims = num; }

    /**
     * @return name of user
     */
    public String getUserName() {return name; }

    /**
     * @param newName new name of user
     */
    public void setUserName(String newName) {this.name = newName; }

    /**
     * @return email of user
     */
    public String getUserEmail() { return email; }

    /**
     * @param newEmail new email of user
     */
    public void setUserEmail(String newEmail) { this.email = newEmail; }

    /**
     * @return religion of user
     */
    public String getUserReligion() { return religion; }

    /**
     * @param newRel new religion of user
     */
    public void setUserReligion(String newRel) {this.religion = newRel; }

    /**
     * @return permission level of user
     */
    public String getPermissionLevel(){
        return permissionLevel;
    }

    /**
     * @param permlvl new permission level of user
     */
    public void setPermissionLevel(String permlvl){
        this.permissionLevel = permlvl;
    }

}

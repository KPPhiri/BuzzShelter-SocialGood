package com.example.philipphiri.homelessapp;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * User class with attributes
 */
public class User {
    private final String residence;
    private  String name;
    private String claims;
    private final String religion;
    private final String email;
    //static DatabaseReference databaseUsers;
    //private DatabaseReference userData;


    /**
     * @param userType type of user
     * @param permissionLevel permission level of user
     * @param residence residence of user
     * @param name name of user
     * @param claims claims of user
     * @param email email of user
     * @param religion religion of user
     */
    public User(String userType, String permissionLevel, String residence, String name,
                String claims, String email, String religion) {
        String userType1 = userType;
        String permissionLevel1 = permissionLevel;
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

//    /**
//     * @return user type
//     */
//    public String getUserType(){
//        return userType;
//    }

//    /**
//     * @param type new user type
//     */
//    public void setUserType(String type){
//        this.userType = type;
//    }

    /**
     * @return residence of user
     */
    public CharSequence getUserResidence() { return residence; }
    //UD WARNING
    // public void setUserResidence(String res) { this.residence = res; }

//    /**
//     * @param res new user residence
//     */
//    public void setUserResidence(String res) { this.residence = res; }

    /**
     * @return user's number of claims
     */
    public String getNumClaims() {
        DatabaseReference userData;
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

//    /**
//     * @param num new number of claims
//     */
//    public void setNumClaims(String num) {this.claims = num; }

    /**
     * @return name of user
     */
    public CharSequence getUserName() {return name; }
//    UD WARNING
   public void setUserName(String newName) {

        if(!newName.isEmpty() && (newName.length() < 10)) {
            this.name = newName;
        }
    }

//    /**
//     * @param newName new name of user
//     */
//    public void setUserName(String newName) {this.name = newName; }

    /**
     * @return email of user
     */
    public CharSequence getUserEmail() { return email; }
    //UD WARNING
    //public void setUserEmail(String newEmail) { this.email = newEmail; }

//    /**
//     * @param newEmail new email of user
//     */
//    public void setUserEmail(String newEmail) { this.email = newEmail; }

    //UD WARNING
    /**
     * @return religion of user
     */
    public CharSequence getUserReligion() { return religion; }
    // public void setUserReligion(String newRel) {this.religion = newRel; }

//    /**
//     * @param newRel new religion of user
//     */
//    public void setUserReligion(String newRel) {this.religion = newRel; }

    //UD WARNING
//    public String getPermissionLevel(){
//        return permissionLevel;
//    }
//    public void setPermissionLevel(String permlvl){
//        this.permissionLevel = permlvl;
//    }

//    /**
//     * @return permission level of user
//     */
//    public String getPermissionLevel(){
//        return permissionLevel;
//    }

//    /**
//     * @param permlvl new permission level of user
//     */
//    public void setPermissionLevel(String permlvl){
//        this.permissionLevel = permlvl;
//    }

}

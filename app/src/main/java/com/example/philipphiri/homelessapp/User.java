package com.example.philipphiri.homelessapp;

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

    public String getNumClaims() { return claims; }
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

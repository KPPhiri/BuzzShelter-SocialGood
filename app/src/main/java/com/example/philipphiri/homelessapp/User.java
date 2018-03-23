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

    public User(String userType, String permissionLevel, String residence, String name, String claims) {
        this.userType = userType;
        this.permissionLevel = permissionLevel;
        this.residence = residence;
        this.name = name;
        this.claims = claims;
        //this.email = email;
        //this.religion = religion;
    }

    public String getUserType(){
        return userType;
    }

    public String getUserResidence() { return residence; }

    public String getNumClaims() { return claims; }

    public String getUserName() {return name; }

    public String getUserEmail() { return email; }

    public String getUserReligion() { return religion; }

    public String getPermissionLevel(){
        return permissionLevel;
    }

    public void setUserType(){
        this.userType = userType;
    }

    public void setPermissionLevel(){
        this.permissionLevel = permissionLevel;
    }

}

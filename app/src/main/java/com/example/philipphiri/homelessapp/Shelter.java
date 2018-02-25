package com.example.philipphiri.homelessapp;

/**
 * Created by philipphiri on 2/24/18.
 */

public class Shelter {
    private String address;
    private String capacity;
    private String latitude;
    private String longitude;
    private String phoneNumber;
    private String restrictions;
    private String shelterName;
    private String specialNotes;
    private String uniqueKey;

    public Shelter(String address, String capacity, String latitude, String longitude, String phoneNumber, String restrictions, String shelterName, String specialNotes, String uniqueKey) {
        this.address = address;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
        this.restrictions = restrictions;
        this.shelterName = shelterName;
        this.specialNotes = specialNotes;
        this.uniqueKey = uniqueKey;
    }
}

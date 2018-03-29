package com.example.philipphiri.homelessapp;

/**
 * Created by philipphiri on 2/24/18.
 */

public class Shelter {
    private String address;
    private String capacity;
    private double latitude;
    private double longitude;
    private String phoneNumber;
    private String restrictions;
    private String shelterName;
    private String specialNotes;
    private String uniqueKey;
    private String vacancies;


    public Shelter(String address, String capacity, double latitude, double longitude, String phoneNumber, String restrictions, String shelterName, String specialNotes, String uniqueKey) {
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

    public String getShelterName() {
        return shelterName;
    }
    public String getShelterAddress(){
        return address;
    }
    public String getShelterCapacity() { return capacity; }
    public String getShelterRestrictions() { return restrictions; }
    public double getShelterLongitude() {
        return longitude;
    }
    public double getShelterLatitude() { return latitude; }
    public String getShelterPhone() {
        return phoneNumber;
    }
    public String getShelterNotes() {
        return specialNotes;
    }
    public String getVacancies() { return vacancies; }

}

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
    public void setShelterName(String name) { shelterName = name; }
    public String getShelterAddress(){
        return address;
    }
    public void setShelterAddress(String add) { address = add; }
    public String getShelterCapacity() { return capacity; }
    public void setCapacity(String c) { capacity = c; }
    public String getShelterRestrictions() { return restrictions; }
    public void setShelterRestrictions(String restrict) { restrictions = restrict; }
    public double getShelterLongitude() {
        return longitude;
    }
    public void setShelterLongitude(Double longi) { longitude = longi; }
    public double getShelterLatitude() { return latitude; }
    public void setShelterLatitude(Double lati) { latitude = lati; }
    public String getShelterPhone() {
        return phoneNumber;
    }
    public void setShelterPhone(String phone) { phoneNumber = phone; }
    public String getShelterNotes() {
        return specialNotes;
    }
    public void setShelterNotes(String notes) { specialNotes = notes; }
    //public String getVacancies() { return vacancies; }
    public String getUniqueKey() {return uniqueKey; }
    public void setUniqueKey(String key) { uniqueKey = key; }

}

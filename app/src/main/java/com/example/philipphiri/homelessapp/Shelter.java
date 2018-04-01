package com.example.philipphiri.homelessapp;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    static DatabaseReference databaseShelters;

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
        //this.claims = "0";
    }

    public String getShelterName() {
        databaseShelters = FirebaseDatabase.getInstance().getReference("Shelters");
        databaseShelters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot tuple : dataSnapshot.getChildren()) {
                    if (tuple.child("Unique Key").getValue().equals(uniqueKey)) {
                        shelterName = (String) tuple.child("Shelter Name").getValue();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return shelterName;
    }
    public void setShelterName(String name) { shelterName = name; }

    public String getShelterAddress(){
        databaseShelters = FirebaseDatabase.getInstance().getReference("Shelters");
        databaseShelters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot tuple : dataSnapshot.getChildren()) {
                    if (tuple.child("Unique Key").getValue().equals(uniqueKey)) {
                        address = (String) tuple.child("Address").getValue();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return address;
    }
    public void setShelterAddress(String add) { address = add; }

    public String getShelterCapacity() {

        databaseShelters = FirebaseDatabase.getInstance().getReference("Shelters");
        databaseShelters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot tuple : dataSnapshot.getChildren()) {
                    if (tuple.child("Unique Key").getValue().equals(uniqueKey)) {
                        capacity = (String) tuple.child("Capacity").getValue();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return capacity; }
    public void setCapacity(String c) { capacity = c; }

    public String getShelterRestrictions() {
        databaseShelters = FirebaseDatabase.getInstance().getReference("Shelters");
        databaseShelters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot tuple : dataSnapshot.getChildren()) {
                    if (tuple.child("Unique Key").getValue().equals(uniqueKey)) {
                        restrictions = (String) tuple.child("Restrictions").getValue();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return restrictions; }
    public void setShelterRestrictions(String restrict) { restrictions = restrict; }
    public double getShelterLongitude() {

        return longitude;
    }
    public void setShelterLongitude(Double longi) { longitude = longi; }

    public double getShelterLatitude() {

        return latitude; }
    public void setShelterLatitude(Double lati) { latitude = lati; }

    public String getShelterPhone() {
        databaseShelters = FirebaseDatabase.getInstance().getReference("Shelters");
        databaseShelters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot tuple : dataSnapshot.getChildren()) {
                    if (tuple.child("Unique Key").getValue().equals(uniqueKey)) {
                        phoneNumber = (String) tuple.child("Phone Number").getValue();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return phoneNumber;
    }
    public void setShelterPhone(String phone) { phoneNumber = phone; }

    public String getShelterNotes() {
        databaseShelters = FirebaseDatabase.getInstance().getReference("Shelters");
        databaseShelters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot tuple : dataSnapshot.getChildren()) {
                    if (tuple.child("Unique Key").getValue().equals(uniqueKey)) {
                        specialNotes = (String) tuple.child("Special Notes").getValue();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return specialNotes;
    }
    public void setShelterNotes(String notes) { specialNotes = notes; }

    public String getUniqueKey() {
        databaseShelters = FirebaseDatabase.getInstance().getReference("Shelters");
        databaseShelters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot tuple : dataSnapshot.getChildren()) {
                    if (tuple.child("Unique Key").getValue().equals(uniqueKey)) {
                        uniqueKey = (String) tuple.child("Unique Key").getValue();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return uniqueKey; }
    public void setUniqueKey(String key) { uniqueKey = key; }


}

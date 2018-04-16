package com.example.philipphiri.homelessapp;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Shelter class with attributes
 */
class Shelter {
    private String address;
    private String capacity;
    private final double latitude;
    private final double longitude;
    private String phoneNumber;
    private String restrictions;
    private String shelterName;
    private String specialNotes;
    private String uniqueKey;

    private static DatabaseReference databaseShelters;

    /**
     * @param address address of shelter
     * @param capacity capacity of shelter
     * @param latitude latitude of shelter
     * @param longitude longitude of shelter
     * @param phoneNumber phone number of shelter
     * @param restrictions restrictions of shelter
     * @param shelterName name of shelter
     * @param specialNotes special notes of shelter
     * @param uniqueKey unique key of shelter
     */
    public Shelter(String address, String capacity, double latitude, double longitude,
                   String phoneNumber, String restrictions, String shelterName,
                   String specialNotes, String uniqueKey) {
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

    /**
     * @return shelter name
     */
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

//    /**
//     * @param name new shelter name
//     */
//   // public void setShelterName(String name) { shelterName = name; }
//    public void setShelterName(String name) {
//        if(!name.isEmpty() && isLetter(name)) {
//            shelterName = name;
//        }
//    }

    /**
     * @return shelter address
     */
    public synchronized CharSequence getShelterAddress(){
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

//    /**
//     * @param add new shelter address
//     */
//    public void setShelterAddress(String add) { address = add; }
//    // public void setShelterAddress(String add) { address = add; }

    /**
     * @return shelter capacity
     */
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

//    /**
//     * @param c new capacity of shelter
//     */
//    public void setCapacity(String c) { capacity = c; }
//    //public void setCapacity(String c) { capacity = c; }

    /**
     * @return shelter restrictions
     */
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

//    /**
//     * @param restrict new shelter restrictions
//     */
//    public void setShelterRestrictions(String restrict) { restrictions = restrict; }

    /**
     * @return shelter longitude
     */
    // public void setShelterRestrictions(String restrict) { restrictions = restrict; }
    public double getShelterLongitude() {

        return longitude;
    }

//    /**
//     * @param longi new shelter longitude
//     */
////    public void setShelterLongitude(Double longi) { longitude = longi; }
//    //public void setShelterLongitude(Double longi) { longitude = longi; }

    /**
     * @return shelter latitude
     */
    public double getShelterLatitude() {

        return latitude; }

//    /**
//     * @param lati new shelter latitude
//     */
//    public void setShelterLatitude(Double lati) { latitude = lati; }
    // public void setShelterLatitude(Double lati) { latitude = lati; }

    /**
     * @return shelter phone number
     */
    public CharSequence getShelterPhone() {
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

//    /**
//     * @param phone new shelter phone number
//     */
//    public void setShelterPhone(String phone) { phoneNumber = phone; }
    //  public void setShelterPhone(String phone) { phoneNumber = phone; }

    /**
     * @return shelter notes
     */
    public CharSequence getShelterNotes() {
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

//    /**
//     * @param notes new shelter notes
//     */
//    public void setShelterNotes(String notes) { specialNotes = notes; }
    // public void setShelterNotes(String notes) { specialNotes = notes; }

    /**
     * @return unique shelter key
     */
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

//    /**
//     * @param key new unique shelter key
//     */
//    public void setUniqueKey(String key) { uniqueKey = key; }
    // public void setUniqueKey(String key) { uniqueKey = key; }

    //why is this here?
//    private boolean isLetter(String name) {
//        char[] chars = name.toCharArray();
//
//        for (char c : chars) {
//            if(!Character.isLetter(c)) {
//                return false;
//            }
//        }
//
//        return true;
//    }


}

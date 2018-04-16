package com.example.philipphiri.homelessapp;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maps Activity
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    //private static final int ERROR_DIALOG_REQUEST = 9001;
    private GoogleMap mMap;
    private List<Shelter> shelters;
    private DatabaseReference databaseShelters;
    //private static final String TAG = "MapsActivity";
    //private NDSpinner filters;
    private Dialog genderCategories;
    private Dialog ageCategories;
    private Button filter;
    private Map<String,Marker> hashMapMarker;
    private static final float zoomLevel = 11f;
    private static final float lat = 33.753746f;
    private static final float lng = -84.386330f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        databaseShelters = FirebaseDatabase.getInstance().getReference("Shelters");
        shelters = new ArrayList<>();
    }


    //check if user has up-to-date google services
//    public boolean isServicesOK() {
//        Log.d(TAG, "isServicesOK: checking google services version");
//        int available = GoogleApiAvailability.getInstance()
//          .isGooglePlayServicesAvailable(MapsActivity.this);
//        //user is okay
//        if (available == ConnectionResult.SUCCESS) {
//            Log.d(TAG, "isServicesOK: checking google is working");
//            return true;
//        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
//            //error like update google play (fixable error)
//            Log.d(TAG, "isServicesOK: error occured but we can fix it");
//            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MapsActivity.this,
//              available, ERROR_DIALOG_REQUEST);
//            dialog.show();
//        } else {
//            Toast.makeText(this, "You can't make map request.";)
//        }
//        return false;
//    }
//

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        genderCategories = new Dialog(this);
        ageCategories = new Dialog(this);
        NDSpinner filters;
        filters = findViewById(R.id.filterSpinner);
        hashMapMarker = new HashMap<>();
        ArrayAdapter<Filter> filterAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Filter.values());
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        filters.setAdapter(filterAdapter);

        filters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if ("Gender".equals(selectedItem)) {
                    showGenderPopUp();
                } else if ("Age".equals(selectedItem)) {
                    showAgePopUp();
                } else if ("No Filters".equals(selectedItem)) {
                    showAllShelters();
                    Log.d("ShelterY", shelters.size() + " ");
                }

            }

            // to close the onItemSelected
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Log.d("ShelterX", shelters.size() + " ");
        final EditText searchET = findViewById(R.id.searchBar);


        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = searchET.getText().toString();
                //shelterAdapter.filter(text);
                for (Shelter a: shelters) {
                    if(!(a.getShelterName().contains(text))){
                        Marker marker = hashMapMarker.get(a.getShelterName());
                        marker.setVisible(false);
                    }
                }
                for (Shelter a: shelters) {
                    if((a.getShelterName().contains(text))){
                        Marker marker = hashMapMarker.get(a.getShelterName());
                        marker.setVisible(true);
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0 , int arg1,
                                          int arg2, int arg3) {
            }


            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            }
        });





    }

    private void showAllShelters() {
        databaseShelters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shelters.clear();

                for (DataSnapshot tuple : dataSnapshot.getChildren()) {
                    Shelter shelter = new Shelter((String) tuple.child("Address").getValue(),
                            (String) tuple.child("Capacity").getValue(),
                            Double.parseDouble((String) tuple.child("Latitude ").getValue()),
                            Double.parseDouble((String) tuple.child("Longitude ").getValue()),
                            (String) tuple.child("Phone Number").getValue(),
                            (String) tuple.child("Restrictions").getValue(),
                            (String) tuple.child("Shelter Name").getValue(),
                            (String) tuple.child("Special Notes").getValue(),
                            (String) tuple.child("Unique Key").getValue());
                    //Shelter shelter = tuple.getValue(Shelter.class);
                    shelters.add(shelter);

                    LatLng x = new LatLng(Double.parseDouble((String)
                            tuple.child("Latitude ").getValue()),
                            Double.parseDouble((String) tuple.child("Longitude ").getValue()));
                    Marker temp = mMap.addMarker(new MarkerOptions()
                            .position(x).title((String) tuple.child("Shelter Name").getValue())
                            .snippet((String) tuple.child("Phone Number").getValue()));
                    hashMapMarker.put(shelter.getShelterName(), temp);
                    //float zoomLevel = 11f;
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,
                            lng), zoomLevel));
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showGenderPopUp() {
        genderCategories.setContentView(R.layout.gender_categories);
        filter = genderCategories.findViewById(R.id.filterButton);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checkable checkBoxM = genderCategories.findViewById(R.id.male);
                Checkable checkBoxF =  genderCategories.findViewById(R.id.female);

                if(checkBoxM.isChecked() && !checkBoxF.isChecked()) {
                    //shelterAdapter.genFilter("Men");
                    for (Shelter a: shelters) {
                        if(!(a.getShelterRestrictions().contains("Men"))){
                            Marker marker = hashMapMarker.get(a.getShelterName());
                            marker.setVisible(false);
                        }
                    }
                    for (Shelter a: shelters) {
                        if((a.getShelterRestrictions().contains("Men"))){
                            Marker marker = hashMapMarker.get(a.getShelterName());
                            marker.setVisible(true);
                        }
                    }

                }
                if(checkBoxF.isChecked() && !checkBoxM.isChecked()) {
                    //shelterAdapter.genFilter("Women");
                    for (Shelter a: shelters) {
                        if(!(a.getShelterRestrictions().contains("Women"))){
                            Marker marker = hashMapMarker.get(a.getShelterName());
                            marker.setVisible(false);
                        }
                    }
                    for (Shelter a: shelters) {
                        if((a.getShelterRestrictions().contains("Women"))){
                            Marker marker = hashMapMarker.get(a.getShelterName());
                            marker.setVisible(true);
                        }
                    }
                }

                genderCategories.dismiss();
            }
        });
        genderCategories.show();
    }

    private void showAgePopUp() {

        ageCategories.setContentView(R.layout.age_categories);
        filter = ageCategories.findViewById(R.id.filterButton2);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checkable checkBoxN =  ageCategories.findViewById(R.id.Newborns);
                Checkable checkBoxC =  ageCategories.findViewById(R.id.Children);
                Checkable checkBoxY =  ageCategories.findViewById(R.id.Young_Adults);
                Checkable checkBoxA =  ageCategories.findViewById(R.id.Anyone);
                if(checkBoxN.isChecked()) {
                    for (Shelter a: shelters) {
                        if(!(a.getShelterRestrictions().contains("newborns"))
                                || !(a.getShelterRestrictions().contains("Newborns"))
                                || !(a.getShelterRestrictions().contains("Families w/ Newborns"))){
                            Marker marker = hashMapMarker.get(a.getShelterName());
                            marker.setVisible(false);
                        }
                    }
                    for (Shelter a: shelters) {
                        if((a.getShelterRestrictions().contains("newborns"))
                                || (a.getShelterRestrictions().contains("Newborns"))
                                || (a.getShelterRestrictions().contains("Families w/ Newborns"))){
                            Marker marker = hashMapMarker.get(a.getShelterName());
                            marker.setVisible(true);
                        }
                    }


                }
                if(checkBoxC.isChecked()) {
                    for (Shelter a: shelters) {
                        if(!(a.getShelterRestrictions().contains("Children"))){
                            Marker marker = hashMapMarker.get(a.getShelterName());
                            marker.setVisible(false);

                        }
                    }
                    for (Shelter a: shelters) {
                        if((a.getShelterRestrictions().contains("Children"))){
                            Marker marker = hashMapMarker.get(a.getShelterName());
                            marker.setVisible(true);
                        }
                    }
                }
                if (checkBoxY.isChecked()) {
                    for (Shelter a: shelters) {
                        if(!(a.getShelterRestrictions().contains("Young adults"))
                                || !(a.getShelterRestrictions().contains("Young Adults"))){
                            Marker marker = hashMapMarker.get(a.getShelterName());
                            marker.setVisible(false);
                        }
                    }
                    for (Shelter a: shelters) {
                        if((a.getShelterRestrictions().contains("Young adults"))
                                || (a.getShelterRestrictions().contains("Young Adults"))){
                            Marker marker = hashMapMarker.get(a.getShelterName());
                            marker.setVisible(true);
                        }
                    }
                }
                if (checkBoxA.isChecked()) {
                    for (Shelter a: shelters) {
                        if(!(a.getShelterRestrictions().contains("Anyone"))){
                            Marker marker = hashMapMarker.get(a.getShelterName());
                            marker.setVisible(false);
                        }
                    }
                    for (Shelter a: shelters) {
                        if((a.getShelterRestrictions().contains("Anyone"))){
                            Marker marker = hashMapMarker.get(a.getShelterName());
                            marker.setVisible(true);
                        }
                    }
                }

                ageCategories.dismiss();
            }
        });
        ageCategories.show();
    }

}

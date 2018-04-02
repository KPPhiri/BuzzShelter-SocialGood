package com.example.philipphiri.homelessapp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
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
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    //private static final int ERROR_DIALOG_REQUEST = 9001;
    private GoogleMap mMap;
    List<Shelter> shelters;
    DatabaseReference databaseShelters;
    //private static final String TAG = "MapsActivity";
    NDSpinner filters;
    Dialog genderCategories;
    Dialog ageCategories;
    private Button filter;
    ShelterList shelterAdapter;
    HashMap<String,Marker> hashMapMarker;


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

        shelterAdapter = new ShelterList(MapsActivity.this, shelters);






//        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//            @Override
//            public View getInfoWindow(Marker marker) {
//                return null;
//            }
//
//            @Override
//            public View getInfoContents(Marker marker) {
//                View v = getLayoutInflater().inflate(R.layout.window_layout, null);
//                //LatLng latLng = marker.getPosition();
//                TextView tvname = v.findViewById(R.id.tv_name);
//                TextView tvphone = v.findViewById(R.id.tv_phone);
//                tvname.setText(marker.getTitle());
//                tvphone.setText(marker.getSnippet());
//
//                return v;
//            }
//        });
    }


    //check if user has up-to-date google services
//    public boolean isServicesOK() {
//        Log.d(TAG, "isServicesOK: checking google services version");
//        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapsActivity.this);
//        //user is okay
//        if (available == ConnectionResult.SUCCESS) {
//            Log.d(TAG, "isServicesOK: checking google is working");
//            return true;
//        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
//            //error like update google play (fixable error)
//            Log.d(TAG, "isServicesOK: error occured but we can fix it");
//            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MapsActivity.this, available, ERROR_DIALOG_REQUEST);
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
        filters = (NDSpinner) findViewById(R.id.filterSpinner);
        hashMapMarker = new HashMap<>();
        ArrayAdapter<Filter> filterAdapter = new ArrayAdapter<Filter>(this, android.R.layout.simple_spinner_item,
                Filter.values());
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        filters.setAdapter(filterAdapter);

        filters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("Gender")) {
                    showGenderPopUp();
                } else if (selectedItem.equals("Age")) {
                   showAgePopUp();
                } else if (selectedItem.equals("No Filters")) {
                    showAllShelters();
                    Log.d("ShelterY", shelters.size() + " ");
                }

            }

            // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Log.d("ShelterX", shelters.size() + " ");
        final EditText searchET = (EditText)findViewById(R.id.searchBar);


        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
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
                // TODO Auto-generated method stub
            }


            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });





    }
    
    private void showAllShelters() {
        databaseShelters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shelters.clear();

                for (DataSnapshot tuple : dataSnapshot.getChildren()) {
                    Shelter shelter = new Shelter((String) tuple.child("Address").getValue(), (String) tuple.child("Capacity").getValue(), Double.parseDouble((String) tuple.child("Latitude ").getValue()),
                            Double.parseDouble((String) tuple.child("Longitude ").getValue()), (String) tuple.child("Phone Number").getValue(), (String) tuple.child("Restrictions").getValue(),
                            (String) tuple.child("Shelter Name").getValue(), (String) tuple.child("Special Notes").getValue(), (String) tuple.child("Unique Key").getValue());
                    //Shelter shelter = tuple.getValue(Shelter.class);
                    shelters.add(shelter);

                    LatLng x = new LatLng(Double.parseDouble((String) tuple.child("Latitude ").getValue()), Double.parseDouble((String) tuple.child("Longitude ").getValue()));
                    Marker temp = mMap.addMarker(new MarkerOptions()
                            .position(x).title((String) tuple.child("Shelter Name").getValue())
                            .snippet((String) tuple.child("Phone Number").getValue()));
                    hashMapMarker.put(shelter.getShelterName(), temp);
                    float zoomLevel = 11f;
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(33.753746, -84.386330), zoomLevel));
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showGenderPopUp() {
        genderCategories.setContentView(R.layout.gender_categories);
        filter = (Button) genderCategories.findViewById(R.id.filterButton);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBoxM= (CheckBox) genderCategories.findViewById(R.id.male);
                CheckBox checkBoxF = (CheckBox) genderCategories.findViewById(R.id.female);

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

//
//        Marker marker3 = hashMapMarker.get("Eden Village");
//        marker3.setVisible(false);
//        hashMapMarker.remove("Eden Village");
//
//
//        Marker marker1 = hashMapMarker.get("The Shepherd's Inn");
//        marker1.setVisible(false);
//        hashMapMarker.remove("The Shepherd's Inn");
//
//        Marker marker2 = hashMapMarker.get("Fuqua Hall");
//        marker2.setVisible(false);
//        hashMapMarker.remove("Fuqua Hall");

        ageCategories.setContentView(R.layout.age_categories);
        filter = (Button) ageCategories.findViewById(R.id.filterButton2);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBoxN = (CheckBox) ageCategories.findViewById(R.id.Newborns);
                CheckBox checkBoxC = (CheckBox) ageCategories.findViewById(R.id.Children);
                CheckBox checkBoxY = (CheckBox) ageCategories.findViewById(R.id.Young_Adults);
                CheckBox checkBoxA = (CheckBox) ageCategories.findViewById(R.id.Anyone);
                if(checkBoxN.isChecked()) {
//                    shelterAdapter.ageFilter("Newborns");
//                    shelterAdapter.ageFilter("Families w/ Newborns");
//                    shelterAdapter.noFilter();
                    for (Shelter a: shelters) {
                        if(!(a.getShelterRestrictions().contains("newborns"))){
                            Marker marker = hashMapMarker.get(a.getShelterName());
                            marker.setVisible(false);
                        }
                    }
                    for (Shelter a: shelters) {
                        if((a.getShelterRestrictions().contains("newborns"))){
                            Marker marker = hashMapMarker.get(a.getShelterName());
                            marker.setVisible(true);
                        }
                    }


                }
                if(checkBoxC.isChecked()) {
                    //shelterAdapter.ageFilter("Children");
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
//                    shelterAdapter.ageFilter("Young Adults");
//                    shelterAdapter.ageFilter("Young adults");
                    for (Shelter a: shelters) {
                        if(!(a.getShelterRestrictions().contains("Young adults"))){
                            Marker marker = hashMapMarker.get(a.getShelterName());
                            marker.setVisible(false);
                        }
                    }
                    for (Shelter a: shelters) {
                        if((a.getShelterRestrictions().contains("Young adults"))){
                            Marker marker = hashMapMarker.get(a.getShelterName());
                            marker.setVisible(true);
                        }
                    }
                }
                if (checkBoxA.isChecked()) {
                    //shelterAdapter.ageFilter("Anyone");
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

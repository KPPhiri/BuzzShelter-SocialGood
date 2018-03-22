package com.example.philipphiri.homelessapp;

import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    //private static final int ERROR_DIALOG_REQUEST = 9001;
    private GoogleMap mMap;
    List<Shelter> shelters;
    DatabaseReference databaseShelters;
    //private static final String TAG = "MapsActivity";

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
//    protected void addShelters() {
//        databaseShelters.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                shelters.clear();
//
//                for (DataSnapshot tuple: dataSnapshot.getChildren()) {
//                    Shelter shelter = new Shelter((String) tuple.child("Address").getValue(),(String) tuple.child("Capacity").getValue(), Double.parseDouble((String)tuple.child("Latitude ").getValue()),
//                            Double.parseDouble((String) tuple.child("Longitude ").getValue()), (String)tuple.child("Phone Number").getValue(), (String) tuple.child("Restrictions").getValue(),
//                            (String) tuple.child("Shelter Name").getValue(), (String)tuple.child("Special Notes").getValue(), (String) tuple.child("Unique Key").getValue());
//                    //Shelter shelter = tuple.getValue(Shelter.class);
//                    shelters.add(shelter);
//
//
//                    Log.d("test value", Double.parseDouble((String) tuple.child("Longitude ").getValue())+" ");
//                    latLngs.add(new LatLng(Double.parseDouble((String)tuple.child("Latitude ").getValue()), Double.parseDouble((String) tuple.child("Longitude ").getValue())));
//                    Log.d("ADD SHELTERS SIZE", latLngs.size()+" ");
//                }
//
//
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }





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
        databaseShelters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shelters.clear();

                for (DataSnapshot tuple: dataSnapshot.getChildren()) {
                    Shelter shelter = new Shelter((String) tuple.child("Address").getValue(),(String) tuple.child("Capacity").getValue(), Double.parseDouble((String)tuple.child("Latitude ").getValue()),
                            Double.parseDouble((String) tuple.child("Longitude ").getValue()), (String)tuple.child("Phone Number").getValue(), (String) tuple.child("Restrictions").getValue(),
                            (String) tuple.child("Shelter Name").getValue(), (String)tuple.child("Special Notes").getValue(), (String) tuple.child("Unique Key").getValue());
                    //Shelter shelter = tuple.getValue(Shelter.class);
                    shelters.add(shelter);

                    LatLng x = new LatLng(Double.parseDouble((String)tuple.child("Latitude ").getValue()), Double.parseDouble((String) tuple.child("Longitude ").getValue()));
                    mMap.addMarker(new MarkerOptions().position(x).title((String) tuple.child("Shelter Name").getValue()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(x));

                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //puts all the shelters on the map
    }
}

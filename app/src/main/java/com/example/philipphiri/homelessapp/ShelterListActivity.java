package com.example.philipphiri.homelessapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.service.autofill.Dataset;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShelterListActivity extends AppCompatActivity {
    private Button logout;
    Dialog myDialog;

    ListView listViewShelters;
    List<Shelter> shelters;
    DatabaseReference databaseShelters;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_list);
        Intent intent = getIntent();
        //ref of shelters node
        databaseShelters = FirebaseDatabase.getInstance().getReference("Shelters");
        //getting views
        listViewShelters = (ListView) findViewById(R.id.shelterListView);
        shelters = new ArrayList<>();

        logout = findViewById(R.id.logoutButton);
        myDialog = new Dialog(this);
        //need a way so that when a specific shelter is clicked the popup will open corresponding data
        //right now there's no way to open popup

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShelterListActivity.this, WelcomePageActivity.class ));
            }
        });
    }

    protected void onStart() {
        super.onStart();
        databaseShelters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shelters.clear();
                //not going into for loop!
                for (DataSnapshot tuple: dataSnapshot.getChildren()) {
                  Shelter shelter = new Shelter((String) tuple.child("Address").getValue(),(String) tuple.child("Capacity").getValue(), (String)tuple.child("Latitude ").getValue(),
                  (String) tuple.child("Longitude ").getValue(), (String)tuple.child("Phone Number").getValue(), (String) tuple.child("Restrictions").getValue(),
                  (String) tuple.child("Shelter Name").getValue(), (String)tuple.child("Special Notes").getValue(), (String) tuple.child("Unique Key").getValue());
                    //Shelter shelter = tuple.getValue(Shelter.class);
                    shelters.add(shelter);
                }
                //creating adapter
                ShelterList shelterAdapter = new ShelterList(ShelterListActivity.this, shelters);
                //attaching adapter to the listview
                listViewShelters.setAdapter(shelterAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //this will do the retrieving -- website way ??
//    @Override
//    protected void onStart() {
//        super.onStart();
//        //attaching value event listener
//        databaseShelters.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //shelters.clear(); //?clearing previous shelter list
//
//                //iterating through all the nodes
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    //getting shelter
//                    Shelter shelter = postSnapshot.getValue(Shelter.class);
//                    //adding shelter to the list
//                    shelters.add(shelter);
//                }
//                //creating adapter
//                ShelterList shelterAdapter = new ShelterList(ShelterListActivity.this, shelters);
//                //attaching adapter to the listview
//                listViewShelters.setAdapter(shelterAdapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    //for shelter details popup
//    private void ShowDetails(View v) {
//        TextView detailclose;
//        myDialog.setContentView(R.layout.shelter_detail);
//        detailclose = (TextView) myDialog.findViewById(R.id.detailclose);
//        detailclose.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                myDialog.dismiss();
//            }
//        });
//        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        myDialog.show();
//
//    }
}

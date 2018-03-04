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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
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
        listViewShelters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Shelter s = (Shelter) listViewShelters.getItemAtPosition(position);
                ShowDetails(v,s);
            }
        });
    }

    protected void onStart() {
        super.onStart();
        databaseShelters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shelters.clear();

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

    //for shelter details popup
    public void ShowDetails(View v, Shelter s) {
        TextView detailclose;
        myDialog.setContentView(R.layout.shelter_detail);
        detailclose = (TextView) myDialog.findViewById(R.id.detailclose);
        detailclose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tv1 = (TextView) myDialog.findViewById(R.id.shelterName);
        tv1.setText(s.getShelterName());
        TextView tv2 = (TextView) myDialog.findViewById(R.id.shelterCap);
        tv2.setText(s.getShelterCapacity());
        TextView tv3 = (TextView) myDialog.findViewById(R.id.shelterRes);
        tv3.setText(s.getShelterRestrictions());
        TextView tv4 = (TextView) myDialog.findViewById(R.id.shelterLongLat);
        tv4.setText(s.getShelterLongitude() + " " + s.getShelterLatitude());
        TextView tv5 = (TextView) myDialog.findViewById(R.id.shelterphone);
        tv5.setText(s.getShelterPhone());
        TextView tv6 = (TextView) myDialog.findViewById(R.id.shelteradd);
        tv6.setText(s.getShelterAddress());
        TextView tv7 = (TextView) myDialog.findViewById(R.id.shelternotes);
        tv7.setText(s.getShelterNotes());

        myDialog.show();

    }
}

package com.example.philipphiri.homelessapp;

import android.app.Dialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.service.autofill.Dataset;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.text.TextWatcher;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ShelterListActivity extends AppCompatActivity {
    private Button logout;
    private Button filter;
    Dialog myDialog;
    Dialog genderCategories;
    Dialog ageCategories;

    ListView listViewShelters;
    List<Shelter> shelters;
    DatabaseReference databaseShelters;
    NDSpinner filters;
    ShelterList shelterAdapter;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_list);
        Intent intent = getIntent();
        //ref of shelters node
        databaseShelters = FirebaseDatabase.getInstance().getReference("Shelters");
        //getting views
        listViewShelters = (ListView) findViewById(R.id.shelterListView);
        listViewShelters.setTextFilterEnabled(true);
        shelters = new ArrayList<>();

        logout = findViewById(R.id.logoutButton);
        myDialog = new Dialog(this);

        genderCategories = new Dialog(this);
        ageCategories = new Dialog(this);
        filters = (NDSpinner) findViewById(R.id.filterSpinner);
        ArrayAdapter<Filter> filterAdapter = new ArrayAdapter<Filter> (this, android.R.layout.simple_spinner_item,
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
                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

    private void showGenderPopUp() {
        genderCategories.setContentView(R.layout.gender_categories);
        filter = (Button) genderCategories.findViewById(R.id.filterButton);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBoxM= (CheckBox) genderCategories.findViewById(R.id.male);
                CheckBox checkBoxF = (CheckBox) genderCategories.findViewById(R.id.female);
//                if(checkBoxM.isChecked() && !checkBoxF.isChecked()) {
//                    //remove all the shelters that contain only women restrictions
//                    shelterAdapter.genFilter("Women");
//                }
//                if(checkBoxF.isChecked() && !checkBoxM.isChecked()) {
//                    //remove all the shelters that contain only men restrictions
//                    shelterAdapter.genFilter("Men");
//                }

                if(checkBoxM.isChecked() && !checkBoxF.isChecked()) {
                    shelterAdapter.genFilter("Men");
                }
                if(checkBoxF.isChecked() && !checkBoxM.isChecked()) {
                    shelterAdapter.genFilter("Women");
                }

                genderCategories.dismiss();
            }
        });
        genderCategories.show();
    }

    private void showAgePopUp() {
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
                    //adds all the shelters that contain only Newborns restrictions
                    shelterAdapter.ageFilter("Newborns");
                    shelterAdapter.ageFilter("Families w/ Newborns");
                    shelterAdapter.ageFilter("newborns");
                }
                if(checkBoxC.isChecked()) {
                    shelterAdapter.ageFilter("Children");
                }
                if (checkBoxY.isChecked()) {
                    shelterAdapter.ageFilter("Young Adults");
                    shelterAdapter.ageFilter("Young adults");
                }
                if (checkBoxA.isChecked()) {
                    shelterAdapter.ageFilter("Anyone");
                }

                ageCategories.dismiss();
            }
        });
        ageCategories.show();
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
                shelterAdapter = new ShelterList(ShelterListActivity.this, shelters);
                //attaching adapter to the listview
                listViewShelters.setAdapter(shelterAdapter);


                final EditText searchET = (EditText)findViewById(R.id.searchBar);
                // Capture Text in EditText
                searchET.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable arg0) {
                        // TODO Auto-generated method stub
                        String text = searchET.getText().toString().toLowerCase(Locale.getDefault());
                        shelterAdapter.filter(text);
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


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }




//    protected void onStart() {
//        super.onStart();
//
//
//    }

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

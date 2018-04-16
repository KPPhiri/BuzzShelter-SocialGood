package com.example.philipphiri.homelessapp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.text.TextWatcher;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * shelter list activity
 */
public class ShelterListActivity extends AppCompatActivity {
    //private Button logout;
    private Button filter;
    //private FirebaseAuth user;
    private DatabaseReference userData;
    private Dialog myDialog;
    private Dialog genderCategories;
    private Dialog ageCategories;

    private ListView listViewShelters;
    private List<Shelter> shelters;
    private static DatabaseReference databaseShelters;
    private NDSpinner filters;
    private ShelterList shelterAdapter;

    //int pos;
    private static  Shelter cur;
    //static String numbo;
    private Dialog myDialogPop;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_list);

        //******so that shelter adapter isnt null THIS IS FOR JUNIT TO WORK********
        Shelter original = new Shelter("ee", "capacity", 2.0,
                0.0,"phone", "rest", "name",
                "note", "100");
        List <Shelter> o = new ArrayList<>();
        o.add(original);
        shelterAdapter = new ShelterList(ShelterListActivity.this,o);

        //ref of shelters node
        databaseShelters = FirebaseDatabase.getInstance().getReference("Shelters");
        //getting views
        listViewShelters = (ListView) findViewById(R.id.shelterListView);
        listViewShelters.setTextFilterEnabled(true);
        shelters = new ArrayList<>();

        //logout = findViewById(R.id.logoutButton);
        myDialog = new Dialog(this);
        myDialogPop = new Dialog(this);

        genderCategories = new Dialog(this);
        ageCategories = new Dialog(this);
        filters = (NDSpinner) findViewById(R.id.filterSpinner);
        ArrayAdapter<Filter> filterAdapter = new ArrayAdapter<> (
                this, android.R.layout.simple_spinner_item, Filter.values());
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        filters.setAdapter(filterAdapter);

        filters.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if ("Gender".equals(selectedItem)) {
                    showGenderPopUp();
                } else if ("Age".equals(selectedItem)) {
                    showAgePopUp();
                } else if ("No Filters".equals(selectedItem)) {
                    showEntireList();
                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(ShelterListActivity.this, WelcomePageActivity.class ));
//            }
//        });

        //for getting current user's info
        //user = FirebaseAuth.getInstance();
        userData = FirebaseDatabase.getInstance().getReference().child("Users");

        listViewShelters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Shelter s = (Shelter) listViewShelters.getItemAtPosition(position);
                //curShelter(pos);
                ShowDetails(s);
            }
        });

    }
    /**
    private void vacancies() {
        int input = 0; // vacancies user wants to put
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.activity_shelter_list,);
        final AlertDialog.Builder builder = new AlertDialog.Builder(listViewShelters.this);
        builder.setMessage(shelters.get().getVacancies()).setNegativeButton("Exit", null);
        AlertDialog.Builder builder2 = new AlertDialog.Builder(listViewShelters. this);
        builder2.setView(input);
        builder2.setMessage("How many spaces to reserve in?").setPositiveButton("Enter",
                (dialog, which) -> {
                    try {
                        AlertDialog.Builder builder3 = new AlertDialog.
                            Builder(listViewShelters.this);
                        builder3.setMessage("Not enough space").setNegativeButton("Exit",
                                null).show();
                    }
                    catch (NumberFormatException n) {
                        AlertDialog.Builder builder3 =
                        new AlertDialog.Builder(listViewShelters, this);
                        builder3.setMessage("Invalid input").setNegativeButton("Exit, null").show();
                    }
        }).show();
     private void vacancies() {
     int input = 0; // vacancies user wants to put
     ArrayAdapter adapter = new ArrayAdapter(this, R.layout.activity_shelter_list,);
     final AlertDialog.Builder builder = new AlertDialog.Builder(listViewShelters.this);
     builder.setMessage(shelters.get().getVacancies()).setNegativeButton("Exit", null);
     AlertDialog.Builder builder2 = new AlertDialog.Builder(listViewShelters. this);
     builder2.setView(input);
     builder2.setMessage("How many spaces to reserve in?").setPositiveButton("Enter",
     (dialog, which) -> {
     try {
     AlertDialog.Builder builder3 = new AlertDialog.Builder(listViewShelters.this);
     builder3.setMessage("Not enough space").setNegativeButton("Exit",
     null).show();
     }
     catch (NumberFormatException n) {
     AlertDialog.Builder builder3 = new AlertDialog.Builder(listViewShelters, this);
     builder3.setMessage("Invalid input").setNegativeButton("Exit, null").show();
     }
     }).show();

     }
     */
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

    private void showEntireList() {
        shelterAdapter.noFilter();
    }

    protected void onStart() {
        super.onStart();
        databaseShelters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shelters.clear();

                for (DataSnapshot tuple: dataSnapshot.getChildren()) {
                    Shelter shelter = new Shelter((String) tuple.child("Address").getValue(),
                            (String) tuple.child("Capacity").getValue(),
                            Double.parseDouble((String)tuple.child("Latitude ").getValue()),
                            Double.parseDouble((String) tuple.child("Longitude ").getValue()),
                            (String)tuple.child("Phone Number").getValue(),
                            (String) tuple.child("Restrictions").getValue(),
                            (String) tuple.child("Shelter Name").getValue(),
                            (String)tuple.child("Special Notes").getValue(),
                            (String) tuple.child("Unique Key").getValue());

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
                        String text = searchET.getText().toString().
                                toLowerCase(Locale.getDefault());
                        shelterAdapter.filter(text);
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

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * @param pos position of current shelter
     */
    private void curShelter(Integer pos) {
        cur = (Shelter) listViewShelters.getItemAtPosition(pos);

    }

    /**
     * @param added add to shelter capacity
     */
    public  static void release(String added) {
        DatabaseReference shelter = databaseShelters.child(cur.getUniqueKey());
        shelter.child("Capacity").setValue(Integer.toString(
                Integer.parseInt(cur.getShelterCapacity()) + Integer.parseInt(added)));

    }
    //update registered shelter and claim number
        /**
         * update registered shelter and claim number
         * @param cur current shelter
         * @param claims claim number
         */
        private void claim(Shelter cur, EditText claims){
            curShelter(Integer.parseInt(cur.getUniqueKey()));
            // user = FirebaseAuth.getInstance();
            userData = FirebaseDatabase.getInstance().getReference().child("Users");
            DatabaseReference current_user = userData.child(WelcomePageActivity.getCurrentUser());
            current_user.child("ShelterRegistered").setValue(cur.getShelterName());
            current_user.child("NumberClaimed").setValue(claims.getText().toString());
            DatabaseReference shelter = databaseShelters.child(cur.getUniqueKey());
            shelter.child("Capacity").setValue(Integer.toString(
                    Integer.parseInt(cur.getShelterCapacity()) - Integer.parseInt(
                            claims.getText().toString())));
        }

     private static  boolean verification;

    /**
     * helper to verify legal claim
     * @param claimNum number of claims
     * @param cap capacity
     * @return boolean value
     */

    private boolean verifyClaim(String claimNum, String cap) {
        if ((Integer.parseInt(claimNum) != 0) && (Integer.parseInt(claimNum) < Integer.parseInt(cap))) {
            verification = true;
            return true;
        } else {
            verification = false;
            return false;
        }
    }

    /**
     *
     * @return boolean if verified
     */
    public  static boolean getVerify() {
        return verification;
    }

    //for shelter details popup

    /**
     * for shelter details popup
     * @param s shelter
     */

    private void ShowDetails(Shelter s) {
        final Shelter cur = s;
        Button claimButton;
        TextView detailclose;
        final EditText claims;
        myDialog.setContentView(R.layout.shelter_detail);
        claimButton = (Button) myDialog.findViewById(R.id.claim_button);
        detailclose = (TextView) myDialog.findViewById(R.id.detailclose);
        claims = (EditText) myDialog.findViewById(R.id.claim_number);
        detailclose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        claimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ("0".equals(MainPageActivity.getCurrentUser().getNumClaims())) {
                    boolean check = verifyClaim(claims.getText().toString(),
                            cur.getShelterCapacity());
                    //should we have a check to see if what they typed is even a number?

                    if (check) {
                        claim(cur,claims);

                    } else {
                        claims.setError("Not Enough Space");
                    }
                    //DO NOT DELETE THIS COMMENTED CODE****
//                    if (Integer.parseInt(claims.getText().toString()) != 0
//                      && Integer.parseInt(claims.getText().
//                      toString()) < Integer.parseInt(cur.getShelterCapacity())) {
//                        claim(cur,claims);
//                    } else if ( Integer.parseInt(claims.getText().
//                      toString()) > Integer.parseInt(cur.getShelterCapacity())){
//                        claims.setError("Not Enough Space");
//                    } else {
//                        claims.setError("Please Enter Valid Number");
//                    }
                } else {
                    ShowReleasePopUp();
                }

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

    private void ShowReleasePopUp() {
        Button okButton;
        myDialogPop.setContentView(R.layout.release_claims_popup);
        okButton = (Button) myDialogPop.findViewById(R.id.okButt);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialogPop.dismiss();
            }
        });

        TextView release;
        release = (TextView) myDialogPop.findViewById(R.id.releaseTextView);
        myDialogPop.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialogPop.show();

    }
}

package com.example.philipphiri.homelessapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShelterListActivity extends AppCompatActivity {
    private Button logout;
    Dialog myDialog;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_list);

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

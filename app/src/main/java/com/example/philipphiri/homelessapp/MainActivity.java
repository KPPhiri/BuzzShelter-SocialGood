package com.example.philipphiri.homelessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText logField = (EditText) findViewById(R.id.login);
                EditText passField = (EditText) findViewById(R.id.password);

                logField.setVisibility(View.VISIBLE);
                passField.setVisibility(View.VISIBLE);
            }
        });


    }

    
}

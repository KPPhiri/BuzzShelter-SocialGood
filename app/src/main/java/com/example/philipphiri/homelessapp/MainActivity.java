package com.example.philipphiri.homelessapp;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText logField = (EditText) findViewById(R.id.login);
                final EditText passField = (EditText) findViewById(R.id.password);

                login.setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View view) {
                                if (logField.getText().toString().equals("user")
                                        && passField.getText().toString().equals("pass")) {
                                    Log.i("clicks","Success");
                                    Intent i = new Intent(MainActivity.this, Main2Activity.class);
                                    startActivity(i);
                                } else {

                                    // inflate the layout of the popup window
                                    LayoutInflater inflater = (LayoutInflater)
                                            getSystemService(LAYOUT_INFLATER_SERVICE);
                                    View popupView = inflater.inflate(R.layout.wrong_login, null);

                                    // create the popup window
                                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                    boolean focusable = true; // lets taps outside the popup also dismiss it
                                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                                    // show the popup window
                                    ConstraintLayout activity_main_layout;
                                    activity_main_layout = findViewById(R.id.main_activity_layout);

                                    popupWindow.showAtLocation(activity_main_layout, Gravity.CENTER, 0, 0);

                                    // dismiss the popup window when touched
                                    popupView.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            popupWindow.dismiss();
                                            return true;
                                        }
                                    });

                                }
                            }
                        }


                );


                logField.setVisibility(View.VISIBLE);
                passField.setVisibility(View.VISIBLE);
            }
        });

    }

    
}

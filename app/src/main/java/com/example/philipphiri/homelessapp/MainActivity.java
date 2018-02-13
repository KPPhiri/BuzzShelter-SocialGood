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


        final Button login = findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText logField = findViewById(R.id.login);
                final EditText passField = findViewById(R.id.password);
                final Button cancel = findViewById(R.id.cancelButton);

                login.setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View view) {
                                if (logField.getText().toString().equals("user")
                                        && passField.getText().toString().equals("pass")) {
                                    Log.i("MainActivity","Successfully logged in");
                                    Intent i = new Intent(MainActivity.this, Main2Activity.class);
                                    startActivity(i);
                                } else {
                                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                                    View popupView = inflater.inflate(R.layout.wrong_login, null);

                                    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

                                    ConstraintLayout activity_main_layout;
                                    activity_main_layout = findViewById(R.id.main_activity_layout);

                                    popupWindow.showAtLocation(activity_main_layout, Gravity.CENTER, 0, 0);

                                    popupView.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View view1, MotionEvent event1) {
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
                cancel.setVisibility(View.VISIBLE);

                cancel.setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View view) {
                                Intent b = new Intent(MainActivity.this, MainActivity.class);
                                //restarts welcome screen to refresh buttons
                                startActivity(b);
                            }
                        }
                );
            }
        });

    }

    
}

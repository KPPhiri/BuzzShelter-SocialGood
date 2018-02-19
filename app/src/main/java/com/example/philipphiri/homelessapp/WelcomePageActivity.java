package com.example.philipphiri.homelessapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomePageActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth user;
    private EditText editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = FirebaseAuth.getInstance();
        Button loginButton = (Button) findViewById(R.id.loginButton);
        Button regButton = (Button) findViewById(R.id.regButton);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        Button cancel = (Button) findViewById(R.id.cancelButton);
        editTextEmail.setVisibility(View.VISIBLE);
        editTextPassword.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.VISIBLE);

        findViewById(R.id.loginButton).setOnClickListener(this);
        findViewById(R.id.regButton).setOnClickListener(this);
        findViewById(R.id.cancelButton).setOnClickListener(this);

    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();

        }  else if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        } else if (password.length() < 6) {
            editTextPassword.setError("Password must be at least 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        user.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.i("clicks","Success");
                    Intent i = new Intent(WelcomePageActivity.this, Main2Activity.class);
                    startActivity(i);
                } else {
                    //Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("GRATAFASKLJFLASJDLF");
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.loginButton) {
            loginUser();
        } else if (view.getId() == R.id.cancelButton){
            startActivity(new Intent(this, WelcomePageActivity.class));
        } else if (view.getId() == R.id.regButton){
            Intent b = new Intent(WelcomePageActivity.this, RegistrationActivity.class);
            //restarts welcome screen to refresh buttons
            startActivity(b);
        }
    }

    
}

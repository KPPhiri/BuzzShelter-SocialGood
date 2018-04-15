package com.example.philipphiri.homelessapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.RadioGroup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Registration activity
 */

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    RadioButton buttonUser;
    private ProgressBar progressBar;
    private EditText editTextEmail, editTextPassword, editTextName, editTextReligion;
    private RadioButton buttonAdmin;
    private RadioGroup radioGroup;
    private static FirebaseAuth user;
    private DatabaseReference userData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextReligion = (EditText) findViewById(R.id.editTextReligion);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        buttonAdmin = (RadioButton) findViewById(R.id.buttonAdmin);
        buttonUser = (RadioButton) findViewById(R.id.buttonUser);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);



        user = FirebaseAuth.getInstance();

        findViewById(R.id.buttonRegister).setOnClickListener(this);
        findViewById(R.id.textExist).setOnClickListener(this);

        user = FirebaseAuth.getInstance();
        userData = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    private void registerUser() {
        //final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        //final String name = editTextName.getText().toString().trim();
        //final String religion = editTextReligion.getText().toString().trim();

        if (editTextEmail.getText().toString().trim().isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(
                editTextEmail.getText().toString().trim()).matches()) {
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

        progressBar.setVisibility(View.VISIBLE);

        user.createUserWithEmailAndPassword(editTextEmail.getText().toString().trim(),
                password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String email = editTextEmail.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                String religion = editTextReligion.getText().toString().trim();
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    String user_id = user.getCurrentUser().getUid();
                    DatabaseReference current_user = userData.child(user_id);

                    if(radioGroup.getCheckedRadioButtonId() == buttonAdmin.getId()) {
                        current_user.child("UserType").setValue("Admin");
                    } else {
                        current_user.child("UserType").setValue("User");
                    }
                    current_user.child("ShelterRegistered").setValue("none");
                    current_user.child("NumberClaimed").setValue("0");
                    current_user.child("Email").setValue(email);
                    current_user.child("Name").setValue(name);
                    current_user.child("Religion").setValue(religion);

                    finish();
                    startActivity(new Intent(RegistrationActivity.this, WelcomePageActivity.class));
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(),
                                "Email is already used", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(),
                                task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.buttonRegister) {
            registerUser();
        } else if (view.getId() == R.id.textExist){
            startActivity(new Intent(this, WelcomePageActivity.class));
        }
    }
}
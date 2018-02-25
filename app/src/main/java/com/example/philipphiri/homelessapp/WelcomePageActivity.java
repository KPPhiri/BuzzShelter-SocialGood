package com.example.philipphiri.homelessapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class WelcomePageActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth user;
    private DatabaseReference userData;
    private EditText editTextEmail, editTextPassword;
    private Button okay;
    private Button cancel;
    private Button loginButton, regButton;


    Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDialog = new Dialog(this);
        user = FirebaseAuth.getInstance();
        userData = FirebaseDatabase.getInstance().getReference();


        loginButton = (Button) findViewById(R.id.loginButton);
        regButton = (Button) findViewById(R.id.regButton);



        findViewById(R.id.loginButton).setOnClickListener(this);
        findViewById(R.id.regButton).setOnClickListener(this);

    }

    private void loginUser() {
        Intent i = new Intent(WelcomePageActivity.this, ShelterListActivity.class);
        startActivity(i);
//        String email = editTextEmail.getText().toString().trim();
//        String password = editTextPassword.getText().toString().trim();
//
//        if (email.isEmpty()) {
//            editTextEmail.setError("Email is required");
//            editTextEmail.requestFocus();
//            return;
//        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            editTextEmail.setError("Please enter a valid email");
//            editTextEmail.requestFocus();
//
//        }  else if (password.isEmpty()) {
//            editTextPassword.setError("Password is required");
//            editTextPassword.requestFocus();
//            return;
//        } else if (password.length() < 6) {
//            editTextPassword.setError("Password must be at least 6 characters");
//            editTextPassword.requestFocus();
//            return;
//        }
//
//        user.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()) {
//                    Log.i("clicks","Success");
//                    String user_id = user.getCurrentUser().getUid();
//                    DatabaseReference current_user = userData.child("Users").child(user_id);
//                    current_user.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            String type = dataSnapshot.child("UserType").getValue(String.class);
//                            if(type.equals("Admin")) {
//
//                            } else {
//
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                    Intent i = new Intent(WelcomePageActivity.this, ShelterListActivity.class);
//                    startActivity(i);
//                } else {
//                    myDialog.dismiss();
//                }
//            }
//        });
    }

//    private static class UserViewHolder extends RecyclerView.ViewHolder{
//        View vView;
//        public UserViewHolder(View itemView) {
//            super(itemView);
//            vView = itemView;
//        }
//
//        public void setUserName(String name) {
//
//        }
//
//    }
    private void ShowPopUp(View v) {

        myDialog.setContentView(R.layout.loginpopup);
        cancel = (Button) myDialog.findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        okay = (Button) myDialog.findViewById(R.id.okayButton);
        editTextEmail = (EditText) myDialog.findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) myDialog.findViewById(R.id.editTextPassword);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.loginButton) {
            ShowPopUp(view);
        } else if (view.getId() == R.id.regButton){
            Intent b = new Intent(WelcomePageActivity.this, RegistrationActivity.class);
            //restarts welcome screen to refresh buttons
            startActivity(b);
        }

    }


}

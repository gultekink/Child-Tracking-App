package com.example.childtrackingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpandInChild extends AppCompatActivity {
    EditText usernameEdittext;
    EditText emailEditText;
    EditText passwordEditTExt;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_upand_in_child);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        usernameEdittext = findViewById(R.id.usernameId);
        emailEditText = findViewById(R.id.emailId);
        passwordEditTExt = findViewById(R.id.passwordText);


    }
        public void loginButton(View view){
        Intent intent = new Intent(SignUpandInChild.this,ChildScreen.class);
        startActivity(intent);
    }
}

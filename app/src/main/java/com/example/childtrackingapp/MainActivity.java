package com.example.childtrackingapp;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity {
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUser user;
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


    }

    public void kid(View view){

        Intent intent = new Intent(MainActivity.this, SignUpandInChild.class);
        startActivity(intent);
        CustomIntent.customType(MainActivity.this,"fadein-to-fadeout");


    }
    public void parents(View view){

        Intent intent = new Intent(MainActivity.this, SignUpandInParent.class);
        startActivity(intent);
        CustomIntent.customType(MainActivity.this,"fadein-to-fadeout");


    }

}

package com.example.childtrackingapp;

import com.google.firebase.firestore.FirebaseFirestore;

public class User {
    public String username,email;

    public User(){

    }
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }


}

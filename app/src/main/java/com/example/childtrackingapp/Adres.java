package com.example.childtrackingapp;

import com.google.firebase.auth.FirebaseUser;

import java.util.UUID;


public class Adres {
    String id;
    String adres;
    String date;


    public Adres(){
    }

    public Adres(String id, String adres,String date) {
        this.id = id;
        this.adres = adres;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getAdres() {
        return adres;
    }

    public String getDate() {
        return date;
    }
}

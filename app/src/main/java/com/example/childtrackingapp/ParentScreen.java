package com.example.childtrackingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class ParentScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_screen);

    }
    public void mapButton(View view){
        Intent intent = new Intent(ParentScreen.this,MapsActivity.class);
        startActivity(intent);

    }
    public void Button(View view){
        Intent intent = new Intent(ParentScreen.this,HistoryChan.class);
        startActivity(intent);
    }
    public void uyariii(View view){
        Intent intent = new Intent(ParentScreen.this,DangerousArea.class);
        startActivity(intent);
    }

}

package com.example.childtrackingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import static android.provider.ContactsContract.*;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    String Channel_id ="Notification";
    String text = "İçerik";
    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    List<Address> addressList;
    FirebaseFirestore firebaseFirestore;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser ;
    Double latitude ;
    Double longtitude ;
    Object adresimiz ;
    FieldValue date ;
    String email;
    Calendar calendar;
    Date currenttime;
    String currentDateandTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        calendar = Calendar.getInstance();
        currenttime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss",Locale.getDefault());
        currentDateandTime = sdf.format(new Date());
        email = firebaseUser.getEmail();
        System.out.println("üğğf"+email);
        date = FieldValue.serverTimestamp();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){

            NotificationChannel channel = new NotificationChannel(Channel_id,"ChannelName", NotificationManager.IMPORTANCE_LOW);
            channel.setDescription("notificaniotst");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }




    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

         locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
         locationListener = new LocationListener() {

             @Override
            public void onLocationChanged(Location location) {
                mMap.clear();
                   latitude = location.getLatitude();
                   longtitude = location.getLongitude();
                LatLng latLng = new LatLng(latitude,longtitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                mMap.addMarker(new MarkerOptions().position(latLng).title("Son Lokasyon"));



                 Geocoder geocoder = new Geocoder(getApplicationContext(),Locale.getDefault());

                try {
                     addressList = geocoder.getFromLocation(latitude,longtitude,1);
                    if(addressList !=null && addressList.size()>0){
                        adresimiz = addressList.get(0).getAddressLine(0);
                        String id = databaseReference.push().getKey();
                        Adres adres = new Adres(id,adresimiz.toString(),currentDateandTime);
                        databaseReference.child(firebaseUser.getUid()).child(id).setValue(adres);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
               /*  HashMap<String, Object> hash = new HashMap<String, Object>();

                    hash.put("adresimiz", adresimiz);
                    hash.put("latitude",latitude);
                    hash.put("longtitude",longtitude);
                    hash.put("date",date);*/

               /*  databaseReference.child("Users").child(uuid).child(uuid1.toString()).setValue(adres).addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         Toast.makeText(MapsActivity.this, "Okey", Toast.LENGTH_SHORT).show();
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(MapsActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                     }
                 });
*/

             }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else{

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,60000,1000,locationListener);

            mMap.clear();
            Location lastlocation =locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            LatLng lastLatLng = new LatLng(lastlocation.getLatitude(),lastlocation.getLongitude());
            if(lastLatLng != null){
                mMap.addMarker(new MarkerOptions().position(lastLatLng).title("You're here!"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng,15));
            }

        }
        try {
            Circle circle = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(37.768, 30.5619))
                    .radius(10000)
                    .strokeColor(Color.RED)
                    .fillColor(Color.BLUE));
        }catch (Exception e){
            System.out.println("üü"+e.getLocalizedMessage());
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==1 && grantResults.length<0){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,60000,1000,locationListener);

                Location lastlocation =locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                LatLng lastLatLng = new LatLng(lastlocation.getLatitude(),lastlocation.getLongitude());
                if(lastLatLng != null){
                    mMap.addMarker(new MarkerOptions().position(lastLatLng).title("You're here!"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng,15));
                }

            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void Notificaiton() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Channel_id)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle(text)
                .setContentText("İlk yazı")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1,builder.build());
    }



}

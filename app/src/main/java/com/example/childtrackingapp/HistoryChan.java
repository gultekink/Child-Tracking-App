package com.example.childtrackingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class HistoryChan extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    ArrayList<String> adresArray;
    ArrayList<String> dateArray;
    Recycler_holder recycler_holder;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference ;
    List<Adres> adresList;


    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_chan);
        textView = (TextView) findViewById(R.id.adresText);
        firebaseFirestore = FirebaseFirestore.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        adresList = new ArrayList<>();
        adresArray = new ArrayList<>();
        dateArray = new ArrayList<>();

        getDataFromFB();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recycler_holder= new Recycler_holder(adresArray,dateArray);
        recyclerView.setAdapter(recycler_holder);
    }

        public void getDataFromFB() {

       /* databaseReference.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            for(DataSnapshot ds :dataSnapshot.getChildren()){

                Adres adres = ds.getValue(Adres.class);
                adresArray.add(adres);

            }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/
        databaseReference.child(firebaseUser.getUid()).orderByPriority().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot ds : dataSnapshot.getChildren()){

                Adres adres = ds.getValue(Adres.class);
                adresArray.add(adres.adres);
                dateArray.add(adres.date);

                recycler_holder.notifyDataSetChanged();
            }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


            /*
            collectionReference = firebaseFirestore.collection("History");
            collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {
                        Map<String,Object> map = snapshot.getData();
                         adres = (String) map.get("adresimiz");
                        if (adres == null ){
                            adresArray.add(gecersiz);

                        }else {

                                // Date date = (Date) map.get("date");
                                adresArray.add(adres);
                            //dateArray.add(date);
                        }

                        recycler_holder.notifyDataSetChanged();


                    }
                }
            });
        */


    }

}


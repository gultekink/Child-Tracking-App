package com.example.childtrackingapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpandInParent extends AppCompatActivity implements View.OnClickListener {
    EditText usernameEdittext;
    EditText emailEditText;
    EditText passwordEditTExt;
    FirebaseAuth mAuth;
    Object newuuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_upand_in_parent);
        emailEditText = findViewById(R.id.emailId);
        passwordEditTExt = findViewById(R.id.passwordText);
        usernameEdittext = findViewById(R.id.usernameId);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.cardview).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);

        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if (firebaseUser != null) {
            Intent intent = new Intent(SignUpandInParent.this, ParentScreen.class);
            startActivity(intent);

        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    private void registerUser() {

        if (emailEditText.getText().toString().isEmpty()) {
            Toast.makeText(SignUpandInParent.this, "Email adresi boş bırakılamaz!", Toast.LENGTH_SHORT).show();
        } else if (passwordEditTExt.getText().toString().isEmpty()) {
            Toast.makeText(SignUpandInParent.this, "Password boş bırakılamaz!", Toast.LENGTH_SHORT).show();
        } else if(emailEditText.getText().toString().isEmpty() && passwordEditTExt.getText().toString().isEmpty()) {
            Toast.makeText(SignUpandInParent.this, "Email adresi ve Password boş bırakılamaz!", Toast.LENGTH_SHORT).show();
        } else{
            mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(),passwordEditTExt.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignUpandInParent.this, "Kayıt başarılı!", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUpandInParent.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }

   private void signMetodu() {

       mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(),passwordEditTExt.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   Toast.makeText(SignUpandInParent.this, "Giriş başarılı!", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(SignUpandInParent.this, ParentScreen.class);
                   startActivity(intent);
               }
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Toast.makeText(SignUpandInParent.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
           }
       });

        }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                registerUser();
                break;
            case R.id.cardview:
                signMetodu();
                break;
        }
    }



}

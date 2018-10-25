package com.example.project.chatvroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignedIn extends AppCompatActivity {


    private FirebaseAuth.AuthStateListener mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in);

        setupFirebaseAuth();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.signin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.chat:
                startActivity(new Intent(this , ChatActivity.class));
                return true;

            case R.id.account_settings:

                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            case R.id.sign_out:

                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, Login.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {

        FirebaseAuth.getInstance().addAuthStateListener(mAuth);
        super.onStart();
    }

    @Override
    protected void onDestroy() {

        if (mAuth != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuth);
        }
        super.onDestroy();
    }

    /*
    --------------------------------------------- FIREBASE SETUP ---------------------------------------------
    */

    private void setupFirebaseAuth() {

        mAuth = new FirebaseAuth.AuthStateListener() {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user != null) {
                    Toast.makeText(SignedIn.this, "User signed in:" + user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignedIn.this, "User signed out", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignedIn.this, Login.class));
                    finish();
                }

            }
        };
    }
}

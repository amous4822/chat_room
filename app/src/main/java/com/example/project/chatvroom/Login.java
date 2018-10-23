package com.example.project.chatvroom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;


import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {


    private EditText mEmail;
    private EditText mPassword;
    private Button mLogin;


    Context mContext ;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.email_login);
        mPassword = findViewById(R.id.password_login);
        mLogin = findViewById(R.id.login_button);

        setupFirebaseAuth();

        mLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isValid(mEmail.getText().toString()) && isValid(mPassword.getText().toString())) {
                    if (testEmail(mEmail.getText().toString()) && testPassword(mPassword.getText().toString())) {

                        Toast.makeText(Login.this, "success", Toast.LENGTH_SHORT).show();

                        final ProgressDialog dialog = ProgressDialog.show(Login.this, "Loading", "Please wait...", true);

                        FirebaseAuth.getInstance().signInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        dialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.dismiss();
                                Toast.makeText(Login.this, "Error while authentication try again", Toast.LENGTH_LONG).show();
                            }
                        });

                    } else {

                        Toast.makeText(Login.this, "Email or Password is wrong", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(Login.this, "You must fill all the fields properly !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView mResendVerification = (TextView) findViewById(R.id.resend_verify);
        mResendVerification.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                ResendVerificationDialog dialog = new ResendVerificationDialog();
                dialog.show(getSupportFragmentManager() , "resend_verification_dialog");
            }
        });

        TextView mRegister = findViewById(R.id.register_button);
        mRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Login.this , Register.class));
            }
        });

    }



    private boolean isValid(String text) {
        return !TextUtils.isEmpty(text) && !text.contains(" ");
    }

    private boolean testEmail(String email) {
        return email.contains(".") && email.contains("@");
    }

    private boolean testPassword(String pass) {
        return pass.length() >= 6;
    }

    /*
    ------------------------------------------------ FIREBASE FUNCTIONS -------------------------------------
     */

    private void setupFirebaseAuth() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    if (user.isEmailVerified()){
                        Toast.makeText(Login.this, "User logged in : " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    } else{
                        Toast.makeText(Login.this, "Please verify your account: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    }

                } else {
                    Toast.makeText(Login.this, "No signed in", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseApp.initializeApp(this);
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }
}

package com.example.project.chatvroom;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.chatvroom.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends AppCompatActivity {

    private TextView mSecurity;
    private TextView mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSecurity = findViewById(R.id.name_display);
        mName = findViewById(R.id.security_display);

        getAccountData();
    }

    private void getAccountData(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query1 = reference.child("users")
                .orderByKey()
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    User user = dataSnapshot1.getValue(User.class);
                    mName.setText(user.getName());
                    mSecurity.setText(user.getSecurity_level());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(SettingsActivity.this, "Success database", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

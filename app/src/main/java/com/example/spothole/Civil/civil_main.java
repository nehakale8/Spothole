package com.example.spothole.Civil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.spothole.ER.er_login;
import com.example.spothole.ER.er_main;
import com.example.spothole.R;
import com.google.firebase.auth.FirebaseAuth;

public class civil_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_civil_main);

        Button btn=findViewById(R.id.buttonLogoutCivil);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth firebaseAuth;
                firebaseAuth= FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                Intent logout=new Intent(civil_main.this, civil_login.class);
                startActivity(logout);
                finish();
                Toast.makeText(civil_main.this, "You have logged out successfully!", Toast.LENGTH_LONG).show();
            }
        });
    }
}

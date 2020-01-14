package com.example.spothole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.spothole.Citizen.citizen_login;
import com.example.spothole.Civil.civil_login;
import com.example.spothole.ER.er_login;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn_citizen = findViewById(R.id.buttonCitizen);
        Button btn_civil = findViewById(R.id.buttonCivil);
        Button btn_er = findViewById(R.id.buttonER);
        btn_citizen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(login.this, citizen_login.class);
                startActivity(intent);
            }
        });
        btn_civil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(login.this, civil_login.class);
                startActivity(intent);
            }
        });
        btn_er.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(login.this, er_login.class);
                startActivity(intent);
            }
        });
    }
}

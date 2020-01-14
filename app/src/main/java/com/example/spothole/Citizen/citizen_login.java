package com.example.spothole.Citizen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.spothole.R;

public class citizen_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_login);

        Button btn_report = findViewById(R.id.buttonReport);
        Button btn_view = findViewById(R.id.buttonView);

        btn_report.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(citizen_login.this, report_instruct.class);
                startActivity(intent);
            }
        });

        btn_view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(citizen_login.this, report_instruct.class);
                startActivity(intent);
            }
        });

    }
}

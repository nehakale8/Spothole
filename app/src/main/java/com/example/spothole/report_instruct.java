package com.example.spothole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class report_instruct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_instruct);

        Button btn_gotit = findViewById(R.id.buttonGotIt);

        btn_gotit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(report_instruct.this, report_instruct.class);
                startActivity(intent);
            }
        });
    }
}

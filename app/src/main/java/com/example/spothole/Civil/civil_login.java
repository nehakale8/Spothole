package com.example.spothole.Civil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.spothole.ER.er_login;
import com.example.spothole.ER.er_main;
import com.example.spothole.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class civil_login extends AppCompatActivity {
    EditText txtEmail,txtPassword;
    FirebaseAuth mfirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_civil_login);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            startActivity(new Intent(civil_login.this, civil_main.class));
            finish();
        } else {
            txtEmail = (EditText) findViewById(R.id.civil_email);
            txtPassword = (EditText) findViewById(R.id.civil_password);
            Button btn_login = findViewById(R.id.buttonLoginCivil);
            mfirebaseAuth = FirebaseAuth.getInstance();

            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String email = txtEmail.getText().toString().trim();
                    String password = txtPassword.getText().toString().trim();
                    if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                        Toast.makeText(civil_login.this, "Please enter all fields", Toast.LENGTH_LONG).show();
                        return;
                    } else if (TextUtils.isEmpty(email)) {
                        Toast.makeText(civil_login.this, "Please enter email", Toast.LENGTH_LONG).show();
                        return;
                    } else if (TextUtils.isEmpty(password)) {
                        Toast.makeText(civil_login.this, "Please enter password", Toast.LENGTH_LONG).show();
                        return;
                    } else if (password.length() < 6) {
                        Toast.makeText(civil_login.this, " Password is too small!", Toast.LENGTH_LONG).show();
                    } else if (!(email.isEmpty() && password.isEmpty())) {
                        mfirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(civil_login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(civil_login.this, "Login successful", Toast.LENGTH_LONG).show();
                                    finish();
                                    startActivity(new Intent(civil_login.this, civil_main.class));
                                    finish();

                                } else {
                                    Toast.makeText(civil_login.this, "Invalid Password or Email Id", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(civil_login.this, "Error Occurred", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}

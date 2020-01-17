package com.example.spothole.Citizen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spothole.R;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class ReportPothole extends AppCompatActivity {

    static int REQUEST_TAKE_PHOTO = 1;
    File photoFile;
    String photoPath;
    TextView info;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_report_pothole);
            Button takePic = findViewById(R.id.takepic);
            info=findViewById(R.id.textView);


            takePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Create intent to open camera app
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Proceed only if there is a camera app
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                        // Attempt to allocate a file to store the photo

                        try {
                            File storageDir = getFilesDir();
                            photoFile = File.createTempFile("SNAPSHOT", ".jpg", storageDir);
                            photoPath = photoFile.getAbsolutePath();
                        } catch (IOException ex) {
                            return;
                        }
                        // Send off to the camera app to get a photo
                        Uri photoURI = FileProvider.getUriForFile(ReportPothole.this, "com.example.spothole.fileprovider", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                    }
                }
            });
        }


}


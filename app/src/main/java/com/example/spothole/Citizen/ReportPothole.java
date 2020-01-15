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

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;

public class ReportPothole extends AppCompatActivity {

    static int REQUEST_TAKE_PHOTO = 1;
    File photoFile;
    String photoPath;
    TextView info;
    private class ClarifaiTask extends AsyncTask<File, Integer, Boolean> {
        protected Boolean doInBackground(File... images) {
            ClarifaiClient client = new ClarifaiBuilder("274cf60f4b3d4766a4c027fa6da97514").buildSync();
            List<ClarifaiOutput<Concept>> predictionResults;
            for (File image : images) {
                predictionResults = client.getDefaultModels().generalModel().predict()
                        .withInputs(ClarifaiInput.forImage(image))
                        .executeSync()
                        .get();

                for (ClarifaiOutput<Concept> result : predictionResults) {
                    for (Concept datum : result.data()) {

                        Toast toast=Toast.makeText(getApplicationContext(),"hello"+datum.name(),Toast.LENGTH_LONG);
                        toast.show();
                        if (datum.name().contains("door"))
                            return true;
                    }
                }
            }

            return false;
        }

        protected void onPostExecute(Boolean result) {

            // Delete photo
            (new File(photoPath)).delete();
            photoPath = null;
/*
            // If image contained object, close the AlarmActivity
            if (result) {
                info.setText("Success!");
                finish();
            } else info.setText("Try again...");
            */

        }

    }

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

        @Override
         public void onResume() {
            super.onResume();
            if (photoPath != null) {
                new ClarifaiTask().execute(new File(photoPath));
            }
        }

}


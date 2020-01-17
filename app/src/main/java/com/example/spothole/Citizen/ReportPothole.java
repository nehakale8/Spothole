package com.example.spothole.Citizen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spothole.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Handler;


public class ReportPothole extends AppCompatActivity {

    static int REQUEST_TAKE_PHOTO = 1;
    File photoFile;
    String photoPath;
    TextView info;
    TextView latitude, longitude;
    Button btn_upload;
    Uri photoURI;

    FirebaseStorage storage;
    StorageReference storageReference;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_report_pothole);
            Button takePic = findViewById(R.id.takepic);
            info=findViewById(R.id.textView);
            btn_upload=(Button)findViewById(R.id.btn_upload);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://spothole-1c211.appspot.com/");


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
                        photoURI = FileProvider.getUriForFile(ReportPothole.this, "com.example.spothole.fileprovider", photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

//                        if (photoPath!=null) {
//                            try {
//                                ExifInterface exif = new ExifInterface(photoPath);
//                                String lat = ExifInterface.TAG_GPS_LATITUDE;
//                                String lat_data = exif.getAttribute(lat);
//
//                                String longi = ExifInterface.TAG_GPS_LONGITUDE;
//                                String longi_data = exif.getAttribute(longi);
//                                latitude = findViewById(R.id.latitude);
//                                longitude = findViewById(R.id.longitude);
//
//                                latitude.setText("yo");
//                                longitude.setText(longi);
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        else
//                        {
//                            latitude=findViewById(R.id.latitude);
//                            latitude.setText(photoPath);
//                        }



                    }
                }
            });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        }

    private void uploadImage()
    {
        if (photoURI != null) {

            // Code for showing progressDialog while uploading
            final ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(photoURI)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(ReportPothole.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(ReportPothole.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }

}


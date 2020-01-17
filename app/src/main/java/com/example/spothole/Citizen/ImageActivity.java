package com.example.spothole.Citizen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.spothole.R;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;





public class ImageActivity extends AppCompatActivity {

    private Button nUploadBtn;
    private ImageView nImageView;
    private static final int CAMERA_REQUEST_CODE=1;
    private StorageReference nStorage;
    private ProgressDialog nProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        nStorage= FirebaseStorage.getInstance().getReference();
        nUploadBtn=(Button)findViewById(R.id.button);
        nImageView=(ImageView)findViewById(R.id.imageView);
        nProgress= new ProgressDialog(this);
        nUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent , CAMERA_REQUEST_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAMERA_REQUEST_CODE && resultCode==RESULT_OK)
        {
            nProgress.setMessage("Uploading Image..");
            nProgress.show();
            Uri uri=data.getData();
            StorageReference filepath=nStorage.child("PHOTOS").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    nProgress.dismiss();

                    Toast.makeText(ImageActivity.this,"Uploading Finished...",Toast.LENGTH_LONG).show();
                }
            });

            }


        }

    }


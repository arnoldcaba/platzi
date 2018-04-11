package com.arnoldcaba.platzigram.post.views;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.arnoldcaba.platzigram.PlatzigramAplication;
import com.arnoldcaba.platzigram.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


public class NewPostActivity extends AppCompatActivity {

    private static final String TAG = "NewPostActivity" ;
    private ImageView imgPhoto;
    private Button btnCreatePost;
    private String photoPath;
    private PlatzigramAplication app;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        btnCreatePost = findViewById(R.id.btnCreatePost);

        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upLoadPhoto();
            }
        });
        app = (PlatzigramAplication) getApplicationContext();
        storageReference =  app.getStorageReference();

        imgPhoto = findViewById(R.id.imgPhoto);
        if(getIntent().getExtras() != null){
            photoPath = getIntent().getExtras().getString("PHOTO_PATH_TEMP");
            showPhoto();
        }

    }

    private void upLoadPhoto() {
        imgPhoto.setDrawingCacheEnabled(true);
        imgPhoto.buildDrawingCache();

        Bitmap bitmap = imgPhoto.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);

        byte[] photoByte = baos.toByteArray();

        String photoName = photoPath.substring(photoPath.lastIndexOf("/")+1, photoPath.length());

        StorageReference photoReference = storageReference.child("postImages/"+photoName);
        UploadTask uploadTask = photoReference.putBytes(photoByte);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG,"Error al subir foto"+e.toString());
                e.printStackTrace();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri uriphoto = taskSnapshot.getDownloadUrl();
                String photoURL  = uriphoto.toString();
                Log.w(TAG,"Url photo >"+photoURL);
                finish();
            }
        });
    }

    private void showPhoto(){
        Picasso.get().load(photoPath).resize(2310,4096).into(imgPhoto);
    }
}

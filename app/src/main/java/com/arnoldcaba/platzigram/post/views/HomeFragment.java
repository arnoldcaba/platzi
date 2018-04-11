package com.arnoldcaba.platzigram.post.views;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.arnoldcaba.platzigram.R;
import com.arnoldcaba.platzigram.adapter.RecyclerviewPictureAdapter;
import com.arnoldcaba.platzigram.modelo.Picture;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private static final int REQUEST_CAMERA =1 ;
    private FloatingActionButton fabCamera;
    private String photoPathTemp="";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        showToolbar("Home",false,view);

        fabCamera = view.findViewById(R.id.fabCamera);
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        //Recicler
        RecyclerView picturesRecycler = view.findViewById(R.id.pictureRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        picturesRecycler.setLayoutManager(linearLayoutManager);

        RecyclerviewPictureAdapter recyclerviewPictureAdapter = new RecyclerviewPictureAdapter(buildPictures(),R.layout.cardview_picture,getActivity());

        picturesRecycler.setAdapter(recyclerviewPictureAdapter);
        return view;
    }


    public ArrayList<Picture> buildPictures(){
        ArrayList<Picture> pictures = new ArrayList<Picture>();
        pictures.add(new Picture("https://pixabay.com/get/ea37b90620f5033ed1584d05fb1d4e90e371e1d118ac104497f2c871a7edbcb8_640.jpg","Arnold Caballero", "10 dias","3 me gusta"));
        pictures.add(new Picture("https://pixabay.com/get/ea37b90620f5013ed1584d05fb1d4e90e371e1d118ac104497f2c871a7edbcb8_640.jpg","Cristina Puentes", "2 dias","0 me gusta"));
        pictures.add(new Picture("https://pixabay.com/get/ea37b9062efd013ed1584d05fb1d4e90e371e1d118ac104497f2c871a7edbcb8_640.jpg","Isabel Caballero", "10 dias","20 me gusta"));
        pictures.add(new Picture("https://pixabay.com/get/ea37b80e2afd033ed1584d05fb1d4e90e371e1d118ac104497f3c17da4e8b7be_640.jpg","Daniela Caballero", "11 dias","23 me gusta"));
        pictures.add(new Picture("https://pixabay.com/get/ea37b80d21f5033ed1584d05fb1d4e90e371e1d118ac104497f3c17da4e8b7be_640.jpg","Milo Alejandro", "17 dias","3 me gusta"));

        return pictures;
    }


    //metodo para crear la toolbar
    public void showToolbar(String titulo, boolean upButton, View view){
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(titulo);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

    private void takePicture() {

        Intent intentTakePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intentTakePicture.resolveActivity(getActivity().getPackageManager())!=null){//valida si el dispositivo tiene camara

            File photoFile = null;

            try {
                photoFile = createImageFile();
            }catch (Exception e){
                e.printStackTrace();
            }

            if(photoFile!=null){
                Uri photoUri = FileProvider.getUriForFile(getActivity(),"com.arnoldcaba.platzigram",photoFile);
                intentTakePicture.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(intentTakePicture,REQUEST_CAMERA);//arranca la activity de camara pero trae un identificador en este caso 1

            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(new Date()); //toma la hora de sistema para poner como nombre
        String imageFileName = "JPEG_"+timeStamp+"_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photo = File.createTempFile(imageFileName,".jpg",storageDir);
        /*
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(photo.getAbsolutePath(),bmOptions);
        bitmap = Bitmap.createScaledBitmap(bitmap,300,400,true);
        File photoFinal = File.createTempFile(imageFileName,"jpg",getActivity().getExternalFilesDir(bitmap));
        */
        photoPathTemp = "file:" + photo.getAbsolutePath();

        return photo;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //cuando la funcion camara termino
        if (requestCode == REQUEST_CAMERA && resultCode == getActivity().RESULT_OK){
            Log.d("home fragment","Camera ok ;-)");
            Intent i = new Intent(getContext(), NewPostActivity.class);
            i.putExtra("PHOTO_PATH_TEMP",photoPathTemp);
            startActivity(i);
        }
    }
}

package com.arnoldcaba.platzigram.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arnoldcaba.platzigram.R;
import com.arnoldcaba.platzigram.adapter.RecyclerviewPictureAdapter;
import com.arnoldcaba.platzigram.modelo.Picture;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        showToolbar("",false,view);

        RecyclerView picturesRecycler = view.findViewById(R.id.pictureProfileRecycler);
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

}

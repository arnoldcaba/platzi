package com.arnoldcaba.platzigram.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.arnoldcaba.platzigram.R;
import com.arnoldcaba.platzigram.adapter.RecyclerviewPictureAdapter;
import com.arnoldcaba.platzigram.modelo.Picture;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        RecyclerView picturesRecycler = view.findViewById(R.id.searchRecycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        //gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL); no necesario para este layout
        picturesRecycler.setLayoutManager(gridLayoutManager);


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

}

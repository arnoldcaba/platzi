package com.arnoldcaba.platzigram.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arnoldcaba.platzigram.R;
import com.arnoldcaba.platzigram.modelo.Picture;
import com.arnoldcaba.platzigram.post.views.PictureDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by arnoldgustavocaballeromantilla on 5/04/18.
 */

public class RecyclerviewPictureAdapter extends RecyclerView.Adapter<RecyclerviewPictureAdapter.PictureViewHolder>{

    private ArrayList<Picture> pictures;
    private int resourse;
    private Activity activity;

    public RecyclerviewPictureAdapter(ArrayList<Picture> pictures, int resourse, Activity activity) {
        this.pictures = pictures;
        this.resourse = resourse;
        this.activity = activity;
    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resourse,parent,false);
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PictureViewHolder holder, int position) {
        Picture picture = pictures.get(position);
        holder.userNameCard.setText(picture.getUserName());
        holder.timeCard.setText(picture.getTime());
        holder.likeNumberCard.setText(picture.getLikeNumber());
        Picasso.get().load(picture.getPicture()).into(holder.pictureCard);

        holder.pictureCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PictureDetailActivity.class);
                //transition efecto grafico

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    Explode explode = new Explode();
                    explode.setDuration(1000);
                    activity.getWindow().setExitTransition(explode);
                    activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity,v,activity.getString(R.string.transitionname_picture)).toBundle());

                }else{
                    activity.startActivity(intent);
                }






            }
        });



    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    //clase interna
    public class PictureViewHolder extends RecyclerView.ViewHolder{

        private ImageView pictureCard;
        private TextView userNameCard;
        private TextView timeCard;
        private TextView likeNumberCard;

        public PictureViewHolder(View itemView) {
            super(itemView);

            pictureCard = itemView.findViewById(R.id.pictureCardview);
            userNameCard = itemView.findViewById(R.id.userNameCard);
            timeCard = itemView.findViewById(R.id.timeCard);
            likeNumberCard = itemView.findViewById(R.id.likeNumberCard);
        }
    }
}

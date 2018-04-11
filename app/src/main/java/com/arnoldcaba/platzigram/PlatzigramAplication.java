package com.arnoldcaba.platzigram;

import android.app.Application;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by arnoldgustavocaballeromantilla on 8/04/18.
 */

public class PlatzigramAplication extends Application {

    private FirebaseStorage firebaseStorage;

    @Override
    public void onCreate() {
        super.onCreate();

        firebaseStorage = FirebaseStorage.getInstance();
    }

    public StorageReference getStorageReference(){
        return firebaseStorage.getReference();
    }
}

package com.arnoldcaba.platzigram.login.interactor;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by arnoldgustavocaballeromantilla on 7/04/18.
 */

public interface LoginInteractor {

    void signIn(String username, String password, Activity activity,FirebaseAuth firebaseAuth);
}

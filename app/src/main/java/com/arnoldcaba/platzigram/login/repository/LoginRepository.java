package com.arnoldcaba.platzigram.login.repository;

import android.app.Activity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by arnoldgustavocaballeromantilla on 7/04/18.
 */

public interface LoginRepository {
    void singIn(String username, String password, Activity activity,FirebaseAuth firebaseAuth);
}

package com.arnoldcaba.platzigram.login.presenter;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by arnoldgustavocaballeromantilla on 7/04/18.
 */

public interface LoginPresenter {
    void signIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth); // va para el interactor
    void loginSuccess();
    void loginUnsuccess(String error);


}

package com.arnoldcaba.platzigram.login.views;

import android.view.View;

/**
 * Created by arnoldgustavocaballeromantilla on 7/04/18.
 */

public interface LoginView {
    void goCreateAccount(View v);
    void goContainer();
    void enableInputs();
    void disableInputs();
    void showProgressBar();
    void hideProgressBar();
    void loginerror(String error);
}

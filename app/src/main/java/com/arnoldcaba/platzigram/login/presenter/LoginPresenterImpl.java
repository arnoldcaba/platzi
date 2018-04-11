package com.arnoldcaba.platzigram.login.presenter;

import android.app.Activity;

import com.arnoldcaba.platzigram.login.interactor.LoginInteractor;
import com.arnoldcaba.platzigram.login.interactor.LoginInteractorImpl;
import com.arnoldcaba.platzigram.login.views.LoginView;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by arnoldgustavocaballeromantilla on 7/04/18.
 */

public class LoginPresenterImpl implements LoginPresenter {


    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        loginInteractor = new LoginInteractorImpl(this);
    }

    @Override
    public void signIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth) {
        loginView.disableInputs();
        loginView.showProgressBar();
        loginInteractor.signIn(username,password,activity,firebaseAuth);
    }

    @Override
    public void loginSuccess() {
        loginView.goContainer();
        loginView.hideProgressBar();

    }

    @Override
    public void loginUnsuccess(String error) {
        loginView.enableInputs();
        loginView.hideProgressBar();
        loginView.loginerror(error);
    }
}

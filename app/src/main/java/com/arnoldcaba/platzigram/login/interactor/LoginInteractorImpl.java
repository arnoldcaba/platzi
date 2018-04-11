package com.arnoldcaba.platzigram.login.interactor;

import android.app.Activity;

import com.arnoldcaba.platzigram.login.presenter.LoginPresenter;
import com.arnoldcaba.platzigram.login.presenter.LoginPresenterImpl;
import com.arnoldcaba.platzigram.login.repository.LoginRepository;
import com.arnoldcaba.platzigram.login.repository.LoginRepositoryImpl;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by arnoldgustavocaballeromantilla on 7/04/18.
 */

public class LoginInteractorImpl implements LoginInteractor {

    private LoginPresenter loginPresenter;
    private LoginRepository repository;

    public LoginInteractorImpl(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
        repository = new LoginRepositoryImpl(loginPresenter);
    }

    @Override
    public void signIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth) {
        repository.singIn(username,password,activity,firebaseAuth);
    }
}

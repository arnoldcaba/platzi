package com.arnoldcaba.platzigram.login.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arnoldcaba.platzigram.R;
import com.arnoldcaba.platzigram.login.presenter.LoginPresenter;
import com.arnoldcaba.platzigram.login.presenter.LoginPresenterImpl;
import com.arnoldcaba.platzigram.view.ContainerActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.EmailAuthCredential;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements LoginView{

    private TextInputEditText userName, password;
    private Button login;
    private LoginButton loginButtonFacebook;
    private ProgressBar progressBarLogin;
    private LoginPresenter presenter;

    private static final String TAG = "LoginRepositoryImpl" ;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //facebook signin
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();


        //seting firebase variables
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser!=null){
                    Log.w(TAG,"Usuario logeado" + firebaseUser.getEmail());
                    goContainer();
                }else {
                    Log.w(TAG,"Usuario no logeado");

                }
            }
        };

        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        loginButtonFacebook = findViewById(R.id.login_facebook);
        progressBarLogin = findViewById(R.id.progressBarLogin);
        hideProgressBar();
        presenter = new LoginPresenterImpl(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singIn(userName.getText().toString(),password.getText().toString());


            }
        });

        ImageView logo = findViewById(R.id.logo);//boton para abrir con el navegador url
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri dir = Uri.parse("http://www.platzi.com");
                Intent callIntent = new Intent(Intent.ACTION_VIEW,dir);
                startActivity(callIntent);
            }
        });

        loginButtonFacebook.setReadPermissions(Arrays.asList("email"));
        loginButtonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.w(TAG,"facebok login success token"+loginResult.getAccessToken().getApplicationId());
                singInFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.w(TAG,"facebok login cancelado");
            }

            @Override
            public void onError(FacebookException error) {
                Log.w(TAG,"Ocurrió un error"+error.toString());
                error.printStackTrace();
            }
        });
    }

    private void singInFacebook(AccessToken accessToken) {
        AuthCredential authCredentialFacebook = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(authCredentialFacebook).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    //almacenando email
                    FirebaseUser user = task.getResult().getUser();
                    SharedPreferences preferences = getSharedPreferences("USER", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("email",user.getEmail());
                    editor.commit();

                    goContainer();
                    Toast.makeText(LoginActivity.this, "Login facebook exitoso", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Login facebook no exitoso", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void singIn(String username, String password) {
        presenter.signIn(username,password,this, firebaseAuth);
    }


    @Override
    public void goCreateAccount(View v) {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void goContainer() {
        Intent intent = new Intent(this, ContainerActivity.class);
        startActivity(intent);
    }

    @Override
    public void enableInputs() {
        userName.setEnabled(true);
        password.setEnabled(true);
        login.setEnabled(true);
    }

    @Override
    public void disableInputs() {
        userName.setEnabled(false);
        password.setEnabled(false);
        login.setEnabled(false);
    }

    @Override
    public void showProgressBar() {
        progressBarLogin.setVisibility(View.VISIBLE);
        Log.i("funciona el show:","si");

    }

    @Override
    public void hideProgressBar() {
        progressBarLogin.setVisibility(View.GONE);
    }

    @Override
    public void loginerror(String error) {
        Toast.makeText(this, getResources().getString(R.string.login_error)+error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }
}

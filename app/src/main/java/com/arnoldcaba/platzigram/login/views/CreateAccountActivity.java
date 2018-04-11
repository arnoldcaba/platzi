package com.arnoldcaba.platzigram.login.views;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arnoldcaba.platzigram.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccountActivity extends AppCompatActivity {

    private static final String TAG = "CrateAccountActivity" ;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    public Button btnJoinUs;
    public EditText edtEmail,edtPassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        //muestra el boton en la toolbar  esto se complementa en el manifes con las intrucciocnes de gerarquia
        showToolbar(getResources().getString(R.string.toolbar_titel_createaccount),true);

        btnJoinUs = findViewById(R.id.joinUs);
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password_createAccount);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser!=null){
                    Log.w(TAG,"Usuario logeado" + firebaseUser.getEmail());
                }else {
                    Log.w(TAG,"Usuario no logeado");

                }
            }
        };

        btnJoinUs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(CreateAccountActivity.this, "Cuenta creada exitosamente", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(CreateAccountActivity.this, "Ocurrió error al crear la cuenta", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });

    }

    //metodo para crear la toolbar
    public void showToolbar(String titulo, boolean upButton){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

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
}

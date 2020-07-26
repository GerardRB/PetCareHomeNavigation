package com.example.myapplication.ui.inicio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.HomeActivity_Cuidador;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class cuidadorLoginActivity extends AppCompatActivity {
    private EditText mcorreo, mcontraseña;
    private Button mlogin, mregistrarse;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuidador_login);

        mlogin = findViewById(R.id.login);
        mregistrarse = findViewById(R.id.registrarse);
        mAuth = FirebaseAuth.getInstance();

        //Inicio de sesion del cuidador
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cuidadorLoginActivity.this, HomeActivity_Cuidador.class);
                startActivity(intent);
                finish();
                return;
            }
        })      ;

        //Registro del cuidador.
        mregistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(cuidadorLoginActivity.this, cuidadorRegistro.class);
                startActivity(intent2);
                finish();
                return;

            }
        });

    }

}

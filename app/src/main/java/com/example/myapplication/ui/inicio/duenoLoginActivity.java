package com.example.myapplication.ui.inicio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.HomeActivity_Dueno;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class duenoLoginActivity extends AppCompatActivity {

    private EditText mcorreo, mcontraseña;
    private Button mlogin, mregistrarse;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dueno_login);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
                if(usuario!=null){
                    Intent intent = new Intent(duenoLoginActivity.this, HomeActivity_Dueno.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        mcorreo= (EditText) findViewById(R.id.correo);
        mcontraseña = (EditText) findViewById(R.id.contraseña);

        mlogin= (Button) findViewById(R.id.login);
        mregistrarse = (Button) findViewById(R.id.registrarse);

        //Registro del dueño.
        mregistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String correo = mcorreo.getText().toString();
                final String contraseña = mcontraseña.getText().toString();
                mAuth.createUserWithEmailAndPassword(correo, contraseña).addOnCompleteListener(duenoLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(duenoLoginActivity.this, "Error al registrarse", Toast.LENGTH_SHORT).show();
                        } else{
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference usuario_actual = FirebaseDatabase.getInstance().getReference().child("usuario").child("dueño").child(user_id);
                            usuario_actual.setValue(true);
                        }
                    }
                });
            }
        });

        //Inicio de sesion del dueño
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String correo = mcorreo.getText().toString();
                final String contraseña = mcontraseña.getText().toString();
                mAuth.signInWithEmailAndPassword(correo, contraseña).addOnCompleteListener(duenoLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(duenoLoginActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}

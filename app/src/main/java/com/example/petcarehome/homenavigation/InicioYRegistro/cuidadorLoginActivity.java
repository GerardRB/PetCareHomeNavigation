package com.example.petcarehome.homenavigation.InicioYRegistro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.HomeActivity_Cuidador;
import com.example.petcarehome.homenavigation.HomeActivity_Dueno;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class cuidadorLoginActivity extends AppCompatActivity {
    private EditText mcorreo, mcontraseña;
    private Button mlogin, mregistrarse;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuidador_login);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
                if(usuario!=null){
                    Intent intent = new Intent(cuidadorLoginActivity.this, HomeActivity_Cuidador.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        mcorreo=  findViewById(R.id.correo);
        mcontraseña =  findViewById(R.id.contraseña);

        mlogin=  findViewById(R.id.login);
        mregistrarse =  findViewById(R.id.registrarse);

        //Registro del cuidador botón.
        mregistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cuidadorLoginActivity.this, registroCuidadorActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        //Inicio de sesion del cuidador botón
        mlogin.setOnClickListener(new View.OnClickListener() {

            final String correo = mcorreo.getText().toString();
            final String contra = mcontraseña.getText().toString();
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(correo, contra).addOnCompleteListener(cuidadorLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(cuidadorLoginActivity.this, "Error al inciar sesión", Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intent = new Intent(cuidadorLoginActivity.this, HomeActivity_Cuidador.class);
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

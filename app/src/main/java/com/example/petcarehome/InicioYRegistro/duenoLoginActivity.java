package com.example.petcarehome.InicioYRegistro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.HomeActivity_Dueno;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        mcorreo=  findViewById(R.id.correo);
        mcontraseña =  findViewById(R.id.contraseña);

        mlogin=  findViewById(R.id.login);
        mregistrarse =  findViewById(R.id.registrarse);

        //Registro del dueño botón.
        mregistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(duenoLoginActivity.this, registroDuenoActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        //Inicio de sesion del dueño botón
        mlogin.setOnClickListener(new View.OnClickListener() {

            final String correo = mcorreo.getText().toString();
            final String contra = mcontraseña.getText().toString();
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(correo, contra).addOnCompleteListener(duenoLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(duenoLoginActivity.this, "Error al inciar sesión", Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intent = new Intent(duenoLoginActivity.this, HomeActivity_Dueno.class);
                        }
                    }
                });
            }
        });
    }
}

package com.example.petcarehome.InicioYRegistro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.HomeActivity_Cuidador;
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
        mcorreo=  findViewById(R.id.correo);
        mcontraseña =  findViewById(R.id.contraseña);

        mlogin=  findViewById(R.id.login);
        mregistrarse =  findViewById(R.id.registrarse);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()!=null){
        alertHayUsuario();
        }

        //Registro del cuidador botón.
        mregistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mAuth.getCurrentUser() != null) {
                    alertHayUsuario();
                } else {
                    Intent intent = new Intent(cuidadorLoginActivity.this, registroCuidadorActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //Inicio de sesion del cuidador botón
        mlogin.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                String correo = mcorreo.getText().toString();
                String contra = mcontraseña.getText().toString();
                if (correo.isEmpty() || contra.isEmpty()) {
                    if (correo.isEmpty()) {
                        mcorreo.setError("Campo obligatorio");
                    }
                    if (contra.isEmpty()) {
                        mcontraseña.setError("Campo obligatorio");
                    }
                } else {
                    if (mAuth.getCurrentUser() != null) {
                        alertHayUsuario();
                    } else {
                        mAuth.signInWithEmailAndPassword(correo, contra).addOnCompleteListener(cuidadorLoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(cuidadorLoginActivity.this, "Error al inciar sesión", Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent intent = new Intent(cuidadorLoginActivity.this, HomeActivity_Cuidador.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    public void alertHayUsuario() {
        AlertDialog.Builder alertDialog2 = new
                AlertDialog.Builder(
                this);

        // Título del dialogo
        alertDialog2.setTitle("Sesión ");

        // Mensaje del dialogo
        alertDialog2.setMessage("Actualmente tienes una sesión abierta como dueño.\n¿Deseas cerrarla?");

        // Btn Positivo "Yes"
        alertDialog2.setPositiveButton("Sí",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Lo que va a pasar después de dar sí
                        mAuth.signOut();
                        dialog.dismiss();
                    }
                });

        // Btn Negativo "NO"
        alertDialog2.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Lo que va a pasar después de dar no
                        AlertDialog.Builder alertDialog3 = new
                                AlertDialog.Builder(
                                cuidadorLoginActivity.this);
                        alertDialog3.setMessage("No podrás iniciar sesión o registrar una nueva cuenta como cuidador hasta que finalices la sesión actual.\nIntenta ingresar como Dueño");
                        alertDialog3.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog3.show();
                        dialog.cancel();
                    }
                });

        // Mostrando el alertDialog
        alertDialog2.show();
    }
}

package com.example.petcarehome.homenavigation.ui.config.clases_fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.petcarehome.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class cambiar_contrasena extends Fragment {
    EditText contra1, contra2;
    Button actualizarContra;
    FirebaseUser user;
    FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cambiar_contrasena_fragment, container, false);
        //Clase para el fragmento de cambiar contraseña
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contra1 = view.findViewById(R.id.contrasena_modif);
        contra2 = view.findViewById(R.id.contrasena_modif2);
        actualizarContra = view.findViewById(R.id.actualizarContra);




        actualizarContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Las contraseñas no coinciden";
                String contrasena = contra1.getText().toString();
                String contraconfirm = contra2.getText().toString();
                //Validacion para las contraseñas
                if (!contrasena.equals(contraconfirm) || contrasena.isEmpty()|| contraconfirm.isEmpty()) {
                    if (contrasena.isEmpty()) {
                        contra1.setError("Campo obligatorio");
                    }
                    if (contraconfirm.isEmpty()) {
                        contra2.setError("Campo obligatorio");
                    }
                    if (!contrasena.equals(contraconfirm)){
                        message += "\nIngrese de nuevo las contraseñas";
                    }
                }else{
                    //Si es correcto actualizar la contraseña del usuario
                    mAuth = FirebaseAuth.getInstance();
                    user = mAuth.getCurrentUser();
                    if (user!=null) {
                        user.updatePassword(contrasena).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Has cambiado tu contraseña :)", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Hubo un error al cambiar tu contraseña :(", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "Error, intenta más tarde", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}

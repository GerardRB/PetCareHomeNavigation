package com.example.petcarehome.homenavigation.ui.config.clases_fragmentos;

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
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class cambiar_contrasenad extends Fragment {
    EditText contra1, contra2;
    Button actualizarContra;
    String correo, contravieja;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cambiar_contrasenad_fragment, container, false);
        //Clase para el fragmento de cambiar contraseña
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        contra1 = view.findViewById(R.id.contrasena_modifd);
        contra2 = view.findViewById(R.id.contrasena_modif2d);
        actualizarContra = view.findViewById(R.id.actualizarContrad);




        actualizarContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    ActualizarContrasena();

            }

        });
    }

    private void ActualizarContrasena() {

        final String contraseña = contra1.getText().toString();
        final String  contraseñaconfirm = contra2.getText().toString();

        //Referencias a la BD.
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        final DatabaseReference duenoRef = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE)
                .child(FirebaseReferences.DUENO_REFERENCE).child(user.getUid());
        duenoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                correo = dataSnapshot.child("correo").getValue().toString();
                contravieja = dataSnapshot.child("contraseña").getValue().toString();
                AuthCredential credential = EmailAuthProvider.getCredential(correo, contravieja);

                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            String message = "Las contraseñas no coinciden";
                            //Validacion para las contraseñas
                            if (!contraseña.equals(contraseñaconfirm) || contraseña.isEmpty()|| contraseñaconfirm.isEmpty()) {
                                if (contraseña.isEmpty()) {
                                    contra1.setError("Campo obligatorio");
                                }
                                if (contraseñaconfirm.isEmpty()) {
                                    contra2.setError("Campo obligatorio");
                                }
                                if (!contraseña.equals(contraseñaconfirm)){
                                    message += "\nIngrese de nuevo las contraseñas";
                                }
                            }
                            user.updatePassword(contraseña).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()){
                                        Toast.makeText(getActivity(), "Error al cambiar tu contraseña", Toast.LENGTH_SHORT).show();
                                    }else{
                                        duenoRef.child("contraseña").setValue(contraseña);
                                        Toast.makeText(getActivity(), "Has cambiado tu contraseña", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(getActivity(), "Error de autenticación", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

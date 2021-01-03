package com.example.petcarehome.homenavigation.ui.config.clases_fragmentos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class perfil_usuario_cuidador extends Fragment {
    private FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    FirebaseStorage firebaseStorage;
    TextView tipo_userc, nombre_userc, correo_userc, tel_userc, domicilio_userc;
    ImageView fotoc;


    Button Actualizarc;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.perfil_fragment_cuidador, container,false);
        //Clase para el fragmento del perfil - fragmento principal del apartado de configuración
    } @Override

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tipo_userc = view.findViewById(R.id.tipo_usuarioc_bd);
        nombre_userc = view.findViewById(R.id.nombre_usuarioc_bd);

        correo_userc = view.findViewById(R.id.correo_usuarioc_bd);
        tel_userc = view.findViewById(R.id.tel_usuarioc_bd);
        domicilio_userc = view.findViewById(R.id.domicilio_usuarioc_bd);
        fotoc = view.findViewById(R.id.profile_imagec);
        Actualizarc = view.findViewById(R.id.editar_perfil_configc);

        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        final DatabaseReference cuidadorRef = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE)
                .child(FirebaseReferences.CUIDADOR_REFERENCE).child(user.getUid());

        String idUser;
        idUser = user.getUid();
        final StorageReference storagePerfilCuidadorReference =
                firebaseStorage.getInstance().getReference
                        (FirebaseReferences.STORAGE_IMAGENPERFIL_REFERENCE).
                        child(FirebaseReferences.STORAGE_IMAGENPERFILCUIDADOR_REFERENCE).child(idUser).child("img" + idUser + ".jpg");


        cuidadorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              //  for (DataSnapshot snapshot:
                //        dataSnapshot.getChildren()) {
                    String tipo = dataSnapshot.child("tipo").getValue().toString();
                    String nombre = dataSnapshot.child("nombre").getValue().toString() + " " + dataSnapshot.child("apellidos").getValue().toString();
                    String correo = dataSnapshot.child("correo").getValue().toString();
                    String tel = dataSnapshot.child("telefono").getValue().toString();
                    String domicilio = dataSnapshot.child("calle").getValue(String.class) + ""
                            + dataSnapshot.child("noext").getValue(String.class) + " "
                            + dataSnapshot.child("noint").getValue(String.class) + ", "
                            + dataSnapshot.child("alcaldia").getValue(String.class);


                    tipo_userc.setText(tipo);
                    nombre_userc.setText(nombre);

                    correo_userc.setText(correo);
                    tel_userc.setText(tel);
                    domicilio_userc.setText(domicilio);


              //  }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        Actualizarc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              startActivity(new Intent(getActivity(), actualizar_datosc.class));
                Glide.with(getContext()).load(storagePerfilCuidadorReference).into(fotoc);
                return;
            }
        });

    }

    }


package com.example.petcarehome.homenavigation.ui.config.clases_fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.ui.difusion.FullScreenImageActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class perfil_usuario_dueno extends Fragment {
    private FirebaseDatabase firebaseDatabase;
    TextView tipo_userd, nombre_userd,
            correo_userd, tel_userd, domicilio_userd;
    Button Actualizard;
    FirebaseUser user;
    FirebaseStorage firebaseStorage;
    ImageView fotod;
    String foto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.perfil_fragment_dueno, container, false);
        //Clase para el fragmento del perfil - fragmento principal del apartado de configuración
    }

    @Override

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tipo_userd = view.findViewById(R.id.tipo_usuariod_bd);
        nombre_userd = view.findViewById(R.id.nombre_usuariod_bd);

        correo_userd = view.findViewById(R.id.correo_usuariod_bd);
        tel_userd = view.findViewById(R.id.tel_usuariod_bd);
        domicilio_userd = view.findViewById(R.id.domicilio_usuariod_bd);
        fotod = view.findViewById(R.id.profile_imaged);
        Actualizard = view.findViewById(R.id.editar_perfil_configd);
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        foto = "";
        final DatabaseReference duenoRef = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE)
                .child(FirebaseReferences.DUENO_REFERENCE).child(user.getUid());

        String idUser;
        idUser = user.getUid();
        final StorageReference storagePerfilDuenoReference =
                firebaseStorage.getInstance().getReference
                        (FirebaseReferences.STORAGE_IMAGENPERFIL_REFERENCE).
                        child(FirebaseReferences.STORAGE_IMAGENPERFILDUENO_REFERENCE).child(idUser).child("img" + idUser + ".jpg");


        duenoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String tipo = dataSnapshot.child("tipo").getValue().toString();
                String nombre = dataSnapshot.child("nombre").getValue().toString() + " " + dataSnapshot.child("apellidos").getValue().toString();
                String correo = dataSnapshot.child("correo").getValue().toString();
                String tel = dataSnapshot.child("cel").getValue().toString();
                String domicilio = dataSnapshot.child("calle").getValue(String.class) + " "
                        + dataSnapshot.child("noext").getValue(String.class) + " "
                        + dataSnapshot.child("noint").getValue(String.class) + ", "
                        +dataSnapshot.child("colonia").getValue(String.class)+", "
                        + dataSnapshot.child("alcaldia").getValue(String.class);
                tipo_userd.setText(tipo);
                nombre_userd.setText(nombre);

                correo_userd.setText(correo);
                tel_userd.setText(tel);
                domicilio_userd.setText(domicilio);
                //foto = dataSnapshot.child("foto").getValue(String.class);
                //Glide.with(getActivity()).load(foto).apply(RequestOptions.circleCropTransform()).into(fotod);
                //  }
                if (dataSnapshot.child("foto").getValue(String.class).isEmpty()){
                    Glide.with(getContext()).load(R.drawable.ic_user).apply(RequestOptions.circleCropTransform()).into(fotod);
                    foto = "user";
                }else {
                    foto = dataSnapshot.child("foto").getValue(String.class);
                    Glide.with(getContext()).load(foto).apply(RequestOptions.circleCropTransform()).into(fotod);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        fotod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FullScreenImageActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), fotod, Objects.requireNonNull(ViewCompat.getTransitionName(fotod)));
                Bundle bundle = new Bundle();
                bundle.putString("title", "perfil");
                bundle.putString("foto", foto);
                intent.putExtras(bundle);
                startActivity(intent, options.toBundle());
            }
        });

        Actualizard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), actualizar_datosd.class));
                return;
            }
        });
    }
}

package com.example.petcarehome.homenavigation.ui.config.clases_fragmentos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.Calificacion;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.ui.difusion.FullScreenImageActivity;
import com.example.petcarehome.homenavigation.ui.mapa.dueno.VerCalificaciones;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

public class perfil_usuario_cuidador extends Fragment {
    private FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    FirebaseStorage firebaseStorage;
    TextView tipo_userc, nombre_userc, correo_userc, tel_userc, domicilio_userc, califfinal;
    ImageView fotoc;
    String foto;
    RatingBar ratingcuidador;


    Button Actualizarc;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.perfil_fragment_cuidador, container,false);
        //Clase para el fragmento del perfil - fragmento principal del apartado de configuración
    } @Override

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        califfinal = view.findViewById(R.id.calificacionc);
        ratingcuidador = view.findViewById(R.id.ratingcuidador);
        tipo_userc = view.findViewById(R.id.tipo_usuarioc_bd);
        nombre_userc = view.findViewById(R.id.nombre_usuarioc_bd);

        correo_userc = view.findViewById(R.id.correo_usuarioc_bd);
        tel_userc = view.findViewById(R.id.tel_usuarioc_bd);
        domicilio_userc = view.findViewById(R.id.domicilio_usuarioc_bd);
        fotoc = view.findViewById(R.id.profile_imagec);
        Actualizarc = view.findViewById(R.id.editar_perfil_configc);

        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        foto = "";

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
                if (getActivity() == null) {
                    return;
                }
                    String tipo = dataSnapshot.child("tipo").getValue().toString();
                    String nombre = dataSnapshot.child("nombre").getValue().toString() + " " + dataSnapshot.child("apellidos").getValue().toString();
                    String correo = dataSnapshot.child("correo").getValue().toString();
                    String tel = dataSnapshot.child("telefono").getValue().toString();
                    String domicilio = dataSnapshot.child("calle").getValue(String.class) + " "
                            + dataSnapshot.child("noext").getValue(String.class) + " "
                            + dataSnapshot.child("noint").getValue(String.class) + ", "
                            +dataSnapshot.child("colonia").getValue(String.class)+", "
                            + dataSnapshot.child("alcaldia").getValue(String.class);

                ArrayList<Float> listcalificacion = new ArrayList<>();

                for (DataSnapshot snacali :
                        dataSnapshot.child("calificaciones").getChildren()) {
                    if (snacali.exists()) {
                        Float calif = snacali.child("calificacion").getValue(Float.class);
                        listcalificacion.add(calif);
                    }
                }

                Float calfinal = Float.valueOf(0);
                if (!listcalificacion.isEmpty()) {

                    for (int i = 0; i < listcalificacion.size(); i++) {
                        calfinal += listcalificacion.get(i);
                    }
                    calfinal = calfinal/listcalificacion.size();
                }
                califfinal.setText(""+calfinal);
                ratingcuidador.setRating(calfinal);
                //foto = dataSnapshot.child("foto").getValue(String.class);
                tipo_userc.setText(tipo);
                nombre_userc.setText(nombre);

                correo_userc.setText(correo);
                    tel_userc.setText(tel);
                    domicilio_userc.setText(domicilio);
                if (dataSnapshot.child("foto").getValue(String.class).isEmpty()){
                    Glide.with(getActivity()).load(R.drawable.ic_user).apply(RequestOptions.circleCropTransform()).into(fotoc);
                    foto = "user";
                }else {
                    foto = dataSnapshot.child("foto").getValue(String.class);
                    Glide.with(getActivity()).load(foto).apply(RequestOptions.circleCropTransform()).into(fotoc);
                }
                    //Glide.with(getActivity()).load(foto).apply(RequestOptions.circleCropTransform()).into(fotoc);


              //  }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        califfinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuidadorRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Calificacion> listcalificacion = new ArrayList<>();
                        Intent intent = new Intent(getActivity(), VerCalificaciones.class);
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), ratingcuidador, Objects.requireNonNull(ViewCompat.getTransitionName(ratingcuidador)));

                        String nombre = dataSnapshot.child("nombre").getValue().toString() + " " + dataSnapshot.child("apellidos").getValue().toString();
                        for (DataSnapshot snacali :
                                dataSnapshot.child("calificaciones").getChildren()) {
                            if (snacali.exists()) {
                                Calificacion c = snacali.getValue(Calificacion.class);
                               // Float calif = snacali.child("calificacion").getValue(Float.class);
                                listcalificacion.add(c);
                            }
                        }

                        Float calfinal = Float.valueOf(0);
                        if (!listcalificacion.isEmpty()) {

                            for (int i = 0; i < listcalificacion.size(); i++) {
                                calfinal += listcalificacion.get(i).getCalificacion();
                            }
                            calfinal = calfinal/listcalificacion.size();
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("title",nombre);
                        bundle.putFloat("calif", calfinal);
                        bundle.putString("idCuidador", "Mis calificaciones");
                        bundle.putSerializable("lista", listcalificacion);
                        intent.putExtras(bundle);
                        startActivity(intent, options.toBundle());


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

        fotoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FullScreenImageActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), fotoc, Objects.requireNonNull(ViewCompat.getTransitionName(fotoc)));
                Bundle  bundle = new Bundle();
                bundle.putString("title", "perfil");
                bundle.putString("foto", foto);
                intent.putExtras(bundle);
                startActivity(intent, options.toBundle());
            }
        });




        Actualizarc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              startActivity(new Intent(getActivity(), actualizar_datosc.class));
                return;
            }
        });

    }

    }


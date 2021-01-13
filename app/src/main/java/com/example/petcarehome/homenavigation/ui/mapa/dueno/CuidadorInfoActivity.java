package com.example.petcarehome.homenavigation.ui.mapa.dueno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.petcarehome.InicioYRegistro.Cuidador;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.Calificacion;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.Objetos.Mascota;
import com.example.petcarehome.homenavigation.ui.difusion.FullScreenImageActivity;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class CuidadorInfoActivity extends AppCompatActivity {

    private ImageView fotoCuidador;
    private TextView nombreCuidadorTV, emailTV, telefonoTV, distanciaTV, calNum, sinServicios;
    private RecyclerView recyclerMascotas;
    private RatingBar calificacion;

    private FusedLocationProviderClient fusedLocationClient;

    private Cuidador cuidador;
    private Double lat, lng;
    private ArrayList<Mascota> listMascotas;
    private AdapterMascotas adapterMascotas;
    private String fotoc;
    private double cal, sumagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuidador_info);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        cal = 0;

        //Recibir Cuidador
        Bundle cuidadorSeleccionado = getIntent().getExtras();
        cuidador = null;
        if (cuidadorSeleccionado != null) {
            cuidador = (Cuidador) cuidadorSeleccionado.getSerializable("cuidador");
            lat = cuidadorSeleccionado.getDouble("lat");
            lng = cuidadorSeleccionado.getDouble("lng");
        }

        //Referencias a elementos del layout
        nombreCuidadorTV = findViewById(R.id.text_nombre_cuidador);
        emailTV = findViewById(R.id.text_email_cuidador);
        telefonoTV = findViewById(R.id.text_telefono_cuidador);
        distanciaTV = findViewById(R.id.text_distancia);
        fotoCuidador = findViewById(R.id.id_fotoCuidador);
        calificacion = findViewById(R.id.id_calif);
        calNum = findViewById(R.id.text_calif);
        sinServicios = findViewById(R.id.id_label_sin_servicios);

        listMascotas = cuidador.getMascotas();
        if (listMascotas.isEmpty()){
            sinServicios.setVisibility(View.VISIBLE);
        }
        //Construir Recycler
        recyclerMascotas = findViewById(R.id.recyclerMascotasCuidadorInfo);
        recyclerMascotas.setLayoutManager(new LinearLayoutManager(this));

        adapterMascotas = new AdapterMascotas(listMascotas, this);
        recyclerMascotas.setAdapter(adapterMascotas);


        //llenar los Textviews
        nombreCuidadorTV.setText(cuidador.getNombre() + " " + cuidador.getApellidos());
        emailTV.setText(cuidador.getCorreo());
        telefonoTV.setText(cuidador.getTelefono());
        fotoc = cuidador.getFoto();
        if (fotoc.isEmpty()){
            Glide.with(this).load(R.drawable.ic_user).apply(RequestOptions.circleCropTransform()).into(fotoCuidador);
            fotoc = "user";
        } else {
            Glide.with(this).load(fotoc).apply(RequestOptions.circleCropTransform()).into(fotoCuidador);
        }
        fotoCuidador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CuidadorInfoActivity.this, FullScreenImageActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(CuidadorInfoActivity.this, fotoCuidador, Objects.requireNonNull(ViewCompat.getTransitionName(fotoCuidador)));
                Bundle  bundle = new Bundle();
                bundle.putString("title", cuidador.getNombre() + " " + cuidador.getApellidos());
                bundle.putString("foto", fotoc);
                intent.putExtras(bundle);
                startActivity(intent, options.toBundle());
            }
        });

        final DecimalFormat formatDistancia = new DecimalFormat();
        formatDistancia.setMaximumFractionDigits(2);

        Double dist = (GeoFireUtils.getDistanceBetween(new GeoLocation(lat, lng), new GeoLocation(cuidador.getLat(), cuidador.getLng())))/1000;
        distanciaTV.setText(formatDistancia.format(dist) + "Km");



        calcularCalificacion();




        calNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCalif = new Intent(CuidadorInfoActivity.this, VerCalificaciones.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(CuidadorInfoActivity.this, calificacion, Objects.requireNonNull(ViewCompat.getTransitionName(calificacion)));
                Bundle  bundle = new Bundle();
                bundle.putString("title", cuidador.getNombre() + " " + cuidador.getApellidos());
                bundle.putString("idCuidador", cuidador.getIdUser());
                bundle.putBoolean("general", true);
                //bundle.putFloat("calif", cal);
                //bundle.putSerializable("lista", listCalificaciones);
                intentCalif.putExtras(bundle);
                startActivity(intentCalif, options.toBundle());
            }
        });




    }

    private void calcularCalificacion() {
        DatabaseReference cuidadorRef = FirebaseDatabase.getInstance().getReference().child(FirebaseReferences.USERS_REFERENCE).child(FirebaseReferences.CUIDADOR_REFERENCE).child(cuidador.getIdUser()).child("calificaciones");
        cuidadorRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //listCal.clear();
                int count = 0;
                sumagen = 0;
                cal = 0;
                for (DataSnapshot snapCalif:
                        dataSnapshot.getChildren()) {
                    if (snapCalif.exists()){
                        sumagen += snapCalif.child("calificacion").getValue(Double.class);
                        count++;
                        //listCal.add(calN);
                    }
                }
                if (count != 0){
                    cal = sumagen / count;
                    DecimalFormat calFormat = new DecimalFormat();
                    calFormat.setMaximumFractionDigits(1);

                    calificacion.setRating((float) cal);
                    calNum.setText(calFormat.format(cal));
                } else {
                    calificacion.setRating(0);
                    calNum.setText("Sin calificaciones");
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
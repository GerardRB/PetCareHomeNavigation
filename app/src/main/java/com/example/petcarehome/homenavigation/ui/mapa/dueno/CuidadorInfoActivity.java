package com.example.petcarehome.homenavigation.ui.mapa.dueno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private TextView nombreCuidadorTV, emailTV, telefonoTV, distanciaTV, calNum;
    private RecyclerView recyclerMascotas;
    private RatingBar calificacion;

    private FusedLocationProviderClient fusedLocationClient;

    private Cuidador cuidador;
    private Double lat, lng;
    private ArrayList<Mascota> listMascotas;
    private ArrayList<Calificacion> listCalificaciones;
    private AdapterMascotas adapterMascotas;
    private String fotoc;
    private Float cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuidador_info);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        listCalificaciones = new ArrayList<>();

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

        //Construir Recycler
        listMascotas = cuidador.getMascotas();
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


        if (cuidador.getCalificaciones() != null){
            listCalificaciones = cuidador.getCalificaciones();
        }


        if (!listCalificaciones.isEmpty()){
            //llenar el rating bar
            cal = calcularCalificacion(listCalificaciones);
        } else {
            cal = Float.valueOf(0);
        }

        calificacion.setRating(cal);
        calNum.setText(""+cal);



        calNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCalif = new Intent(CuidadorInfoActivity.this, VerCalificaciones.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(CuidadorInfoActivity.this, calificacion, Objects.requireNonNull(ViewCompat.getTransitionName(calificacion)));
                Bundle  bundle = new Bundle();
                bundle.putString("title", cuidador.getNombre() + " " + cuidador.getApellidos());
                bundle.putString("idCuidador", cuidador.getIdUser());
                bundle.putFloat("calif", cal);
                bundle.putSerializable("lista", listCalificaciones);
                intentCalif.putExtras(bundle);
                startActivity(intentCalif, options.toBundle());
            }
        });




    }

    private float calcularCalificacion(ArrayList<Calificacion> calificaciones) {

        float suma, prom;
        suma = 0;
        for (int i = 0; i < calificaciones.size(); i++ ){
            suma += calificaciones.get(i).getCalificacion();
        }

        prom = suma / calificaciones.size();

        return prom;

    }
}
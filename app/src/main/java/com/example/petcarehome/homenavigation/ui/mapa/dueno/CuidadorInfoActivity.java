package com.example.petcarehome.homenavigation.ui.mapa.dueno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petcarehome.InicioYRegistro.Cuidador;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.Mascota;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CuidadorInfoActivity extends AppCompatActivity {

    private ImageView fotoCuidador;
    private TextView nombreCuidadorTV, emailTV, telefonoTV, domicilioTV, distanciaTV;
    private RecyclerView recyclerMascotas;

    private FusedLocationProviderClient fusedLocationClient;

    private Cuidador cuidador;
    private Double lat, lng;
    private ArrayList<Mascota> listMascotas;
    private AdapterMascotas adapterMascotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuidador_info);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


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
        domicilioTV = findViewById(R.id.text_domicilio_cuidador);
        distanciaTV = findViewById(R.id.text_distancia);

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
        String dom = cuidador.getCalle() + " " + cuidador.getNoext() + " " + cuidador.getNoint() + ", " + cuidador.getColonia() +
                ", " + cuidador.getAlcaldia();
        domicilioTV.setText(dom);

        final DecimalFormat formatDistancia = new DecimalFormat();
        formatDistancia.setMaximumFractionDigits(2);

        Double dist = (GeoFireUtils.getDistanceBetween(new GeoLocation(lat, lng), new GeoLocation(cuidador.getLat(), cuidador.getLng())))/1000;
        distanciaTV.setText(formatDistancia.format(dist) + "Km");

    }
}
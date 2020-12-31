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
import com.example.petcarehome.homenavigation.Objetos.ReportePerdidasID;
import com.example.petcarehome.homenavigation.ui.difusion.encontradas.AdapterReportesEncontradas;
import com.example.petcarehome.homenavigation.ui.difusion.perdidas.AdapterReportesPerdidas;

import java.util.ArrayList;

public class CuidadorInfoActivity extends AppCompatActivity {

    private ImageView fotoCuidador;
    private TextView nombreCuidadorTV, emailTV, telefonoTV, domicilioTV;
    private RecyclerView recyclerMascotas;

    private Cuidador cuidador;
    private ArrayList<Mascota> listMascotas;
    private AdapterMascotas adapterMascotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuidador_info);

        //Recibir Cuidador
        Bundle cuidadorSeleccionado = getIntent().getExtras();
        cuidador = null;
        if (cuidadorSeleccionado != null){
            cuidador = (Cuidador) cuidadorSeleccionado.getSerializable("cuidador");
        }

        //Referencias a elementos del layout
        nombreCuidadorTV = findViewById(R.id.text_nombre_cuidador);
        emailTV = findViewById(R.id.text_email_cuidador);
        telefonoTV = findViewById(R.id.text_telefono_cuidador);
        domicilioTV = findViewById(R.id.text_domicilio_cuidador);

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
        String dom = cuidador.getCalle() + " No. Ext. " + cuidador.getNoext() + " No. Int. " + cuidador.getNoint() +
                ", " + cuidador.getAlcaldia();
        domicilioTV.setText(dom);







    }
}
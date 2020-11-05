package com.example.petcarehome.homenavigation.ui.difusion.encontradas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petcarehome.homenavigation.Objetos.ReporteEncontradas;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.ReporteEncontradasID;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetalleReporteEncontradaActivity extends AppCompatActivity {

    private ReporteEncontradasID reporteE;
    private TextView idReporte, tipoMascota, fechaEncontrada, horaEncontrada, alcaldia, colonia, calle, descripcion, correoUser;
    private ImageView foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reporte_encontrada);

        //Recibir reporte Seleccionado
        Bundle reporteSeleccionado = getIntent().getExtras();
        reporteE = null;
        if (reporteSeleccionado != null){
            reporteE = (ReporteEncontradasID) reporteSeleccionado.getSerializable("reporteEncontrada");
        }

        //Referencia a textviews
        idReporte = findViewById(R.id.text_id_DRME);
        tipoMascota = findViewById(R.id.text_tipo_DRME);
        fechaEncontrada = findViewById(R.id.text_fecha_encontrada_DRME);
        horaEncontrada = findViewById(R.id.text_hora_encontrada_DRME);
        alcaldia = findViewById(R.id.text_alcaldia_DRME);
        colonia = findViewById(R.id.text_colonia_DRME);
        calle = findViewById(R.id.text_calle_DRME);
        descripcion = findViewById(R.id.text_descricpion_DRME);
        correoUser = findViewById(R.id.text_id_user_DRME);
        foto = findViewById(R.id.id_imageDRME);

        //Imprimir valores del objeto recibido
        idReporte.setText(reporteE.getId());
        tipoMascota.setText(reporteE.getReporteEncontradas().getTipo());
        fechaEncontrada.setText(reporteE.getReporteEncontradas().getFecha());
        horaEncontrada.setText(reporteE.getReporteEncontradas().getHora());
        alcaldia.setText(reporteE.getReporteEncontradas().getAlcaldia());
        colonia.setText(reporteE.getReporteEncontradas().getColonia());
        calle.setText(reporteE.getReporteEncontradas().getCalle());
        descripcion.setText(reporteE.getReporteEncontradas().getDescripcion());
        correoUser.setText(reporteE.getReporteEncontradas().getIdUser());
        Picasso.with(this).load(reporteE.getReporteEncontradas().getFoto()).into(foto, new Callback() {
            @Override
            public void onSuccess() {
                foto.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                Toast.makeText(getApplicationContext(),"Error al cargar imagen", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

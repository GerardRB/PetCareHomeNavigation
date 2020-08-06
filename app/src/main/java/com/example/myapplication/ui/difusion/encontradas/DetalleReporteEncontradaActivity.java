package com.example.myapplication.ui.difusion.encontradas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.R;

public class DetalleReporteEncontradaActivity extends AppCompatActivity {

    private ReporteEncontradas reporteE;
    private TextView idReporte, tipoMascota, fechaEncontrada, horaEncontrada, alcaldia, colonia, calle, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reporte_encontrada);

        //Recibir reporte Seleccionado
        Bundle reporteSeleccionado = getIntent().getExtras();
        reporteE = null;
        if (reporteSeleccionado != null){
            reporteE = (ReporteEncontradas) reporteSeleccionado.getSerializable("reporteEncontrada");
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

        //Imprimir valores del objeto recibido
        idReporte.setText(Integer.toString(reporteE.getId()));
        tipoMascota.setText(reporteE.getTipo());
        fechaEncontrada.setText(reporteE.getFecha());
        horaEncontrada.setText(reporteE.getHora());
        alcaldia.setText(reporteE.getAlcaldia());
        colonia.setText(reporteE.getColonia());
        calle.setText(reporteE.getCalle());
        descripcion.setText(reporteE.getDescripcion());


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

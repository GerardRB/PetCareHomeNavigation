package com.example.petcarehome.ui.difusion.perdidas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.petcarehome.Objetos.ReportePerdidas;
import com.example.petcarehome.R;

public class DetalleReportePerdidasActivity extends AppCompatActivity {

    private ReportePerdidas reporteP;
    private TextView idReporte, nombreMascota, tipoMascota, edad, fechaExtravio, horaExtravio, alcaldia, colonia, calle, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reporte_perdidas);

        //Recibir reporte Seleccionado
        Bundle reporteSeleccionado = getIntent().getExtras();
        reporteP = null;
        if (reporteSeleccionado != null){
            reporteP = (ReportePerdidas) reporteSeleccionado.getSerializable("reportePerdida");
        }



        //Referencia a textviews
        idReporte = findViewById(R.id.text_id_DRMP);
        nombreMascota = findViewById(R.id.text_nombre_DRMP);
        tipoMascota = findViewById(R.id.text_tipo_DRMP);
        edad = findViewById(R.id.text_edad_DRMP);
        fechaExtravio = findViewById(R.id.text_fecha_extravio_DRMP);
        horaExtravio = findViewById(R.id.text_hora_Extravio_DRMP);
        alcaldia = findViewById(R.id.text_alcaldia_DRMP);
        colonia = findViewById(R.id.text_colonia_DRMP);
        calle = findViewById(R.id.text_calle_DRMP);
        descripcion = findViewById(R.id.text_descricpion_DRMP);

        //Toast.makeText(getApplicationContext(), "Nombre: " + reporteP.getNombre(), Toast.LENGTH_LONG).show();


        //Imprimir valores del objeto recibido
        idReporte.setText(Integer.toString(reporteP.getId()));
        nombreMascota.setText(reporteP.getNombre());
        tipoMascota.setText(reporteP.getTipo());
        edad.setText(reporteP.getEdad());
        fechaExtravio.setText(reporteP.getFecha());
        horaExtravio.setText(reporteP.getHora());
        alcaldia.setText(reporteP.getAlcaldia());
        colonia.setText(reporteP.getColonia());
        calle.setText(reporteP.getCalle());
        descripcion.setText(reporteP.getDescripcion());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

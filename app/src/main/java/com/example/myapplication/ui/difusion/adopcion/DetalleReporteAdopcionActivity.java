package com.example.myapplication.ui.difusion.adopcion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.Objetos.ReporteAdopcion;
import com.example.myapplication.R;

public class DetalleReporteAdopcionActivity extends AppCompatActivity {

    private ReporteAdopcion reporteA;
    private TextView idReporte, tipoMascota, raza, edad, vacunas, esterilizacion, alcaldia, colonia, calle, descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reporte_adopcion);


        //Recibir reporte Seleccionado
        Bundle reporteSeleccionado = getIntent().getExtras();
        reporteA = null;
        if (reporteSeleccionado != null){
            reporteA = (ReporteAdopcion) reporteSeleccionado.getSerializable("reporteAdopcion");
        }

        //Referencia a textviews
        idReporte = findViewById(R.id.text_id_DRMA);
        tipoMascota = findViewById(R.id.text_tipo_DRMA);
        raza = findViewById(R.id.text_raza_DRMA);
        edad = findViewById(R.id.text_edad_DRMA);
        vacunas = findViewById(R.id.text_vacunas_DRMA);
        esterilizacion = findViewById(R.id.text_esterilizacion_DRMA);
        alcaldia = findViewById(R.id.text_alcaldia_DRMA);
        colonia = findViewById(R.id.text_colonia_DRMA);
        calle = findViewById(R.id.text_calle_DRMA);
        descripcion = findViewById(R.id.text_descricpion_DRMA);

        //Imprimir valores del objeto recibido
        idReporte.setText(Integer.toString(reporteA.getId()));
        tipoMascota.setText(reporteA.getTipo());
        raza.setText(reporteA.getRaza());
        edad.setText(reporteA.getEdad());
        vacunas.setText(reporteA.getVacunas());
        esterilizacion.setText(reporteA.getEsterilizacion());
        alcaldia.setText(reporteA.getAlcaldia());
        colonia.setText(reporteA.getColonia());
        calle.setText(reporteA.getCalle());
        descripcion.setText(reporteA.getDescripcion());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

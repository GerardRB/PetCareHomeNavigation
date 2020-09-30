package com.example.petcarehome.ui.difusion.perdidas;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petcarehome.Objetos.ReportePerdidas;
import com.example.petcarehome.Objetos.ReportePerdidasID;
import com.example.petcarehome.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetalleReportePerdidasActivity extends AppCompatActivity {

    private ReportePerdidasID reporteP;
    private TextView idReporte, nombreMascota, tipoMascota, edad, fechaExtravio, horaExtravio, alcaldia, colonia, calle, descripcion;
    private ImageView foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reporte_perdidas);

        //Recibir reporte Seleccionado
        Bundle reporteSeleccionado = getIntent().getExtras();
        reporteP = null;
        if (reporteSeleccionado != null){
            reporteP = (ReportePerdidasID) reporteSeleccionado.getSerializable("reportePerdida");
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
        foto = findViewById(R.id.id_imageDRMP);

        //Toast.makeText(getApplicationContext(), "Nombre: " + reporteP.getNombre(), Toast.LENGTH_LONG).show();


        //Imprimir valores del objeto recibido
        idReporte.setText(reporteP.getId());
        nombreMascota.setText(reporteP.getReportePerdidas().getNombre());
        tipoMascota.setText(reporteP.getReportePerdidas().getTipo());
        edad.setText(reporteP.getReportePerdidas().getEdad());
        fechaExtravio.setText(reporteP.getReportePerdidas().getFecha());
        horaExtravio.setText(reporteP.getReportePerdidas().getHora());
        alcaldia.setText(reporteP.getReportePerdidas().getAlcaldia());
        colonia.setText(reporteP.getReportePerdidas().getColonia());
        calle.setText(reporteP.getReportePerdidas().getCalle());
        descripcion.setText(reporteP.getReportePerdidas().getDescripcion());
        Picasso.with(this).load(reporteP.getReportePerdidas().getFoto()).into(foto, new Callback() {
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

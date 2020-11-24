package com.example.petcarehome.homenavigation.ui.difusion.adopcion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petcarehome.homenavigation.Objetos.ReporteAdopcion;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.ReporteAdopcionID;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetalleReporteAdopcionActivity extends AppCompatActivity {

    private ReporteAdopcionID reporteA;
    private TextView idReporte, tipoMascota, raza, edad, vacunas, esterilizacion, alcaldia, colonia, calle, descripcion, correoUser;
    private ImageView foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reporte_adopcion);


        //Recibir reporte Seleccionado
        Bundle reporteSeleccionado = getIntent().getExtras();
        reporteA = null;
        if (reporteSeleccionado != null){
            reporteA = (ReporteAdopcionID) reporteSeleccionado.getSerializable("reporteAdopcion");
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
        correoUser = findViewById(R.id.text_id_user_DRMA);
        foto = findViewById(R.id.id_imageDRMA);

        //Imprimir valores del objeto recibido
        idReporte.setText(reporteA.getId());
        tipoMascota.setText(reporteA.getReporteAdopcion().getTipo());
        raza.setText(reporteA.getReporteAdopcion().getRaza());
        edad.setText(reporteA.getReporteAdopcion().getEdad());
        vacunas.setText(reporteA.getReporteAdopcion().getVacunas());
        esterilizacion.setText(reporteA.getReporteAdopcion().getEsterilizacion());
        alcaldia.setText(reporteA.getReporteAdopcion().getAlcaldia());
        colonia.setText(reporteA.getReporteAdopcion().getColonia());
        calle.setText(reporteA.getReporteAdopcion().getCalle());
        descripcion.setText(reporteA.getReporteAdopcion().getDescripcion());
        correoUser.setText(reporteA.getReporteAdopcion().getIdUser());
        Picasso.with(this).load(reporteA.getReporteAdopcion().getFoto()).into(foto, new Callback() {
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

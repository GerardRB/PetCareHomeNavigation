package com.example.myapplication.ui.difusion.adopcion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.R;

public class GenerarReporteAdopcionActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int COD_SELECCIONA = 10;
    //private static final int COD_SELECCIONA = 10;

    private Spinner comboAlcaldias, comboTipoMascota, comboVacunas, comboEsterilizacion;
    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_reporte_adopcion);

        //Referencia al componente ImageView en xml
        imageView = findViewById(R.id.id_input_imageRMA);
        imageView.setOnClickListener(this);

        //Referencia al Boton generar reporte en xml
        button = findViewById(R.id.id_btn_generarRMA);
        button.setOnClickListener(this);

        //spinner de alcaldias
        comboAlcaldias = findViewById(R.id.spinner_alcaldiaRMA);
        ArrayAdapter<CharSequence> adapterAlcaldia = ArrayAdapter.createFromResource(this, R.array.combo_alcaldia, android.R.layout.simple_spinner_item);
        comboAlcaldias.setAdapter(adapterAlcaldia);

        //spinner de tipo de mascota
        comboTipoMascota = findViewById(R.id.spinner_tipoRMA);
        ArrayAdapter<CharSequence> adapterTipoMascota = ArrayAdapter.createFromResource(this, R.array.combo_tipoRMP, android.R.layout.simple_spinner_item);
        comboTipoMascota.setAdapter(adapterTipoMascota);

        //spinner de vacunas
        comboVacunas = findViewById(R.id.spinner_vacunasRMA);
        ArrayAdapter<CharSequence> adapterVacunas = ArrayAdapter.createFromResource(this, R.array.combo_sino, android.R.layout.simple_spinner_item);
        comboVacunas.setAdapter(adapterVacunas);

        //spinner de esterilizacion
        comboEsterilizacion = findViewById(R.id.spinner_esterilizacionRMA);
        ArrayAdapter<CharSequence> adapterEsterilizacion = ArrayAdapter.createFromResource(this, R.array.combo_sino, android.R.layout.simple_spinner_item);
        comboEsterilizacion.setAdapter(adapterEsterilizacion);

    }

    //Acceso a la galería
    private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicación"), 10);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            Uri path = data.getData();
            imageView.setImageURI(path);
        }
    }

    //Regresar a la actividad anterior con la flecha de action bar(por defecto)
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //Eventos Onclick dentro de la actividad
    @Override
    public void onClick(View v) {
        if (v.getId()==button.getId()){
            Toast.makeText(getApplicationContext(), "Reporte generado exitosamente", Toast.LENGTH_LONG).show();
            onBackPressed();
        }
        if(v.getId()==imageView.getId()){
            cargarImagen();
        }

    }

}

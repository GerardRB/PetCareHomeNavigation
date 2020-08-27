package com.example.myapplication.ui.difusion.adopcion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.myapplication.Objetos.FirebaseReferences;
import com.example.myapplication.Objetos.ReporteAdopcion;
import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GenerarReporteAdopcionActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int COD_SELECCIONA = 10;
    //private static final int COD_SELECCIONA = 10;

    private Spinner comboAlcaldias, comboTipoMascota, comboVacunas, comboEsterilizacion;
    private EditText raza, edad, colonia, calle, descripcion;
    private ImageView imageView;
    private Button button;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_reporte_adopcion);

        //Referencia a editTexts
        raza = findViewById(R.id.id_input_razaRMA);
        edad = findViewById(R.id.id_input_edadRMA);
        colonia = findViewById(R.id.id_input_coloniaRMA);
        calle = findViewById(R.id.id_input_calleRMA);
        descripcion = findViewById(R.id.id_input_descripcionRMA);

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
            validarCampos();
        }
        if(v.getId()==imageView.getId()){
            cargarImagen();
        }

    }

    private void validarCampos() {
        String tipoM, razaM, edadM, vacunas, esterilizacion, alcaldiaE, coloniaE, calleE, descripcionE;
        String mensaje = "Faltan campos por ingresar";
        String reporte = "";

        //Obtener valores ingresados
        tipoM = comboTipoMascota.getSelectedItem().toString();
        razaM = raza.getText().toString();
        edadM = edad.getText().toString();
        vacunas = comboVacunas.getSelectedItem().toString();
        esterilizacion = comboEsterilizacion.getSelectedItem().toString();
        alcaldiaE = comboAlcaldias.getSelectedItem().toString();
        coloniaE = colonia.getText().toString();
        calleE = calle.getText().toString();
        descripcionE = descripcion.getText().toString();

        //validacion
        if(tipoM.equals("Seleccionar") || razaM.isEmpty() || edadM.isEmpty() || vacunas.equals("Seleccionar") || esterilizacion.equals("Seleccionar") || alcaldiaE.equals("Seleccionar") || descripcionE.isEmpty()){
            if (tipoM.equals("Seleccionar"))
                mensaje += "\nSeleccione un tipo de mascota";
            if (razaM.isEmpty())
                raza.setError("Obligatorio");
            if (edadM.isEmpty())
                edad.setError("Obligatorio");
            if (vacunas.equals("Seleccionar"))
                mensaje += "\nSeleccione si o no está vacunada la mascota";
            if (esterilizacion.equals("Seleccionar"))
                mensaje += "\nSeleccione si o no está esterilizada la mascota";
            if (alcaldiaE.equals("Seleccionar"))
                mensaje += "\nSeleccione una alcaldía";
            if (descripcionE.isEmpty())
                descripcion.setError("Obligatorio");
            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
        } else {
            ReporteAdopcion reporteA = new ReporteAdopcion(tipoM, razaM, edadM, vacunas, esterilizacion, alcaldiaE, coloniaE, calleE, descripcionE, R.drawable.ic_perro, 1);

            //Guardar en base de datos
            firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference petCareReference = firebaseDatabase.getReference(FirebaseReferences.PETCARE_REFERENCE);
            petCareReference.child(FirebaseReferences.REPORTEADOPCION_REFERENCE).push().setValue(reporteA);

            Intent intent = new Intent(GenerarReporteAdopcionActivity.this, AdopcionFragment.class);

            Bundle  bundle = new Bundle();
            bundle.putSerializable("reporteAdopcion", reporteA);

            intent.putExtras(bundle);
            //startActivity(intent);

            reporte = "Reporte generado: \nID: " + reporteA.getId() +
                    "\nTipo: " + reporteA.getTipo() +
                    "\nRaza: " + reporteA.getRaza() +
                    "\nEdad: " + reporteA.getEdad() +
                    "\nVacunado: " + reporteA.getVacunas() +
                    "\nEsterilizado: " + reporteA.getEsterilizacion() +
                    "\nZona: " + reporteA.getAlcaldia() +
                    ", col. " + reporteA.getColonia() +
                    ", calle " + reporteA.getCalle() +
                    "\nDescripción: " + reporteA.getDescripcion();
            Toast.makeText(getApplicationContext(), reporte, Toast.LENGTH_LONG).show();
            onBackPressed();
        }



    }

}

package com.example.myapplication.ui.difusion.perdidas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication.Objetos.FirebaseReferences;
import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class GenerarReporteExtravioActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int COD_SELECCIONA = 10;
    //private static final int COD_SELECCIONA = 10;

    private Spinner comboAlcaldias, comboTipoMascota;
    private EditText fecha, hora, nombre, edad, colonia, calle, descripcion;
    private DatePickerDialog.OnDateSetListener fechaSetListener;
    private TimePickerDialog.OnTimeSetListener horaSetListener;
    private ImageView imageView;
    private Button buttonGenerar;
    private ArrayList<ReportePerdidas> listReportes;

    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_reporte_extravio);



        //Referencia a elementos
        nombre = findViewById(R.id.id_input_nombreRMP);
        edad = findViewById(R.id.id_input_edadRMP);
        colonia = findViewById(R.id.id_input_coloniaRMP);
        calle = findViewById(R.id.id_input_calleRMP);
        descripcion = findViewById(R.id.id_input_descripcionRMP);

        //Referencia al componente ImageView en xml
        imageView = findViewById(R.id.id_input_imageRMP);
        imageView.setOnClickListener(this);

        //Referencia al Boton generar reporte en xml
        buttonGenerar = findViewById(R.id.id_btn_generarRMP);
        buttonGenerar.setOnClickListener(this);

        //spinner de alcaldias
        comboAlcaldias = findViewById(R.id.spinner_alcaldiaRMP);
        ArrayAdapter<CharSequence> adapterAlcaldia = ArrayAdapter.createFromResource(this, R.array.combo_alcaldia, android.R.layout.simple_spinner_item);
        comboAlcaldias.setAdapter(adapterAlcaldia);

        //spinner de tipo de mascota
        comboTipoMascota = findViewById(R.id.spinner_tipoRMP);
        ArrayAdapter<CharSequence> adapterTipoMascota = ArrayAdapter.createFromResource(this, R.array.combo_tipoRMP, android.R.layout.simple_spinner_item);
        comboTipoMascota.setAdapter(adapterTipoMascota);


        //fecha con dialogo date picker
        fecha = (EditText) findViewById(R.id.id_input_fechaRMP);
        fecha.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Calendar cal = Calendar.getInstance();
                    int myear = cal.get(Calendar.YEAR);
                    int mmonth = cal.get(Calendar.MONTH);
                    int mday = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(GenerarReporteExtravioActivity.this,
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            fechaSetListener, myear, mmonth, mday);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();

                }
            }
        });

        //Obtener fecha del dialogo y escribirla en el editText
        fechaSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String mes, date;
                month = month +1;
                switch (month){
                    case 1:
                        mes = "ENE";
                        break;
                    case 2:
                        mes = "FEB";
                        break;
                    case 3:
                        mes = "MAR";
                        break;
                    case 4:
                        mes = "ABR";
                        break;
                    case 5:
                        mes = "MAY";
                        break;
                    case 6:
                        mes = "JUN";
                        break;
                    case 7:
                        mes = "JUL";
                        break;
                    case 8:
                        mes = "AGO";
                        break;
                    case 9:
                        mes = "SEP";
                        break;
                    case 10:
                        mes = "OCT";
                        break;
                    case 11:
                        mes = "NOV";
                        break;
                    case 12:
                        mes = "DIC";
                        break;
                    default:
                        mes = " ";
                }
                if (dayOfMonth <= 9){
                    date = "0" + dayOfMonth + "/" + mes +  "/" + year;
                } else {
                    date = dayOfMonth +  "/" + mes +  "/" + year;
                }
                fecha.setText(date);

            }
        };


        //hora con dialogo time picker
        hora = (EditText) findViewById(R.id.id_input_horaRMP);
        hora.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    Calendar cal = Calendar.getInstance();
                    int mhour = cal.get(Calendar.HOUR);
                    int mminute = cal.get(Calendar.MINUTE);

                    TimePickerDialog dialog = new TimePickerDialog(GenerarReporteExtravioActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, horaSetListener, mhour, mminute, android.text.format.DateFormat.is24HourFormat(GenerarReporteExtravioActivity.this));
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();

                }
            }
        });
        //Obtener la hora, darle fomrato 12hrs am-pm y mostrarla en el editText
        horaSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String ampm, horas, minuto, datetime;
                if (hourOfDay < 12) {
                    ampm = " am";
                }
                else{
                    ampm = " pm";
                }
                if (minute <= 9){
                    minuto = ":0" + minute;
                }
                else{
                    minuto = ":" + minute;
                }

                switch (hourOfDay){
                    case 0:
                        horas = "12";
                        break;
                    case 1:
                        horas = "01";
                        break;
                    case 2:
                        horas = "02";
                        break;
                    case 3:
                        horas = "03";
                        break;
                    case 4:
                        horas = "04";
                        break;
                    case 5:
                        horas = "05";
                        break;
                    case 6:
                        horas = "06";
                        break;
                    case 7:
                        horas = "07";
                        break;
                    case 8:
                        horas = "08";
                        break;
                    case 9:
                        horas = "09";
                        break;
                    case 10:
                        horas = "10";
                        break;
                    case 11:
                        horas = "11";
                        break;
                    case 12:
                        horas = "12";
                        break;
                    case 13:
                        horas = "01";
                        break;
                    case 14:
                        horas = "02";
                        break;
                    case 15:
                        horas = "03";
                        break;
                    case 16:
                        horas = "04";
                        break;
                    case 17:
                        horas = "05";
                        break;
                    case 18:
                        horas = "06";
                        break;
                    case 19:
                        horas = "07";
                        break;
                    case 20:
                        horas = "08";
                        break;
                    case 21:
                        horas = "09";
                        break;
                    case 22:
                        horas = "10";
                        break;
                    case 23:
                        horas = "11";
                        break;
                    default:
                        horas = " ";
                        break;
                }

                datetime = horas + minuto + ampm;
                hora.setText(datetime);


            }
        };
    }

    /*private void startFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }*/


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
        switch(v.getId()){
            case R.id.id_btn_generarRMP:
                //Toast.makeText(getApplicationContext(), "Reporte generado exitosamente", Toast.LENGTH_LONG).show();
                ValidarCampos();
                break;
            case R.id.id_input_imageRMP:
                cargarImagen();
                break;
        }


    }

    private void ValidarCampos() {
        String nombreM, tipoM, edadM, fechaE, horaE, alcaldiaE, coloniaE, calleE, descripcionE;
        String mensaje = "Faltan campos por ingresar";
        String reporte = "";
        nombreM = nombre.getText().toString();
        tipoM = comboTipoMascota.getSelectedItem().toString();
        edadM = edad.getText().toString();
        fechaE = fecha.getText().toString();
        horaE = hora.getText().toString();
        alcaldiaE = comboAlcaldias.getSelectedItem().toString();
        coloniaE = colonia.getText().toString();
        calleE = calle.getText().toString();
        descripcionE = descripcion.getText().toString();



        if(nombreM.isEmpty() || tipoM.equals("Seleccionar") || edadM.isEmpty() || fechaE.isEmpty() || horaE.isEmpty() || alcaldiaE.equals("Seleccionar") || descripcionE.isEmpty()){
            if(nombreM.isEmpty())
                nombre.setError("Obligatorio");
            if (tipoM.equals("Seleccionar"))
                mensaje += "\nSeleccione un tipo de mascota";
            if (edadM.isEmpty())
                edad.setError("Obligatorio");
            if (fechaE.isEmpty())
                fecha.setError("Obligatorio");
            if (horaE.isEmpty())
                hora.setError("Obligatorio");
            if (alcaldiaE.equals("Seleccionar"))
                mensaje += "\nSeleccione una alcaldía";
            if (descripcionE.isEmpty())
                descripcion.setError("Obligatorio");
            Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
        } else {
            ReportePerdidas reporteP = new ReportePerdidas(nombreM, tipoM, edadM, fechaE, horaE, alcaldiaE, coloniaE, calleE, descripcionE, R.drawable.ic_perro, 1);

            //Guardar en base de datos
            firebaseDatabase = FirebaseDatabase.getInstance();
            final DatabaseReference petCareReference = firebaseDatabase.getReference(FirebaseReferences.PETCARE_REFERENCE);
            petCareReference.child(FirebaseReferences.REPORTEPERDIDA_REFERENCE).push().setValue(reporteP);


            Intent intent = new Intent(GenerarReporteExtravioActivity.this, PerdidasFragment.class);

            Bundle  bundle = new Bundle();
            bundle.putSerializable("reportePerdida", reporteP);

            intent.putExtras(bundle);
            //startActivity(intent);

            reporte = "Reporte generado: \nID: " + reporteP.getId() +
                    "\nNombre: " + reporteP.getNombre() +
                    "\nTipo: " + reporteP.getTipo() +
                    "\nEdad: " + reporteP.getEdad() +
                    "\nFecha: " + reporteP.getFecha() +
                    "\nHora: " + reporteP.getHora() +
                    "\nZona: " + reporteP.getAlcaldia() +
                    ", col. " + reporteP.getColonia() +
                    ", calle " + reporteP.getCalle() +
                    "\nDescripción: " + reporteP.getDescripcion();
            Toast.makeText(getApplicationContext(), reporte, Toast.LENGTH_LONG).show();
            onBackPressed();
        }








    }
}




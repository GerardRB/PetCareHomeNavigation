package com.example.myapplication.ui.difusion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.Calendar;

public class GenerarReporteExtravioActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int COD_SELECCIONA = 10;
    //private static final int COD_SELECCIONA = 10;

    private Spinner comboAlcaldias, comboTipoMascota;
    private EditText fecha;
    private DatePickerDialog.OnDateSetListener fechaSetListener;
    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_reporte_extravio);


        //Cargar imagen desde la galeria
        imageView = findViewById(R.id.id_input_imageRMP);
        imageView.setOnClickListener(this);

        //Boton generar reporte
        button = findViewById(R.id.id_btn_generarRMP);
        button.setOnClickListener(this);




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
                    case 5:
                        mes = "MAY";
                        break;
                    case 6:
                        mes = "JUN";
                        break;
                    case 7:
                        mes = "JUL";
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




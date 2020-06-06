package com.example.myapplication.ui.difusion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.Calendar;

public class GenerarReporteExtravioActivity extends AppCompatActivity {

    Spinner comboAlcaldias;
    EditText fecha;
    DatePickerDialog.OnDateSetListener fechaSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_reporte_extravio);

        //spinner de alcaldias
        comboAlcaldias = findViewById(R.id.spinner_alcaldia);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.combo_alcaldia, android.R.layout.simple_spinner_item);
        comboAlcaldias.setAdapter(adapter);

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

        fechaSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                String date = dayOfMonth + "/" + month + "/" + year;
                fecha.setText(date);

            }
        };

    }


    //Regresar a la actividad anterior con la flecha de action bar(por defecto)
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

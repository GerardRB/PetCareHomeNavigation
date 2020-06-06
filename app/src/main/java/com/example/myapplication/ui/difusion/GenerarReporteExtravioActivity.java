package com.example.myapplication.ui.difusion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.myapplication.R;

public class GenerarReporteExtravioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_reporte_extravio);

    }


    //Regresar a la actividad anterior con la flecha de action bar(por defecto)
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

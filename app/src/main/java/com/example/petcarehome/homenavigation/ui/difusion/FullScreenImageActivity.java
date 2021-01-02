package com.example.petcarehome.homenavigation.ui.difusion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.ReportePerdidas;

public class FullScreenImageActivity extends AppCompatActivity {
    private String titulo, foto;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        //Recibir reporte Seleccionado
        Bundle bundleRecibido = getIntent().getExtras();
        titulo = null;
        foto = null;
        if (bundleRecibido != null){
            titulo = bundleRecibido.getString("title");
            foto = bundleRecibido.getString("foto");
        }

        imageView = findViewById(R.id.id_image_reporte);

        if (titulo.isEmpty()){
            setTitle("Foto de la mascota");
        } else {
            setTitle("Foto de " + titulo);
        }

        Glide.with(this).load(foto).into(imageView);



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
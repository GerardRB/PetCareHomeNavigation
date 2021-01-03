package com.example.petcarehome.homenavigation.ui.difusion;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.petcarehome.R;

public class FullScreenImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        //Recibir reporte Seleccionado
        Bundle bundleRecibido = getIntent().getExtras();
        String titulo = null;
        String foto = null;
        if (bundleRecibido != null){
            titulo = bundleRecibido.getString("title");
            foto = bundleRecibido.getString("foto");
        }

        ImageView imageView = findViewById(R.id.id_image_reporte);

        if (titulo.isEmpty()){
            setTitle("Foto de la mascota");
        } else {
            setTitle("Foto de " + titulo);
        }

        if (foto.isEmpty()){
            Glide.with(this).load(R.drawable.ic_gallery_error).into(imageView);
        } else {
            Glide.with(this).load(foto).into(imageView);
        }




    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
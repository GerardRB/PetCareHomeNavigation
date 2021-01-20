package com.example.petcarehome.homenavigation.ui.petfriendly.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FotoGaleriaActivity extends AppCompatActivity {
    private static final String TAG = FotoGaleriaActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_galeria);
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.black));

        Bundle extras = getIntent().getExtras();
        String imagen = null;
        String key = null;

        StorageReference mStorage = FirebaseStorage.getInstance().getReference();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        ImageView imagenView = findViewById(R.id.imagen_galeria);
        if (extras != null) {
            imagen = extras.getString("imagen");
            key = extras.getString("key");
        }

        if (key != null && imagen != null) {
            Log.d(TAG, "Cargando foto (Galeria): " + key + "/" + imagen);
            StorageReference ref = mStorage
                    .child(FirebaseReferences.STORAGE_GALERIA_LUGAR)
                    .child(key)
                    .child(imagen);

            Glide.with(this)
                    .load(ref)
                    .into(imagenView);
        }
    }
}
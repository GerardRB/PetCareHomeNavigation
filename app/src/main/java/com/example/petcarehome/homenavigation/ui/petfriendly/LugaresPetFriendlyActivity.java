package com.example.petcarehome.homenavigation.ui.petfriendly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.CategoriaLugar;
import com.example.petcarehome.homenavigation.ui.petfriendly.adaptadores.LugaresAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class LugaresPetFriendlyActivity extends AppCompatActivity {
    private LugaresAdapter adaptador;
    private CategoriaLugar mCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugares_pet_friendly);
        if (getIntent().getExtras() != null) {
            mCategoria = (CategoriaLugar) getIntent().getExtras().getSerializable("categoria");
        }

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        StorageReference storage = FirebaseStorage.getInstance().getReference();
        adaptador = new LugaresAdapter(this, database, storage, mCategoria);

        RecyclerView grid = findViewById(R.id.grid_petfriendly);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        grid.setLayoutManager(gridLayoutManager);
        grid.setAdapter(adaptador);
        adaptador.conectar();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LugaresPetFriendlyActivity.this, AgregarPetfriendlyActivity.class);
                intent.putExtra("categoria", mCategoria);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        adaptador.desconectar();
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }
}
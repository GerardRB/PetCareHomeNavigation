package com.example.petcarehome.homenavigation.ui.petfriendly.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.CategoriaLugar;
import com.example.petcarehome.homenavigation.ui.petfriendly.adapters.LugaresAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class LugaresPetFriendlyActivity extends AppCompatActivity {
    private static final String TAG = "LugaresPetFirendly";
    private LugaresAdapter adaptador;
    private CategoriaLugar mCategoria;
    private EditText mTextoBusqueda;

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

        ExtendedFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LugaresPetFriendlyActivity.this, AgregarPetfriendlyActivity.class);
                intent.putExtra("categoria", mCategoria);
                startActivity(intent);
            }
        });

        mTextoBusqueda = findViewById(R.id.texto_busqueda);
        mTextoBusqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adaptador.setFiltro(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(mCategoria.getNombre());
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lugares_pet_friendly_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_back) {
            finish();
            return true;
        } else if (id == R.id.action_search) {
            if (mTextoBusqueda.getVisibility() == View.GONE) {
                mTextoBusqueda.setVisibility(View.VISIBLE);
            } else {
                mTextoBusqueda.setVisibility(View.GONE);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
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
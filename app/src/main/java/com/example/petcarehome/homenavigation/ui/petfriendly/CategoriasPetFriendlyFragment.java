package com.example.petcarehome.homenavigation.ui.petfriendly;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.ui.petfriendly.adaptadores.CategoriasAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class CategoriasPetFriendlyFragment extends Fragment {
    public CategoriasPetFriendlyFragment() {
    }

    public static CategoriasPetFriendlyFragment newInstance() {
        return new CategoriasPetFriendlyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorias_pet_friendly, container, false);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        StorageReference storage = FirebaseStorage.getInstance().getReference();
        CategoriasAdapter adaptador = new CategoriasAdapter(getContext(), database, storage);

        RecyclerView grid = view.findViewById(R.id.grid_categorias);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        grid.setLayoutManager(gridLayoutManager);
        grid.setAdapter(adaptador);
        adaptador.conectar();

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AgregarCategoriaActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
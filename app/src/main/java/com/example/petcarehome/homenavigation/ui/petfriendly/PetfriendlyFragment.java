package com.example.petcarehome.homenavigation.ui.petfriendly;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.petcarehome.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PetfriendlyFragment extends Fragment {
    private LugaresAdapter adaptador;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        StorageReference storage = FirebaseStorage.getInstance().getReference();
        adaptador = new LugaresAdapter(getContext(), database, storage);
        return inflater.inflate(R.layout.fragment_petfriendly, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView grid = view.findViewById(R.id.grid_petfriendly);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        grid.setLayoutManager(gridLayoutManager);
        grid.setAdapter(adaptador);
        adaptador.conectar();

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PetfriendlyFragment.this.getContext(), AgregarPetfriendlyActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adaptador.desconectar();
    }
}

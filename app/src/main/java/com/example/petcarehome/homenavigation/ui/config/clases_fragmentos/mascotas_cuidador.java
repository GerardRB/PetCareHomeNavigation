package com.example.petcarehome.homenavigation.ui.config.clases_fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.ui.difusion.perdidas.GenerarReporteExtravioActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class mascotas_cuidador extends Fragment {
    FloatingActionButton agregarMasco;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mascotas_cuidador_fragment, container,false);
        //Clase para el fragmento de mascotas para el cuidador
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        agregarMasco = view.findViewById(R.id.agregar_mascota);
        agregarMasco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFormularioMascotas = new Intent(getContext(), FormularioMascotas.class);
                startActivity(intentFormularioMascotas);
            }
        });

    }
}

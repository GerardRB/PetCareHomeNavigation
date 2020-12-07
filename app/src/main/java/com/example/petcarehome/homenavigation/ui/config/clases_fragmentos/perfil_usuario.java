package com.example.petcarehome.homenavigation.ui.config.clases_fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.petcarehome.R;

public class perfil_usuario extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.perfil_fragment, container,false);
        //Clase para el fragmento del perfil - fragmento principal del apartado de configuración
    }
}

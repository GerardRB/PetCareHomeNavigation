package com.example.petcarehome.homenavigation.ui.config.clases_fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.petcarehome.R;

public class terminos_y_condiciones extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.terminos_y_condiciones_fragment, container,false);
        //Clase para el fragmento de términos y condiciones
    }
}

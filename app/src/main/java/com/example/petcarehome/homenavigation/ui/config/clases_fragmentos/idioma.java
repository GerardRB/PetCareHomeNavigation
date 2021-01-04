package com.example.petcarehome.homenavigation.ui.config.clases_fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.petcarehome.R;

public class idioma extends Fragment {
    Button buttonid;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.idioma_fragment, container,false);
        //Clase para el fragmento de idioma


    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonid = view.findViewById(R.id.idioma_esp);
        buttonid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Espera nuevos idiomas :)", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.example.petcarehome.homenavigation.ui.petfriendly.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.LugarPetFriendly;
import com.example.petcarehome.homenavigation.ui.petfriendly.activities.AgregarPetfriendlyActivity;
import com.example.petcarehome.homenavigation.ui.petfriendly.activities.AgregarResenaActivity;
import com.example.petcarehome.homenavigation.ui.petfriendly.activities.LugaresPetFriendlyActivity;
import com.example.petcarehome.homenavigation.ui.petfriendly.adapters.ResenaAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class ResenasLugarFragment extends Fragment {
    private static final String ARG_LUGAR = "lugar";
    private LugarPetFriendly mLugar;

    public ResenasLugarFragment() {
    }

    public static ResenasLugarFragment newInstance(LugarPetFriendly lugar) {
        ResenasLugarFragment fragment = new ResenasLugarFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LUGAR, lugar);
        fragment.setArguments(args);
        return fragment;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLugar = (LugarPetFriendly) getArguments().getSerializable(ARG_LUGAR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resenas_lugar, container, false);
        ListView lista = view.findViewById(R.id.resenas_lista);

        ArrayList<LugarPetFriendly.Resena> resenas = mLugar.getResenas();
        Collections.sort(resenas);

        ResenaAdapter adapter = new ResenaAdapter(resenas, getContext());
        lista.setAdapter(adapter);

        ExtendedFloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AgregarResenaActivity.class);
                intent.putExtra("lugar", mLugar);
                startActivity(intent);
            }
        });

        return view;
    }
}
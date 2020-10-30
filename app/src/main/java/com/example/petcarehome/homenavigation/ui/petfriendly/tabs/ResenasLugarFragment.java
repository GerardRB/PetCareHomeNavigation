package com.example.petcarehome.homenavigation.ui.petfriendly.tabs;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.LugarPetFriendly;
import com.example.petcarehome.homenavigation.ui.petfriendly.adaptadores.ResenaAdapter;

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
        ResenaAdapter adapter = new ResenaAdapter(mLugar.getResenas(), getContext());
        lista.setAdapter(adapter);
        return view;
    }
}
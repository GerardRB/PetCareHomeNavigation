package com.example.petcarehome.homenavigation.ui.petfriendly.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.Objetos.LugarPetFriendly;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DescripcionLugarFragment extends Fragment {
    private static final String ARG_LUGAR = "lugar";
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private LugarPetFriendly mLugar;
    private RatingBar mRatingBar;

    public DescripcionLugarFragment() {
    }

    public static DescripcionLugarFragment newInstance(LugarPetFriendly lugar) {
        DescripcionLugarFragment fragment = new DescripcionLugarFragment();
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

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_descripcion_lugar, container, false);
        mRatingBar = view.findViewById(R.id.detalles_rating);
        mRatingBar.setRating(mLugar.getEstrellas());

        ImageView imagenView = view.findViewById(R.id.detalles_imagen);
        if (mLugar.getFoto() != null) {
            StorageReference ref = mStorage.child(FirebaseReferences.STORAGE_FOTO_LUGAR_PETFRIENDLY)
                    .child(mLugar.getId())
                    .child(mLugar.getFoto());
            Glide.with(getContext())
                    .load(ref)
                    .into(imagenView);
        }

        TextView nombre = view.findViewById(R.id.detalles_nombre);
        nombre.setText(mLugar.getNombre());

        TextView descripcion = view.findViewById(R.id.detalles_descripcion);
        descripcion.setText(mLugar.getDescripcion());

        TextView tvDireccion = view.findViewById(R.id.detalles_direccion);
        String sDireccion = mLugar.getDireccion();
        if (sDireccion != null && !sDireccion.isEmpty()) {
            tvDireccion.setText(sDireccion);
        }

        Button botonBorrar = view.findViewById(R.id.detalles_boton_borrar);
        botonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child(FirebaseReferences.LUGARES_PET_FRIENDLY_REFERENCE)
                        .child(mLugar.getCategoria())
                        .child("lugares")
                        .child(mLugar.getId())
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        getActivity().finish();
                    }
                });
            }
        });
        return view;
    }
}
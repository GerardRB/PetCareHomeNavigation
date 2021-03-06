package com.example.petcarehome.homenavigation.ui.petfriendly.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

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

        TextView estrellas = view.findViewById(R.id.detalles_rating_numero);
        estrellas.setText(String.format("Promedio: %d/5", mLugar.getEstrellas()));

        MapView map = view.findViewById(R.id.map);
        map.setVisibility(View.GONE);
        String sLatLng = mLugar.getLatlng();
        Log.d(DescripcionLugarFragment.class.getCanonicalName(), "LATLNG: " + sLatLng);
        if (sLatLng != null && !sLatLng.isEmpty()) {
            try {
                String[] values = sLatLng.split(",");
                final CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(Float.parseFloat(values[0]), Float.parseFloat(values[1])))
                        .zoom(15).build();

                Bundle mapViewBundle = null;
                if (savedInstanceState != null) {
                    mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
                }

                map.onCreate(mapViewBundle);
                map.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(final GoogleMap googleMap) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(googleMap.getCameraPosition().target);
                        final Marker markerCenter = googleMap.addMarker(markerOptions);
                        googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                            public void onCameraMove() {
                                markerCenter.setPosition(googleMap.getCameraPosition().target);
                            }
                        });

                        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
                    }
                });

                map.setVisibility(View.VISIBLE);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
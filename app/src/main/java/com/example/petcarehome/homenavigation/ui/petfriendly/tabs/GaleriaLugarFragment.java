package com.example.petcarehome.homenavigation.ui.petfriendly.tabs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.Convertir;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.Objetos.LugarPetFriendly;
import com.example.petcarehome.homenavigation.ui.petfriendly.adaptadores.GaleriaAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class GaleriaLugarFragment extends Fragment {
    private static final String ARG_LUGAR = "lugar";
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final String TAG = "GaleriaLugarFragment";
    private GaleriaAdapter adaptador;
    private LugarPetFriendly mLugar;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private Dialog mDialog;

    public GaleriaLugarFragment() {
    }

    public static GaleriaLugarFragment newInstance(LugarPetFriendly lugar) {
        GaleriaLugarFragment fragment = new GaleriaLugarFragment();
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

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();
        adaptador = new GaleriaAdapter(getContext(), mDatabase, mStorage, mLugar);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adaptador != null) {
            adaptador.desconectar();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_galeria_lugar, container, false);
    }

    private void dispatchTakePictureIntent() {
        try {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "No es posible agregar la foto", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            mDialog = ProgressDialog.show(getContext(), "",
                    "Guardando imagen", true);

            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            final String nombreArchivo = UUID.randomUUID().toString();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapData = bos.toByteArray();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bitmapData);
            StorageReference fotoRef = mStorage
                    .child(FirebaseReferences.STORAGE_GALERIA_LUGAR)
                    .child(mLugar.getId())
                    .child(nombreArchivo);

            UploadTask uploadTask = fotoRef.putStream(inputStream);
            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    mDialog.dismiss();

                    if (task.isSuccessful()) {
                        Log.d(TAG, "Imagen subida: " + nombreArchivo);

                        if (mLugar.getFotosGaleria() == null) {
                            mLugar.setFotosGaleria(new ArrayList<String>());
                        }

                        mLugar.getFotosGaleria().add(nombreArchivo);
                        try {
                            mDatabase.child(FirebaseReferences.LUGARES_PET_FRIENDLY_REFERENCE)
                                    .child(mLugar.getCategoria())
                                    .child("lugares")
                                    .child(mLugar.getId())
                                    .updateChildren(Convertir.aMapa(mLugar));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "No es actualizar el lugar", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        mDialog = new AlertDialog.Builder(getContext())
                                .setMessage("No se ha podido subir la foto para el lugar, por favor intente más tarde")
                                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        mDialog.dismiss();
                                    }
                                }).create();
                    }
                }
            });
        } else if (resultCode != RESULT_OK) {
            Toast.makeText(getContext(), "No es posible agregar la foto", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView grid = view.findViewById(R.id.grid_galeria);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        grid.setLayoutManager(gridLayoutManager);
        grid.setAdapter(adaptador);
        adaptador.conectar();

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
    }
}
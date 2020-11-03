package com.example.petcarehome.homenavigation.ui.petfriendly.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.CategoriaLugar;
import com.example.petcarehome.homenavigation.Objetos.Convertir;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.Objetos.LugarPetFriendly;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class AgregarPetfriendlyActivity extends AppCompatActivity {
    private static final String TAG = "AregarPetfriendly";
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private CategoriaLugar mCategoria;
    private Button mBotonAgregar;
    private Button mBotonFoto;
    private Dialog mDialog;
    private ByteArrayInputStream mInputStream;
    private DatabaseReference mLugaresPetfriendlyRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_petfriendly);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            mCategoria = (CategoriaLugar) intent.getExtras().getSerializable("categoria");
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();
        mLugaresPetfriendlyRef = mDatabase.child(FirebaseReferences.LUGARES_PET_FRIENDLY_REFERENCE)
                .child(mCategoria.getId())
                .child("lugares");

        mBotonFoto = findViewById(R.id.button_tomar_foto_lugar_petfriendly);
        mBotonFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        mBotonAgregar = findViewById(R.id.button_agregar_lugar);
        mBotonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog = ProgressDialog.show(AgregarPetfriendlyActivity.this, "",
                        "Guardando lugar", true);

                EditText entradaNombre = findViewById(R.id.edit_text_nombre_lugar);
                EditText entradaDescripcion = findViewById(R.id.edit_text_descripcion_lugar);
                RatingBar entradaEstrellas = findViewById(R.id.rating_resena);
                subirFotoOGuardarLugar(entradaNombre.getText().toString(),
                        entradaDescripcion.getText().toString(),
                        (int) entradaEstrellas.getRating(),
                        mInputStream);
            }
        });
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        try {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mBotonAgregar.setEnabled(false);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(AgregarPetfriendlyActivity.this, "No es posible agregar la foto", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            mBotonAgregar.setEnabled(true);
            mBotonFoto.setText(getText(R.string.label_cambiar_foto_lugar));

            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapData = bos.toByteArray();
            mInputStream = new ByteArrayInputStream(bitmapData);
        } else if (resultCode != RESULT_OK) {
            mBotonAgregar.setEnabled(true);
            Toast.makeText(AgregarPetfriendlyActivity.this, "No es posible agregar la foto", Toast.LENGTH_LONG).show();
        }
    }

    private void guardarLugar(DatabaseReference ref, LugarPetFriendly lugar) {
        try {
            ref.updateChildren(Convertir.aMapa(lugar), new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Log.e(TAG, databaseError.getMessage());
                        mDialog.dismiss();
                        mDialog = new AlertDialog.Builder(getBaseContext())
                                .setMessage("No se ha podido crear el lugar, por favor intente más tarde")
                                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        mDialog.dismiss();
                                    }
                                }).create();
                    } else {
                        Log.d(TAG, "Lugar agregado!");
                        AgregarPetfriendlyActivity.this.finish();
                    }
                }
            });
        } catch (IllegalAccessException e) {
            Log.e(TAG, e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.toString());
            Toast.makeText(AgregarPetfriendlyActivity.this, "No se ha podido crear el lugar", Toast.LENGTH_LONG).show();
        } finally {
            mDialog.dismiss();
        }
    }

    private void subirFotoOGuardarLugar(String nombre, String descripcion, int estrellas, InputStream streamFoto) {
        final DatabaseReference ref = mLugaresPetfriendlyRef.push();
        final LugarPetFriendly lugar = new LugarPetFriendly(ref.getKey());
        Log.d(TAG, "Agregando lugar " + lugar.getId());
        lugar.setNombre(nombre);
        lugar.setDescripcion(descripcion);
        lugar.setEstrellas(estrellas);
        lugar.setCategoria(mCategoria.getId());
        lugar.getResenas().add(new LugarPetFriendly.Resena(estrellas, descripcion, "Usuario PetFriendly"));

        if (streamFoto != null) {
            String archivoFoto = lugar.getId() + ".jpeg";
            StorageReference fotoRef = mStorage.child(FirebaseReferences.STORAGE_FOTO_LUGAR_PETFRIENDLY).child(archivoFoto);
            lugar.setFoto(archivoFoto);

            UploadTask uploadTask = fotoRef.putStream(streamFoto);
            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        guardarLugar(ref, lugar);
                    } else {
                        mDialog = new AlertDialog.Builder(getBaseContext())
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
        } else {
            guardarLugar(ref, lugar);
        }
    }
}
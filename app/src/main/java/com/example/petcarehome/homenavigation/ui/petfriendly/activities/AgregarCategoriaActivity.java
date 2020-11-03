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
import android.widget.Toast;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.CategoriaLugar;
import com.example.petcarehome.homenavigation.Objetos.Convertir;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
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

public class AgregarCategoriaActivity extends AppCompatActivity {
    private static final String TAG = "AregarCategoria";
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private Button mBotonAgregar;
    private Button mBotonFoto;
    private Dialog mDialog;
    private ByteArrayInputStream mInputStream;
    private DatabaseReference mCategoriasPetfriendlyRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_categoria);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();
        mCategoriasPetfriendlyRef = mDatabase.child(FirebaseReferences.LUGARES_PET_FRIENDLY_REFERENCE);

        mBotonFoto = findViewById(R.id.boton_agregar_foto);
        mBotonFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        mBotonAgregar = findViewById(R.id.boton_agregar);
        mBotonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog = ProgressDialog.show(AgregarCategoriaActivity.this, "",
                        "Guardando categoría", true);

                EditText entradaNombre = findViewById(R.id.edit_text_nombre);
                subirFotoOGuardarCategoria(entradaNombre.getText().toString(),  mInputStream);
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
            Toast.makeText(this, "No es posible agregar la foto", Toast.LENGTH_LONG).show();
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
            Toast.makeText(this, "No es posible agregar la foto", Toast.LENGTH_LONG).show();
        }
    }

    private void guardarCategoria(DatabaseReference ref, CategoriaLugar categoria) {
        try {
            ref.updateChildren(Convertir.aMapa(categoria), new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Log.e(TAG, databaseError.getMessage());
                        mDialog.dismiss();
                        mDialog = new AlertDialog.Builder(getBaseContext())
                                .setMessage("No se ha podido crear la categoria, por favor intente más tarde")
                                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        mDialog.dismiss();
                                    }
                                }).create();
                    } else {
                        Log.d(TAG, "Categoría agregada!");
                        finish();
                    }
                }
            });
        } catch (IllegalAccessException e) {
            Log.e(TAG, e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.toString());
            Toast.makeText(this, "No se ha podido crear la categoria", Toast.LENGTH_LONG).show();
        } finally {
            mDialog.dismiss();
        }
    }

    private void subirFotoOGuardarCategoria(String nombre, InputStream streamFoto) {
        final DatabaseReference ref = mCategoriasPetfriendlyRef.push();
        final CategoriaLugar categoria = new CategoriaLugar(ref.getKey());
        Log.d(TAG, "Agregando categoria " + categoria.getId());
        categoria.setNombre(nombre);

        if (streamFoto != null) {
            String archivoFoto = categoria.getId() + ".jpeg";
            StorageReference fotoRef = mStorage.child(FirebaseReferences.STORAGE_FOTO_LUGAR_PETFRIENDLY).child(archivoFoto);
            categoria.setFoto(archivoFoto);

            UploadTask uploadTask = fotoRef.putStream(streamFoto);
            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        guardarCategoria(ref, categoria);
                    } else {
                        mDialog = new AlertDialog.Builder(getBaseContext())
                                .setMessage("No se ha podido subir la foto para la categoría, por favor intente más tarde")
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
            guardarCategoria(ref, categoria);
        }
    }
}
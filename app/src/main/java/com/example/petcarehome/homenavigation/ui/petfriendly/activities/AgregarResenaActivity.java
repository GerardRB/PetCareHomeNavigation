package com.example.petcarehome.homenavigation.ui.petfriendly.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.Convertir;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.Objetos.LugarPetFriendly;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AgregarResenaActivity extends AppCompatActivity {
    private static final String TAG = AgregarResenaActivity.class.getCanonicalName();
    private LugarPetFriendly mLugar;
    private Dialog mDialog;
    private Button mBotonAgregar;
    private EditText mEditTextComentario;
    private RatingBar mRatingResena;

    private DatabaseReference mDatabase;
    private DatabaseReference mLugaresRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_resena);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            mLugar = (LugarPetFriendly) intent.getExtras().getSerializable("lugar");
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mLugaresRef = mDatabase.child(FirebaseReferences.LUGARES_PET_FRIENDLY_REFERENCE)
                .child(mLugar.getCategoria())
                .child("lugares")
                .child(mLugar.getId());

        mEditTextComentario = findViewById(R.id.edit_text_comentario);
        mRatingResena = findViewById(R.id.rating_resena);

        mBotonAgregar = findViewById(R.id.boton_agregar);
        mBotonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comentario = mEditTextComentario.getText().toString();
                Integer estrellas = mRatingResena.getNumStars();

                if (comentario.isEmpty()) {
                    mDialog = new AlertDialog.Builder(AgregarResenaActivity.this)
                            .setMessage("Debe escribir un comentario")
                            .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mDialog.dismiss();
                                }
                            }).show();
                    return;
                }

                mDialog = ProgressDialog.show(AgregarResenaActivity.this, "",
                        "Guardando reseña", true);

                if (mLugar.getResenas() == null)
                    mLugar.setResenas(new ArrayList<LugarPetFriendly.Resena>());

                mLugar.getResenas().add(new LugarPetFriendly.Resena(estrellas, comentario, "Usuario PetFriendly"));
                agregarResena(mLugar);
            }
        });
    }

    public void agregarResena(LugarPetFriendly lugar) {
        try {
            mLugaresRef.setValue(Convertir.aMapa(lugar), new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Log.e(TAG, databaseError.getMessage());
                        mDialog.dismiss();
                        mDialog = new AlertDialog.Builder(getBaseContext())
                                .setMessage("No se ha podido crear la reseña, por favor intente más tarde")
                                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        mDialog.dismiss();
                                    }
                                }).create();
                    } else {
                        Log.d(TAG, "Reseña agregada!");
                        AgregarResenaActivity.this.finish();
                    }
                }
            });
        } catch (IllegalAccessException e) {
            Log.e(TAG, e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.toString());
            Toast.makeText(AgregarResenaActivity.this, "No se ha podido crear la reseña", Toast.LENGTH_LONG).show();
        } finally {
            mDialog.dismiss();
        }
    }
}
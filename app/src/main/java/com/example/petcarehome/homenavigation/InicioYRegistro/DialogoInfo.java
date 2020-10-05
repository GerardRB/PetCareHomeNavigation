package com.example.petcarehome.homenavigation.InicioYRegistro;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogoInfo extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //final Dialog dialog = new Dialog();
        builder.setTitle("¿PetCare?")
                .setMessage("PetCare es una aplicación móvil que brinda el medio para la búsqueda de un servicio de cuidado para mascotas. " +
                        "¡Aquí podrás encontrar a tu cuidador ideal! :) ")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        return builder.create();
    }
}
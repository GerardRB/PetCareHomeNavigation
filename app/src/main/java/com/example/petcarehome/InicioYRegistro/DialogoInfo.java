package com.example.petcarehome.InicioYRegistro;

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
        builder.setTitle("¿Quiénes somos ?")
                .setMessage("PetCare es una app que permite a los usuarios interactuar con un entorno 100% dedicado hacia las mascotas y que brinda un medio para llevar a cabo la búsqueda de cuidadores de mascotas, la difusión de reportes de extravío y adopción de mascotas y la visualización de diversos lugares “Pet Friendly”.\n")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        return builder.create();
    }
}
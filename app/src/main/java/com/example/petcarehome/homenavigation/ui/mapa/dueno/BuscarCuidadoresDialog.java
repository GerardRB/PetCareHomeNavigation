package com.example.petcarehome.homenavigation.ui.mapa.dueno;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.petcarehome.R;

public class BuscarCuidadoresDialog extends AppCompatDialogFragment {
    private EditText editTextUbicacion;
    private Spinner spinnerMascota, spinnerCuidado;
    private SeekBar seekBar;
    private TextView textViewKm;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_buscar_cuidadores, null);

        editTextUbicacion = view.findViewById(R.id.input_location);


        spinnerMascota = view.findViewById(R.id.dialog_tipo_mascota);
        ArrayAdapter<CharSequence> adapterTipoMascota = ArrayAdapter.createFromResource(getContext(), R.array.combo_tipoRMP, android.R.layout.simple_spinner_item);
        spinnerMascota.setAdapter(adapterTipoMascota);

        spinnerCuidado = view.findViewById(R.id.dialog_tipo_cuidado);
        ArrayAdapter<CharSequence> adapterTipoCuidado = ArrayAdapter.createFromResource(getContext(), R.array.combo_cuidados, android.R.layout.simple_spinner_item);
        spinnerCuidado.setAdapter(adapterTipoCuidado);

        seekBar = view.findViewById(R.id.dialog_seekBar);
        textViewKm = view.findViewById(R.id.text_dialog_distancia);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int distancia = progress / 10;
                textViewKm.setText(distancia + " Km");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        builder.setView(view)
                .setTitle("Buscar un cuidador")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                })
                .setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(),"Buscando...", Toast.LENGTH_LONG).show();
                    }
                });


        AlertDialog alert = builder.create();
        return alert;

    }
}

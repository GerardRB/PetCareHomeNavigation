package com.example.petcarehome.homenavigation.ui.mapa.dueno;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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
import androidx.core.app.ActivityCompat;

import com.example.petcarehome.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class BuscarCuidadoresDialog extends AppCompatDialogFragment {
    private Spinner spinnerMascota, spinnerCuidado;
    private SeekBar seekBar;
    private TextView textViewKm, textViewUbicacion;

    private FusedLocationProviderClient fusedLocationClient;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_buscar_cuidadores, null);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        textViewUbicacion = view.findViewById(R.id.text_location);
        getAddress();


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
                        Toast.makeText(getContext(), "Buscando...", Toast.LENGTH_LONG).show();
                    }
                });


        AlertDialog alert = builder.create();
        return alert;

    }

    private void getAddress() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        final Task<Location> task = fusedLocationClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                //Cuando se logra obtener la ubicación
                if(location != null) {
                    //updateLocationFB(new LatLng(location.getLatitude(), location.getLongitude()));
                    try {
                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        if (!addressList.isEmpty()){
                            Address address = addressList.get(0);
                            String currentAddress = address.getAddressLine(0);
                            textViewUbicacion.setText(currentAddress);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}

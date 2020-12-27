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
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.example.petcarehome.homenavigation.Objetos.Busqueda;
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
    private TextView textViewdist, textViewUbicacion, textViewKm;
    private CheckBox checkMascota, checkCuidado, checkDistancia;

    private FusedLocationProviderClient fusedLocationClient;

    private double lat, lng;

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
        spinnerMascota.setVisibility(View.GONE);

        spinnerCuidado = view.findViewById(R.id.dialog_tipo_cuidado);
        ArrayAdapter<CharSequence> adapterTipoCuidado = ArrayAdapter.createFromResource(getContext(), R.array.combo_cuidados, android.R.layout.simple_spinner_item);
        spinnerCuidado.setAdapter(adapterTipoCuidado);
        spinnerCuidado.setVisibility(View.GONE);


        seekBar = view.findViewById(R.id.dialog_seekBar);
        seekBar.setVisibility(View.GONE);
        textViewdist = view.findViewById(R.id.text_dialog_distancia);
        textViewdist.setVisibility(View.GONE);
        textViewKm = view.findViewById(R.id.label_text_km);
        textViewKm.setVisibility(View.GONE);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int distancia = progress / 5;
                textViewdist.setText(String.valueOf(distancia));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        checkMascota = view.findViewById(R.id.checkBox_tipo_mascota_busqueda);
        checkMascota.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == false){
                    spinnerMascota.setVisibility(View.GONE);
                } else {
                    spinnerMascota.setVisibility(View.VISIBLE);
                }
            }
        });

        checkCuidado = view.findViewById(R.id.checkBox_tipo_servicio_busqueda);
        checkCuidado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == false){
                    spinnerCuidado.setVisibility(View.GONE);
                } else {
                    spinnerCuidado.setVisibility(View.VISIBLE);
                }
            }
        });

        checkDistancia = view.findViewById(R.id.checkBox_distancia_busqueda);
        checkDistancia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == false){
                    textViewdist.setVisibility(View.GONE);
                    textViewKm.setVisibility(View.GONE);
                    seekBar.setVisibility(View.GONE);
                } else {
                    textViewdist.setVisibility(View.VISIBLE);
                    textViewKm.setVisibility(View.VISIBLE);
                    seekBar.setVisibility(View.VISIBLE);
                }
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
                        iniciarBusqueda();
                    }
                });


        AlertDialog alert = builder.create();
        return alert;

    }

    private void iniciarBusqueda() {
        String cad = "Iniciando búsqueda...";
        String error = "Faltan campos por ingresar.";
        String tipoM, tipoS;
        int dist;
        LatLng ubicacion;
        boolean e = false;

        Busqueda busqueda = new Busqueda();

        tipoM = spinnerMascota.getSelectedItem().toString();
        tipoS = spinnerCuidado.getSelectedItem().toString();
        dist = Integer.parseInt(textViewdist.getText().toString());
        ubicacion = new LatLng(lat, lng);

        if (checkMascota.isChecked() || checkCuidado.isChecked() || checkDistancia.isChecked()){

            if (checkMascota.isChecked()){
                if (tipoM.equals("Seleccionar")){
                    error = error + "\n -Seleccione un tipo de mascota";
                    e = true;
                }else {
                    busqueda.setTipoMascota(tipoM);
                    cad = cad + "\n -TIPO DE MASCOTA: " + tipoM;
                }
            }

            if (checkCuidado.isChecked()){
                if (tipoS.equals("Seleccionar")){
                    error = error + "\n -Seleccione un tipo de servicio";
                    e = true;
                } else {
                    busqueda.setTipoServicio(tipoS);
                    cad = cad + "\n -TIPO DE SERVICIO: " + tipoS;
                }
            }

            if (checkDistancia.isChecked()){
                if (dist == 0){
                    error = error + "\n -Seleccione una distancia mayor a 0";
                    e = true;
                } else {
                    busqueda.setDistancia(dist);
                    cad = cad + "\n -DISTANCIA MAX: " + dist + "KM";
                }
            }

            busqueda.setUbicacion(ubicacion);
            cad = cad + "\n -LAT: " + ubicacion.latitude + "\n -LNG: " + ubicacion.longitude;

            if (e == true){
                AlertDialog.Builder alerta = new AlertDialog.Builder(this.getContext());
                alerta.setMessage(error + "\n Por favor intente de nuevo.").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog errorFltr = alerta.create();
                errorFltr.setTitle("Error");
                errorFltr.show();
            } else {
                //Caso exitoso(Debe generar el filtro)
                Toast.makeText(getContext(), cad, Toast.LENGTH_LONG).show();
                dismiss();
            }

        } else {
            Toast.makeText(getContext(), "No realizó ninguna búsqueda", Toast.LENGTH_LONG).show();
            dismiss();
        }

        //Toast.makeText(getContext(), "Buscando...", Toast.LENGTH_LONG).show();

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
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    try {
                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                        List<Address> addressList = geocoder.getFromLocation(lat, lng, 1);
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

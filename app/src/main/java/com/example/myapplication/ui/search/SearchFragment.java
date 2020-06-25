package com.example.myapplication.ui.search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.myapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class SearchFragment extends Fragment implements View.OnClickListener{

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private boolean requestingLocationUpdates;
    private LocationRequest locationRequest;
    private ExtendedFloatingActionButton fabbusqueda, fablocation;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng cdmx = new LatLng(19.515626, -99.140994);
            googleMap.addMarker(new MarkerOptions().position(cdmx).title("CDMX"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(cdmx));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cdmx,15));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //mapfragment
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        //Botones flotates referencias y escuchador
        fabbusqueda = view.findViewById(R.id.fab_busqueda);
        fablocation = view.findViewById(R.id.fab_location);

        fabbusqueda.setOnClickListener(this);
        fablocation.setOnClickListener(this);

        /*Boton estado cuidador
        final ToggleButton switchEstado = root.findViewById(R.id.switch1);
        switchEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchEstado.isChecked()){
                    Toast.makeText(getContext(),"Estado: Inctivo", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(),"Estado: Activo", Toast.LENGTH_LONG).show();
                }
            }
        });*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_busqueda:
                openDialog();
                break;
            case R.id.fab_location:
                //getCurrentLocation();
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

                //Permiso para la ubicación
                /* DUDA: Una vez que accedes al permiso imprime Actualizando ubicacion,
                pero al momento de preguntar y confirmar, imprime el mensaje de negativa y para que actualice la ubicacion es necesario volver a apretar el boton*/
                if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    //Cuando el permiso se dio, llamar al metodo
                    Toast.makeText(getContext(),"Actualizando ubicación", Toast.LENGTH_LONG).show();
                    //getCurrentLocation();
                    break;
                } else{
                    //Cuando el permiso no se dio, pedir permiso
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                    /*Dependiendo de la respuesta al permiso:
                    1.- Permite: Imprime Actualizando ubicacion y ejecuta getcurrentLocation
                    2.- Rechaza: Imprime Debes conceder el permiso para acceder a tu ubicación actual
                    */
                    //Toast.makeText(getContext(),"Debes conceder el permiso para acceder a tu ubicación actual", Toast.LENGTH_LONG).show();
                    break;
                }
        }
    }

    private void openDialog() {
        SearchDialog searchDialog = new SearchDialog();
        searchDialog.show(getChildFragmentManager(), "Buscar un cuidador");
    }


    /*private void getCurrentLocation() {
        Task<Location> task = fusedLocationClient.getLastLocation();*/
/*
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    */

        /*task.addOnSuccessListener(new OnSuccessListener<Location>() {

            @Override
            public void onSuccess(final Location location) {
                //Cuando se logra obtener la ubicación
                if(location != null){
                    //Sincronizar el mapa
                    SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(new OnMapReadyCallback() {

                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            //Inicializar latitud y longitud
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            //Crear opciones para el marcador
                            MarkerOptions options = new MarkerOptions().position(latLng)
                                    .title("Mi ubicación");
                            //Zoom en el mapa
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                            //Añadir marcador en el mapa
                            googleMap.addMarker(options);
                        }
                    });
                }

            }

        });
    }*/

}

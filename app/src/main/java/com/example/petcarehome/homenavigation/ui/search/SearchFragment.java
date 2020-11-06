package com.example.petcarehome.homenavigation.ui.search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.petcarehome.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class SearchFragment extends Fragment implements View.OnClickListener{

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private boolean requestingLocationUpdates, mLocationPermissionGranted;
    private LocationRequest locationRequest;
    private ExtendedFloatingActionButton fabbusqueda, fablocation;
    private GoogleMap mMap;
    private Location  mLastKnownLocation;

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
            LatLng cdmx = new LatLng(19.504803, -99.146900);
            //googleMap.addMarker(new MarkerOptions().position(cdmx).title("CDMX"));
            mMap = googleMap;
            mMap.moveCamera(CameraUpdateFactory.newLatLng(cdmx));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cdmx,15));

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
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
                getLocationPermission();

        }
    }

    private void openDialog() {
        SearchDialog searchDialog = new SearchDialog();
        searchDialog.show(getChildFragmentManager(), "Buscar un cuidador");
    }


    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ActivityCompat.checkSelfPermission(this.getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            //getDeviceLocation();
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    44);
        }
    }

    /*private void getDeviceLocation() {
        //Get the best and most recent location of the device, which may be null in rare cases when a location is not available.

        try {
            if (mLocationPermissionGranted) {
                Task locationResult = fusedLocationClient.getLastLocation();
                locationResult.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            Toast.makeText(getContext(),"Actualizando ubicación", Toast.LENGTH_LONG).show();
                            mLastKnownLocation = (Location) task.getResult();
                            LatLng current = new LatLng(mLastKnownLocation.getLatitude(),
                                    mLastKnownLocation.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(current).title("Mi ubicación"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current,15));

                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(19.504803, -99.146900), 15));
                        }
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }*/


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this.getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            //getDeviceLocation();
            //getCurrentLocation();
            //Inicializar la tarea de ubicación.
            Task<Location> task = fusedLocationClient.getLastLocation();

            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(final Location location) {
                    //Cuando se logra obtener la ubicación
                    if(location != null){

                        //Inicializar latitud y longitud
                        LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
                        //Crear opciones para el marcador
                        MarkerOptions options = new MarkerOptions().position(current)
                                .title("Mi ubicación").icon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_marcador_de_posicion)).snippet("Ubicación actual");
                        //Zoom en el mapa
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 15));
                        //Añadir marcador en el mapa
                        mMap.addMarker(options);
                    }
                }

            });
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    44);
        }

    }

}

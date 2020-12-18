package com.example.petcarehome.homenavigation.ui.mapa.cuidador;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.petcarehome.InicioYRegistro.Cuidador;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapaFragmentCuidador extends Fragment implements View.OnClickListener{

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private boolean requestingLocationUpdates, mLocationPermissionGranted;
    private LocationRequest locationRequest;
    private ExtendedFloatingActionButton fablocation;
    private SwitchCompat estadoButton;
    private GoogleMap mMap;
    private Location mLastKnownLocation;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference cuidadorRef;
    private FirebaseUser firebaseUser;
    private String idCuidador;


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
        View root = inflater.inflate(R.layout.fragment_mapa_cuidador, container, false);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_cuidador);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }


        //Botones flotates referencias y escuchador
        fablocation = view.findViewById(R.id.fab_location_cuidador);
        fablocation.setOnClickListener(this);

        estadoButton = view.findViewById(R.id.switch1);
        estadoButton.setOnClickListener(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        idCuidador = firebaseUser.getUid();

        cuidadorRef = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE).child(FirebaseReferences.CUIDADOR_REFERENCE).child(idCuidador);

        cuidadorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String estadoCuidador = dataSnapshot.child("estado").getValue(String.class);
                if (estadoCuidador.equals("Activo")){
                    estadoButton.setChecked(true);
                } else {
                    estadoButton.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.switch1:
                cambiarEstado();
                break;
            case R.id.fab_location_cuidador:
                getLocationPermission();

        }
    }

    private void cambiarEstado() {
        if (estadoButton.isChecked()){
            cuidadorRef.child("estado").setValue("Activo", new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError != null){
                        Toast.makeText(getContext(), "No se pudo actualizar tu estado\nEstado: Inactivo", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Actualización de estado exitosa.\nEstado: Activo", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            cuidadorRef.child("estado").setValue("Inactivo", new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError != null){
                        Toast.makeText(getContext(), "No se pudo actualizar tu estado\nEstado: Activo", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Actualización de estado exitosa.\nEstado: Inactivo", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
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
            //mLocationPermissionGranted = true;
            //getDeviceLocation(mLocationPermissionGranted);
            //getCurrentLocation(mLocationPermissionGranted);
            startLocationUpdates();
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    44);
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    /*
    private void getCurrentLocation(boolean mLocationPermissionGranted) {

        try {
            if (mLocationPermissionGranted){
                //Inicializar la tarea de ubicación.
                //fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.getContext());
                final Task<Location> task = fusedLocationClient.getLastLocation();

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
                        } else {
                            Toast.makeText(getContext(),"Ubicación actual es null", Toast.LENGTH_LONG).show();
                            Toast.makeText(getContext(),"Exception: " + task.getException(), Toast.LENGTH_LONG).show();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(19.504803, -99.146900), 15));
                        }
                    }
                });
            }
        } catch (SecurityException e){
            Log.e("Exception: %s", e.getMessage());
        }

    }*/

    private void startLocationUpdates() {
        //revision de permisos
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //creación de la solicitud de ubicacion
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(60000);
        locationRequest.setFastestInterval(30000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //Devolucion de la llamada de la solicitud de la ubicacion
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    mMap.clear();
                    //Inicializar latitud y longitud
                    LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
                    //Crear opciones para el marcador
                    MarkerOptions options = new MarkerOptions().position(current)
                            .title("Mi ubicación").icon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_marcador_de_posicion)).snippet("Ubicación actual");
                    //Zoom en el mapa
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 16));
                    //Añadir marcador en el mapa
                    mMap.addMarker(options);
                }
            }
        };

        //Solicitud de actualizacion de ubicacion
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }


}
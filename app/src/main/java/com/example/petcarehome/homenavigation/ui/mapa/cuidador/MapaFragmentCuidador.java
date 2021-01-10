package com.example.petcarehome.homenavigation.ui.mapa.cuidador;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
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

import java.util.Objects;

public class MapaFragmentCuidador extends Fragment implements View.OnClickListener {

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    //private boolean requestingLocationUpdates, mLocationPermissionGranted;
    private LocationRequest locationRequest;
    private SwitchCompat estadoButton;
    private GoogleMap mMap;
    //private Location mLastKnownLocation;

    private MarkerOptions marker;

    private DatabaseReference cuidadorRef;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;


    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

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
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cdmx, 15));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mapa_cuidador, container, false);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getContext()));
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
        ExtendedFloatingActionButton fablocation = view.findViewById(R.id.fab_location_cuidador);
        fablocation.setOnClickListener(this);

        estadoButton = view.findViewById(R.id.switch1);
        estadoButton.setOnClickListener(this);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String idCuidador = firebaseUser.getUid();

        marker = null;

        cuidadorRef = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE).child(FirebaseReferences.CUIDADOR_REFERENCE).child(idCuidador);
        //Poner el switch dependiendo del estado en el que esté el cuidador en la base de datos
        cuidadorRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Verifica el estado en la base de datos
                String estadoCuidador = dataSnapshot.child(FirebaseReferences.CUIDADOR_ESTADO_REFERENCE).getValue(String.class);
                //Si es activo...
                if (estadoCuidador.equals("Activo")) {
                    //Pone el switch en activo
                    estadoButton.setChecked(true);
                    //Inicia los servicios de actualizacion de ubicación
                    getLocationPermission();
                    //Obtiene la  ubicación y la actualiza en la base de datos
                    getLastLoc();
                } else {
                    estadoButton.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch1:
                cambiarEstado();
                break;
            case R.id.fab_location_cuidador:
                getLocationPermission();

        }
    }


    private void cambiarEstado() {
        if (estadoButton.isChecked()) {
            //Verificar que se tenga una ubicación definida para actualizar el estado
            //Si no está definida, se muestra un dialogo que muestra un mensaje al usuario para que obtenga su ubicación
            if (marker == null) {
                AlertDialog.Builder obtenerUbiError = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                obtenerUbiError.setMessage("Debes obtener tu ubicación antes de cambiar a estado ACTIVO")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                estadoButton.setChecked(false);
            } else { //Si sí hay una ubicación definida obtiene la ubicación del marcador
                double lati = marker.getPosition().latitude;
                double lngi = marker.getPosition().longitude;
                LatLng locationFB = new LatLng(lati, lngi);
                //Actualiza la ubicación en la base de datos
                updateLocationFB(locationFB);
                //Actualiza el estado en la base de datos
                cuidadorRef.child(FirebaseReferences.CUIDADOR_ESTADO_REFERENCE).setValue("Activo", new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Toast.makeText(getContext(), "No se pudo actualizar tu estado\nEstado: Inactivo", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Actualización de estado exitosa.\nEstado: Activo", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        } else {
            // Si el switch.isChecked == false entonces
            //Borra la ubicación de la base de datos
            deleteLocationFB();
            //Actualiza el estado en la base de datos
            cuidadorRef.child(FirebaseReferences.CUIDADOR_ESTADO_REFERENCE).setValue("Inactivo", new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        Toast.makeText(getContext(), "No se pudo actualizar tu estado\nEstado: Activo", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Actualización de estado exitosa.\nEstado: Inactivo", Toast.LENGTH_LONG).show();
                    }
                }
            });
            if (marker != null){
                //Detiene las actualizaciones de ubicacion
                stopLocationUpdates();
            }
        }
    }


    //Actualizar la ubicacion en la base de datos
    private void updateLocationFB(LatLng locationFB) {
        cuidadorRef.child(FirebaseReferences.CUIDADOR_LATITUD_REFERENCE).setValue(locationFB.latitude, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(getContext(), "No se pudo actualizar tu latitud", Toast.LENGTH_LONG).show();
                }
            }
        });
        cuidadorRef.child(FirebaseReferences.CUIDADOR_LONGITUD_REFERENCE).setValue(locationFB.longitude, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(getContext(), "No se pudo actualizar tu longitud", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    //Borrar la ubicacion en la base de datos
    private void deleteLocationFB() {
        cuidadorRef.child(FirebaseReferences.CUIDADOR_LATITUD_REFERENCE).removeValue( new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(getContext(), "No se pudo actualizar tu latitud", Toast.LENGTH_LONG).show();
                }
            }
        });
        cuidadorRef.child(FirebaseReferences.CUIDADOR_LONGITUD_REFERENCE).removeValue( new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Toast.makeText(getContext(), "No se pudo actualizar tu longitud", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    private void getLocationPermission() {

        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(this.getContext()),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //mLocationPermissionGranted = true;
            //getDeviceLocation(mLocationPermissionGranted);
            //getCurrentLocation(mLocationPermissionGranted);
            checkLocationSettings();
        } else {
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    44);
        }
    }

    //Crear marcador personalizado
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    //Actualizar la ubicación en la base de datos cuando se inicia la app y se está en estado activo
    private void getLastLoc() {
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                            updateLocationFB(new LatLng(location.getLatitude(), location.getLongitude()));
                        }
                    }
                });

    }


    private void checkLocationSettings(){

        //creación de la solicitud de ubicacion
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //Verificar la configuracion actual de la ubicación
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        SettingsClient client = LocationServices.getSettingsClient(Objects.requireNonNull(getActivity()));
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());


        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdates(locationRequest);
            }
        });

        task.addOnFailureListener(Objects.requireNonNull(getActivity()), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        //resolvable.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                        startIntentSenderForResult(resolvable.getResolution().getIntentSender(), REQUEST_CHECK_SETTINGS, null, 0, 0, 0, null);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS && resultCode == Activity.RESULT_OK){
                //creación de la solicitud de ubicacion
                locationRequest = LocationRequest.create();
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                startLocationUpdates(locationRequest);
        }
    }

    //Iniciar los servicios de actualizacion de ubicación
    private void startLocationUpdates(LocationRequest locationRequest) {
        //revision de permisos
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

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
                    marker = new MarkerOptions().position(current)
                            .title("Mi ubicación").icon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_marcador_de_posicion)).snippet("Ubicación actual");
                    //Zoom en el mapa
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 16));
                    //Añadir marcador en el mapa
                    mMap.addMarker(marker);
                }
            }
        };

        //Solicitud de actualizacion de ubicacion
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());


    }

    //Detener los servicios de actualizacion de ubicacion
    private void stopLocationUpdates() {
        //Detiene los servicios de actualizacion de ubicacion
        fusedLocationClient.removeLocationUpdates(locationCallback);
        //Mandar marker a null y limpiar el mapa
        marker = null;
        mMap.clear();
    }


}
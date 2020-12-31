package com.example.petcarehome.homenavigation.ui.mapa.dueno;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.petcarehome.InicioYRegistro.Cuidador;
import com.example.petcarehome.R;
import com.example.petcarehome.homenavigation.Objetos.Busqueda;
import com.example.petcarehome.homenavigation.Objetos.FirebaseReferences;
import com.example.petcarehome.homenavigation.Objetos.MarcadorCuidador;
import com.example.petcarehome.homenavigation.Objetos.Mascota;
import com.example.petcarehome.homenavigation.Objetos.ReportePerdidasID;
import com.example.petcarehome.homenavigation.Objetos.Servicio;
import com.example.petcarehome.homenavigation.ui.difusion.perdidas.DetalleReportePerdidasActivity;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Locale;

public class MapaFragmentDueno extends Fragment implements View.OnClickListener {

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private boolean mLocationPermissionGranted;
    private LocationRequest locationRequest;
    private ExtendedFloatingActionButton fabbusqueda, fablocation;
    private GoogleMap mMap;
    private Busqueda busqueda;

    //private Marker markerCurrent;
    private Marker marker;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private ArrayList<MarcadorCuidador> listMarkerCuidador;

    private String currentAddress;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference cuidadoresRef;
    private ArrayList<Cuidador> listCuidadores;
    private ValueEventListener busquedaListener;

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
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cdmx, 15));

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mapa_dueno, container, false);
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

        listCuidadores = new ArrayList<>();
        busquedaListener = null;


        //Botones flotates referencias y escuchador
        fabbusqueda = view.findViewById(R.id.fab_busqueda);
        fablocation = view.findViewById(R.id.fab_location);

        fabbusqueda.setOnClickListener(this);
        fablocation.setOnClickListener(this);

        marker = null;
        //markerCurrent = null;

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
        switch (v.getId()) {
            case R.id.fab_busqueda:
                openDialog();
                break;
            case R.id.fab_location:
                checkLocationSettings();

        }
    }

    private void openDialog() {
        if (marker == null){
            AlertDialog.Builder obtenerUbiError = new AlertDialog.Builder(getActivity());
            obtenerUbiError.setMessage("Debes obtener tu ubicación antes de realizar una búsqueda de cuidadores.")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else {
            BuscarCuidadoresDialog buscarCuidadoresDialog = new BuscarCuidadoresDialog();
            buscarCuidadoresDialog.setTargetFragment(this, 20);
            buscarCuidadoresDialog.show(getFragmentManager().beginTransaction(), "Buscar un cuidador");
        }

    }

    /*
    private void getLocationPermission() {

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
    }*/




    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void checkLocationSettings(){

        //creación de la solicitud de ubicacion
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //Verificar la configuracion actual de la ubicación
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        SettingsClient client = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());


        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdates(locationRequest);
            }
        });

        task.addOnFailureListener(getActivity(), new OnFailureListener() {
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

        if (requestCode == 20 && resultCode == Activity.RESULT_OK){
            Bundle bundle = data.getExtras();
            busqueda = (Busqueda) bundle.getSerializable("busqueda");
            iniciarBusqueda(busqueda);
        }

    }

    private void iniciarBusqueda(final Busqueda busqueda) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        cuidadoresRef = firebaseDatabase.getReference().child(FirebaseReferences.USERS_REFERENCE).child(FirebaseReferences.CUIDADOR_REFERENCE);
        mMap.clear();
        MarkerOptions dueno = new MarkerOptions().position(busqueda.getUbicacion())
                .title("Mi ubicación").snippet("Ubicación actual");
        marker = mMap.addMarker(dueno);
        //String dist = String.valueOf(busqueda.getDistancia());
        if (busqueda.getTipoMascota().isEmpty()){
            if (busqueda.getTipoServicio().isEmpty()){
                if (busqueda.getDistancia() == 0){
                    Toast.makeText(getContext(),"Busqueda sin filtros", Toast.LENGTH_LONG).show();
                } else {
                    //Busqueda de cuidadores solo por distancia
                    Toast.makeText(getContext(),"Busqueda solo por distancia", Toast.LENGTH_LONG).show();
                    busquedaDistancia(cuidadoresRef, busqueda.getDistancia(), busqueda.getUbicacion());
                }
            } else {
                if (busqueda.getDistancia() == 0){
                    Toast.makeText(getContext(),"Busqueda solo por servicio", Toast.LENGTH_LONG).show();
                    busquedaServicio(cuidadoresRef, busqueda.getTipoServicio());
                } else {
                    Toast.makeText(getContext(),"Busqueda por servicio y distancia", Toast.LENGTH_LONG).show();
                    busquedaServicioDistancia(cuidadoresRef, busqueda.getTipoServicio(), busqueda.getDistancia(), busqueda.getUbicacion());
                }
            }
        } else {
            if (busqueda.getTipoServicio().isEmpty()){
                if (busqueda.getDistancia() == 0){
                    Toast.makeText(getContext(),"Busqueda solo por tipo de mascota", Toast.LENGTH_LONG).show();
                    busquedaMascota(cuidadoresRef, busqueda.getTipoMascota());
                } else {
                    //Busqueda de cuidadores solo por distancia
                    Toast.makeText(getContext(),"Busqueda por tipo de mascota y distancia", Toast.LENGTH_LONG).show();
                    busquedaMascotaDistancia(cuidadoresRef, busqueda.getTipoMascota(), busqueda.getDistancia(), busqueda.getUbicacion());
                }
            } else {
                if (busqueda.getDistancia() == 0){
                    Toast.makeText(getContext(),"Busqueda por tipo de mascota y servicio", Toast.LENGTH_LONG).show();
                    busquedaMascotaServicio(cuidadoresRef, busqueda.getTipoMascota(), busqueda.getTipoServicio());
                } else {
                    Toast.makeText(getContext(),"Busqueda por tipo de mascota, servicio y distancia", Toast.LENGTH_LONG).show();
                    busquedaMascotaServicioDistancia(cuidadoresRef, busqueda.getTipoMascota(), busqueda.getTipoServicio(), busqueda.getDistancia(), busqueda.getUbicacion());
                }
            }
        }

        /*cuidadoresRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCuidadores.removeAll(listCuidadores);
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()) {
                    String estado = snapshot.child(FirebaseReferences.CUIDADOR_ESTADO_REFERENCE).getValue(String.class);
                    if (estado.equals("Activo")){
                        //Cuidador cuidador = new Cuidador();
                        String nombre = snapshot.child("nombre").getValue(String.class);
                        String apellidos = snapshot.child("apellidos").getValue(String.class);
                        String telefono = snapshot.child("telefono").getValue(String.class);
                        String email = snapshot.child("correo").getValue(String.class);
                        Double lat = snapshot.child("lat").getValue(Double.class);
                        Double lng = snapshot.child("lng").getValue(Double.class);
                        Cuidador cuidador = new Cuidador();
                        cuidador.setNombre(nombre);
                        cuidador.setApellidos(apellidos);
                        cuidador.setTelefono(telefono);
                        cuidador.setCorreo(email);
                        cuidador.setLat(lat);
                        cuidador.setLng(lng);
                        listCuidadores.add(cuidador);
                    }
                }
                llenarMapa(listCuidadores);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

    }

    //Busqueda solo por mascota
    private void busquedaMascota(DatabaseReference cuidadoresRef, final String tipoMascota) {
        listCuidadores = new ArrayList<>();
        busquedaListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCuidadores.removeAll(listCuidadores);
                for (DataSnapshot snapCuidador:
                        dataSnapshot.getChildren()) {
                    String estado = snapCuidador.child(FirebaseReferences.CUIDADOR_ESTADO_REFERENCE).getValue(String.class);
                    if (estado.equals("Activo")){
                        String nombre = snapCuidador.child("nombre").getValue(String.class);
                        String apellidos = snapCuidador.child("apellidos").getValue(String.class);
                        String calle = snapCuidador.child("calle").getValue(String.class);
                        String noInt = snapCuidador.child("noint").getValue(String.class);
                        String noExt = snapCuidador.child("noext").getValue(String.class);
                        String colonia = snapCuidador.child("colonia").getValue(String.class);
                        String alcaldia = snapCuidador.child("alcaldia").getValue(String.class);
                        String telefono = snapCuidador.child("telefono").getValue(String.class);
                        String email = snapCuidador.child("correo").getValue(String.class);
                        String foto = snapCuidador.child("foto").getValue(String.class);
                        String comentarios = snapCuidador.child("comentarios").getValue(String.class);
                        ArrayList<Mascota> listMascotas = new ArrayList<>();
                        Double calif = snapCuidador.child("calificacion").getValue(Double.class);
                        Double lat = snapCuidador.child("lat").getValue(Double.class);
                        Double lng = snapCuidador.child("lng").getValue(Double.class);

                        Cuidador cuidador = new Cuidador();
                        cuidador.setNombre(nombre);
                        cuidador.setApellidos(apellidos);
                        cuidador.setCalle(calle);
                        cuidador.setNoint(noInt);
                        cuidador.setNoext(noExt);
                        cuidador.setColonia(colonia);
                        cuidador.setAlcaldia(alcaldia);
                        cuidador.setTelefono(telefono);
                        cuidador.setCorreo(email);
                        cuidador.setFoto(foto);
                        cuidador.setComentarios(comentarios);
                        cuidador.setCalificacion(calif);
                        cuidador.setLat(lat);
                        cuidador.setLng(lng);
                        for (DataSnapshot snapMascota:
                                snapCuidador.child("mascotas").getChildren()) {
                            if (snapMascota.exists()){
                                String tipoM = snapMascota.getKey();
                                if (tipoM.equals(tipoMascota)){
                                    Mascota mascota = snapMascota.getValue(Mascota.class);
                                    listMascotas.add(mascota);
                                }
                            }
                        }
                        if (!listMascotas.isEmpty()) {
                            cuidador.setMascotas(listMascotas);
                            listCuidadores.add(cuidador);
                        }
                    }
                }
                if (!listCuidadores.isEmpty()) {
                    llenarMapa(listCuidadores);
                } else {
                    Toast.makeText(getContext(), "No se encontraron cuidadores", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        cuidadoresRef.addValueEventListener(busquedaListener);
    }

    //Busqueda solo por servicio
    private void busquedaServicio(DatabaseReference cuidadoresRef, final String tipoServicio) {
        listCuidadores = new ArrayList<>();
        busquedaListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCuidadores.removeAll(listCuidadores);
                for (DataSnapshot snapCuidador:
                        dataSnapshot.getChildren()) {
                    String estado = snapCuidador.child(FirebaseReferences.CUIDADOR_ESTADO_REFERENCE).getValue(String.class);
                    if (estado.equals("Activo")){
                        String nombre = snapCuidador.child("nombre").getValue(String.class);
                        String apellidos = snapCuidador.child("apellidos").getValue(String.class);
                        String calle = snapCuidador.child("calle").getValue(String.class);
                        String noInt = snapCuidador.child("noint").getValue(String.class);
                        String noExt = snapCuidador.child("noext").getValue(String.class);
                        String colonia = snapCuidador.child("colonia").getValue(String.class);
                        String alcaldia = snapCuidador.child("alcaldia").getValue(String.class);
                        String telefono = snapCuidador.child("telefono").getValue(String.class);
                        String email = snapCuidador.child("correo").getValue(String.class);
                        String foto = snapCuidador.child("foto").getValue(String.class);
                        String comentarios = snapCuidador.child("comentarios").getValue(String.class);
                        ArrayList<Mascota> listMascotas = new ArrayList<>();
                        Double calif = snapCuidador.child("calificacion").getValue(Double.class);
                        Double lat = snapCuidador.child("lat").getValue(Double.class);
                        Double lng = snapCuidador.child("lng").getValue(Double.class);

                        Cuidador cuidador = new Cuidador();
                        cuidador.setNombre(nombre);
                        cuidador.setApellidos(apellidos);
                        cuidador.setCalle(calle);
                        cuidador.setNoint(noInt);
                        cuidador.setNoext(noExt);
                        cuidador.setColonia(colonia);
                        cuidador.setAlcaldia(alcaldia);
                        cuidador.setTelefono(telefono);
                        cuidador.setCorreo(email);
                        cuidador.setFoto(foto);
                        cuidador.setComentarios(comentarios);
                        cuidador.setCalificacion(calif);
                        cuidador.setLat(lat);
                        cuidador.setLng(lng);
                        for (DataSnapshot snapMascota:
                                snapCuidador.child("mascotas").getChildren()) {
                            if (snapMascota.exists()){
                                String tMascota = snapMascota.getKey();
                                Mascota mascota = new Mascota();
                                mascota.setTipo(tMascota);
                                ArrayList<Servicio> listServicios = new ArrayList<>();
                                for (DataSnapshot snapServicio:
                                        snapMascota.child("servicios").getChildren()) {
                                    if (snapServicio.exists()){
                                        String servicio = snapServicio.child("tipoServicio").getValue(String.class);
                                        if (servicio.equals(tipoServicio)){
                                            Servicio serv = snapServicio.getValue(Servicio.class);
                                            listServicios.add(serv);
                                        }
                                    }
                                }
                                if(!listServicios.isEmpty()){
                                    mascota.setServicios(listServicios);
                                    listMascotas.add(mascota);
                                }
                            }
                        }
                        if(!listMascotas.isEmpty()){
                            cuidador.setMascotas(listMascotas);
                            listCuidadores.add(cuidador);
                        }
                    }
                }
                if (!listCuidadores.isEmpty()) {
                    llenarMapa(listCuidadores);
                } else {
                    Toast.makeText(getContext(), "No se encontraron cuidadores", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        cuidadoresRef.addValueEventListener(busquedaListener);
    }


    //Busqueda solo por distancia
    private void busquedaDistancia(DatabaseReference cuidadoresRef, final int distancia, final LatLng ubicacion) {
        listCuidadores = new ArrayList<>();
        busquedaListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCuidadores.removeAll(listCuidadores);
                for (DataSnapshot snapCuidador:
                        dataSnapshot.getChildren()) {
                    String estado = snapCuidador.child(FirebaseReferences.CUIDADOR_ESTADO_REFERENCE).getValue(String.class);
                    if (estado.equals("Activo")){
                        Double latCuidador = snapCuidador.child("lat").getValue(Double.class);
                        Double lngCuidador = snapCuidador.child("lng").getValue(Double.class);
                        GeoLocation locDueno = new GeoLocation(ubicacion.latitude, ubicacion.longitude);
                        GeoLocation locCuidador = new GeoLocation(latCuidador, lngCuidador);
                        Double distance = (GeoFireUtils.getDistanceBetween(locDueno, locCuidador))/1000;
                        if (distance.intValue() <= distancia){
                            String nombre = snapCuidador.child("nombre").getValue(String.class);
                            String apellidos = snapCuidador.child("apellidos").getValue(String.class);
                            String calle = snapCuidador.child("calle").getValue(String.class);
                            String noInt = snapCuidador.child("noint").getValue(String.class);
                            String noExt = snapCuidador.child("noext").getValue(String.class);
                            String colonia = snapCuidador.child("colonia").getValue(String.class);
                            String alcaldia = snapCuidador.child("alcaldia").getValue(String.class);
                            String telefono = snapCuidador.child("telefono").getValue(String.class);
                            String email = snapCuidador.child("correo").getValue(String.class);
                            String foto = snapCuidador.child("foto").getValue(String.class);
                            String comentarios = snapCuidador.child("comentarios").getValue(String.class);
                            ArrayList<Mascota> listMascotas = new ArrayList<>();
                            Double calif = snapCuidador.child("calificacion").getValue(Double.class);
                            Double lat = snapCuidador.child("lat").getValue(Double.class);
                            Double lng = snapCuidador.child("lng").getValue(Double.class);

                            Cuidador cuidador = new Cuidador();
                            cuidador.setNombre(nombre);
                            cuidador.setApellidos(apellidos);
                            cuidador.setCalle(calle);
                            cuidador.setNoint(noInt);
                            cuidador.setNoext(noExt);
                            cuidador.setColonia(colonia);
                            cuidador.setAlcaldia(alcaldia);
                            cuidador.setTelefono(telefono);
                            cuidador.setCorreo(email);
                            cuidador.setFoto(foto);
                            cuidador.setComentarios(comentarios);
                            cuidador.setCalificacion(calif);
                            cuidador.setLat(lat);
                            cuidador.setLng(lng);
                            listCuidadores.add(cuidador);
                            for (DataSnapshot snapMascota:
                                    snapCuidador.child("mascotas").getChildren()) {
                                if (snapMascota.exists()){
                                    Mascota mascota = snapMascota.getValue(Mascota.class);
                                    listMascotas.add(mascota);
                                }
                            }
                            if (!listMascotas.isEmpty()){
                                cuidador.setMascotas(listMascotas);
                                listCuidadores.add(cuidador);
                            }
                        }
                    }
                }
                if (!listCuidadores.isEmpty()){
                    llenarMapa(listCuidadores);
                } else {
                    Toast.makeText(getContext(), "No se encontraron cuidadores", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        cuidadoresRef.addValueEventListener(busquedaListener);
    }

    //Busqueda por Mascota y servicio
    private void busquedaMascotaServicio(DatabaseReference cuidadoresRef, final String tipoMascota, final String tipoServicio) {
        listCuidadores = new ArrayList<>();
        busquedaListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCuidadores.removeAll(listCuidadores);
                for (DataSnapshot snapCuidador:
                        dataSnapshot.getChildren()) {
                    String estado = snapCuidador.child(FirebaseReferences.CUIDADOR_ESTADO_REFERENCE).getValue(String.class);
                    if (estado.equals("Activo")){
                        String nombre = snapCuidador.child("nombre").getValue(String.class);
                        String apellidos = snapCuidador.child("apellidos").getValue(String.class);
                        String calle = snapCuidador.child("calle").getValue(String.class);
                        String noInt = snapCuidador.child("noint").getValue(String.class);
                        String noExt = snapCuidador.child("noext").getValue(String.class);
                        String colonia = snapCuidador.child("colonia").getValue(String.class);
                        String alcaldia = snapCuidador.child("alcaldia").getValue(String.class);
                        String telefono = snapCuidador.child("telefono").getValue(String.class);
                        String email = snapCuidador.child("correo").getValue(String.class);
                        String foto = snapCuidador.child("foto").getValue(String.class);
                        String comentarios = snapCuidador.child("comentarios").getValue(String.class);
                        ArrayList<Mascota> listMascotas = new ArrayList<>();
                        Double calif = snapCuidador.child("calificacion").getValue(Double.class);
                        Double lat = snapCuidador.child("lat").getValue(Double.class);
                        Double lng = snapCuidador.child("lng").getValue(Double.class);

                        Cuidador cuidador = new Cuidador();
                        cuidador.setNombre(nombre);
                        cuidador.setApellidos(apellidos);
                        cuidador.setCalle(calle);
                        cuidador.setNoint(noInt);
                        cuidador.setNoext(noExt);
                        cuidador.setColonia(colonia);
                        cuidador.setAlcaldia(alcaldia);
                        cuidador.setTelefono(telefono);
                        cuidador.setCorreo(email);
                        cuidador.setFoto(foto);
                        cuidador.setComentarios(comentarios);
                        cuidador.setCalificacion(calif);
                        cuidador.setLat(lat);
                        cuidador.setLng(lng);
                        for (DataSnapshot snapMascota:
                                snapCuidador.child("mascotas").getChildren()) {
                            if (snapMascota.exists()){
                                String tipoM = snapMascota.getKey();
                                if (tipoM.equals(tipoMascota)){
                                    Mascota mascota = new Mascota();
                                    mascota.setTipo(tipoM);
                                    ArrayList<Servicio> listServicios = new ArrayList<>();
                                    for (DataSnapshot snapServicio:
                                            snapMascota.child("servicios").getChildren()) {
                                        if (snapServicio.exists()){
                                            String servicio = snapServicio.child("tipoServicio").getValue(String.class);
                                            if (servicio.equals(tipoServicio)){
                                                Servicio serv = snapServicio.getValue(Servicio.class);
                                                listServicios.add(serv);
                                            }
                                        }
                                    }
                                    if (!listServicios.isEmpty()) {
                                        mascota.setServicios(listServicios);
                                        listMascotas.add(mascota);
                                    }
                                }
                            }
                        }
                        if (!listMascotas.isEmpty()) {
                            cuidador.setMascotas(listMascotas);
                            listCuidadores.add(cuidador);
                        }
                    }
                }
                if (!listCuidadores.isEmpty()) {
                    llenarMapa(listCuidadores);
                } else {
                    Toast.makeText(getContext(), "No se encontraron cuidadores", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        cuidadoresRef.addValueEventListener(busquedaListener);
    }

    //Busqueda por Mascota y distancia
    private void busquedaMascotaDistancia(DatabaseReference cuidadoresRef, final String tipoMascota, final int distancia, final LatLng ubicacion) {
        listCuidadores = new ArrayList<>();
        busquedaListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCuidadores.removeAll(listCuidadores);
                for (DataSnapshot snapCuidador:
                        dataSnapshot.getChildren()) {
                    String estado = snapCuidador.child(FirebaseReferences.CUIDADOR_ESTADO_REFERENCE).getValue(String.class);
                    if (estado.equals("Activo")){
                        Double latCuidador = snapCuidador.child("lat").getValue(Double.class);
                        Double lngCuidador = snapCuidador.child("lng").getValue(Double.class);
                        GeoLocation locDueno = new GeoLocation(ubicacion.latitude, ubicacion.longitude);
                        GeoLocation locCuidador = new GeoLocation(latCuidador, lngCuidador);
                        Double distance = (GeoFireUtils.getDistanceBetween(locDueno, locCuidador))/1000;
                        if (distance <= distancia){
                            String nombre = snapCuidador.child("nombre").getValue(String.class);
                            String apellidos = snapCuidador.child("apellidos").getValue(String.class);
                            String calle = snapCuidador.child("calle").getValue(String.class);
                            String noInt = snapCuidador.child("noint").getValue(String.class);
                            String noExt = snapCuidador.child("noext").getValue(String.class);
                            String colonia = snapCuidador.child("colonia").getValue(String.class);
                            String alcaldia = snapCuidador.child("alcaldia").getValue(String.class);
                            String telefono = snapCuidador.child("telefono").getValue(String.class);
                            String email = snapCuidador.child("correo").getValue(String.class);
                            String foto = snapCuidador.child("foto").getValue(String.class);
                            String comentarios = snapCuidador.child("comentarios").getValue(String.class);
                            ArrayList<Mascota> listMascotas = new ArrayList<>();
                            Double calif = snapCuidador.child("calificacion").getValue(Double.class);
                            Double lat = snapCuidador.child("lat").getValue(Double.class);
                            Double lng = snapCuidador.child("lng").getValue(Double.class);

                            Cuidador cuidador = new Cuidador();
                            cuidador.setNombre(nombre);
                            cuidador.setApellidos(apellidos);
                            cuidador.setCalle(calle);
                            cuidador.setNoint(noInt);
                            cuidador.setNoext(noExt);
                            cuidador.setColonia(colonia);
                            cuidador.setAlcaldia(alcaldia);
                            cuidador.setTelefono(telefono);
                            cuidador.setCorreo(email);
                            cuidador.setFoto(foto);
                            cuidador.setComentarios(comentarios);
                            cuidador.setCalificacion(calif);
                            cuidador.setLat(lat);
                            cuidador.setLng(lng);
                            for (DataSnapshot snapMascota:
                                    snapCuidador.child("mascotas").getChildren()) {
                                if (snapMascota.exists()){
                                    String tiopM = snapMascota.getKey();
                                    if (tiopM.equals(tipoMascota)) {
                                        Mascota mascota = snapMascota.getValue(Mascota.class);
                                        listMascotas.add(mascota);
                                    }
                                }
                            }
                            if (!listMascotas.isEmpty()){
                                cuidador.setMascotas(listMascotas);
                                listCuidadores.add(cuidador);
                            }
                        }
                    }
                }
                if (!listCuidadores.isEmpty()){
                    llenarMapa(listCuidadores);
                } else {
                    Toast.makeText(getContext(), "No se encontraron cuidadores", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        cuidadoresRef.addValueEventListener(busquedaListener);
    }

    //Busqueda por Servicio y distancia
    private void busquedaServicioDistancia(DatabaseReference cuidadoresRef, final String tipoServicio, final int distancia, final LatLng ubicacion) {
        listCuidadores = new ArrayList<>();
        busquedaListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCuidadores.removeAll(listCuidadores);
                for (DataSnapshot snapCuidador:
                        dataSnapshot.getChildren()) {
                    String estado = snapCuidador.child(FirebaseReferences.CUIDADOR_ESTADO_REFERENCE).getValue(String.class);
                    if (estado.equals("Activo")){
                        Double latCuidador = snapCuidador.child("lat").getValue(Double.class);
                        Double lngCuidador = snapCuidador.child("lng").getValue(Double.class);
                        GeoLocation locDueno = new GeoLocation(ubicacion.latitude, ubicacion.longitude);
                        GeoLocation locCuidador = new GeoLocation(latCuidador, lngCuidador);
                        Double distance = (GeoFireUtils.getDistanceBetween(locDueno, locCuidador))/1000;
                        if (distance <= distancia){
                            String nombre = snapCuidador.child("nombre").getValue(String.class);
                            String apellidos = snapCuidador.child("apellidos").getValue(String.class);
                            String calle = snapCuidador.child("calle").getValue(String.class);
                            String noInt = snapCuidador.child("noint").getValue(String.class);
                            String noExt = snapCuidador.child("noext").getValue(String.class);
                            String colonia = snapCuidador.child("colonia").getValue(String.class);
                            String alcaldia = snapCuidador.child("alcaldia").getValue(String.class);
                            String telefono = snapCuidador.child("telefono").getValue(String.class);
                            String email = snapCuidador.child("correo").getValue(String.class);
                            String foto = snapCuidador.child("foto").getValue(String.class);
                            String comentarios = snapCuidador.child("comentarios").getValue(String.class);
                            ArrayList<Mascota> listMascotas = new ArrayList<>();
                            Double calif = snapCuidador.child("calificacion").getValue(Double.class);
                            Double lat = snapCuidador.child("lat").getValue(Double.class);
                            Double lng = snapCuidador.child("lng").getValue(Double.class);

                            Cuidador cuidador = new Cuidador();
                            cuidador.setNombre(nombre);
                            cuidador.setApellidos(apellidos);
                            cuidador.setCalle(calle);
                            cuidador.setNoint(noInt);
                            cuidador.setNoext(noExt);
                            cuidador.setColonia(colonia);
                            cuidador.setAlcaldia(alcaldia);
                            cuidador.setTelefono(telefono);
                            cuidador.setCorreo(email);
                            cuidador.setFoto(foto);
                            cuidador.setComentarios(comentarios);
                            cuidador.setCalificacion(calif);
                            cuidador.setLat(lat);
                            cuidador.setLng(lng);
                            for (DataSnapshot snapMascota:
                                    snapCuidador.child("mascotas").getChildren()) {
                                if (snapMascota.exists()){
                                    String tMascota = snapMascota.getKey();
                                    Mascota mascota = new Mascota();
                                    mascota.setTipo(tMascota);
                                    ArrayList<Servicio> listServicios = new ArrayList<>();
                                    for (DataSnapshot snapServicio:
                                            snapMascota.child("servicios").getChildren()) {
                                        if (snapServicio.exists()){
                                            String servicio = snapServicio.child("tipoServicio").getValue(String.class);
                                            if (servicio.equals(tipoServicio)){
                                                Servicio serv = snapServicio.getValue(Servicio.class);
                                                listServicios.add(serv);
                                            }
                                        }
                                    }
                                    if(!listServicios.isEmpty()){
                                        mascota.setServicios(listServicios);
                                        listMascotas.add(mascota);
                                    }
                                }
                            }
                            if (!listMascotas.isEmpty()){
                                cuidador.setMascotas(listMascotas);
                                listCuidadores.add(cuidador);
                            }
                        }
                    }
                }
                if (!listCuidadores.isEmpty()){
                    llenarMapa(listCuidadores);
                } else {
                    Toast.makeText(getContext(), "No se encontraron cuidadores", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        cuidadoresRef.addValueEventListener(busquedaListener);
    }

    //Busqueda por Mascota, Servicio y distancia
    private void busquedaMascotaServicioDistancia(DatabaseReference cuidadoresRef, final String tipoMascota, final String tipoServicio, final int distancia, final LatLng ubicacion) {
        listCuidadores = new ArrayList<>();
        busquedaListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCuidadores.removeAll(listCuidadores);
                for (DataSnapshot snapCuidador:
                        dataSnapshot.getChildren()) {
                    String estado = snapCuidador.child(FirebaseReferences.CUIDADOR_ESTADO_REFERENCE).getValue(String.class);
                    if (estado.equals("Activo")){
                        Double latCuidador = snapCuidador.child("lat").getValue(Double.class);
                        Double lngCuidador = snapCuidador.child("lng").getValue(Double.class);
                        GeoLocation locDueno = new GeoLocation(ubicacion.latitude, ubicacion.longitude);
                        GeoLocation locCuidador = new GeoLocation(latCuidador, lngCuidador);
                        Double distance = (GeoFireUtils.getDistanceBetween(locDueno, locCuidador))/1000;
                        if (distance <= distancia){
                            String nombre = snapCuidador.child("nombre").getValue(String.class);
                            String apellidos = snapCuidador.child("apellidos").getValue(String.class);
                            String calle = snapCuidador.child("calle").getValue(String.class);
                            String noInt = snapCuidador.child("noint").getValue(String.class);
                            String noExt = snapCuidador.child("noext").getValue(String.class);
                            String colonia = snapCuidador.child("colonia").getValue(String.class);
                            String alcaldia = snapCuidador.child("alcaldia").getValue(String.class);
                            String telefono = snapCuidador.child("telefono").getValue(String.class);
                            String email = snapCuidador.child("correo").getValue(String.class);
                            String foto = snapCuidador.child("foto").getValue(String.class);
                            String comentarios = snapCuidador.child("comentarios").getValue(String.class);
                            ArrayList<Mascota> listMascotas = new ArrayList<>();
                            Double calif = snapCuidador.child("calificacion").getValue(Double.class);
                            Double lat = snapCuidador.child("lat").getValue(Double.class);
                            Double lng = snapCuidador.child("lng").getValue(Double.class);

                            Cuidador cuidador = new Cuidador();
                            cuidador.setNombre(nombre);
                            cuidador.setApellidos(apellidos);
                            cuidador.setCalle(calle);
                            cuidador.setNoint(noInt);
                            cuidador.setNoext(noExt);
                            cuidador.setColonia(colonia);
                            cuidador.setAlcaldia(alcaldia);
                            cuidador.setTelefono(telefono);
                            cuidador.setCorreo(email);
                            cuidador.setFoto(foto);
                            cuidador.setComentarios(comentarios);
                            cuidador.setCalificacion(calif);
                            cuidador.setLat(lat);
                            cuidador.setLng(lng);
                            for (DataSnapshot snapMascota:
                                    snapCuidador.child("mascotas").getChildren()) {
                                if (snapMascota.exists()){
                                    String tipoM = snapMascota.getKey();
                                    if (tipoM.equals(tipoMascota)){
                                        Mascota mascota = new Mascota();
                                        mascota.setTipo(tipoM);
                                        ArrayList<Servicio> listServicios = new ArrayList<>();
                                        for (DataSnapshot snapServicio:
                                                snapMascota.child("servicios").getChildren()) {
                                            if (snapServicio.exists()){
                                                String servicio = snapServicio.child("tipoServicio").getValue(String.class);
                                                if (servicio.equals(tipoServicio)){
                                                    Servicio serv = snapServicio.getValue(Servicio.class);
                                                    listServicios.add(serv);
                                                }
                                            }
                                        }
                                        if (!listServicios.isEmpty()) {
                                            mascota.setServicios(listServicios);
                                            listMascotas.add(mascota);
                                        }
                                    }
                                }
                            }
                            if (!listMascotas.isEmpty()){
                                cuidador.setMascotas(listMascotas);
                                listCuidadores.add(cuidador);
                            }
                        }
                    }
                }
                if (!listCuidadores.isEmpty()){
                    llenarMapa(listCuidadores);
                } else {
                    Toast.makeText(getContext(), "No se encontraron cuidadores", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        cuidadoresRef.addValueEventListener(busquedaListener);
    }

    private void llenarMapa(final ArrayList<Cuidador> listCuidadores) {
        if (busquedaListener != null){
            listMarkerCuidador = new ArrayList<>();
            for (int i = 0; i < listCuidadores.size(); i++){
                LatLng locationCuidador = new LatLng(listCuidadores.get(i).getLat(), listCuidadores.get(i).getLng());
                String nombre;
                nombre = listCuidadores.get(i).getNombre() + " " + listCuidadores.get(i).getApellidos();

                //String snippetMarker = nombre + "\n Datos de contacto:\nTeléfono: " + telefono + "\nEmail: " + email;
                //Crear opciones para el marcador
                MarkerOptions markerCuidador = new MarkerOptions().position(locationCuidador)
                        .title(nombre).icon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_marcador_de_posicion));
                Marker marcador = mMap.addMarker(markerCuidador);
                listMarkerCuidador.add(new MarcadorCuidador(marcador.getId(), listCuidadores.get(i)));
            }
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    String id = marker.getId();
                    for (int j = 0; j< listMarkerCuidador.size(); j++){
                        if (id.equals(listMarkerCuidador.get(j).getIdMarker())){
                            Intent intentCuidador = new Intent(getContext(), CuidadorInfoActivity.class);
                            Bundle  bundle = new Bundle();
                            Cuidador cuidador = listMarkerCuidador.get(j).getCuidador();
                            bundle.putSerializable("cuidador", cuidador);
                            bundle.putDouble("lat", busqueda.getUbicacion().latitude);
                            bundle.putDouble("lng", busqueda.getUbicacion().longitude);
                            intentCuidador.putExtras(bundle);
                            startActivity(intentCuidador);
                        }
                    }
                    return true;
                }
            });
        } else {
            mMap.clear();
        }

    }


    private void startLocationUpdates(LocationRequest locationRequest) {
        //revision de permisos
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        //creación de la solicitud de ubicacion
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        //Devolucion de la llamada de la solicitud de la ubicacion
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (marker != null){
                        marker.remove();
                    }
                    //Inicializar latitud y longitud
                    LatLng current = new LatLng(location.getLatitude(), location.getLongitude());

                    //Crear opciones para el marcador
                    MarkerOptions markerOptions = new MarkerOptions().position(current)
                            .title("Mi ubicación").snippet("Ubicación actual");
                    //Zoom en el mapa
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 16));
                    //Añadir marcador en el mapa
                    marker = mMap.addMarker(markerOptions);
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

    @Override
    public void onPause() {
        super.onPause();
        if (busquedaListener != null){
            cuidadoresRef.removeEventListener(busquedaListener);
        }
    }
}

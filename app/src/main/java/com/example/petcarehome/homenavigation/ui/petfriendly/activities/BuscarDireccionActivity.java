package com.example.petcarehome.homenavigation.ui.petfriendly.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.example.petcarehome.R;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class BuscarDireccionActivity extends FragmentActivity implements OnMapReadyCallback {
    protected static final int REQUEST_CHECK_SETTINGS = 10;
    private static final String TAG = "BuscarDireccionActivity";
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private Marker markerCenter;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_direccion);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        CameraPosition position = new CameraPosition.Builder()
                .target(new LatLng(19.504927546328837, -99.14678835041674))
                .zoom(15).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mMap.getCameraPosition().target);
        markerCenter = mMap.addMarker(markerOptions);
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            public void onCameraMove() {
                markerCenter.setPosition(mMap.getCameraPosition().target);
            }
        });
    }

    public void onClick(View view) {
        if (view.getId() == R.id.fab_location) {
            getLocationPermission();
        } else if (view.getId() == R.id.fab_guardar) {
            LatLng pos = markerCenter.getPosition();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("latlng", String.format("%s,%s", pos.latitude, pos.longitude));
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

    private void getLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            checkLocationSettings();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    44);
        }
    }

    private void checkLocationSettings() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());


        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdates(locationRequest);
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
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
        if (requestCode == REQUEST_CHECK_SETTINGS && resultCode == Activity.RESULT_OK) {
            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            startLocationUpdates(locationRequest);
        }
    }

    private void startLocationUpdates(LocationRequest locationRequest) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (markerCenter != null) {
                        markerCenter.remove();
                    }

                    LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions().position(current)
                            .title("Mi ubicación").snippet("Ubicación actual");

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 16));
                    //marker = mMap.addMarker(markerOptions);
                }
            }
        };

        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }
}
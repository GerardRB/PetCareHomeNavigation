package com.example.petcarehome.homenavigation.Objetos;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Busqueda implements Serializable {

    private LatLng ubicacion;
    private String tipoMascota, tipoServicio;
    private int distancia;

    public Busqueda() {
    }

    public Busqueda(LatLng ubicacion, String tipoMascota, String tipoServicio, int distancia) {
        this.ubicacion = ubicacion;
        this.tipoMascota = tipoMascota;
        this.tipoServicio = tipoServicio;
        this.distancia = distancia;
    }


    public LatLng getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(LatLng ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTipoMascota() {
        return tipoMascota;
    }

    public void setTipoMascota(String tipoMascota) {
        this.tipoMascota = tipoMascota;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }
}

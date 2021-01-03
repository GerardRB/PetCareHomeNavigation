package com.example.petcarehome.homenavigation.Objetos;

import com.example.petcarehome.InicioYRegistro.Cuidador;

public class MarcadorCuidador {
    private String idMarker;
    private Cuidador cuidador;

    public MarcadorCuidador() {
    }

    public MarcadorCuidador(String idMarker, Cuidador cuidador) {
        this.idMarker = idMarker;
        this.cuidador = cuidador;
    }

    public String getIdMarker() {
        return idMarker;
    }

    public void setIdMarker(String idMarker) {
        this.idMarker = idMarker;
    }

    public Cuidador getCuidador() {
        return cuidador;
    }

    public void setCuidador(Cuidador cuidador) {
        this.cuidador = cuidador;
    }
}

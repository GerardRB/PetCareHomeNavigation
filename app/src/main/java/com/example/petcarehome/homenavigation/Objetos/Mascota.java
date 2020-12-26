package com.example.petcarehome.homenavigation.Objetos;

import java.util.ArrayList;

public class Mascota {
    String tipo;
    ArrayList<Servicio> servicios;

    public Mascota(String tipo, ArrayList<Servicio> servicios) {
        this.tipo = tipo;
        this.servicios = servicios;
    }

    public Mascota(){

    }

    public ArrayList<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(ArrayList<Servicio> servicios) {
        this.servicios = servicios;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

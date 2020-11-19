package com.example.petcarehome.InicioYRegistro;

public class Servicio {
    String tipoServicio;
    Double precio;
    String descripcion;

    public Servicio(String tipoServicio, Double precio, String descripcion) {
        this.tipoServicio = tipoServicio;
        this.precio = precio;
        this.descripcion = descripcion;
    }
    public Servicio(){

    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}


package com.example.petcarehome.Objetos;

import java.io.Serializable;

public class ReporteEncontradas implements Serializable {
    private String tipo, fecha, hora, alcaldia, colonia, calle, descripcion;
    private int foto, id;

    public ReporteEncontradas(){
    }

    public ReporteEncontradas(String tipo, String fecha, String hora, String alcaldia, String colonia, String calle, String descripcion, int foto, int id) {
        this.tipo = tipo;
        this.fecha = fecha;
        this.hora = hora;
        this.alcaldia = alcaldia;
        this.colonia = colonia;
        this.calle = calle;
        this.descripcion = descripcion;
        this.foto = foto;
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getAlcaldia() {
        return alcaldia;
    }

    public void setAlcaldia(String alcaldia) {
        this.alcaldia = alcaldia;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

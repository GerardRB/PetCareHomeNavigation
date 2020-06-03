package com.example.myapplication.ui.difusion;

public class ReportePerdidas {
    private String nombre;
    private String fecha;
    private String zona;
    private String descripcion;
    private int foto;

    public ReportePerdidas(){

    }

    public ReportePerdidas(String nombre, String fecha, String zona, String descripcion, int foto) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.zona = zona;
        this.descripcion = descripcion;
        this.foto = foto;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
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
}

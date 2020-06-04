package com.example.myapplication.ui.difusion.perdidas;

public class ReportePerdidas {
    private String zona;
    private String fecha;
    private String nombre;
    private String descripcion;
    private int foto;

    public ReportePerdidas(){

    }

    public ReportePerdidas(String zona, String fecha, String nombre, String descripcion, int foto) {
        this.zona = zona;
        this.fecha = fecha;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto = foto;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

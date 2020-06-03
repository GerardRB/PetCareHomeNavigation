package com.example.myapplication.ui.difusion;

public class ReporteEncontradas {
    private String fecha;
    private String zona;
    private String descripcion;
    private int foto;

    public ReporteEncontradas(){
    }

    public ReporteEncontradas(String fecha, String zona, String descripcion, int foto) {
        this.fecha = fecha;
        this.zona = zona;
        this.descripcion = descripcion;
        this.foto = foto;
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

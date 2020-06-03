package com.example.myapplication.ui.difusion;

public class ReporteVo {
    private String nombre;
    private String fecha;
    private String descripcion;
    private int foto;

    public ReporteVo(){

    }

    public ReporteVo(String nombre, String fecha, String descripcion, int foto) {
        this.nombre = nombre;
        this.fecha = fecha;
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

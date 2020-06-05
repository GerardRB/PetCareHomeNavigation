package com.example.myapplication.ui.difusion.adopcion;

public class ReporteAdopcion {
    private String tipo;
    private String edad;
    private String cantidad;
    private String descripcion;
    private int foto;

    public ReporteAdopcion(){
    }

    public ReporteAdopcion(String tipo, String edad, String cantidad, String descripcion, int foto) {
        this.tipo = tipo;
        this.edad = edad;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.foto = foto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
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
package com.example.petcarehome.homenavigation.Objetos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReportePerdidas implements Serializable {
    private String idRep, nombre, tipo, edad, fecha, hora, alcaldia, colonia, calle, descripcion, foto, idUser;
    //private List<String> fotos;

    public ReportePerdidas(){

    }

    public ReportePerdidas(String idRep, String nombre, String tipo, String edad, String fecha, String hora, String alcaldia, String colonia, String calle, String descripcion, String foto, String idUser) {
        this.idRep = idRep;
        this.nombre = nombre;
        this.tipo = tipo;
        this.edad = edad;
        this.fecha = fecha;
        this.hora = hora;
        this.alcaldia = alcaldia;
        this.colonia = colonia;
        this.calle = calle;
        this.descripcion = descripcion;
        this.foto = foto;
        this.idUser = idUser;
    }

    public String getIdRep() {
        return idRep;
    }

    public void setIdRep(String idRep) {
        this.idRep = idRep;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}



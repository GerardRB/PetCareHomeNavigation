package com.example.petcarehome.homenavigation.Objetos;

import java.io.Serializable;

public class ReporteAdopcion implements Serializable {
    private String tipo, raza, edad, vacunas, esterilizacion, alcaldia, colonia, calle, descripcion, foto, idUser;

    public ReporteAdopcion(){
    }

    public ReporteAdopcion(String tipo, String raza, String edad, String vacunas, String esterilizacion, String alcaldia, String colonia, String calle, String descripcion, String foto, String idUser) {
        this.tipo = tipo;
        this.raza = raza;
        this.edad = edad;
        this.vacunas = vacunas;
        this.esterilizacion = esterilizacion;
        this.alcaldia = alcaldia;
        this.colonia = colonia;
        this.calle = calle;
        this.descripcion = descripcion;
        this.foto = foto;
        this.idUser = idUser;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getVacunas() {
        return vacunas;
    }

    public void setVacunas(String vacunas) {
        this.vacunas = vacunas;
    }

    public String getEsterilizacion() {
        return esterilizacion;
    }

    public void setEsterilizacion(String esterilizacion) {
        this.esterilizacion = esterilizacion;
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

    public void setId(String idUser) {
        this.idUser = idUser;
    }
}

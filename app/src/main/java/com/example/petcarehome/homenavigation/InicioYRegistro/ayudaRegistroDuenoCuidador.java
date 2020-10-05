package com.example.petcarehome.homenavigation.InicioYRegistro;

public class ayudaRegistroDuenoCuidador {
//7 campos a guardar en la bd

String nombre, apellidos, calle, noext, noint, alcaldia, cel;

    public ayudaRegistroDuenoCuidador(String nombre, String apellidos, String calle, String noext, String noint, String alcaldia, String cel) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.calle = calle;
        this.noext = noext;
        this.noint = noint;
        this.alcaldia = alcaldia;
        this.cel = cel;
    }

    public ayudaRegistroDuenoCuidador(){

    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getCalle() {
        return calle;
    }

    public String getNoext() {
        return noext;
    }

    public String getNoint() {
        return noint;
    }

    public String getAlcaldia() {
        return alcaldia;
    }

    public String getCel() {
        return cel;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setNoext(String noext) {
        this.noext = noext;
    }

    public void setNoint(String noint) {
        this.noint = noint;
    }

    public void setAlcaldia(String alcaldia) {
        this.alcaldia = alcaldia;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }
}


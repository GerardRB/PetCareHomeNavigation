package com.example.petcarehome.InicioYRegistro;

public class Dueno {
//7 campos a guardar en la bd

String nombre, apellidos, calle, noext, noint, colonia, alcaldia, telefono, correo, foto, tipo, contraseña;


    public Dueno(){

    }

    public Dueno(String nombre, String apellidos, String calle, String noext, String noint, String colonia, String alcaldia, String telefono, String correo, String foto, String tipo, String contraseña) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.calle = calle;
        this.noext = noext;
        this.noint = noint;
        this.colonia = colonia;
        this.alcaldia = alcaldia;
        this.telefono = telefono;
        this.correo = correo;
        this.foto = foto;
        this.tipo = tipo;
        this.contraseña = contraseña;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNoext() {
        return noext;
    }

    public void setNoext(String noext) {
        this.noext = noext;
    }

    public String getNoint() {
        return noint;
    }

    public void setNoint(String noint) {
        this.noint = noint;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getAlcaldia() {
        return alcaldia;
    }

    public void setAlcaldia(String alcaldia) {
        this.alcaldia = alcaldia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}


package com.example.petcarehome.InicioYRegistro;

import com.example.petcarehome.homenavigation.Objetos.LugarPetFriendly;
import com.example.petcarehome.homenavigation.Objetos.Mascota;
import com.example.petcarehome.homenavigation.Objetos.Servicio;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cuidador {
    String nombre, apellidos, calle, noext, noint, alcaldia, telefono, correo, foto, comentarios, estado, tipo;
    ArrayList<Mascota> mascotas;
    Servicio servicio;
    Double calificacion, lat, lng;


    public Cuidador(){

    }

    public Cuidador(String nombre, String apellidos, String calle, String noext, String noint, String alcaldia, String telefono, String correo, String foto, String comentarios, String estado, ArrayList<Mascota> mascotas, Servicio servicio, Double calificacion, Double lat, Double lng, String tipo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.calle = calle;
        this.noext = noext;
        this.noint = noint;
        this.alcaldia = alcaldia;
        this.telefono = telefono;
        this.correo = correo;
        this.foto = foto;
        this.comentarios = comentarios;
        this.estado = estado;
        this.mascotas = mascotas;
        this.servicio = servicio;
        this.calificacion = calificacion;
        this.lat = lat;
        this.lng = lng;
        this.tipo = tipo;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ArrayList<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(ArrayList<Mascota> mascotas) {
        this.mascotas = mascotas;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

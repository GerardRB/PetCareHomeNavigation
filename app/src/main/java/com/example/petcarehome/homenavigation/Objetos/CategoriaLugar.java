package com.example.petcarehome.homenavigation.Objetos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class CategoriaLugar implements Serializable {
    private String id;
    private String nombre;
    private String foto;

    public CategoriaLugar() {
    }

    public CategoriaLugar(String id, String nombre, String foto, ArrayList<LugarPetFriendly> lugares) {
        this.nombre = nombre;
        this.foto = foto;

    }

    public CategoriaLugar(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaLugar that = (CategoriaLugar) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package com.example.petcarehome.homenavigation.Objetos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class LugarPetFriendly implements Serializable {
    private String id;
    private String nombre;
    private String descripcion;
    private String foto;
    private String categoria;
    private int estrellas;
    private ArrayList<Resena> resenas;
    private ArrayList<String> fotosGaleria;

    public LugarPetFriendly() {
        this.resenas = new ArrayList<>();
        this.fotosGaleria = new ArrayList<>();
    }

    public LugarPetFriendly(String id) {
        this.id = id;
        this.estrellas = 0;
        this.resenas = new ArrayList<>();
        this.fotosGaleria = new ArrayList<>();
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

    public int getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(int estrellas) {
        this.estrellas = estrellas;
    }

    public ArrayList<Resena> getResenas() {
        return resenas;
    }

    public void setResenas(ArrayList<Resena> resenas) {
        this.resenas = resenas;
    }

    public ArrayList<String> getFotosGaleria() {
        return fotosGaleria;
    }

    public void setFotosGaleria(ArrayList<String> fotosGaleria) {
        this.fotosGaleria = fotosGaleria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LugarPetFriendly that = (LugarPetFriendly) o;
        if (that.id != null && id != null) {
            return id.equals(that.id);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class Resena implements Serializable {
        private int estrellas;
        private String comentario;
        private String autor;

        public Resena() {
        }

        public Resena(int estrellas, String comentario, String autor) {
            this.estrellas = estrellas;
            this.comentario = comentario;
            this.autor = autor;
        }

        public int getEstrellas() {
            return estrellas;
        }

        public void setEstrellas(int estrellas) {
            this.estrellas = estrellas;
        }

        public String getComentario() {
            return comentario;
        }

        public void setComentario(String comentario) {
            this.comentario = comentario;
        }

        public String getAutor() {
            return autor;
        }

        public void setAutor(String autor) {
            this.autor = autor;
        }
    }
}

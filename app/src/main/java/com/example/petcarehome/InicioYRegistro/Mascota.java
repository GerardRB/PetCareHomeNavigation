package com.example.petcarehome.InicioYRegistro;

public class Mascota {
    String tipo;

    public Mascota(String tipo) {
        this.tipo = tipo;
    }
    public Mascota(){

    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

package com.example.petcarehome.homenavigation.Objetos;

import java.io.Serializable;

public class Calificacion implements Serializable {
    private float c1, c2, c3, c4, c5, calificacion;
    private String comentarios;

    public Calificacion() {
    }

    public Calificacion(float c1, float c2, float c3, float c4, float c5, float calificacion, String comentarios) {
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        this.c5 = c5;
        this.calificacion = calificacion;
        this.comentarios = comentarios;
    }

    public float getC1() {
        return c1;
    }

    public void setC1(float c1) {
        this.c1 = c1;
    }

    public float getC2() {
        return c2;
    }

    public void setC2(float c2) {
        this.c2 = c2;
    }

    public float getC3() {
        return c3;
    }

    public void setC3(float c3) {
        this.c3 = c3;
    }

    public float getC4() {
        return c4;
    }

    public void setC4(float c4) {
        this.c4 = c4;
    }

    public float getC5() {
        return c5;
    }

    public void setC5(float c5) {
        this.c5 = c5;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

}

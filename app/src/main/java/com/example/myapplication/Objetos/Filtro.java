package com.example.myapplication.Objetos;

import java.io.Serializable;

public class Filtro implements Serializable {
    private String zona, tipoM, fecha1, fecha2;

    public Filtro() {
    }

    public Filtro(String zona, String tipoM, String fecha1, String fecha2) {
        this.zona = zona;
        this.tipoM = tipoM;
        this.fecha1 = fecha1;
        this.fecha2 = fecha2;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getTipoM() {
        return tipoM;
    }

    public void setTipoM(String tipoM) {
        this.tipoM = tipoM;
    }

    public String getFecha1() {
        return fecha1;
    }

    public void setFecha1(String fecha1) {
        this.fecha1 = fecha1;
    }

    public String getFecha2() {
        return fecha2;
    }

    public void setFecha2(String fecha2) {
        this.fecha2 = fecha2;
    }
}

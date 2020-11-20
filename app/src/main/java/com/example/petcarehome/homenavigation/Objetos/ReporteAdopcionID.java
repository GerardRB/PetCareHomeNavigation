package com.example.petcarehome.homenavigation.Objetos;

import java.io.Serializable;

public class ReporteAdopcionID implements Serializable {

    private String id;
    private ReporteAdopcion reporteAdopcion;

    public ReporteAdopcionID() {
    }

    public ReporteAdopcionID(String id, ReporteAdopcion reporteAdopcion) {
        this.id = id;
        this.reporteAdopcion = reporteAdopcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReporteAdopcion getReporteAdopcion() {
        return reporteAdopcion;
    }

    public void setReporteAdopcion(ReporteAdopcion reporteAdopcion) {
        this.reporteAdopcion = reporteAdopcion;
    }
}

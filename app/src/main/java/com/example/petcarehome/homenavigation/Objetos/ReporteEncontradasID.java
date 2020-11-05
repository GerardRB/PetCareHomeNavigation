package com.example.petcarehome.homenavigation.Objetos;

import java.io.Serializable;

public class ReporteEncontradasID implements Serializable {

    private String id;
    private ReporteEncontradas reporteEncontradas;

    public ReporteEncontradasID() {
    }

    public ReporteEncontradasID(String id, ReporteEncontradas reporteEncontradas) {
        this.id = id;
        this.reporteEncontradas = reporteEncontradas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReporteEncontradas getReporteEncontradas() {
        return reporteEncontradas;
    }

    public void setReporteEncontradas(ReporteEncontradas reporteEncontradas) {
        this.reporteEncontradas = reporteEncontradas;
    }
}

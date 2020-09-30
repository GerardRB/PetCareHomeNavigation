package com.example.petcarehome.homenavigation.Objetos;

import java.io.Serializable;

public class ReportePerdidasID implements Serializable {
    
    private String id;
    private ReportePerdidas reportePerdidas;

    public ReportePerdidasID() {
    }

    public ReportePerdidasID(String id, ReportePerdidas reportePerdidas) {
        this.id = id;
        this.reportePerdidas = reportePerdidas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReportePerdidas getReportePerdidas() {
        return reportePerdidas;
    }

    public void setReportePerdidas(ReportePerdidas reportePerdidas) {
        this.reportePerdidas = reportePerdidas;
    }
}

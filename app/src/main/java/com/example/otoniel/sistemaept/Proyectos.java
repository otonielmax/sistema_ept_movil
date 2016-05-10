package com.example.otoniel.sistemaept;

import java.io.Serializable;

/**
 * Created by Otoniel on 06/03/2016.
 */
public class Proyectos implements Serializable {
    int idProyecto;
    int avanceFinanciero;
    int avanceFisico;
    String nombreProyecto;
    String descripcion;
    float monto;
    String estatus;
    int periodo;
    String correlativo;

    public String getCorrelativo() {
        return this.correlativo;
    }

    public void setCorrelativo(String correlativo) {
        this.correlativo = correlativo;
    }

    public int getPeriodo() {
        return this.periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public String getEstatus() {
        return this.estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public int getIdProyecto() {
        return this.idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public int getAvanceFinanciero() {
        return this.avanceFinanciero;
    }

    public void setAvanceFinanciero(int avanceFinanciero) {
        this.avanceFinanciero = avanceFinanciero;
    }

    public int getAvanceFisico() {
        return this.avanceFisico;
    }

    public void setAvanceFisico(int avanceFisico) {
        this.avanceFisico = avanceFisico;
    }

    public String getNombreProyecto() {
        return this.nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMonto() {
        String mensaje = "" + this.monto;
        return mensaje;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public Proyectos() {

    }
}

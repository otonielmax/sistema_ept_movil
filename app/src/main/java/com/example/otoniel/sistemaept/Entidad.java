package com.example.otoniel.sistemaept;

/**
 * Created by Otoniel on 06/03/2016.
 */
public class Entidad {
    int evento;
    int idUsuario;
    String nombreEntidad;

    int primerTramo;
    int segundoTramo;
    int tercerTramo;
    int cuartoTramo;
    int tam;

    public int getEvento() {
        return this.evento;
    }

    public void setEvento(int evento) {
        this.evento = evento;
    }

    public String getNombreEntidad() {
        return this.nombreEntidad;
    }

    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    public int getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getPrimerTramo() {
        return this.primerTramo;
    }

    public void setPrimerTramo(int primerTramo) {
        this.primerTramo = primerTramo;
    }

    public int getSegundoTramo() {
        return this.segundoTramo;
    }

    public void setSegundoTramo(int segundoTramo) {
        this.segundoTramo = segundoTramo;
    }

    public int getTercerTramo() {
        return this.tercerTramo;
    }

    public void setTercerTramo(int tercerTramo) {
        this.tercerTramo = tercerTramo;
    }

    public int getCuartoTramo() {
        return this.cuartoTramo;
    }

    public void setCuartoTramo(int cuartoTramo) {
        this.cuartoTramo = cuartoTramo;
    }

    public int getTam() {
        this.tam = this.getPrimerTramo() + this.getSegundoTramo() + this.getTercerTramo() + this.getCuartoTramo();
        return this.tam;
    }
    public Entidad() {

    }
}

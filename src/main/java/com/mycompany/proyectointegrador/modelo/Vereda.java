/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.modelo;

/**
 *
 * @author edward
 */




public class Vereda {
    private String id;
    private String codigoDane;
    private String nombre;
    private Municipio municipio;

    public Vereda(String id, String codigoDane, String nombre, Municipio municipio) {
        this.id = id;
        this.codigoDane = codigoDane;
        this.nombre = nombre;
        this.municipio = municipio;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCodigoDane() { return codigoDane; }
    public void setCodigoDane(String codigoDane) { this.codigoDane = codigoDane; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Municipio getMunicipio() { return municipio; }
    public void setMunicipio(Municipio municipio) { this.municipio = municipio; }

    @Override
    public String toString() {
        return "Vereda{" + "id=" + id + ", codigoDane=" + codigoDane + ", nombre=" + nombre +
                ", municipio=" + municipio.getNombre() + '}';
    }
}



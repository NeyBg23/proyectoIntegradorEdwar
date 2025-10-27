/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.modelo;

/**
 *
 * @author edward
 */




public class LugarDeProduccion {
    private String id;
    private String numeroRegistro;
    private String nombre;
    private Predio predio;

    public LugarDeProduccion(String id, String numeroRegistro, String nombre, Predio predio) {
        this.id = id;
        this.numeroRegistro = numeroRegistro;
        this.nombre = nombre;
        this.predio = predio;
    }

    public String getId() { return id; }
    public String getNumeroRegistro() { return numeroRegistro; }
    public String getNombre() { return nombre; }
    public Predio getPredio() { return predio; }

    public void setId(String id) { this.id = id; }
    public void setNumeroRegistro(String numeroRegistro) { this.numeroRegistro = numeroRegistro; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPredio(Predio predio) { this.predio = predio; }

    @Override
    public String toString() {
        return "LugarDeProduccion{" + "id=" + id + ", numeroRegistro=" + numeroRegistro + ", nombre=" + nombre +
                ", predio=" + predio.getNombre() + '}';
    }
}

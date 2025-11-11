/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.modelo;

public class Departamento {
    private int id;
    private String codigoDane;
    private String nombre;
    
    public Departamento(int id, String codigoDane, String nombre) {
        this.id = id;
        this.codigoDane = codigoDane;
        this.nombre = nombre;
    }
    
    public Departamento(String codigoDane, String nombre) {
        this.codigoDane = codigoDane;
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return nombre; // Solo mostrar el nombre
    }

    
    public int getId() { return id; }
    public String getCodigoDane() { return codigoDane; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.modelo;

/**
 *
 * @author edward
 */




public class Departamento {
    private int id;
    private String codigoDane;
    private String nombre; // ✅ Este debe ser String, NO int

    public Departamento() {}

    public Departamento(int id, String codigoDane, String nombre) {
        this.id = id;
        this.codigoDane = codigoDane;
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCodigoDane() { return codigoDane; }
    public void setCodigoDane(String codigoDane) { this.codigoDane = codigoDane; }

    public String getNombre() { return nombre; } // ✅ Retorna String
    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() {
        return nombre; // 👈 También puedes devolver solo el nombre
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.modelo;

/**
 *
 * @author edward
 */

public class Lote {
    private final String id;
    private final String codigo;
    private final String nombre;
    private final float area;
    private final LugarDeProduccion lugarDeProduccion;

    public Lote(String id, String codigo, String nombre, float area, LugarDeProduccion lugarDeProduccion) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.area = area;
        this.lugarDeProduccion = lugarDeProduccion;
    }

    // Getters y setters

    @Override
    public String toString() {
        return "Lote{" + "id=" + id + ", codigo=" + codigo + ", nombre=" + nombre + ", area=" + area +
                ", lugar=" + lugarDeProduccion.getNombre() + '}';
    }
}

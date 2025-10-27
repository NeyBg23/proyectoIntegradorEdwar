/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.modelo;

/**
 *
 * @author edward
 */




/**
 * Representa una especie vegetal registrada para inspección fitosanitaria.
 * 
 * @author edward
 */


public class EspecieVegetal {
    private int id;
    private String codigo;
    private String nombre;
    private float densidad;
    private String descripcion;

    public EspecieVegetal(int id, String codigo, String nombre, float densidad, String descripcion) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.densidad = densidad;
        this.descripcion = descripcion;
    }

    // ===== Getters y Setters =====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public float getDensidad() { return densidad; }
    public void setDensidad(float densidad) { this.densidad = densidad; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() {
        return "EspecieVegetal{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", densidad=" + densidad +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}

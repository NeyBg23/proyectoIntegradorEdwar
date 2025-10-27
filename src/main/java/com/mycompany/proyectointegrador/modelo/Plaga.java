/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.modelo;

/**
 *
 * @author edward
 */
 






public class Plaga {
    private String codigo;
    private String nombre;
    private String descripcion;
    private float nivelIncidencia;
    private String nivelAlerta;

    public Plaga(String codigo, String nombre, String descripcion, float nivelIncidencia, String nivelAlerta) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nivelIncidencia = nivelIncidencia;
        this.nivelAlerta = nivelAlerta;
    }

    
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public float getNivelIncidencia() { return nivelIncidencia; }
    public void setNivelIncidencia(float nivelIncidencia) { this.nivelIncidencia = nivelIncidencia; }

    public String getNivelAlerta() { return nivelAlerta; }
    public void setNivelAlerta(String nivelAlerta) { this.nivelAlerta = nivelAlerta; }

    @Override
    public String toString() {
        return "Plaga{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", nivelIncidencia=" + nivelIncidencia +
                ", nivelAlerta='" + nivelAlerta + '\'' +
                '}';
    }
}



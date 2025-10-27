/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.modelo;

/**
 *
 * @author edward
 */


public class Asistente {
    private String id;
    private String nombre;
    private String identificacion;
    private String telefono;
    private String correo;
    private String tarjetaProfesional;

    public Asistente(String id, String nombre, String identificacion, String telefono, String correo, String tarjetaProfesional) {
        this.id = id;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.telefono = telefono;
        this.correo = correo;
        this.tarjetaProfesional = tarjetaProfesional;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTarjetaProfesional() { return tarjetaProfesional; }
    public void setTarjetaProfesional(String tarjetaProfesional) { this.tarjetaProfesional = tarjetaProfesional; }

    @Override
    public String toString() {
        return nombre + " (" + tarjetaProfesional + ")";
    }
}

















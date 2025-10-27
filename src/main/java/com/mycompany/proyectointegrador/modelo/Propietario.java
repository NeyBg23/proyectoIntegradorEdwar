/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.modelo;

/**
 * Representa un propietario de predios agrícolas
 * 
 * @author edward
 */
public class Propietario {
    private String id;
    private String nombre;
    private String identificacion;
    private String telefono;
    private String correo;

    /**
     * Constructor completo con ID (para leer de base de datos)
     */
    public Propietario(String id, String nombre, String identificacion, String telefono, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.telefono = telefono;
        this.correo = correo;
    }
    
    /**
     * Constructor sin ID (para insertar nuevos propietarios)
     * El ID se generará automáticamente en la base de datos
     */
    public Propietario(String nombre, String identificacion, String telefono, String correo) {
        this.id = null; // Se generará en la BD
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.telefono = telefono;
        this.correo = correo;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return nombre + " (" + identificacion + ")";
    }
}

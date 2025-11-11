/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.modelo;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Clase modelo para Propietario
 * Representa a los productores agrícolas del sistema
 * @author Equipo Proyecto Integrador
 * @version 1.0
 */
public class Propietario {
    
    private UUID id;
    private String identificacion;
    private String nombre;
    private String telefono;
    private String correo;
    private LocalDateTime fechaRegistro;
    
    // Constructor vacío
    public Propietario() {
        this.fechaRegistro = LocalDateTime.now();
    }
    
    // Constructor completo
    public Propietario(UUID id, String identificacion, String nombre, String telefono, 
                      String correo, LocalDateTime fechaRegistro) {
        this.id = id;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaRegistro = fechaRegistro;
    }
    
    // Constructor para nuevos registros (sin ID ni fecha)
    public Propietario(String identificacion, String nombre, String telefono, String correo) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaRegistro = LocalDateTime.now();
    }
    
    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    @Override
    public String toString() {
        return "Propietario{" +
                "id=" + id +
                ", identificacion='" + identificacion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}

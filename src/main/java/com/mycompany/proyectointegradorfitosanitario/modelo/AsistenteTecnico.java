/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.modelo;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Clase modelo para Asistente Técnico
 * @author Equipo Proyecto Integrador
 * @version 1.0
 */
public class AsistenteTecnico {
    
    private UUID id;
    private String identificacion;
    private String nombre;
    private String telefono;
    private String correo;
    private String tarjetaProfesional;
    private String tipo; // OFICIAL o PARTICULAR
    private LocalDateTime fechaRegistro;
    
    public AsistenteTecnico() {
        this.fechaRegistro = LocalDateTime.now();
    }
    
    public AsistenteTecnico(UUID id, String identificacion, String nombre, String telefono,
                           String correo, String tarjetaProfesional, String tipo, LocalDateTime fechaRegistro) {
        this.id = id;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.tarjetaProfesional = tarjetaProfesional;
        this.tipo = tipo;
        this.fechaRegistro = fechaRegistro;
    }
    
    public AsistenteTecnico(String identificacion, String nombre, String telefono,
                           String correo, String tarjetaProfesional, String tipo) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.tarjetaProfesional = tarjetaProfesional;
        this.tipo = tipo;
        this.fechaRegistro = LocalDateTime.now();
    }
    
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
    
    public String getTarjetaProfesional() { return tarjetaProfesional; }
    public void setTarjetaProfesional(String tarjetaProfesional) { this.tarjetaProfesional = tarjetaProfesional; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    @Override
    public String toString() {
        return "AsistenteTecnico{" + "id=" + id + ", identificacion='" + identificacion + '\'' +
                ", nombre='" + nombre + '\'' + ", tipo='" + tipo + '\'' + '}';
    }
}


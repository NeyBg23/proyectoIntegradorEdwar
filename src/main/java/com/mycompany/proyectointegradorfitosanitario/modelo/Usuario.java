/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.modelo;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Clase modelo para Usuario
 * Representa a los usuarios del sistema (ADMIN, PROPIETARIO, ASISTENTE)
 * @author Equipo Proyecto Integrador
 * @version 1.0
 */
public class Usuario {
    
    // Atributos
    private UUID id;
    private String username;
    private String passwordHash;
    private String rol;
    private String email;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
    
    // Enum para roles
    public enum Rol {
        ADMIN, PROPIETARIO, ASISTENTE
    }
    
    // Constructor vacío
    public Usuario() {
        this.activo = true;
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Constructor completo
    public Usuario(UUID id, String username, String passwordHash, String rol, 
                   String email, Boolean activo, LocalDateTime fechaCreacion) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.rol = rol;
        this.email = email;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
    }
    
    // Constructor para nuevos usuarios (sin ID ni fecha)
    public Usuario(String username, String passwordHash, String rol, String email) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.rol = rol;
        this.email = email;
        this.activo = true;
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Getters y Setters
    
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }
    
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", rol='" + rol + '\'' +
                ", email='" + email + '\'' +
                ", activo=" + activo +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}

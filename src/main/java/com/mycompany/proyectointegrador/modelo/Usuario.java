/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.modelo;

/**
 * Modelo para un usuario del sistema.
 * Soporta roles: admin, propietario, asistente.
 */
public class Usuario {
    private String id;           // UUID generado por la base de datos
    private String nombreUsuario;
    private String contrasena;   // ¡Importante: considera almacenar cifrada!
    private String rol;          // admin, propietario, asistente
    private boolean estado;
    
    // Constructor completo
    public Usuario(String id, String nombreUsuario, String contrasena, String rol, boolean estado) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.estado = estado;
    }

    // Constructor sin ID (para insertar nuevos)
    public Usuario(String nombreUsuario, String contrasena, String rol) {
        this(null, nombreUsuario, contrasena, rol, true);
    }
    
    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }

    @Override
    public String toString() {
        return nombreUsuario + " (" + rol + ")";
    }
}

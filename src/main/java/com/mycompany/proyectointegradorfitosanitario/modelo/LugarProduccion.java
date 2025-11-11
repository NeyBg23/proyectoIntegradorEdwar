/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.modelo;

import java.time.LocalDateTime;
import java.util.UUID;

public class LugarProduccion {
    private UUID id;
    private String nombre;
    private String descripcion;
    private UUID propietarioId;
    private String tipoProduccion;
    private LocalDateTime fechaRegistro;
    
    public LugarProduccion(UUID id, String nombre, String descripcion, UUID propietarioId, 
                           String tipoProduccion, LocalDateTime fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.propietarioId = propietarioId;
        this.tipoProduccion = tipoProduccion;
        this.fechaRegistro = fechaRegistro;
    }
    
    public LugarProduccion(String nombre, String descripcion, UUID propietarioId, String tipoProduccion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.propietarioId = propietarioId;
        this.tipoProduccion = tipoProduccion;
        this.fechaRegistro = LocalDateTime.now();
    }
    
    public UUID getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public UUID getPropietarioId() { return propietarioId; }
    public String getTipoProduccion() { return tipoProduccion; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
}


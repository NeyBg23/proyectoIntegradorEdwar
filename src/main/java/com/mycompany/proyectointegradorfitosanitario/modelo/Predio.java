/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.modelo;

import java.time.LocalDateTime;
import java.util.UUID;

public class Predio {
    private UUID id;
    private String nombre;
    private String numeroRegistro;
    private Double areaHectareas;
    private UUID propietarioId;
    private Integer veredaId;
    private LocalDateTime fechaRegistro;
    
    public Predio(UUID id, String nombre, String numeroRegistro, Double areaHectareas, 
                  UUID propietarioId, Integer veredaId, LocalDateTime fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.numeroRegistro = numeroRegistro;
        this.areaHectareas = areaHectareas;
        this.propietarioId = propietarioId;
        this.veredaId = veredaId;
        this.fechaRegistro = fechaRegistro;
    }
    
    public Predio(String nombre, String numeroRegistro, Double areaHectareas, UUID propietarioId, Integer veredaId) {
        this.nombre = nombre;
        this.numeroRegistro = numeroRegistro;
        this.areaHectareas = areaHectareas;
        this.propietarioId = propietarioId;
        this.veredaId = veredaId;
        this.fechaRegistro = LocalDateTime.now();
    }
    
    public UUID getId() { return id; }
    public String getNombre() { return nombre; }
    public String getNumeroRegistro() { return numeroRegistro; }
    public Double getAreaHectareas() { return areaHectareas; }
    public UUID getPropietarioId() { return propietarioId; }
    public Integer getVeredaId() { return veredaId; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    
    @Override
    public String toString() {
        return getNombre() + " (ICA: " + getNumeroRegistro() + ")";
    }

}


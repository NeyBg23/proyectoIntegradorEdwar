/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.modelo;

/**
 *
 * @author edward
 */



public class InformeInspeccion {
    private String id;
    private String codigo;
    private int plantasAfectadas;
    private int plantasTotales;
    private int plantasAproximadas;
    private String observacion;
    private String recomendaciones;

    public InformeInspeccion(String id, String codigo, int plantasAfectadas, int plantasTotales,
                             int plantasAproximadas, String observacion, String recomendaciones) {
        this.id = id;
        this.codigo = codigo;
        this.plantasAfectadas = plantasAfectadas;
        this.plantasTotales = plantasTotales;
        this.plantasAproximadas = plantasAproximadas;
        this.observacion = observacion;
        this.recomendaciones = recomendaciones;
    }

    // Getters y setters
}


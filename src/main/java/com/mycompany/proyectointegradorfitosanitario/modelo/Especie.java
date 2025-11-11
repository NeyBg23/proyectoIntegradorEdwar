/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.modelo;

public class Especie {
    private int id;
    private String codigo;
    private String nombreCientifico;
    private String nombreComun;
    private Integer densidadSiembra;
    
    public Especie(int id, String codigo, String nombreCientifico, String nombreComun, Integer densidadSiembra) {
        this.id = id;
        this.codigo = codigo;
        this.nombreCientifico = nombreCientifico;
        this.nombreComun = nombreComun;
        this.densidadSiembra = densidadSiembra;
    }
    
    public Especie(String codigo, String nombreCientifico, String nombreComun, Integer densidadSiembra) {
        this.codigo = codigo;
        this.nombreCientifico = nombreCientifico;
        this.nombreComun = nombreComun;
        this.densidadSiembra = densidadSiembra;
    }
    
    public int getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getNombreCientifico() { return nombreCientifico; }
    public String getNombreComun() { return nombreComun; }
    public Integer getDensidadSiembra() { return densidadSiembra; }
}

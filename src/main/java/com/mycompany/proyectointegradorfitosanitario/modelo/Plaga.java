/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.modelo;

public class Plaga {
    private String codigo;
    private String nombreCientifico;
    private String nombreComun;
    private String nivelAlerta;
    
    public Plaga(String codigo, String nombreCientifico, String nombreComun, String nivelAlerta) {
        this.codigo = codigo;
        this.nombreCientifico = nombreCientifico;
        this.nombreComun = nombreComun;
        this.nivelAlerta = nivelAlerta;
    }
    
    public String getCodigo() { return codigo; }
    public String getNombreCientifico() { return nombreCientifico; }
    public String getNombreComun() { return nombreComun; }
    public String getNivelAlerta() { return nivelAlerta; }
}

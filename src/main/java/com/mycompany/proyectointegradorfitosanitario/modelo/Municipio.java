/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.modelo;

public class Municipio {
    private int id;
    private String codigo;
    private String nombre;
    private int departamentoId;
    
    public Municipio(int id, String codigo, String nombre, int departamentoId) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.departamentoId = departamentoId;
    }
    
    public int getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int getDepartamentoId() { return departamentoId; }
    
    @Override
    public String toString() { return nombre; }
}


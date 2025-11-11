/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.modelo;

public class Vereda {
    private int id;
    private String nombre;
    private int municipioId;
    
    public Vereda(int id, String nombre, int municipioId) {
        this.id = id;
        this.nombre = nombre;
        this.municipioId = municipioId;
    }
    
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getMunicipioId() { return municipioId; }
    
    @Override
    public String toString() { return nombre; }
}

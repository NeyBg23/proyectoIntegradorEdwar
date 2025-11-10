package com.mycompany.proyectointegrador.modelo;

public class Municipio {
    private int id;
    private String codigoDane;
    private String nombre;
    private int departamentoId;

    public Municipio(int id, String codigoDane, String nombre, int departamentoId) {
        this.id = id;
        this.codigoDane = codigoDane;
        this.nombre = nombre;
        this.departamentoId = departamentoId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCodigoDane() { return codigoDane; }
    public void setCodigoDane(String codigoDane) { this.codigoDane = codigoDane; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getDepartamentoId() { return departamentoId; }
    public void setDepartamentoId(int departamentoId) { this.departamentoId = departamentoId; }
}

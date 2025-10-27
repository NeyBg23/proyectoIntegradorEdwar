/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.modelo;

/**
 *
 * @author edward
 */




import java.util.ArrayList;

public class InspeccionFitosanitaria {
    private String id;
    private String codigo;
    private String fecha;
    private int trimestre;
    private ArrayList<InformeInspeccion> informes;

    public InspeccionFitosanitaria(String id, String codigo, String fecha, int trimestre) {
        this.id = id;
        this.codigo = codigo;
        this.fecha = fecha;
        this.trimestre = trimestre;
        this.informes = new ArrayList<>();
    }

    public void agregarInforme(InformeInspeccion informe) {
        informes.add(informe);
    }

    public ArrayList<InformeInspeccion> getInformes() { return informes; }
}

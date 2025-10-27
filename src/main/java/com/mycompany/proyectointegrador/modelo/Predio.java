/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.modelo;

/**
 * Representa un predio agrícola registrado en el sistema.
 * Un predio pertenece a un propietario y está ubicado en una vereda.
 * 
 * @author edward
 */
public class Predio {
    // Atributos
    private String id;
    private String numeroRegistro;
    private String nombre;
    private float area;
    private Vereda vereda;
    private String propietarioId; // ✅ AGREGADO: ID del propietario

    // ========== CONSTRUCTORES ==========
    
    /**
     * Constructor completo con Vereda (usado internamente en la aplicación)
     * @param id Identificador único del predio
     * @param numeroRegistro Número de registro del predio
     * @param nombre Nombre del predio
     * @param area Área del predio en hectáreas
     * @param vereda Vereda donde está ubicado el predio
     */
    public Predio(String id, String numeroRegistro, String nombre, float area, Vereda vereda) {
        this.id = id;
        this.numeroRegistro = numeroRegistro;
        this.nombre = nombre;
        this.area = area;
        this.vereda = vereda;
        this.propietarioId = null; // Se puede asignar después
    }

    /**
     * Constructor para uso con DAO (cuando se lee de la base de datos)
     * @param id Identificador único del predio
     * @param nombre Nombre del predio
     * @param propietarioId ID del propietario
     */
    public Predio(String id, String nombre, String propietarioId) {
        this.id = id;
        this.nombre = nombre;
        this.propietarioId = propietarioId;
        this.numeroRegistro = null;
        this.area = 0.0f;
        this.vereda = null;
    }

    /**
     * Constructor vacío (requerido por algunos frameworks)
     */
    public Predio() {
        // Constructor vacío
    }

    // ========== GETTERS Y SETTERS ==========
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public Vereda getVereda() {
        return vereda;
    }

    public void setVereda(Vereda vereda) {
        this.vereda = vereda;
    }

    /**
     * ✅ CORREGIDO: Ahora devuelve el propietarioId correctamente
     * @return ID del propietario del predio
     */
    public String getPropietarioId() {
        return propietarioId;
    }

    /**
     * ✅ AGREGADO: Setter para el propietarioId
     * @param propietarioId ID del propietario
     */
    public void setPropietarioId(String propietarioId) {
        this.propietarioId = propietarioId;
    }

    // ========== MÉTODO TOSTRING ==========
    
    @Override
    public String toString() {
        if (vereda != null) {
            return "Predio{" +
                    "id='" + id + '\'' +
                    ", numeroRegistro='" + numeroRegistro + '\'' +
                    ", nombre='" + nombre + '\'' +
                    ", area=" + area +
                    ", vereda=" + vereda.getNombre() +
                    ", propietarioId='" + propietarioId + '\'' +
                    '}';
        } else {
            return "Predio{" +
                    "id='" + id + '\'' +
                    ", nombre='" + nombre + '\'' +
                    ", propietarioId='" + propietarioId + '\'' +
                    '}';
        }
    }
}

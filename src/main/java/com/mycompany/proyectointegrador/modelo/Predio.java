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
    private String propietarioId;

    // ========== CONSTRUCTORES ==========
    
    /**
     * Constructor completo con Vereda
     */
    public Predio(String id, String numeroRegistro, String nombre, float area, Vereda vereda) {
        this.id = id;
        this.numeroRegistro = numeroRegistro;
        this.nombre = nombre;
        this.area = area;
        this.vereda = vereda;
        this.propietarioId = null;
    }

    /**
     * Constructor para DAO (lectura de BD)
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
     * Constructor simple para uso general
     */
    public Predio(String id, String nombre, float area, Vereda vereda) {
        this.id = id;
        this.nombre = nombre;
        this.area = area;
        this.vereda = vereda;
        this.numeroRegistro = null;
        this.propietarioId = null;
    }

    /**
     * Constructor vacío
     */
    public Predio() {
        this.id = null;
        this.numeroRegistro = null;
        this.nombre = null;
        this.area = 0.0f;
        this.vereda = null;
        this.propietarioId = null;
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

    public String getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(String propietarioId) {
        this.propietarioId = propietarioId;
    }

    /**
     * Obtiene la ubicación como String (nombre de la vereda)
     */
    public String getUbicacion() {
        return vereda != null ? vereda.getNombre() : "Sin vereda";
    }

    // ========== MÉTODO TOSTRING ==========
    
    @Override
    public String toString() {
        if (vereda != null) {
            return "Predio{" +
                    "id='" + id + '\'' +
                    ", nombre='" + nombre + '\'' +
                    ", area=" + area +
                    ", vereda=" + vereda.getNombre() +
                    ", propietarioId='" + propietarioId + '\'' +
                    '}';
        } else {
            return "Predio{" +
                    "id='" + id + '\'' +
                    ", nombre='" + nombre + '\'' +
                    ", area=" + area +
                    ", propietarioId='" + propietarioId + '\'' +
                    '}';
        }
    }
}

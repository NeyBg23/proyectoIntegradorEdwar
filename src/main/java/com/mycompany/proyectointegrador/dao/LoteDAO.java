/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Data Access Object para Lotes (Predios)
 * Maneja operaciones CRUD con la tabla lotes en Supabase
 * 
 * Tabla: lotes (o predios, según tu estructura)
 * Campos: id, nombre, ubicacion, area, propietario_id
 * 
 * @author edward
 */
public class LoteDAO {

    /**
     * Obtiene todos los lotes disponibles
     * @return Map con nombre del lote como clave e ID como valor
     * @throws SQLException Si ocurre error en la BD
     */
    public Map<String, Integer> listar() throws SQLException {
        Map<String, Integer> mapaLotes = new HashMap<>();
        
        // Intenta primero con tabla "lotes"
        String sql = "SELECT id, nombre FROM lotes ORDER BY nombre";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                mapaLotes.put(nombre, id);
            }
            
            System.out.println("✅ Lotes cargados correctamente: " + mapaLotes.size());
            
        } catch (SQLException e) {
            // Si no existe tabla "lotes", intenta con "predios"
            System.out.println("⚠️ Tabla 'lotes' no encontrada, intentando con 'predios'...");
            sql = "SELECT id, nombre FROM predios ORDER BY nombre";
            
            try (Connection conn = DatabaseConnection.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    mapaLotes.put(nombre, id);
                }
                
                System.out.println("✅ Predios cargados correctamente: " + mapaLotes.size());
            }
        }
        
        return mapaLotes;
    }

    /**
     * Obtiene un lote por su ID
     * @param id ID del lote
     * @return Nombre del lote o null si no existe
     * @throws SQLException Si ocurre error en la BD
     */
    public String obtenerNombre(int id) throws SQLException {
        String sql = "SELECT nombre FROM lotes WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombre");
                }
            }
        }
        
        return null;
    }

    /**
     * Cuenta el total de lotes
     * @return Total de lotes en la BD
     * @throws SQLException Si ocurre error en la BD
     */
    public int contar() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM lotes";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        
        return 0;
    }
}

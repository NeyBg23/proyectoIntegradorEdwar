/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.dao;

/**
 *
 * @author NeyBg
 */


import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * DAO para cargar veredas
 */
public class VeredaDAO {
    
    /**
     * Obtiene todas las veredas
     * @return Map con nombre → ID
     * @throws SQLException
     */
    public Map<String, Integer> listarVeredas() throws SQLException {
        Map<String, Integer> mapa = new HashMap<>();
        String sql = "SELECT id, nombre FROM veredas ORDER BY nombre";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                mapa.put(nombre, id);
            }
        }
        
        return mapa;
    }
}

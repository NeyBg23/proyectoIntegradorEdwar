/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.dao;

import com.mycompany.proyectointegradorfitosanitario.config.DatabaseConnection;
import com.mycompany.proyectointegradorfitosanitario.modelo.LugarProduccion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LugarProduccionDAO {
    
    public boolean insertar(LugarProduccion lugar) {
        String sql = """
            INSERT INTO lugares_produccion (nombre, propietario_id, numero_registro_ica, descripcion)
            VALUES (?, ?, 
                'LP-' || EXTRACT(YEAR FROM NOW()) || '-' || 
                LPAD(CAST((SELECT COUNT(*) + 1 FROM lugares_produccion) AS VARCHAR), 4, '0'),
            ?)
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, lugar.getNombre());
            stmt.setObject(2, lugar.getPropietarioId());
            stmt.setString(3, lugar.getDescripcion());
            
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Lugar de producción insertado: " + lugar.getNombre());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar lugar: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public List<LugarProduccion> listarPorPropietario(UUID propietarioId) {
        List<LugarProduccion> lugares = new ArrayList<>();
        String sql = """
            SELECT id, nombre, numero_registro_ica, propietario_id, descripcion, fecha_registro
            FROM lugares_produccion
            WHERE propietario_id = ?
            ORDER BY nombre
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, propietarioId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                lugares.add(new LugarProduccion(
                    (UUID) rs.getObject("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    (UUID) rs.getObject("propietario_id"),
                    "",
                    rs.getTimestamp("fecha_registro").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar lugares: " + e.getMessage());
        }
        return lugares;
    }
}

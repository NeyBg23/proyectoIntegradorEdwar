/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.dao;

import com.mycompany.proyectointegradorfitosanitario.config.DatabaseConnection;
import com.mycompany.proyectointegradorfitosanitario.modelo.Predio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PredioDAO {
    
    public boolean insertar(Predio predio) {
        String numeroRegistro = generarNumeroRegistroICA(); // ← Generar automáticamente

        String sql = """
            INSERT INTO predios (nombre, numero_registro, area_hectareas, propietario_id, vereda_id)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, predio.getNombre());
            stmt.setString(2, numeroRegistro); // ← Usar el generado
            stmt.setDouble(3, predio.getAreaHectareas());
            stmt.setObject(4, predio.getPropietarioId());
            stmt.setInt(5, predio.getVeredaId());

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Predio insertado: " + predio.getNombre());
                System.out.println("   Número Registro: " + numeroRegistro);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar predio: " + e.getMessage());
        }
        return false;
    }

    
    public List<Predio> listarPorPropietario(UUID propietarioId) {
        List<Predio> predios = new ArrayList<>();
        String sql = """
            SELECT id, nombre, numero_registro, area_hectareas, propietario_id, vereda_id, fecha_registro
            FROM predios
            WHERE propietario_id = ?
            ORDER BY nombre
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, propietarioId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                predios.add(new Predio(
                    (UUID) rs.getObject("id"),
                    rs.getString("nombre"),
                    rs.getString("numero_registro"),
                    rs.getDouble("area_hectareas"),
                    (UUID) rs.getObject("propietario_id"),
                    rs.getInt("vereda_id"),
                    rs.getTimestamp("fecha_registro").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al listar predios: " + e.getMessage());
        }
        return predios;
    }
    
    /**
    * Genera número de registro ICA automáticamente
    * Formato: ICA-YYYY-000X
    */
   private String generarNumeroRegistroICA() {
       String sql = "SELECT COUNT(*) FROM predios";
       try (Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

           if (rs.next()) {
               int cantidad = rs.getInt(1) + 1; // Próximo número
               return String.format("ICA-%d-%04d", 2025, cantidad);
           }
       } catch (SQLException e) {
           System.err.println("❌ Error: " + e.getMessage());
       }
       return "ICA-2025-0001"; // Default
   }

}

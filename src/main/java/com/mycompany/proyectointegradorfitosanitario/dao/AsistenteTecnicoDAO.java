/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.dao;

import com.mycompany.proyectointegradorfitosanitario.config.DatabaseConnection;
import com.mycompany.proyectointegradorfitosanitario.modelo.AsistenteTecnico;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Data Access Object para la tabla asistentes_tecnicos
 * @author Equipo Proyecto Integrador
 * @version 1.0
 */
public class AsistenteTecnicoDAO {
    
    public boolean insertar(AsistenteTecnico asistente) {
        String sql = """
            INSERT INTO asistentes_tecnicos (id, identificacion, nombre, telefono, correo, tarjeta_profesional, tipo, usuario_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, asistente.getId()); // Usar el ID del usuario
            stmt.setString(2, asistente.getIdentificacion());
            stmt.setString(3, asistente.getNombre());
            stmt.setString(4, asistente.getTelefono());
            stmt.setString(5, asistente.getCorreo());
            stmt.setString(6, asistente.getTarjetaProfesional());
            stmt.setString(7, asistente.getTipo());
            stmt.setObject(8, asistente.getId()); // Repetir ID para usuario_id

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Asistente insertado: " + asistente.getNombre());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar: " + e.getMessage());
        }
        return false;
    }

    
    public AsistenteTecnico buscarPorIdentificacion(String identificacion) {
        String sql = "SELECT id, identificacion, nombre, telefono, correo, tarjeta_profesional, tipo, fecha_registro FROM asistentes_tecnicos WHERE identificacion = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, identificacion);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return mapear(rs);
            
        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
        return null;
    }
    
    public List<AsistenteTecnico> listarTodos() {
        List<AsistenteTecnico> asistentes = new ArrayList<>();
        String sql = "SELECT id, identificacion, nombre, telefono, correo, tarjeta_profesional, tipo, fecha_registro FROM asistentes_tecnicos ORDER BY nombre ASC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) asistentes.add(mapear(rs));
            System.out.println("✅ Se encontraron " + asistentes.size() + " asistentes");
            
        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
        return asistentes;
    }
    
    public boolean actualizar(AsistenteTecnico asistente) {
        String sql = "UPDATE asistentes_tecnicos SET nombre=?, telefono=?, correo=?, tarjeta_profesional=?, tipo=? WHERE id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, asistente.getNombre());
            stmt.setString(2, asistente.getTelefono());
            stmt.setString(3, asistente.getCorreo());
            stmt.setString(4, asistente.getTarjetaProfesional());
            stmt.setString(5, asistente.getTipo());
            stmt.setObject(6, asistente.getId());
            
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Asistente actualizado");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
        return false;
    }
    
    public boolean eliminar(UUID id) {
        String sql = "DELETE FROM asistentes_tecnicos WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, id);
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("✅ Asistente eliminado");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
        return false;
    }
    
    private AsistenteTecnico mapear(ResultSet rs) throws SQLException {
        return new AsistenteTecnico(
            (UUID) rs.getObject("id"),
            rs.getString("identificacion"),
            rs.getString("nombre"),
            rs.getString("telefono"),
            rs.getString("correo"),
            rs.getString("tarjeta_profesional"),
            rs.getString("tipo"),
            rs.getTimestamp("fecha_registro").toLocalDateTime()
        );
    }
}

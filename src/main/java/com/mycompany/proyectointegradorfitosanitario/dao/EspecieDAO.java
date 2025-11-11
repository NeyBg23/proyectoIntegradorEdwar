/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.dao;

import com.mycompany.proyectointegradorfitosanitario.config.DatabaseConnection;
import com.mycompany.proyectointegradorfitosanitario.modelo.Especie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecieDAO {
    
    public boolean insertar(Especie especie) {
        String sql = "INSERT INTO especies_vegetales (codigo, nombre_cientifico, nombre_comun, densidad_siembra) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, especie.getCodigo());
            stmt.setString(2, especie.getNombreCientifico());
            stmt.setString(3, especie.getNombreComun());
            stmt.setObject(4, especie.getDensidadSiembra());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
            return false;
        }
    }
    
    public List<Especie> listarTodos() {
        List<Especie> especies = new ArrayList<>();
        String sql = "SELECT id, codigo, nombre_cientifico, nombre_comun, densidad_siembra FROM especies_vegetales ORDER BY nombre_comun";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                especies.add(new Especie(rs.getInt("id"), rs.getString("codigo"), rs.getString("nombre_cientifico"), 
                                        rs.getString("nombre_comun"), rs.getInt("densidad_siembra")));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
        return especies;
    }
    
    public boolean actualizar(Especie especie) {
        String sql = "UPDATE especies_vegetales SET nombre_comun = ?, densidad_siembra = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, especie.getNombreComun());
            stmt.setObject(2, especie.getDensidadSiembra());
            stmt.setInt(3, especie.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
            return false;
        }
    }
    
    public boolean eliminar(int id) {
        String sql = "DELETE FROM especies_vegetales WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
            return false;
        }
    }
}

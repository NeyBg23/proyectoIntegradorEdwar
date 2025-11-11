/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.dao;

import com.mycompany.proyectointegradorfitosanitario.config.DatabaseConnection;
import com.mycompany.proyectointegradorfitosanitario.modelo.Vereda;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeredaDAO {
    
    public List<Vereda> listarPorMunicipio(int municipioId) {
        List<Vereda> veredas = new ArrayList<>();
        String sql = "SELECT id, nombre, municipio_id FROM veredas WHERE municipio_id = ? ORDER BY nombre";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, municipioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                veredas.add(new Vereda(rs.getInt("id"), rs.getString("nombre"), rs.getInt("municipio_id")));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
        return veredas;
    }
    
    public Vereda buscarPorId(int veredaId) {
        String sql = "SELECT id, nombre, municipio_id FROM veredas WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, veredaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Vereda(rs.getInt("id"), rs.getString("nombre"), rs.getInt("municipio_id"));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
        return null;
    }

}



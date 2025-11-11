/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.dao;

import com.mycompany.proyectointegradorfitosanitario.config.DatabaseConnection;
import com.mycompany.proyectointegradorfitosanitario.modelo.Plaga;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlagaDAO {
    
    public boolean insertar(Plaga plaga) {
        String sql = "INSERT INTO plagas (codigo, nombre_cientifico, nombre_comun, nivel_alerta) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, plaga.getCodigo());
            stmt.setString(2, plaga.getNombreCientifico());
            stmt.setString(3, plaga.getNombreComun());
            stmt.setString(4, plaga.getNivelAlerta());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
            return false;
        }
    }
    
    public List<Plaga> listarTodos() {
        List<Plaga> plagas = new ArrayList<>();
        String sql = "SELECT codigo, nombre_cientifico, nombre_comun, nivel_alerta FROM plagas ORDER BY nombre_comun";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                plagas.add(new Plaga(rs.getString("codigo"), rs.getString("nombre_cientifico"), rs.getString("nombre_comun"), rs.getString("nivel_alerta")));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
        return plagas;
    }
    
    public boolean eliminar(String codigo) {
        String sql = "DELETE FROM plagas WHERE codigo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
            return false;
        }
    }
}

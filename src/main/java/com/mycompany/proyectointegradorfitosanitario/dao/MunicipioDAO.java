/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.dao;

import com.mycompany.proyectointegradorfitosanitario.config.DatabaseConnection;
import com.mycompany.proyectointegradorfitosanitario.modelo.Municipio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MunicipioDAO {
    
    public List<Municipio> listarPorDepartamento(int departamentoId) {
        List<Municipio> municipios = new ArrayList<>();
        String sql = "SELECT id, codigo_dane, nombre, departamento_id FROM municipios WHERE departamento_id = ? ORDER BY nombre";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, departamentoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                municipios.add(new Municipio(rs.getInt("id"), rs.getString("codigo_dane"), rs.getString("nombre"), rs.getInt("departamento_id")));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
        return municipios;
    }
    
    public Municipio buscarPorId(int municipioId) {
        String sql = "SELECT id, codigo_dane, nombre, departamento_id FROM municipios WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, municipioId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Municipio(rs.getInt("id"), rs.getString("codigo_dane"), rs.getString("nombre"), rs.getInt("departamento_id"));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
        return null;
    }

}


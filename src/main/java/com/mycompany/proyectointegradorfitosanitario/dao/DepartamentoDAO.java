/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.dao;

import com.mycompany.proyectointegradorfitosanitario.config.DatabaseConnection;
import com.mycompany.proyectointegradorfitosanitario.modelo.Departamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAO {
    
    public boolean insertar(Departamento depto) {
        String sql = "INSERT INTO departamentos (codigo_dane, nombre) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, depto.getCodigoDane());
            stmt.setString(2, depto.getNombre());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
            return false;
        }
    }
    
    public List<Departamento> listarTodos() {
        List<Departamento> deptos = new ArrayList<>();
        String sql = "SELECT id, codigo_dane, nombre FROM departamentos ORDER BY nombre";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                deptos.add(new Departamento(rs.getInt("id"), rs.getString("codigo_dane"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
        return deptos;
    }
    
    public boolean actualizar(Departamento depto) {
        String sql = "UPDATE departamentos SET nombre = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, depto.getNombre());
            stmt.setInt(2, depto.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
            return false;
        }
    }
    
    public boolean eliminar(int id) {
        String sql = "DELETE FROM departamentos WHERE id = ?";
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

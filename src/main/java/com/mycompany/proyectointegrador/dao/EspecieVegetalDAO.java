/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.dao;

import com.mycompany.proyectointegrador.modelo.EspecieVegetal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecieVegetalDAO {
    public void guardar(EspecieVegetal especie) throws SQLException {
        String sql = "INSERT INTO especies_vegetales (codigo, nombre, densidad, descripcion) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, especie.getCodigo());
            ps.setString(2, especie.getNombre());
            ps.setFloat(3, especie.getDensidad());
            ps.setString(4, especie.getDescripcion());
            ps.executeUpdate();
        }
    }

    public List<EspecieVegetal> listar() throws SQLException {
        List<EspecieVegetal> lista = new ArrayList<>();
        String sql = "SELECT * FROM especies_vegetales";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                EspecieVegetal e = new EspecieVegetal(
                    rs.getInt("id"),
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getFloat("densidad"),
                    rs.getString("descripcion")
                );
                lista.add(e);
            }
        }
        return lista;
    }

    public void actualizar(EspecieVegetal especie) throws SQLException {
        String sql = "UPDATE especies_vegetales SET codigo=?, nombre=?, densidad=?, descripcion=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, especie.getCodigo());
            ps.setString(2, especie.getNombre());
            ps.setFloat(3, especie.getDensidad());
            ps.setString(4, especie.getDescripcion());
            ps.setInt(5, especie.getId());
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM especies_vegetales WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}

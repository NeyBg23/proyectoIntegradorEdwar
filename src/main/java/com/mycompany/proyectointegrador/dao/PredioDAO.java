/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.dao;

import com.mycompany.proyectointegrador.modelo.Predio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PredioDAO {
    public void guardar(Predio predio) throws SQLException {
        String sql = "INSERT INTO predios (id, nombre, propietario_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, predio.getId());
            ps.setString(2, predio.getNombre());
            ps.setString(3, predio.getPropietarioId());
            ps.executeUpdate();
        }
    }

    public List<Predio> listar() throws SQLException {
        List<Predio> lista = new ArrayList<>();
        String sql = "SELECT * FROM predios";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Predio p;
                p = new Predio(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("propietario_id")
                );
                lista.add(p);
            }
        }
        return lista;
    }

    public void actualizar(Predio predio) throws SQLException {
        String sql = "UPDATE predios SET nombre=?, propietario_id=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, predio.getNombre());
            ps.setString(2, predio.getPropietarioId());
            ps.setString(3, predio.getId());
            ps.executeUpdate();
        }
    }

    public void eliminar(String id) throws SQLException {
        String sql = "DELETE FROM predios WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}

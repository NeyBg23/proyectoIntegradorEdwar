/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.dao;

import com.mycompany.proyectointegrador.modelo.Plaga;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlagaDAO {
    public void guardar(Plaga plaga) throws SQLException {
        String sql = "INSERT INTO plagas (codigo, nombre, descripcion, nivel_incidencia, nivel_alerta) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, plaga.getCodigo());
            ps.setString(2, plaga.getNombre());
            ps.setString(3, plaga.getDescripcion());
            ps.setFloat(4, plaga.getNivelIncidencia());
            ps.setString(5, plaga.getNivelAlerta());
            ps.executeUpdate();
        }
    }

    public List<Plaga> listar() throws SQLException {
        List<Plaga> lista = new ArrayList<>();
        String sql = "SELECT * FROM plagas";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Plaga p = new Plaga(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getFloat("nivel_incidencia"),
                    rs.getString("nivel_alerta")
                );
                lista.add(p);
            }
        }
        return lista;
    }

    public void actualizar(Plaga plaga) throws SQLException {
        String sql = "UPDATE plagas SET nombre=?, descripcion=?, nivel_incidencia=?, nivel_alerta=? WHERE codigo=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, plaga.getNombre());
            ps.setString(2, plaga.getDescripcion());
            ps.setFloat(3, plaga.getNivelIncidencia());
            ps.setString(4, plaga.getNivelAlerta());
            ps.setString(5, plaga.getCodigo());
            ps.executeUpdate();
        }
    }

    public void eliminar(String codigo) throws SQLException {
        String sql = "DELETE FROM plagas WHERE codigo=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ps.executeUpdate();
        }
    }
}
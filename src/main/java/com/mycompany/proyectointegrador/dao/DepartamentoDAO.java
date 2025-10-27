/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.dao;

import com.mycompany.proyectointegrador.modelo.Departamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAO {
    public void guardar(Departamento departamento) throws SQLException {
        String sql = "INSERT INTO departamentos (codigo_dane, nombre) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, departamento.getCodigoDane());
            ps.setString(2, departamento.getNombre());
            ps.executeUpdate();
        }
    }

    public List<Departamento> listar() throws SQLException {
        List<Departamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM departamentos";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Departamento d = new Departamento(
                    rs.getInt("id"),
                    rs.getString("codigo_dane"),
                    rs.getString("nombre")
                );
                lista.add(d);
            }
        }
        return lista;
    }

    public void actualizar(Departamento departamento) throws SQLException {
        String sql = "UPDATE departamentos SET codigo_dane=?, nombre=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, departamento.getCodigoDane());
            ps.setString(2, departamento.getNombre());
            ps.setInt(3, departamento.getId());
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM departamentos WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
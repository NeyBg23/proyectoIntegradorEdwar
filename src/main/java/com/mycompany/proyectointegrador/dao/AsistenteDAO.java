/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.dao;

import com.mycompany.proyectointegrador.modelo.Asistente;  // Importa tu clase modelo
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsistenteDAO {
    public void guardar(Asistente asistente) throws SQLException {
        String sql = "INSERT INTO asistentes (id, nombre, identificacion, telefono, correo, tarjeta_profesional) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, asistente.getId());
            ps.setString(2, asistente.getNombre());
            ps.setString(3, asistente.getIdentificacion());
            ps.setString(4, asistente.getTelefono());
            ps.setString(5, asistente.getCorreo());
            ps.setString(6, asistente.getTarjetaProfesional());
            ps.executeUpdate();
        }
    }

    public List<Asistente> listar() throws SQLException {
        List<Asistente> lista = new ArrayList<>();
        String sql = "SELECT * FROM asistentes";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Asistente a = new Asistente(
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("identificacion"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("tarjeta_profesional")
                );
                lista.add(a);
            }
        }
        return lista;
    }

    public void actualizar(Asistente asistente) throws SQLException {
        String sql = "UPDATE asistentes SET nombre=?, identificacion=?, telefono=?, correo=?, tarjeta_profesional=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, asistente.getNombre());
            ps.setString(2, asistente.getIdentificacion());
            ps.setString(3, asistente.getTelefono());
            ps.setString(4, asistente.getCorreo());
            ps.setString(5, asistente.getTarjetaProfesional());
            ps.setString(6, asistente.getId());
            ps.executeUpdate();
        }
    }

    public void eliminar(String id) throws SQLException {
        String sql = "DELETE FROM asistentes WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}

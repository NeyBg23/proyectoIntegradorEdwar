/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.dao;

import com.mycompany.proyectointegrador.modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para usuarios.
 * Permite login y gestión básica de usuarios.
 * ¡NO expone contraseñas en los select!
 */
public class UsuarioDAO {

    // Método para login (devuelve Usuario si es válido, null si no)
    public Usuario login(String nombreUsuario, String contrasena) throws SQLException {
        String sql = "SELECT id, nombre_usuario, contrasena, rol, estado FROM usuarios "
                   + "WHERE nombre_usuario = ? AND contrasena = ? AND estado = TRUE";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasena);  // ¡En producción usa hash!
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Usuario(
                    rs.getString("id"),
                    rs.getString("nombre_usuario"),
                    null,                       // Por seguridad, no envía la contraseña
                    rs.getString("rol"),
                    rs.getBoolean("estado")
                );
            }
        }
        return null; // No se encontró usuario válido
    }

    // CRUD básicos que necesites (crear, actualizar, listar)
    public void crearUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre_usuario, contrasena, rol, estado) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getContrasena());
            ps.setString(3, usuario.getRol());
            ps.setBoolean(4, usuario.isEstado());
            ps.executeUpdate();
        }
    }

    // Listar usuarios (admin)
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, nombre_usuario, rol, estado FROM usuarios";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Usuario(
                    rs.getString("id"),
                    rs.getString("nombre_usuario"),
                    null, // Nunca mostramos contraseñas
                    rs.getString("rol"),
                    rs.getBoolean("estado")
                ));
            }
        }
        return lista;
    }
    
    // Puedes añadir update/delete si lo necesita el admin
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.dao;

import com.mycompany.proyectointegrador.modelo.Propietario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;




public class PropietarioDAO {
    
        /**
     * Data Access Object para la entidad Propietario
     * Maneja todas las operaciones CRUD con la tabla propietarios en Supabase
     * 
     * @author edward
     */
    public void guardar(Propietario propietario) throws SQLException {
        // ✅ NO incluimos 'id' porque se autogenera
        String sql = "INSERT INTO propietarios (nombre, identificacion, telefono, correo) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
                       
            ps.setString(1, propietario.getNombre());
            ps.setString(2, propietario.getIdentificacion());
            ps.setString(3, propietario.getTelefono());
            ps.setString(4, propietario.getCorreo());
            
            ps.executeUpdate();
            System.out.println("✅ Propietario guardado: " + propietario.getNombre());
        
        }
    }
    
    /**
     * Obtiene todos los propietarios de la base de datos
     * 
     * @return Lista de propietarios
     * @throws SQLException si hay error en la base de datos
     */
    

    public List<Propietario> listar() throws SQLException {
        List<Propietario> lista = new ArrayList<>();
        String sql = "SELECT * FROM propietarios ORDER BY nombre";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Propietario p = new Propietario(
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("identificacion"),
                    rs.getString("telefono"),
                    rs.getString("correo")
                );
                lista.add(p);
            }
        }
        return lista;
    }

     /**
     * Actualiza un propietario existente
     * 
     * @param propietario El propietario con los datos actualizados
     * @throws SQLException si hay error en la base de datos
     */
    
    public void actualizar(Propietario propietario) throws SQLException {
        String sql = "UPDATE propietarios SET nombre=?, identificacion=?, telefono=?, correo=? WHERE id::text=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, propietario.getNombre());
            ps.setString(2, propietario.getIdentificacion());
            ps.setString(3, propietario.getTelefono());
            ps.setString(4, propietario.getCorreo());
            ps.setString(5, propietario.getId());
            
            ps.executeUpdate();
        }
    }

     /**
     * Elimina un propietario por su ID
     * 
     * @param id El ID del propietario a eliminar
     * @throws SQLException si hay error en la base de datos
     */
    
    public void eliminar(String id) throws SQLException {
        String sql = "DELETE FROM propietarios WHERE id::text=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}
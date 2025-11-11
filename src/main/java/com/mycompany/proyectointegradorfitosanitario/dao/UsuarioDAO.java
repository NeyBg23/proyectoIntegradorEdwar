/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.dao;

import com.mycompany.proyectointegradorfitosanitario.config.DatabaseConnection;
import com.mycompany.proyectointegradorfitosanitario.modelo.Usuario;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Data Access Object para la tabla usuarios
 * Implementa operaciones CRUD completas
 * @author Equipo Proyecto Integrador
 * @version 1.0
 */
public class UsuarioDAO {
    
    // ===== OPERACIÓN CREATE =====
    /**
     * Inserta un nuevo usuario en la base de datos
     * @param usuario Objeto Usuario a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    public boolean insertar(Usuario usuario) {
        String sql = """
            INSERT INTO usuarios (username, password_hash, rol, email, activo)
            VALUES (?, ?, ?, ?, ?)
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPasswordHash());
            stmt.setString(3, usuario.getRol());
            stmt.setString(4, usuario.getEmail());
            stmt.setBoolean(5, usuario.getActivo());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✅ Usuario insertado: " + usuario.getUsername());
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar usuario:");
            System.err.println("   Username: " + usuario.getUsername());
            System.err.println("   Mensaje: " + e.getMessage());
            
            if (e.getMessage().contains("duplicate key")) {
                System.err.println("   💡 Ya existe un usuario con ese username o email");
            }
        }
        
        return false;
    }
    
    // ===== OPERACIÓN READ (BUSCAR POR USERNAME) =====
    /**
     * Busca un usuario por su username
     * @param username Nombre de usuario
     * @return Usuario encontrado o null si no existe
     */
    public Usuario buscarPorUsername(String username) {
        String sql = """
            SELECT id, username, password_hash, rol, email, activo, fecha_creacion
            FROM usuarios
            WHERE username = ?
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearUsuario(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar usuario por username: " + e.getMessage());
        }
        
        return null;
    }
    
    // ===== OPERACIÓN READ (BUSCAR POR ID) =====
    /**
     * Busca un usuario por su ID
     * @param id UUID del usuario
     * @return Usuario encontrado o null si no existe
     */
    public Usuario buscarPorId(UUID id) {
        String sql = """
            SELECT id, username, password_hash, rol, email, activo, fecha_creacion
            FROM usuarios
            WHERE id = ?
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearUsuario(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar usuario por ID: " + e.getMessage());
        }
        
        return null;
    }
    
    // ===== OPERACIÓN LOGIN =====
    /**
     * Valida el login de un usuario
     * @param username Nombre de usuario
     * @param password Contraseña ingresada
     * @return Usuario si las credenciales son correctas, null en caso contrario
     */
    public Usuario login(String username, String password) {
        String sql = """
            SELECT id, username, password_hash, rol, email, activo, fecha_creacion
            FROM usuarios
            WHERE username = ? AND password_hash = ? AND activo = true
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password); // En producción, deberías cifrar con BCrypt
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                System.out.println("✅ Login exitoso para: " + username);
                return mapearUsuario(rs);
            } else {
                System.err.println("❌ Credenciales inválidas o usuario inactivo");
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error en login: " + e.getMessage());
        }
        
        return null;
    }
    
    // ===== OPERACIÓN READ (LISTAR TODOS) =====
    /**
     * Obtiene la lista de todos los usuarios
     * @return Lista de Usuario
     */
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = """
            SELECT id, username, password_hash, rol, email, activo, fecha_creacion
            FROM usuarios
            ORDER BY username ASC
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
            
            System.out.println("✅ Se encontraron " + usuarios.size() + " usuarios");
            
        } catch (SQLException e) {
            System.err.println("❌ Error al listar usuarios: " + e.getMessage());
        }
        
        return usuarios;
    }
    
    // ===== OPERACIÓN READ (LISTAR POR ROL) =====
    /**
     * Obtiene la lista de usuarios filtrados por rol
     * @param rol Rol a filtrar (ADMIN, PROPIETARIO, ASISTENTE)
     * @return Lista de Usuario
     */
    public List<Usuario> listarPorRol(String rol) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = """
            SELECT id, username, password_hash, rol, email, activo, fecha_creacion
            FROM usuarios
            WHERE rol = ?
            ORDER BY username ASC
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, rol);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
            
            System.out.println("✅ Se encontraron " + usuarios.size() + " usuarios con rol " + rol);
            
        } catch (SQLException e) {
            System.err.println("❌ Error al listar usuarios por rol: " + e.getMessage());
        }
        
        return usuarios;
    }
    
    // ===== OPERACIÓN UPDATE =====
    /**
     * Actualiza los datos de un usuario existente
     * @param usuario Objeto Usuario con datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizar(Usuario usuario) {
        String sql = """
            UPDATE usuarios
            SET username = ?, password_hash = ?, rol = ?, email = ?, activo = ?
            WHERE id = ?
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPasswordHash());
            stmt.setString(3, usuario.getRol());
            stmt.setString(4, usuario.getEmail());
            stmt.setBoolean(5, usuario.getActivo());
            stmt.setObject(6, usuario.getId());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✅ Usuario actualizado: " + usuario.getUsername());
                return true;
            } else {
                System.err.println("⚠️  No se encontró usuario con ID: " + usuario.getId());
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar usuario:");
            System.err.println("   Username: " + usuario.getUsername());
            System.err.println("   Mensaje: " + e.getMessage());
        }
        
        return false;
    }
    
    // ===== OPERACIÓN DELETE =====
    /**
     * Elimina un usuario de la base de datos
     * @param id UUID del usuario a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(UUID id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, id);
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✅ Usuario eliminado correctamente");
                return true;
            } else {
                System.err.println("⚠️  No se encontró usuario con ID: " + id);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar usuario:");
            System.err.println("   ID: " + id);
            System.err.println("   Mensaje: " + e.getMessage());
        }
        
        return false;
    }
    
    // ===== MÉTODO AUXILIAR: MAPEAR RESULTSET A OBJETO =====
    /**
     * Convierte un ResultSet en un objeto Usuario
     * @param rs ResultSet con los datos
     * @return Usuario mapeado
     * @throws SQLException si hay error al leer el ResultSet
     */
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        UUID id = (UUID) rs.getObject("id");
        String username = rs.getString("username");
        String passwordHash = rs.getString("password_hash");
        String rol = rs.getString("rol");
        String email = rs.getString("email");
        Boolean activo = rs.getBoolean("activo");
        LocalDateTime fechaCreacion = rs.getTimestamp("fecha_creacion").toLocalDateTime();
        
        return new Usuario(id, username, passwordHash, rol, email, activo, fechaCreacion);
    }
    
    // ===== MÉTODO DE PRUEBA =====
    /**
     * Main para probar las operaciones del DAO
     */
    public static void main(String[] args) {
        System.out.println("🧪 PRUEBA DE UsuarioDAO");
        System.out.println("=================================\n");
        
        UsuarioDAO dao = new UsuarioDAO();
        
        // 1. Probar login con el usuario admin
        System.out.println("1️⃣  PROBANDO LOGIN...");
        Usuario usuarioLogueado = dao.login("admin", "admin123");
        if (usuarioLogueado != null) {
            System.out.println("   ✅ Login exitoso");
            System.out.println("   Usuario: " + usuarioLogueado.getUsername());
            System.out.println("   Rol: " + usuarioLogueado.getRol());
        } else {
            System.out.println("   ❌ Login fallido");
        }
        System.out.println();
        
        // 2. Listar todos los usuarios
        System.out.println("2️⃣  LISTANDO TODOS LOS USUARIOS...");
        List<Usuario> usuarios = dao.listarTodos();
        usuarios.forEach(u -> System.out.println("   - " + u.getUsername() + " (" + u.getRol() + ")"));
        System.out.println();
        
        // 3. Buscar por username
        System.out.println("3️⃣  BUSCANDO USUARIO POR USERNAME...");
        Usuario encontrado = dao.buscarPorUsername("admin");
        if (encontrado != null) {
            System.out.println("   ✅ Encontrado: " + encontrado.getUsername());
            System.out.println("   Email: " + encontrado.getEmail());
        }
        System.out.println();
        
        System.out.println("=================================");
        System.out.println("🎉 Prueba completada");
    }
}


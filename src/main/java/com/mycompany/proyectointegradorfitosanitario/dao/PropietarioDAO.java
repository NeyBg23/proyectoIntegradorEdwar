package com.mycompany.proyectointegradorfitosanitario.dao;

import com.mycompany.proyectointegradorfitosanitario.config.DatabaseConnection;
import com.mycompany.proyectointegradorfitosanitario.modelo.Propietario;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Data Access Object para la tabla propietarios
 * Implementa operaciones CRUD completas
 * @author Equipo Proyecto Integrador
 * @version 1.0
 */
public class PropietarioDAO {
    
    // ===== OPERACIÓN CREATE =====
    /**
     * Inserta un nuevo propietario en la base de datos
     * @param propietario Objeto Propietario a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    public boolean insertar(Propietario propietario) {
        String sql = """
            INSERT INTO propietarios (identificacion, nombre, telefono, correo)
            VALUES (?, ?, ?, ?)
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, propietario.getIdentificacion());
            stmt.setString(2, propietario.getNombre());
            stmt.setString(3, propietario.getTelefono());
            stmt.setString(4, propietario.getCorreo());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✅ Propietario insertado: " + propietario.getNombre());
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al insertar propietario:");
            System.err.println("   Identificación: " + propietario.getIdentificacion());
            System.err.println("   Mensaje: " + e.getMessage());
            
            if (e.getMessage().contains("duplicate key")) {
                System.err.println("   💡 Ya existe un propietario con esa identificación");
            }
        }
        
        return false;
    }
    
    // ===== OPERACIÓN READ (BUSCAR POR ID) =====
    /**
     * Busca un propietario por su ID
     * @param id UUID del propietario
     * @return Propietario encontrado o null si no existe
     */
    public Propietario buscarPorId(UUID id) {
        String sql = """
            SELECT id, identificacion, nombre, telefono, correo, fecha_registro
            FROM propietarios
            WHERE id = ?
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearPropietario(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar propietario por ID: " + e.getMessage());
        }
        
        return null;
    }
    
    // ===== OPERACIÓN READ (BUSCAR POR IDENTIFICACIÓN) =====
    /**
     * Busca un propietario por su número de identificación
     * @param identificacion Número de identificación
     * @return Propietario encontrado o null si no existe
     */
    public Propietario buscarPorIdentificacion(String identificacion) {
        String sql = """
            SELECT id, identificacion, nombre, telefono, correo, fecha_registro
            FROM propietarios
            WHERE identificacion = ?
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, identificacion);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearPropietario(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar propietario por identificación: " + e.getMessage());
        }
        
        return null;
    }
    
    // ===== OPERACIÓN READ (LISTAR TODOS) =====
    /**
     * Obtiene la lista de todos los propietarios
     * @return Lista de Propietario
     */
    public List<Propietario> listarTodos() {
        List<Propietario> propietarios = new ArrayList<>();
        String sql = """
            SELECT id, identificacion, nombre, telefono, correo, fecha_registro
            FROM propietarios
            ORDER BY nombre ASC
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                propietarios.add(mapearPropietario(rs));
            }
            
            System.out.println("✅ Se encontraron " + propietarios.size() + " propietarios");
            
        } catch (SQLException e) {
            System.err.println("❌ Error al listar propietarios: " + e.getMessage());
        }
        
        return propietarios;
    }
    
    // ===== OPERACIÓN UPDATE =====
    /**
     * Actualiza los datos de un propietario existente
     * @param propietario Objeto Propietario con datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizar(Propietario propietario) {
        String sql = """
            UPDATE propietarios
            SET nombre = ?, telefono = ?, correo = ?
            WHERE id = ?
            """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, propietario.getNombre());
            stmt.setString(2, propietario.getTelefono());
            stmt.setString(3, propietario.getCorreo());
            stmt.setObject(4, propietario.getId());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✅ Propietario actualizado: " + propietario.getNombre());
                return true;
            } else {
                System.err.println("⚠️  No se encontró propietario con ID: " + propietario.getId());
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar propietario:");
            System.err.println("   Identificación: " + propietario.getIdentificacion());
            System.err.println("   Mensaje: " + e.getMessage());
        }
        
        return false;
    }
    
    // ===== OPERACIÓN DELETE =====
    /**
     * Elimina un propietario de la base de datos
     * @param id UUID del propietario a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminar(UUID id) {
        String sql = "DELETE FROM propietarios WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, id);
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("✅ Propietario eliminado correctamente");
                return true;
            } else {
                System.err.println("⚠️  No se encontró propietario con ID: " + id);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar propietario:");
            System.err.println("   ID: " + id);
            System.err.println("   Mensaje: " + e.getMessage());
            
            if (e.getMessage().contains("foreign key")) {
                System.err.println("   💡 No se puede eliminar: existen predios asociados a este propietario");
            }
        }
        
        return false;
    }
    
    // ===== MÉTODO AUXILIAR: MAPEAR RESULTSET A OBJETO =====
    /**
     * Convierte un ResultSet en un objeto Propietario
     * @param rs ResultSet con los datos
     * @return Propietario mapeado
     * @throws SQLException si hay error al leer el ResultSet
     */
    private Propietario mapearPropietario(ResultSet rs) throws SQLException {
        UUID id = (UUID) rs.getObject("id");
        String identificacion = rs.getString("identificacion");
        String nombre = rs.getString("nombre");
        String telefono = rs.getString("telefono");
        String correo = rs.getString("correo");
        LocalDateTime fechaRegistro = rs.getTimestamp("fecha_registro").toLocalDateTime();
        
        return new Propietario(id, identificacion, nombre, telefono, correo, fechaRegistro);
    }
    
    // ===== MÉTODO DE PRUEBA =====
    /**
     * Main para probar las operaciones del DAO
     */
    public static void main(String[] args) {
        System.out.println("🧪 PRUEBA DE PropietarioDAO");
        System.out.println("=================================\n");
        
        PropietarioDAO dao = new PropietarioDAO();
        
        // 1. Insertar un propietario de prueba
        System.out.println("1️⃣  INSERTANDO PROPIETARIO...");
        Propietario nuevoPropietario = new Propietario(
            "1234567890",
            "Carlos Rodríguez García",
            "3001234567",
            "carlos.rodriguez@email.com"
        );
        
        boolean insertado = dao.insertar(nuevoPropietario);
        System.out.println("   Resultado: " + (insertado ? "✅ ÉXITO" : "❌ FALLO") + "\n");
        
        // 2. Listar todos los propietarios
        System.out.println("2️⃣  LISTANDO TODOS LOS PROPIETARIOS...");
        List<Propietario> propietarios = dao.listarTodos();
        propietarios.forEach(p -> System.out.println("   - " + p.getNombre() + " (" + p.getIdentificacion() + ")"));
        System.out.println();
        
        // 3. Buscar por identificación
        System.out.println("3️⃣  BUSCANDO POR IDENTIFICACIÓN...");
        Propietario encontrado = dao.buscarPorIdentificacion("1234567890");
        if (encontrado != null) {
            System.out.println("   ✅ Encontrado: " + encontrado.getNombre());
            System.out.println("   ID: " + encontrado.getId());
            System.out.println("   Teléfono: " + encontrado.getTelefono());
        }
        System.out.println();
        
        System.out.println("=================================");
        System.out.println("🎉 Prueba completada");
    }
}

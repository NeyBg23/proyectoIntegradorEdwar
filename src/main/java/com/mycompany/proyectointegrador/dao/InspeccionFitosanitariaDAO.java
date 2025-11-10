package com.mycompany.proyectointegrador.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para InspeccionFitosanitaria
 * Maneja operaciones CRUD con la tabla inspecciones_fitosanitarias en Supabase
 * 
 * Tabla: inspecciones_fitosanitarias
 * Campos: id, lote_id, asistente_id, fecha_inspeccion, total_plantas, observaciones, estado
 * 
 * @author edward
 */
public class InspeccionFitosanitariaDAO {

    /**
     * Guarda una nueva inspección en la BD
     * @param loteId ID del lote inspeccionado
     * @param asistenteId ID del asistente (VARCHAR(50))
     * @param fechaInspeccion Fecha de inspección (YYYY-MM-DD)
     * @param totalPlantas Número de plantas inspeccionadas
     * @param observaciones Observaciones del hallazgo
     * @param estado Estado (pendiente, aprobada, rechazada)
     * @throws SQLException Si ocurre error en la BD
     */
    public void guardar(int loteId, String asistenteId, String fechaInspeccion, 
                        int totalPlantas, String observaciones, String estado) throws SQLException {
        
        String sql = "INSERT INTO inspecciones_fitosanitarias " +
                     "(lote_id, asistente_id, fecha_inspeccion, total_plantas, observaciones, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            // ✅ VALIDACIÓN: Truncar asistente_id si es mayor a 50 caracteres
            String asistenteIdValido = asistenteId.length() > 50 
                ? asistenteId.substring(0, 50) 
                : asistenteId;
            
            ps.setInt(1, loteId);
            ps.setString(2, asistenteIdValido);
            ps.setDate(3, java.sql.Date.valueOf(fechaInspeccion));
            ps.setInt(4, totalPlantas);
            ps.setString(5, observaciones != null ? observaciones : "");
            ps.setString(6, estado);
            
            ps.executeUpdate();
            System.out.println("✅ Inspección guardada correctamente en BD");
        }
    }

    /**
     * Obtiene todas las inspecciones de la BD
     * @return Lista de Object[] con los datos de inspecciones
     * @throws SQLException Si ocurre error en la BD
     */
    public List<Object[]> listar() throws SQLException {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id, lote_id, asistente_id, fecha_inspeccion, total_plantas, estado, observaciones " +
                     "FROM inspecciones_fitosanitarias ORDER BY fecha_inspeccion DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Object[] fila = new Object[7];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getInt("lote_id");
                fila[2] = rs.getString("asistente_id");
                fila[3] = rs.getDate("fecha_inspeccion");
                fila[4] = rs.getInt("total_plantas");
                fila[5] = rs.getString("estado");
                fila[6] = rs.getString("observaciones");
                
                lista.add(fila);
            }
            System.out.println("✅ " + lista.size() + " inspecciones cargadas de BD");
        }
        
        return lista;
    }

    /**
     * Obtiene las inspecciones de un asistente específico
     * IMPORTANTE: Solo el asistente logueado ve SUS inspecciones
     * @param asistenteId ID del asistente
     * @return Lista de inspecciones del asistente
     * @throws SQLException Si ocurre error en la BD
     */
    public List<Object[]> listarPorAsistente(String asistenteId) throws SQLException {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id, lote_id, asistente_id, fecha_inspeccion, total_plantas, estado, observaciones " +
                     "FROM inspecciones_fitosanitarias " +
                     "WHERE asistente_id = ? ORDER BY fecha_inspeccion DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, asistenteId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[7];
                    fila[0] = rs.getInt("id");
                    fila[1] = rs.getInt("lote_id");
                    fila[2] = rs.getString("asistente_id");
                    fila[3] = rs.getDate("fecha_inspeccion");
                    fila[4] = rs.getInt("total_plantas");
                    fila[5] = rs.getString("estado");
                    fila[6] = rs.getString("observaciones");
                    
                    lista.add(fila);
                }
            }
            System.out.println("✅ " + lista.size() + " inspecciones del asistente cargadas");
        }
        
        return lista;
    }

    /**
     * Actualiza una inspección existente
     * @param id ID de la inspección
     * @param totalPlantas Nuevos datos
     * @param observaciones Nuevas observaciones
     * @param estado Nuevo estado
     * @throws SQLException Si ocurre error en la BD
     */
    public void actualizar(int id, int totalPlantas, String observaciones, String estado) throws SQLException {
        
        String sql = "UPDATE inspecciones_fitosanitarias " +
                     "SET total_plantas = ?, observaciones = ?, estado = ? " +
                     "WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, totalPlantas);
            ps.setString(2, observaciones != null ? observaciones : "");
            ps.setString(3, estado);
            ps.setInt(4, id);
            
            ps.executeUpdate();
            System.out.println("✅ Inspección actualizada correctamente");
        }
    }

    /**
     * Elimina una inspección de la BD
     * @param id ID de la inspección a eliminar
     * @throws SQLException Si ocurre error en la BD
     */
    public void eliminar(int id) throws SQLException {
        
        String sql = "DELETE FROM inspecciones_fitosanitarias WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("✅ Inspección eliminada correctamente");
        }
    }

    /**
     * Obtiene el total de inspecciones registradas
     * @return Total de inspecciones
     * @throws SQLException Si ocurre error en la BD
     */
    public int contarTotal() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM inspecciones_fitosanitarias";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        
        return 0;
    }

    /**
     * Obtiene el total de inspecciones de un asistente
     * @param asistenteId ID del asistente
     * @return Total de inspecciones del asistente
     * @throws SQLException Si ocurre error en la BD
     */
    public int contarPorAsistente(String asistenteId) throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM inspecciones_fitosanitarias WHERE asistente_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, asistenteId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        
        return 0;
    }
}

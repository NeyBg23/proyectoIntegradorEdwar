package com.mycompany.proyectointegrador.dao;

import com.mycompany.proyectointegrador.modelo.Predio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para Predio
 * Maneja operaciones CRUD con la tabla predios en Supabase
 * 
 * @author edward
 */
public class PredioDAO {

    /**
     * Guarda un nuevo predio en la BD
     * ✅ CORREGIDO: Incluye el ID que fue generado en FrmPredio
     * @param predio Objeto Predio a guardar
     * @throws SQLException Si ocurre error en la BD
     */
    public void guardar(Predio predio) throws SQLException {
        // ✅ AGREGADO: id en el INSERT
        String sql = "INSERT INTO predios (id, nombre, area_total, vereda_id, propietario_id, numero_registro_ica) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // ✅ NUEVO: Insertar el ID (parámetro 1)
            ps.setString(1, predio.getId());
            ps.setString(2, predio.getNombre());
            ps.setFloat(3, predio.getArea());

            // Vereda ID (puede ser null)
            if(predio.getVereda() != null) {
                try {
                    int veredaId = Integer.parseInt(String.valueOf(predio.getVereda().getId()));
                    ps.setInt(4, veredaId);
                } catch (NumberFormatException e) {
                    ps.setNull(4, Types.INTEGER);
                }
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            // Propietario ID (UUID requerido)
            if(predio.getPropietarioId() != null && !predio.getPropietarioId().isEmpty()) {
                try {
                    ps.setObject(5, java.util.UUID.fromString(predio.getPropietarioId()));
                } catch (IllegalArgumentException e) {
                    ps.setString(5, predio.getPropietarioId());
                }
            } else {
                ps.setNull(5, Types.VARCHAR);
            }

            // Numero registro ICA (opcional)
            ps.setString(6, predio.getNumeroRegistro() != null ? predio.getNumeroRegistro() : null);

            ps.executeUpdate();
            System.out.println("✅ Predio guardado correctamente en BD - ID: " + predio.getId());
        }
    }




    /**
     * Obtiene todos los predios de la BD con nombre del propietario
     * @return Lista de predios
     * @throws SQLException Si ocurre error en la BD
     */
    public List<Object[]> listarConPropietario() throws SQLException {
        List<Object[]> lista = new ArrayList<>();

        // ✅ JOIN con propietarios para traer el nombre
        String sql = "SELECT p.id, p.nombre, pr.nombre AS propietario_nombre, " +
                     "p.area_total, v.nombre AS vereda_nombre " +
                     "FROM predios p " +
                     "LEFT JOIN propietarios pr ON p.propietario_id = pr.id " +
                     "LEFT JOIN veredas v ON p.vereda_id = v.id " +
                     "ORDER BY p.nombre";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("propietario_nombre") != null ? rs.getString("propietario_nombre") : "N/A";
                fila[3] = rs.getString("vereda_nombre") != null ? rs.getString("vereda_nombre") : "Sin vereda";
                fila[4] = rs.getFloat("area_total");

                lista.add(fila);
            }
            System.out.println("✅ " + lista.size() + " predios cargados con propietarios");
        }

        return lista;
    }



   
    /**
     * Actualiza un predio existente
     * @param predio Objeto Predio con datos actualizados
     * @throws SQLException Si ocurre error en la BD
     */
    public void actualizar(Predio predio) throws SQLException {
        // ✅ CORREGIDO: usar numero_registro_ica
        String sql = "UPDATE predios SET nombre = ?, area_total = ?, vereda_id = ?, numero_registro_ica = ? " +
                     "WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, predio.getNombre());
            ps.setFloat(2, predio.getArea());

            // Vereda ID
            if(predio.getVereda() != null) {
                try {
                    int veredaId = Integer.parseInt(String.valueOf(predio.getVereda().getId()));
                    ps.setInt(3, veredaId);
                } catch (NumberFormatException e) {
                    ps.setNull(3, Types.INTEGER);
                }
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            // Numero registro ICA
            ps.setString(4, predio.getNumeroRegistro() != null ? predio.getNumeroRegistro() : null);

            // ID del predio a actualizar
            ps.setString(5, predio.getId());

            ps.executeUpdate();
            System.out.println("✅ Predio actualizado correctamente");
        }
    }



    /**
     * Elimina un predio por su ID
     * @param id ID del predio a eliminar
     * @throws SQLException Si ocurre error en la BD
     */
    public void eliminar(String id) throws SQLException {
        String sql = "DELETE FROM predios WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, id);
            ps.executeUpdate();
            System.out.println("✅ Predio eliminado correctamente");
        }
    }
    
    /**
     * Obtiene un predio por su ID
     * @param id ID del predio
     * @return Objeto Predio o null si no existe
     * @throws SQLException Si ocurre error en la BD
     */
    public Predio obtenerPorId(String id) throws SQLException {
        // ✅ CORREGIDO: usar numero_registro_ica
        String sql = "SELECT id, numero_registro_ica, nombre, area_total, vereda_id, propietario_id " +
                     "FROM predios WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Predio p = new Predio();
                    p.setId(rs.getString("id"));
                    p.setNumeroRegistro(rs.getString("numero_registro_ica"));
                    p.setNombre(rs.getString("nombre"));
                    p.setArea(rs.getFloat("area_total"));
                    p.setPropietarioId(rs.getString("propietario_id"));

                    return p;
                }
            }
        }

        return null;
    }


    /**
     * Cuenta el total de predios en la BD
     * @return Total de predios
     * @throws SQLException Si ocurre error en la BD
     */
    public int contar() throws SQLException {
        String sql = "SELECT COUNT(*) as total FROM predios";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        
        return 0;
    }
}

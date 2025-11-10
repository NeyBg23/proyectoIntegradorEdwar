package com.mycompany.proyectointegrador.dao;

import com.mycompany.proyectointegrador.modelo.Municipio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MunicipioDAO {
    public void guardar(Municipio municipio) throws SQLException {
        String sql = "INSERT INTO municipios (codigo_dane, nombre, departamento_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, municipio.getCodigoDane());
            ps.setString(2, municipio.getNombre());
            ps.setInt(3, municipio.getDepartamentoId());
            ps.executeUpdate();
        }
    }

    public List<Municipio> listarPorDepartamento(int departamentoId) throws SQLException {
        List<Municipio> lista = new ArrayList<>();
        String sql = "SELECT * FROM municipios WHERE departamento_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, departamentoId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Municipio m = new Municipio(
                    rs.getInt("id"),
                    rs.getString("codigo_dane"),
                    rs.getString("nombre"),
                    rs.getInt("departamento_id")
                );
                lista.add(m);
            }
        }
        return lista;
    }

    public void actualizar(Municipio municipio) throws SQLException {
        String sql = "UPDATE municipios SET codigo_dane=?, nombre=?, departamento_id=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, municipio.getCodigoDane());
            ps.setString(2, municipio.getNombre());
            ps.setInt(3, municipio.getDepartamentoId());
            ps.setInt(4, municipio.getId());
            ps.executeUpdate();
        }
    }
    
    
    public List<Municipio> listarTodos() throws SQLException {
        List<Municipio> lista = new ArrayList<>();
        String sql = "SELECT * FROM municipios";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Municipio m = new Municipio(
                    rs.getInt("id"),
                    rs.getString("codigo_dane"),
                    rs.getString("nombre"),
                    rs.getInt("departamento_id")
                );
                lista.add(m);
            }
        }
        return lista;
    }


    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM municipios WHERE id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}

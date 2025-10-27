/*
 * Clase para gestionar la conexión a la base de datos Supabase (PostgreSQL)
 * Usando Supavisor Transaction Mode (Pooler compartido) - Compatible con IPv4
 */
package com.mycompany.proyectointegrador.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona la conexión a la base de datos PostgreSQL en Supabase
 * usando Supavisor (Connection Pooler) en Transaction Mode - GRATUITO e IPv4
 * 
 * @author edward
 */
public class DatabaseConnection {
    
    // ✅ CONEXIÓN USANDO SUPAVISOR TRANSACTION MODE (Puerto 6543)
    // Compatible con IPv4 y GRATUITO
    private static final String URL = "jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:6543/postgres";
    private static final String USER = "postgres.ibktbfxgpuxbiufkggpo";
    private static final String PASSWORD = "6868718Bg23.";
    
    /**
     * Obtiene una conexión a la base de datos usando Supavisor
     * 
     * @return Connection objeto de conexión a la base de datos
     * @throws SQLException si hay error al conectar
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Cargar el driver de PostgreSQL
            Class.forName("org.postgresql.Driver");
            
            System.out.println("🔄 Conectando via Supavisor (IPv4 - Transaction Mode)...");
            System.out.println("   Host: aws-1-us-east-1.pooler.supabase.com");
            System.out.println("   Puerto: 6543 (Transaction Mode - Pooler compartido)");
            System.out.println("   Usuario: postgres.ibktbfxgpuxbiufkggpo");
            
            // Conexión usando Supavisor
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            
            System.out.println("✅ ¡CONEXIÓN EXITOSA A SUPABASE VÍA SUPAVISOR!");
            System.out.println("   IPv4 compatible");
            System.out.println("   GRATUITO");
            return conn;
            
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Error: Driver PostgreSQL no encontrado");
            System.err.println("   Asegúrate de tener la dependencia en pom.xml");
            throw new SQLException("Driver PostgreSQL no encontrado", e);
            
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar a Supabase:");
            System.err.println("   Mensaje: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Método auxiliar para probar la conexión
     */
    public static boolean probarConexion() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("❌ Error en prueba de conexión: " + e.getMessage());
            return false;
        }
    }
}

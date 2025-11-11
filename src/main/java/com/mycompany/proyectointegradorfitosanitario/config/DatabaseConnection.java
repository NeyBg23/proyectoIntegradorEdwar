package com.mycompany.proyectointegradorfitosanitario.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para conectar con la base de datos de Supabase
 * Usando Session Pooler (IPv4 compatible)
 */
public class DatabaseConnection {
    
    // Datos de conexión usando SESSION POOLER (IPv4 compatible)
    private static final String URL = "jdbc:postgresql://aws-1-us-east-2.pooler.supabase.com:5432/postgres";
    private static final String USER = "postgres.obqfajzfgbrkdopkrgoj";
    private static final String PASSWORD = "6868718Bg23.";
    
    private static Connection connection = null;
    
    /**
     * Obtiene la conexión a la base de datos
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Cargar el driver de PostgreSQL
            Class.forName("org.postgresql.Driver");
            
            // Crear conexión si no existe
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Conexión exitosa a Supabase (Session Pooler - IPv4 Compatible)");
            }
            
            return connection;
            
        } catch (ClassNotFoundException e) {
            System.err.println("❌ ERROR: No se encontró el driver de PostgreSQL");
            System.err.println("   Asegúrate de haber agregado postgresql-42.7.4.jar a las dependencias");
            throw new SQLException("Driver no encontrado", e);
            
        } catch (SQLException e) {
            System.err.println("❌ ERROR: No se pudo conectar a Supabase");
            System.err.println("   Verifica tu conexión a Internet");
            System.err.println("   Mensaje: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Cierra la conexión
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Conexión cerrada");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al cerrar: " + e.getMessage());
        }
    }
    
    /**
     * MÉTODO DE PRUEBA - Ejecuta esto para probar la conexión
     */
    public static void main(String[] args) {
        System.out.println("🧪 PROBANDO CONEXIÓN A SUPABASE (Session Pooler)...\n");
        
        try {
            Connection conn = getConnection();
            System.out.println("\n🎉 ¡CONEXIÓN EXITOSA!");
            System.out.println("================================");
            System.out.println("Base de datos: " + conn.getCatalog());
            System.out.println("Usuario: " + conn.getMetaData().getUserName());
            System.out.println("================================\n");
            closeConnection();
            
        } catch (SQLException e) {
            System.err.println("\n💥 FALLO EN LA CONEXIÓN");
            e.printStackTrace();
        }
    }
}

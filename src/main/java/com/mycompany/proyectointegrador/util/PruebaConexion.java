/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.util;

import com.mycompany.proyectointegrador.dao.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Clase de prueba para verificar la conexión a Supabase
 * 
 * Este código prueba si la conexión a la base de datos funciona correctamente.
 * Si la conexión es exitosa, verás un mensaje "✅ Conexión exitosa a Supabase"
 * Si falla, verás un mensaje de error.
 * 
 * @author edward
 */
public class PruebaConexion {

    /**
     * Método principal: ejecuta la prueba de conexión
     * 
     * Para ejecutar esta prueba:
     * 1. Click derecho en este archivo
     * 2. Run File (o presiona Shift + F6)
     * 
     * @param args No necesita argumentos
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("PRUEBA DE CONEXIÓN A SUPABASE");
        System.out.println("========================================");
        System.out.println();
        
        // Intentar conectar a la base de datos
        Connection conexion = null;
        try {
            // Obtener la conexión
            System.out.println("🔄 Intentando conectar a Supabase...");
            conexion = DatabaseConnection.getConnection();
            
            // Verificar si la conexión es válida
            if (conexion != null && !conexion.isClosed()) {
                System.out.println("✅ Conexión exitosa a Supabase!");
                System.out.println();
                System.out.println("Detalles de la conexión:");
                System.out.println("  - Servidor: db.ibktbfxgpuxbiufkggpo.supabase.co");
                System.out.println("  - Base de datos: postgres");
                System.out.println("  - Puerto: 5432");
                System.out.println("  - Estado: Activo");
                System.out.println();
                System.out.println("✅ ¡LISTA PARA USAR! Ahora puedes guardar y leer datos.");
            } else {
                System.out.println("❌ Error: La conexión es nula o está cerrada.");
            }
            
        } catch (SQLException e) {
            // Si hay error, mostrar el mensaje de error
            System.out.println("❌ ERROR DE CONEXIÓN");
            System.out.println("Mensaje de error: " + e.getMessage());
            System.out.println();
            System.out.println("Posibles causas:");
            System.out.println("  1. El usuario/contraseña es incorrecto");
            System.out.println("  2. La URL de conexión es incorrecta");
            System.out.println("  3. No hay conexión a internet");
            System.out.println("  4. Supabase está caído");
            System.out.println();
            e.printStackTrace(); // Mostrar la traza del error completa
            
        } catch (Exception e) {
            // Otros errores inesperados
            System.out.println("❌ ERROR INESPERADO");
            System.out.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
            
        } finally {
            // Cerrar la conexión si está abierta
            if (conexion != null) {
                try {
                    conexion.close();
                    System.out.println();
                    System.out.println("🔌 Conexión cerrada correctamente.");
                } catch (SQLException e) {
                    System.out.println("⚠️ Error al cerrar la conexión: " + e.getMessage());
                }
            }
            
            System.out.println("========================================");
        }
    }
}

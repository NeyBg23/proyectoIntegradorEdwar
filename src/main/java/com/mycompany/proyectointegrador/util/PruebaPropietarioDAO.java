/*
 * Prueba del PropietarioDAO para verificar que funcione con Supabase
 */
package com.mycompany.proyectointegrador.util;

import com.mycompany.proyectointegrador.dao.PropietarioDAO;
import com.mycompany.proyectointegrador.modelo.Propietario;
import java.util.List;

/**
 * Clase de prueba para PropietarioDAO
 * 
 * Esta prueba verifica que podemos:
 * 1. Guardar un propietario en Supabase
 * 2. Leer todos los propietarios de Supabase
 * 
 * @author edward
 */
public class PruebaPropietarioDAO {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("PRUEBA DE PROPIETARIO DAO");
        System.out.println("========================================");
        System.out.println();
        
        // Crear instancia del DAO
        PropietarioDAO propietarioDAO = new PropietarioDAO();
        
        // ========== PRUEBA 1: INSERTAR PROPIETARIO ==========
        System.out.println("📝 PRUEBA 1: Insertar un propietario nuevo");
        System.out.println("------------------------------------------");
        
        try {
            // Crear un propietario de prueba (sin ID, será generado por la BD)
            Propietario nuevoPropietario = new Propietario(
                "Juan Pérez López",             // Nombre
                "1234567890",                   // Identificación
                "3001234567",                   // Teléfono
                "juan.perez@ejemplo.com"        // Correo
            );
            
            System.out.println("Intentando guardar: " + nuevoPropietario.getNombre());
            
            // Insertar en la base de datos
            propietarioDAO.guardar(nuevoPropietario);
            
            System.out.println("✅ Propietario guardado exitosamente");
            System.out.println();
            
        } catch (Exception e) {
            System.err.println("❌ Error al guardar propietario:");
            System.err.println("   " + e.getMessage());
            e.printStackTrace();
            return; // Si falla, no continuamos
        }
        
        // ========== PRUEBA 2: LEER TODOS LOS PROPIETARIOS ==========
        System.out.println("📖 PRUEBA 2: Leer todos los propietarios");
        System.out.println("------------------------------------------");
        
        try {
            // Obtener todos los propietarios (método correcto es "listar")
            List<Propietario> propietarios = propietarioDAO.listar();
            
            if (propietarios == null || propietarios.isEmpty()) {
                System.out.println("⚠️ No hay propietarios en la base de datos");
            } else {
                System.out.println("✅ Se encontraron " + propietarios.size() + " propietario(s):");
                System.out.println();
                
                // Mostrar cada propietario
                for (int i = 0; i < propietarios.size(); i++) {
                    Propietario p = propietarios.get(i);
                    System.out.println("Propietario #" + (i + 1) + ":");
                    System.out.println("  - ID: " + p.getId());
                    System.out.println("  - Nombre: " + p.getNombre());
                    System.out.println("  - Identificación: " + p.getIdentificacion());
                    System.out.println("  - Teléfono: " + p.getTelefono());
                    System.out.println("  - Correo: " + p.getCorreo()); // ✅ Corregido: getCorreo()
                    System.out.println();
                }
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error al leer propietarios:");
            System.err.println("   " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("========================================");
        System.out.println("✅ PRUEBA COMPLETADA");
        System.out.println("========================================");
    }
}

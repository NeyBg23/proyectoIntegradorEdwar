/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.vista;

import com.mycompany.proyectointegradorfitosanitario.modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Panel de Menú para Administrador
 * Interfaz AZUL - Administración del Sistema
 * @author Equipo Proyecto Integrador
 * @version 1.0
 */
public class FrmMenuAdmin extends JFrame {
    
    private JPanel panelPrincipal;
    private JPanel panelHeader;
    private JLabel lblBienvenida;
    private JLabel lblUsuario;
    private JButton btnGestionarPropietarios;
    private JButton btnGestionarAsistentes;
    private JButton btnVerEspecies;
    private JButton btnVerPlagas;
    private JButton btnReportes;
    private JButton btnCerrarSesion;
    
    private Usuario usuarioLogueado;
    
    /**
     * Constructor - recibe el usuario logueado
     */
    public FrmMenuAdmin(Usuario usuario) {
        this.usuarioLogueado = usuario;
        inicializarComponentes();
    }
    
    /**
     * Inicializa todos los componentes de la interfaz
     */
    private void inicializarComponentes() {
        // Configuración de la ventana
        setTitle("Panel de Administrador - Sistema de Inspección Fitosanitaria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel principal
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);
        panelPrincipal.setBackground(new Color(245, 245, 245));
        
        // ===== PANEL HEADER (AZUL) =====
        panelHeader = new JPanel();
        panelHeader.setBounds(0, 0, 700, 100);
        panelHeader.setBackground(new Color(33, 150, 243)); // AZUL
        panelHeader.setLayout(null);
        
        lblBienvenida = new JLabel("PANEL DE ADMINISTRADOR");
        lblBienvenida.setBounds(20, 15, 660, 35);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 22));
        lblBienvenida.setForeground(Color.WHITE);
        panelHeader.add(lblBienvenida);
        
        lblUsuario = new JLabel("👤 Logueado como: " + usuarioLogueado.getUsername() + " | Rol: " + usuarioLogueado.getRol());
        lblUsuario.setBounds(20, 50, 660, 25);
        lblUsuario.setFont(new Font("Arial", Font.ITALIC, 12));
        lblUsuario.setForeground(new Color(220, 220, 220));
        panelHeader.add(lblUsuario);
        
        panelPrincipal.add(panelHeader);
        
        // ===== BOTONES DE OPCIONES =====
        
        // 1. Gestionar Propietarios
        btnGestionarPropietarios = crearBoton(
            "🌾 Gestionar Propietarios",
            50, 130, 300, 80,
            new Color(0, 128, 96),
            "Administra registros de productores agrícolas"
        );
        btnGestionarPropietarios.addActionListener(this::abrirGestionarPropietarios);
        panelPrincipal.add(btnGestionarPropietarios);
        
        // 2. Gestionar Asistentes
        btnGestionarAsistentes = crearBoton(
            "🔧 Gestionar Asistentes",
            350, 130, 300, 80,
            new Color(0, 102, 204),
            "Administra técnicos inspectores"
        );
        btnGestionarAsistentes.addActionListener(this::abrirGestionarAsistentes);
        panelPrincipal.add(btnGestionarAsistentes);
        
        // 3. Ver Especies
        btnVerEspecies = crearBoton(
            "🌱 Ver Especies Vegetales",
            50, 230, 300, 80,
            new Color(139, 69, 19),
            "Consulta catálogo de cultivos"
        );
        btnVerEspecies.addActionListener(this::abrirVerEspecies);
        panelPrincipal.add(btnVerEspecies);
        
        // 4. Ver Plagas
        btnVerPlagas = crearBoton(
            "🐛 Ver Plagas",
            350, 230, 300, 80,
            new Color(192, 0, 0),
            "Consulta catálogo de plagas fitosanitarias"
        );
        btnVerPlagas.addActionListener(this::abrirVerPlagas);
        panelPrincipal.add(btnVerPlagas);
        
        // 5. Ver Reportes
        btnReportes = crearBoton(
            "📊 Ver Reportes",
            50, 330, 300, 80,
            new Color(102, 102, 102),
            "Análisis de inspecciones realizadas"
        );
        btnReportes.addActionListener(this::abrirReportes);
        panelPrincipal.add(btnReportes);
        
        // 6. Cerrar Sesión
        btnCerrarSesion = crearBoton(
            "🚪 Cerrar Sesión",
            350, 330, 300, 80,
            new Color(211, 47, 47),
            "Salir del sistema"
        );
        btnCerrarSesion.addActionListener(this::cerrarSesion);
        panelPrincipal.add(btnCerrarSesion);
        
        // Agregar panel a la ventana
        setContentPane(panelPrincipal);
    }
    
    /**
     * Método auxiliar para crear botones con estilo
     */
    private JButton crearBoton(String texto, int x, int y, int ancho, int alto, 
                              Color color, String tooltip) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, ancho, alto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
        boton.setToolTipText(tooltip);
        boton.setFocusPainted(false);
        return boton;
    }
    
    // ===== ACCIONES DE BOTONES =====
    
    private void abrirGestionarPropietarios(ActionEvent e) {
        System.out.println("\n📋 Abriendo: Gestionar Propietarios");
        JOptionPane.showMessageDialog(this,
            "Próximamente: CRUD de Propietarios\n" +
            "- Crear propietario\n" +
            "- Ver lista\n" +
            "- Actualizar datos\n" +
            "- Eliminar",
            "Gestionar Propietarios",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirGestionarAsistentes(ActionEvent e) {
        System.out.println("\n📋 Abriendo: Gestionar Asistentes Técnicos");
        JOptionPane.showMessageDialog(this,
            "Próximamente: CRUD de Asistentes Técnicos\n" +
            "- Registrar asistente\n" +
            "- Ver lista\n" +
            "- Actualizar datos\n" +
            "- Eliminar",
            "Gestionar Asistentes Técnicos",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirVerEspecies(ActionEvent e) {
        System.out.println("\n📋 Abriendo: Ver Especies Vegetales");
        JOptionPane.showMessageDialog(this,
            "Próximamente: Consulta de Especies\n" +
            "- Banano\n" +
            "- Tomate\n" +
            "- Café\n" +
            "- Papa",
            "Especies Vegetales",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirVerPlagas(ActionEvent e) {
        System.out.println("\n📋 Abriendo: Ver Plagas");
        JOptionPane.showMessageDialog(this,
            "Próximamente: Consulta de Plagas\n" +
            "- Picudo negro del banano\n" +
            "- Polilla del tomate\n" +
            "- Broca del café\n" +
            "- Tizón tardío",
            "Plagas Fitosanitarias",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirReportes(ActionEvent e) {
        System.out.println("\n📋 Abriendo: Ver Reportes");
        JOptionPane.showMessageDialog(this,
            "Próximamente: Análisis de Reportes\n" +
            "- Inspecciones realizadas\n" +
            "- Plagas detectadas\n" +
            "- Predios con alertas\n" +
            "- Gráficos estadísticos",
            "Reportes del Sistema",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void cerrarSesion(ActionEvent e) {
        System.out.println("\n🚪 Cerrando sesión para: " + usuarioLogueado.getUsername());
        
        int confirmar = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de que deseas cerrar sesión?",
            "Confirmar cierre de sesión",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmar == JOptionPane.YES_OPTION) {
            System.out.println("✅ Sesión cerrada. Volviendo a login...");
            this.dispose();
            
            // Volver a la pantalla de login
            SwingUtilities.invokeLater(() -> {
                FrmLogin loginFrame = new FrmLogin();
                loginFrame.setVisible(true);
            });
        }
    }
    
    /**
     * Main para probar la ventana
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Usuario de prueba
            Usuario admin = new Usuario(
                java.util.UUID.randomUUID(),
                "admin",
                "admin123",
                "ADMIN",
                "admin@ica.gov.co",
                true,
                java.time.LocalDateTime.now()
            );
            
            FrmMenuAdmin frame = new FrmMenuAdmin(admin);
            frame.setVisible(true);
        });
    }
}

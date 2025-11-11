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
 * Panel de Menú para Propietario
 * Interfaz VERDE - Gestión de predios e inspecciones
 * @author Equipo Proyecto Integrador
 * @version 1.0
 */
public class FrmMenuPropietario extends JFrame {
    
    private JPanel panelPrincipal;
    private JPanel panelHeader;
    private JLabel lblBienvenida;
    private JLabel lblUsuario;
    private JButton btnMisDatos;
    private JButton btnMisPredios;
    private JButton btnCrearPredio;
    private JButton btnSolicitarInspeccion;
    private JButton btnVerMisInformes;
    private JButton btnCerrarSesion;
    
    private Usuario usuarioLogueado;
    
    public FrmMenuPropietario(Usuario usuario) {
        this.usuarioLogueado = usuario;
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Panel de Propietario - Sistema de Inspección Fitosanitaria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);
        panelPrincipal.setBackground(new Color(245, 245, 245));
        
        // ===== PANEL HEADER (VERDE) =====
        panelHeader = new JPanel();
        panelHeader.setBounds(0, 0, 700, 100);
        panelHeader.setBackground(new Color(0, 128, 96)); // VERDE
        panelHeader.setLayout(null);
        
        lblBienvenida = new JLabel("PANEL DE PROPIETARIO");
        lblBienvenida.setBounds(20, 15, 660, 35);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 22));
        lblBienvenida.setForeground(Color.WHITE);
        panelHeader.add(lblBienvenida);
        
        lblUsuario = new JLabel("🌾 Logueado como: " + usuarioLogueado.getUsername() + " | Rol: " + usuarioLogueado.getRol());
        lblUsuario.setBounds(20, 50, 660, 25);
        lblUsuario.setFont(new Font("Arial", Font.ITALIC, 12));
        lblUsuario.setForeground(new Color(200, 255, 200));
        panelHeader.add(lblUsuario);
        
        panelPrincipal.add(panelHeader);
        
        // ===== BOTONES =====
        
        btnMisDatos = crearBoton(
            "👤 Mi Perfil",
            50, 130, 300, 80,
            new Color(0, 102, 204),
            "Ver y actualizar mis datos personales"
        );
        btnMisDatos.addActionListener(this::abrirMisDatos);
        panelPrincipal.add(btnMisDatos);
        
        btnMisPredios = crearBoton(
            "🏞️ Mis Predios",
            350, 130, 300, 80,
            new Color(76, 175, 80),
            "Ver lista de mis predios registrados"
        );
        btnMisPredios.addActionListener(this::abrirMisPredios);
        panelPrincipal.add(btnMisPredios);
        
        btnCrearPredio = crearBoton(
            "➕ Crear Predio",
            50, 230, 300, 80,
            new Color(139, 69, 19),
            "Registrar nuevo predio"
        );
        btnCrearPredio.addActionListener(this::abrirCrearPredio);
        panelPrincipal.add(btnCrearPredio);
        
        btnSolicitarInspeccion = crearBoton(
            "📋 Solicitar Inspección",
            350, 230, 300, 80,
            new Color(255, 152, 0),
            "Solicitar inspección fitosanitaria"
        );
        btnSolicitarInspeccion.addActionListener(this::abrirSolicitarInspeccion);
        panelPrincipal.add(btnSolicitarInspeccion);
        
        btnVerMisInformes = crearBoton(
            "📄 Ver mis Informes",
            50, 330, 300, 80,
            new Color(102, 102, 102),
            "Consultar reportes de inspecciones"
        );
        btnVerMisInformes.addActionListener(this::abrirVerInformes);
        panelPrincipal.add(btnVerMisInformes);
        
        btnCerrarSesion = crearBoton(
            "🚪 Cerrar Sesión",
            350, 330, 300, 80,
            new Color(211, 47, 47),
            "Salir del sistema"
        );
        btnCerrarSesion.addActionListener(this::cerrarSesion);
        panelPrincipal.add(btnCerrarSesion);
        
        setContentPane(panelPrincipal);
    }
    
    private JButton crearBoton(String texto, int x, int y, int ancho, int alto, Color color, String tooltip) {
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
    
    private void abrirMisDatos(ActionEvent e) {
        System.out.println("\n👤 Abriendo: Mi Perfil");
        JOptionPane.showMessageDialog(this, "Funcionalidad: Ver y editar mi perfil", "Mi Perfil", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirMisPredios(ActionEvent e) {
        System.out.println("\n🏞️ Abriendo: Mis Predios");
        JOptionPane.showMessageDialog(this, "Funcionalidad: Tabla de mis predios", "Mis Predios", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirCrearPredio(ActionEvent e) {
        System.out.println("\n➕ Abriendo: Crear Predio");
        SwingUtilities.invokeLater(() -> {
            FrmCrearPredio frame = new FrmCrearPredio(usuarioLogueado);
            frame.setVisible(true);
        });
    }
    
    private void abrirSolicitarInspeccion(ActionEvent e) {
        System.out.println("\n📋 Abriendo: Solicitar Inspección");
        JOptionPane.showMessageDialog(this, "Funcionalidad: Solicitar inspección", "Solicitar Inspección", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void abrirVerInformes(ActionEvent e) {
        System.out.println("\n📄 Abriendo: Ver mis Informes");
        JOptionPane.showMessageDialog(this, "Funcionalidad: Mis reportes de inspección", "Mis Informes", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void cerrarSesion(ActionEvent e) {
        System.out.println("\n🚪 Cerrando sesión");
        int confirmar = JOptionPane.showConfirmDialog(this, "¿Cerrar sesión?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                FrmLogin login = new FrmLogin();
                login.setVisible(true);
            });
        }
    }
}

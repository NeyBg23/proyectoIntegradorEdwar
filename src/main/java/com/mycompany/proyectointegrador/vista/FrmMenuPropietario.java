/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Menú Principal para Propietarios
 * Acceso limitado a funcionalidades específicas del propietario
 * Permite: ver predios, solicitar inspecciones, ver reportes
 * @author edward
 */
public class FrmMenuPropietario extends JFrame {

    private JPanel contentPane;
    private JButton btnMisPredios, btnSolicitarInspeccion, btnVerReportes, btnCerrarSesion;
    private JLabel lblUsuarioActual;
    private Usuario usuarioActual;

    // Constructor con Usuario
    public FrmMenuPropietario(Usuario usuario) {
        this.usuarioActual = usuario;
        initComponents();
        configurarVentana();
        crearComponentes();
        agregarEventos();
    }

    private void initComponents() {}

    private void configurarVentana() {
        setTitle("Menú Propietario - Sistema Fitosanitario");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void crearComponentes() {
        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBackground(new Color(236, 240, 241));
        setContentPane(contentPane);

        // ======= Panel superior (Título y Usuario) =======
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(56, 142, 60)); // Verde para propietarios
        panelTitulo.setPreferredSize(new Dimension(0, 100));

        JLabel lblTitulo = new JLabel("MENÚ PROPIETARIO", JLabel.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel lblSubtitulo = new JLabel("Gestión de mis Predios", JLabel.CENTER);
        lblSubtitulo.setForeground(Color.LIGHT_GRAY);

        // Información del usuario propietario
        lblUsuarioActual = new JLabel();
        lblUsuarioActual.setForeground(Color.WHITE);
        lblUsuarioActual.setFont(new Font("Arial", Font.PLAIN, 11));
        lblUsuarioActual.setHorizontalAlignment(JLabel.CENTER);
        
        if (usuarioActual != null) {
            lblUsuarioActual.setText("Usuario: " + usuarioActual.getNombreUsuario() + " | Rol: PROPIETARIO");
        }

        JPanel panelTexto = new JPanel(new GridLayout(3, 1));
        panelTexto.setBackground(new Color(56, 142, 60));
        panelTexto.add(lblTitulo);
        panelTexto.add(lblSubtitulo);
        panelTexto.add(lblUsuarioActual);
        panelTitulo.add(panelTexto, BorderLayout.CENTER);

        contentPane.add(panelTitulo, BorderLayout.NORTH);

        // ======= Panel central (Botones para propietario) =======
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(60, 120, 60, 120));
        panelBotones.setBackground(new Color(236, 240, 241));

        // Botones específicos del propietario
        btnMisPredios = new JButton("Mis Predios");
        btnSolicitarInspeccion = new JButton("Solicitar Inspección");
        btnVerReportes = new JButton("Ver Mis Reportes");
        btnCerrarSesion = new JButton("Cerrar Sesión");

        configurarBoton(btnMisPredios);
        configurarBoton(btnSolicitarInspeccion);
        configurarBoton(btnVerReportes);
        
        // Botón cerrar con estilo diferente
        btnCerrarSesion.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setBackground(new Color(231, 76, 60)); // Rojo
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelBotones.add(btnMisPredios);
        panelBotones.add(btnSolicitarInspeccion);
        panelBotones.add(btnVerReportes);
        panelBotones.add(btnCerrarSesion);

        contentPane.add(panelBotones, BorderLayout.CENTER);
    }

    private void configurarBoton(JButton boton) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(56, 142, 60)); // Verde
        boton.setForeground(Color.WHITE);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(43, 110, 48));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(56, 142, 60));
            }
        });
    }

    private void agregarEventos() {
        // Mis Predios - Abre FrmPredio con filtro del propietario
        btnMisPredios.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Abriendo tus predios...\n\nFuncionalidad: Ver solo los predios del propietario: " + 
                usuarioActual.getNombreUsuario(),
                "Mis Predios",
                JOptionPane.INFORMATION_MESSAGE);
            // Aquí abriría FrmPredio filtrado por propietario
            FrmPredio ventana = new FrmPredio();
            ventana.setVisible(true);
        });

        // Solicitar Inspección
        btnSolicitarInspeccion.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Funcionalidad: Solicitar inspección fitosanitaria para tus predios.",
                "Solicitar Inspección",
                JOptionPane.INFORMATION_MESSAGE);
            // Aquí abriría formulario para solicitar inspección
        });

        // Ver Reportes
        btnVerReportes.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Abriendo tus reportes...\n\nVerás solo inspecciones de tus predios",
                "Mis Reportes",
                JOptionPane.INFORMATION_MESSAGE);
            FrmReporte ventana = new FrmReporte();
            ventana.setVisible(true);
        });

        // Cerrar Sesión
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    /**
     * Cierra la sesión actual y regresa al login
     */
    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(
            this, 
            "¿Desea cerrar sesión?", 
            "Confirmación",
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (opcion == JOptionPane.YES_OPTION) {
            this.dispose();
            FrmLogin login = new FrmLogin();
            login.setVisible(true);
        }
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
}

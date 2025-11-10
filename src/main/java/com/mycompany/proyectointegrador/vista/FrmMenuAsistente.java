package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Menú Principal para Asistentes Técnicos
 * Acceso limitado a funcionalidades operativas
 * @author edward
 */
public class FrmMenuAsistente extends JFrame {

    private JPanel contentPane;
    private JButton btnPredios, btnRealizarInspeccion, btnGenerarInformes, btnHistorialInspecciones, btnCerrarSesion;
    private JLabel lblUsuarioActual;
    private Usuario usuarioActual;

    public FrmMenuAsistente(Usuario usuario) {
        this.usuarioActual = usuario;
        initComponents();
        configurarVentana();
        crearComponentes();
        agregarEventos();
    }

    private void initComponents() {}

    private void configurarVentana() {
        setTitle("Menú Asistente - Sistema Fitosanitario");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void crearComponentes() {
        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBackground(new Color(236, 240, 241));
        setContentPane(contentPane);

        // ======= Panel superior (Título y Usuario) =======
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(255, 152, 0));
        panelTitulo.setPreferredSize(new Dimension(0, 100));

        JLabel lblTitulo = new JLabel("MENÚ ASISTENTE TÉCNICO", JLabel.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel lblSubtitulo = new JLabel("Gestión de Inspecciones Fitosanitarias", JLabel.CENTER);
        lblSubtitulo.setForeground(Color.LIGHT_GRAY);

        lblUsuarioActual = new JLabel();
        lblUsuarioActual.setForeground(Color.WHITE);
        lblUsuarioActual.setFont(new Font("Arial", Font.PLAIN, 11));
        lblUsuarioActual.setHorizontalAlignment(JLabel.CENTER);
        
        if (usuarioActual != null) {
            lblUsuarioActual.setText("Usuario: " + usuarioActual.getNombreUsuario() + " | Rol: ASISTENTE");
        }

        JPanel panelTexto = new JPanel(new GridLayout(3, 1));
        panelTexto.setBackground(new Color(255, 152, 0));
        panelTexto.add(lblTitulo);
        panelTexto.add(lblSubtitulo);
        panelTexto.add(lblUsuarioActual);
        panelTitulo.add(panelTexto, BorderLayout.CENTER);

        contentPane.add(panelTitulo, BorderLayout.NORTH);

        // ======= Panel central (Botones) =======
        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(60, 120, 60, 120));
        panelBotones.setBackground(new Color(236, 240, 241));

        btnPredios = new JButton("Gestionar Predios");
        btnRealizarInspeccion = new JButton("Realizar Inspección");
        btnGenerarInformes = new JButton("Generar Informes");
        btnHistorialInspecciones = new JButton("Historial de Inspecciones");
        btnCerrarSesion = new JButton("Cerrar Sesión");

        configurarBoton(btnPredios);
        configurarBoton(btnRealizarInspeccion);
        configurarBoton(btnGenerarInformes);
        configurarBoton(btnHistorialInspecciones);
        
        btnCerrarSesion.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setBackground(new Color(231, 76, 60));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelBotones.add(btnPredios);
        panelBotones.add(btnRealizarInspeccion);
        panelBotones.add(btnGenerarInformes);
        panelBotones.add(btnHistorialInspecciones);
        panelBotones.add(btnCerrarSesion);

        contentPane.add(panelBotones, BorderLayout.CENTER);
    }

    private void configurarBoton(JButton boton) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(255, 152, 0));
        boton.setForeground(Color.WHITE);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(245, 127, 23));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(255, 152, 0));
            }
        });
    }

    private void agregarEventos() {
        btnPredios.addActionListener(e -> {
            try {
                FrmPredio ventana = new FrmPredio(usuarioActual);
                ventana.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace(); // Para ver el error en consola
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });


        // Realizar Inspección
        btnRealizarInspeccion.addActionListener(e -> {
            FrmInspeccionFitosanitaria ventana = new FrmInspeccionFitosanitaria(usuarioActual);
            ventana.setVisible(true);
        });

        // Generar Informes
        btnGenerarInformes.addActionListener(e -> {
            // Aquí abriría formulario de reportes/informes
            JOptionPane.showMessageDialog(this, 
                "Funcionalidad: Generar informes fitosanitarios.",
                "Generar Informes",
                JOptionPane.INFORMATION_MESSAGE);
        });

        // Historial de Inspecciones
        btnHistorialInspecciones.addActionListener(e -> {
            FrmReporte ventana = new FrmReporte(usuarioActual);
            ventana.setVisible(true);
        });

        // Cerrar Sesión
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
    }

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

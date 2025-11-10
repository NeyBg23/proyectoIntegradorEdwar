/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Menú Principal del Sistema
 * Gestiona navegación a todos los módulos
 * @author edward
 */
public class FrmMenu extends JFrame {

    private JPanel contentPane;
    private JButton btnPropietario, btnAsistente, btnDepartamento, btnMunicipio;
    private JButton btnEspecies, btnPlagas, btnPredios, btnInspecciones, btnReportes;
    private JButton btnCerrarSesion;
    private JLabel lblUsuarioActual;
    private Usuario usuarioActual;

    // Constructor sin parámetros (para compatibilidad con código anterior)
    public FrmMenu() {
        this(null); // Llama al constructor con Usuario
    }

    // Constructor con Usuario (para sistema de login)
    public FrmMenu(Usuario usuario) {
        this.usuarioActual = usuario;
        initComponents();
        configurarVentana();
        crearComponentes();
        agregarEventos();
    }

    private void initComponents() {}

    private void configurarVentana() {
        setTitle("Menú Principal - Sistema Fitosanitario");
        setSize(700, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void crearComponentes() {
        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBackground(new Color(236, 240, 241));
        setContentPane(contentPane);

        // ======= Panel superior (Título y Usuario) =======
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(52, 73, 94));
        panelTitulo.setPreferredSize(new Dimension(0, 100));

        JLabel lblTitulo = new JLabel("MENÚ PRINCIPAL", JLabel.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel lblSubtitulo = new JLabel("Sistema de Inspección Fitosanitaria", JLabel.CENTER);
        lblSubtitulo.setForeground(Color.LIGHT_GRAY);

        // Mostrar usuario actual si existe
        lblUsuarioActual = new JLabel();
        lblUsuarioActual.setForeground(Color.WHITE);
        lblUsuarioActual.setFont(new Font("Arial", Font.PLAIN, 11));
        lblUsuarioActual.setHorizontalAlignment(JLabel.CENTER);

        if (usuarioActual != null) {
            lblUsuarioActual.setText("Usuario: " + usuarioActual.getNombreUsuario() + 
                                     " | Rol: " + usuarioActual.getRol().toUpperCase());
        } else {
            lblUsuarioActual.setText("Modo sin autenticación");
        }

        JPanel panelTexto = new JPanel(new GridLayout(3, 1));
        panelTexto.setBackground(new Color(52, 73, 94));
        panelTexto.add(lblTitulo);
        panelTexto.add(lblSubtitulo);
        panelTexto.add(lblUsuarioActual);
        panelTitulo.add(panelTexto, BorderLayout.CENTER);

        contentPane.add(panelTitulo, BorderLayout.NORTH);

        // ======= Panel central (Botones en Grid 3x3) =======
        JPanel panelBotones = new JPanel(new GridLayout(4, 3, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        panelBotones.setBackground(new Color(236, 240, 241));

        // Inicializar botones
        btnPropietario = new JButton("Propietarios");
        btnAsistente = new JButton("Asistentes");
        btnDepartamento = new JButton("Departamentos");
        btnMunicipio = new JButton("Municipios");
        btnEspecies = new JButton("Especies Vegetales");
        btnPlagas = new JButton("Plagas");
        btnPredios = new JButton("Predios");
        btnInspecciones = new JButton("Inspecciones");
        btnReportes = new JButton("Reportes");
        btnCerrarSesion = new JButton("Cerrar Sesión");

        // Configurar estilo de botones
        configurarBoton(btnPropietario);
        configurarBoton(btnAsistente);
        configurarBoton(btnDepartamento);
        configurarBoton(btnMunicipio);
        configurarBoton(btnEspecies);
        configurarBoton(btnPlagas);
        configurarBoton(btnPredios);
        configurarBoton(btnInspecciones);
        configurarBoton(btnReportes);

        // Botón cerrar sesión con estilo diferente
        btnCerrarSesion.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setBackground(new Color(231, 76, 60)); // Rojo
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // ======= CONTROL DE ACCESO POR ROL =======
        if(usuarioActual != null && "ADMIN".equalsIgnoreCase(usuarioActual.getRol())) {
            // Ocultar botones que el admin NO gestiona
            btnPredios.setVisible(false);
            btnReportes.setVisible(false);

            // ✅ Renombrar botón de inspecciones para admin (solo consulta)
            btnInspecciones.setText("Consultar Inspecciones");
        }


        // Agregar botones al panel
        panelBotones.add(btnPropietario);
        panelBotones.add(btnAsistente);
        panelBotones.add(btnDepartamento);
        panelBotones.add(btnMunicipio);
        panelBotones.add(btnEspecies);
        panelBotones.add(btnPlagas);
        panelBotones.add(btnPredios);
        panelBotones.add(btnInspecciones);
        panelBotones.add(btnReportes);
        panelBotones.add(new JLabel("")); // Espacio vacío
        panelBotones.add(btnCerrarSesion);
        panelBotones.add(new JLabel("")); // Espacio vacío

        contentPane.add(panelBotones, BorderLayout.CENTER);
    }


    private void configurarBoton(JButton boton) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(41, 128, 185));
        boton.setForeground(Color.WHITE);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(31, 97, 141));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(41, 128, 185));
            }
        });
    }

    private void agregarEventos() {
        // Propietarios
        btnPropietario.addActionListener(e -> {
            FrmPropietario ventana = new FrmPropietario();
            ventana.setVisible(true);
        });

        // Asistentes
        btnAsistente.addActionListener(e -> {
            FrmAsistente ventana = new FrmAsistente();
            ventana.setVisible(true);
        });

        // Departamentos
        btnDepartamento.addActionListener(e -> {
            FrmDepartamento ventana = new FrmDepartamento();
            ventana.setVisible(true);
        });

        // Municipios
        btnMunicipio.addActionListener(e -> {
            FrmMunicipio ventana = new FrmMunicipio();
            ventana.setVisible(true);
        });

        // Especies Vegetales
        btnEspecies.addActionListener(e -> {
            FrmEspecieVegetal ventana = new FrmEspecieVegetal();
            ventana.setVisible(true);
        });

        // Plagas
        btnPlagas.addActionListener(e -> {
            FrmPlagas ventana = new FrmPlagas();
            ventana.setVisible(true);
        });

        // Predios
        btnPredios.addActionListener(e -> {
            FrmPredio ventana = new FrmPredio();
            ventana.setVisible(true);
        });

        // Inspecciones
        btnInspecciones.addActionListener(e -> {
            FrmInspeccionFitosanitaria ventana = new FrmInspeccionFitosanitaria(usuarioActual);
            ventana.setVisible(true);
        });

        // Reportes
        btnReportes.addActionListener(e -> {
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
            
            // Si hay sistema de login, abrirlo
            if (usuarioActual != null) {
                FrmLogin login = new FrmLogin();
                login.setVisible(true);
            }
        }
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmMenu().setVisible(true));
    }
}

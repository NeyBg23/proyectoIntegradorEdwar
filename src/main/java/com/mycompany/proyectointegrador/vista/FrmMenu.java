/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.proyectointegrador.vista;

/**
 *
 * @author edward
 */



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author edward
 */
public class FrmMenu extends JFrame {

    private JPanel contentPane;
    private JButton btnPropietario, btnAsistente, btnDepartamento, btnMunicipio, btnSalir;

    public FrmMenu() {
        initComponents();
        configurarVentana();
        crearComponentes();
        agregarEventos();
    }

    private void initComponents() {}

    private void configurarVentana() {
        setTitle("Menú Principal - Proyecto Integrador");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void crearComponentes() {
        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBackground(new Color(236, 240, 241));
        setContentPane(contentPane);

        // ======= Panel superior (Título) =======
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(52, 73, 94));
        panelTitulo.setPreferredSize(new Dimension(0, 80));

        JLabel lblTitulo = new JLabel("MENÚ PRINCIPAL", JLabel.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel lblSubtitulo = new JLabel("Seleccione una opción para gestionar los registros", JLabel.CENTER);
        lblSubtitulo.setForeground(Color.LIGHT_GRAY);

        JPanel panelTexto = new JPanel(new GridLayout(2, 1));
        panelTexto.setBackground(new Color(52, 73, 94));
        panelTexto.add(lblTitulo);
        panelTexto.add(lblSubtitulo);
        panelTitulo.add(panelTexto, BorderLayout.CENTER);

        contentPane.add(panelTitulo, BorderLayout.NORTH);

        // ======= Panel central (Botones) =======
        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(40, 120, 40, 120));
        panelBotones.setBackground(new Color(236, 240, 241));

        btnPropietario = new JButton("Gestionar Propietarios");
        btnAsistente = new JButton("Gestionar Asistentes");
        btnDepartamento = new JButton("Gestionar Departamentos");
        btnMunicipio = new JButton("Gestionar Municipios");
        btnSalir = new JButton("Salir");

        configurarBoton(btnPropietario);
        configurarBoton(btnAsistente);
        configurarBoton(btnDepartamento);
        configurarBoton(btnMunicipio);
        configurarBoton(btnSalir);

        panelBotones.add(btnPropietario);
        panelBotones.add(btnAsistente);
        panelBotones.add(btnDepartamento);
        panelBotones.add(btnMunicipio);
        panelBotones.add(btnSalir);

        contentPane.add(panelBotones, BorderLayout.CENTER);
    }

    private void configurarBoton(JButton boton) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
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
        btnPropietario.addActionListener(e -> {
            FrmPropietario ventanaProp = new FrmPropietario();
            ventanaProp.setVisible(true);
        });

        btnAsistente.addActionListener(e -> {
            FrmAsistente ventanaAsis = new FrmAsistente();
            ventanaAsis.setVisible(true);
        });

        btnDepartamento.addActionListener(e -> {
            FrmDepartamento ventanaDepto = new FrmDepartamento();
            ventanaDepto.setVisible(true);
        });

        // ======= NUEVO BOTÓN MUNICIPIO =======
        btnMunicipio.addActionListener(e -> {
            FrmMunicipio ventanaMun = new FrmMunicipio();
            ventanaMun.setVisible(true);
        });

        btnSalir.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(
                this, "¿Desea salir del sistema?", "Confirmación",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
            );
            if (opcion == JOptionPane.YES_OPTION) {
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmMenu().setVisible(true));
    }
}

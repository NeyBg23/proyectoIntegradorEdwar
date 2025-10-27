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

public class FrmSubmenuInspeccion extends JFrame {

    private JPanel contentPane;
    private JButton btnInspeccionFito, btnEspecieVegetal, btnPlaga, btnVolver;

    public FrmSubmenuInspeccion() {
        initComponents();
        configurarVentana();
        crearComponentes();
        agregarEventos();
    }

    private void initComponents() {}

    private void configurarVentana() {
        setTitle("Gestión de Inspecciones - Proyecto Integrador");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void crearComponentes() {
        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBackground(new Color(236, 240, 241));
        setContentPane(contentPane);

        // ====== Panel superior (Título) ======
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(41, 128, 185));
        panelTitulo.setPreferredSize(new Dimension(0, 90));

        JLabel lblTitulo = new JLabel("GESTIÓN DE INSPECCIONES", JLabel.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));

        JLabel lblSubtitulo = new JLabel("Seleccione una opción para registrar información", JLabel.CENTER);
        lblSubtitulo.setForeground(new Color(220, 220, 220));
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel panelTextos = new JPanel(new GridLayout(2, 1));
        panelTextos.setBackground(new Color(41, 128, 185));
        panelTextos.add(lblTitulo);
        panelTextos.add(lblSubtitulo);
        panelTitulo.add(panelTextos, BorderLayout.CENTER);

        contentPane.add(panelTitulo, BorderLayout.NORTH);

        // ====== Panel central (Botones) ======
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(40, 120, 40, 120));
        panelBotones.setBackground(new Color(236, 240, 241));

        btnInspeccionFito = new JButton("Registrar Inspección Fitosanitaria");
        btnEspecieVegetal = new JButton("Registrar Especie Vegetal");
        btnPlaga = new JButton("Registrar Plaga");
        btnVolver = new JButton("Volver al Menú Principal");

        configurarBoton(btnInspeccionFito);
        configurarBoton(btnEspecieVegetal);
        configurarBoton(btnPlaga);
        configurarBoton(btnVolver);

        panelBotones.add(btnInspeccionFito);
        panelBotones.add(btnEspecieVegetal);
        panelBotones.add(btnPlaga);
        panelBotones.add(btnVolver);

        contentPane.add(panelBotones, BorderLayout.CENTER);
    }

    private void configurarBoton(JButton boton) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(41, 128, 185));
        boton.setForeground(Color.WHITE);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

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
        btnInspeccionFito.addActionListener(e -> {
            new FrmInspeccionFitosanitaria().setVisible(true);
            dispose();
        });

        btnEspecieVegetal.addActionListener(e -> {
            new FrmEspecieVegetal().setVisible(true);
            dispose();
        });

        btnPlaga.addActionListener(e -> {
            new FrmPlagas().setVisible(true);
            dispose();
        });

        btnVolver.addActionListener(e -> {
            new FrmPaginaPrincipal().setVisible(true);
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmSubmenuInspeccion().setVisible(true));
    }
}


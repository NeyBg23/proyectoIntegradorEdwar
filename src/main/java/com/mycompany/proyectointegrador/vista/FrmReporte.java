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

public class FrmReporte extends JFrame {

    public FrmReporte() {
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setTitle("Generación de Reportes Fitosanitarios");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void crearComponentes() {
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(236, 240, 241));
        setContentPane(contentPane);

        // Header
        JPanel header = new JPanel();
        header.setBackground(new Color(52, 73, 94));
        header.setPreferredSize(new Dimension(0, 70));

        JLabel lblTitulo = new JLabel("Reportes del Sistema", JLabel.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.add(lblTitulo);
        contentPane.add(header, BorderLayout.NORTH);

        // Panel central
        JPanel panel = new JPanel(new GridLayout(3, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(60, 150, 60, 150));
        panel.setBackground(new Color(236, 240, 241));

        JButton btnPredios = new JButton("Reporte de Predios");
        JButton btnInspecciones = new JButton("Reporte de Inspecciones");
        JButton btnVolver = new JButton("Volver al Menú");

        configurarBoton(btnPredios, new Color(41, 128, 185));
        configurarBoton(btnInspecciones, new Color(39, 174, 96));
        configurarBoton(btnVolver, new Color(149, 165, 166));

        btnPredios.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Generando reporte de predios...")
        );

        btnInspecciones.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Generando reporte de inspecciones...")
        );

        btnVolver.addActionListener(e -> {
            new FrmMenu().setVisible(true);
            dispose();
        });

        panel.add(btnPredios);
        panel.add(btnInspecciones);
        panel.add(btnVolver);

        contentPane.add(panel, BorderLayout.CENTER);
    }

    private void configurarBoton(JButton boton, Color color) {
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(color.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(color);
            }
        });
    }
}


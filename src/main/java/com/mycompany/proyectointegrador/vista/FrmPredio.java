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

public class FrmPredio extends JFrame {

    public FrmPredio() {
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setTitle("Gestión de Predios");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void crearComponentes() {
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(236, 240, 241));
        setContentPane(contentPane);

        // Encabezado
        JPanel header = new JPanel();
        header.setBackground(new Color(52, 73, 94));
        header.setPreferredSize(new Dimension(0, 70));

        JLabel lblTitulo = new JLabel("Gestión de Predios", JLabel.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.add(lblTitulo);
        contentPane.add(header, BorderLayout.NORTH);

        // Panel principal
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 15));
        panelForm.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));
        panelForm.setBackground(new Color(236, 240, 241));

        panelForm.add(new JLabel("ID Propietario:"));
        JTextField txtId = new JTextField();
        panelForm.add(txtId);

        panelForm.add(new JLabel("Nombre del Predio:"));
        JTextField txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Ubicación:"));
        JTextField txtUbicacion = new JTextField();
        panelForm.add(txtUbicacion);

        panelForm.add(new JLabel("Área (hectáreas):"));
        JTextField txtArea = new JTextField();
        panelForm.add(txtArea);

        // Botones
        JButton btnGuardar = new JButton("Guardar");
        JButton btnVolver = new JButton("Volver al Menú");

        configurarBoton(btnGuardar, new Color(41, 128, 185));
        configurarBoton(btnVolver, new Color(149, 165, 166));

        btnGuardar.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Predio registrado correctamente.")
        );

        btnVolver.addActionListener(e -> {
            new FrmMenu().setVisible(true);
            dispose();
        });

        panelForm.add(btnGuardar);
        panelForm.add(btnVolver);

        contentPane.add(panelForm, BorderLayout.CENTER);
    }

    private void configurarBoton(JButton boton, Color color) {
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 15));
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

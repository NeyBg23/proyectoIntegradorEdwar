/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.proyectointegrador.vista;

/**
 *
 * @author edward
 */


import com.mycompany.proyectointegrador.modelo.InspeccionFitosanitaria;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FrmInspeccionFitosanitaria extends JFrame {
    private JTextField txtId, txtCodigo, txtFecha, txtTrimestre;
    private JButton btnGuardar, btnVolver;

    public FrmInspeccionFitosanitaria() {
        setTitle("Registro de Inspección Fitosanitaria");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 5, 5));

        JLabel lblId = new JLabel("ID:");
        JLabel lblCodigo = new JLabel("Código:");
        JLabel lblFecha = new JLabel("Fecha:");
        JLabel lblTrimestre = new JLabel("Trimestre:");

        txtId = new JTextField();
        txtCodigo = new JTextField();
        txtFecha = new JTextField();
        txtTrimestre = new JTextField();

        btnGuardar = new JButton("Guardar");
        btnVolver = new JButton("Volver");

        add(lblId); add(txtId);
        add(lblCodigo); add(txtCodigo);
        add(lblFecha); add(txtFecha);
        add(lblTrimestre); add(txtTrimestre);
        add(btnGuardar); add(btnVolver);

        // Acción del botón guardar
        btnGuardar.addActionListener(e -> guardarInspeccion());

        // Acción del botón volver
        btnVolver.addActionListener(e -> {
            dispose();
            FrmSubmenuInspeccion frmSubmenu = new FrmSubmenuInspeccion();
            frmSubmenu.setVisible(true);
        });
    }

    private void guardarInspeccion() {
        try {
            String id = txtId.getText();
            String codigo = txtCodigo.getText();
            String fecha = txtFecha.getText();
            int trimestre = Integer.parseInt(txtTrimestre.getText());

            InspeccionFitosanitaria inspeccion = new InspeccionFitosanitaria(id, codigo, fecha, trimestre);

            JOptionPane.showMessageDialog(this, "Inspección registrada correctamente:\n" +
                    "ID: " + id + "\nCódigo: " + codigo + "\nFecha: " + fecha + "\nTrimestre: " + trimestre);

            limpiarCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: el trimestre debe ser un número entero.");
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtCodigo.setText("");
        txtFecha.setText("");
        txtTrimestre.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmInspeccionFitosanitaria().setVisible(true));
    }
}


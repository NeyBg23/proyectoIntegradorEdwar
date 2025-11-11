/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.vista;

import com.mycompany.proyectointegradorfitosanitario.dao.EspecieDAO;
import com.mycompany.proyectointegradorfitosanitario.modelo.Especie;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmGestionarEspecies extends JFrame {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private EspecieDAO dao;
    
    public FrmGestionarEspecies() {
        dao = new EspecieDAO();
        inicializarComponentes();
        cargarDatos();
    }
    
    private void inicializarComponentes() {
        setTitle("Gestionar Especies Vegetales");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(240, 240, 240));
        
        JLabel lblTitulo = new JLabel("GESTIÓN DE ESPECIES VEGETALES");
        lblTitulo.setBounds(20, 15, 660, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(139, 69, 19));
        panel.add(lblTitulo);
        
        String[] columnas = {"ID", "Código", "Científico", "Común", "Densidad"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Arial", Font.PLAIN, 10));
        tabla.setRowHeight(22);
        tabla.getTableHeader().setBackground(new Color(139, 69, 19));
        tabla.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 60, 660, 260);
        panel.add(scroll);
        
        JButton btnAgregar = new JButton("➕ Agregar");
        btnAgregar.setBounds(20, 330, 130, 30);
        btnAgregar.setBackground(new Color(76, 175, 80));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.addActionListener(e -> agregar());
        panel.add(btnAgregar);
        
        JButton btnActualizar = new JButton("🔄 Actualizar");
        btnActualizar.setBounds(160, 330, 130, 30);
        btnActualizar.setBackground(new Color(255, 193, 7));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.addActionListener(e -> cargarDatos());
        panel.add(btnActualizar);
        
        JButton btnVolver = new JButton("⬅️ Volver");
        btnVolver.setBounds(550, 330, 130, 30);
        btnVolver.setBackground(new Color(128, 128, 128));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.addActionListener(e -> this.dispose());
        panel.add(btnVolver);
        
        setContentPane(panel);
    }
    
    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Especie> especies = dao.listarTodos();
        for (Especie e : especies) {
            modeloTabla.addRow(new Object[]{e.getId(), e.getCodigo(), e.getNombreCientifico(), e.getNombreComun(), e.getDensidadSiembra()});
        }
    }
    
    private void agregar() {
        JTextField txtCodigo = new JTextField(5);
        JTextField txtCientifico = new JTextField(10);
        JTextField txtComun = new JTextField(10);
        JTextField txtDensidad = new JTextField(5);
        Object[] msg = {"Código:", txtCodigo, "Científico:", txtCientifico, "Común:", txtComun, "Densidad:", txtDensidad};
        
        if (JOptionPane.showConfirmDialog(this, msg, "Agregar Especie", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            if (txtCodigo.getText().trim().isEmpty() || txtCientifico.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                Integer densidad = txtDensidad.getText().trim().isEmpty() ? null : Integer.parseInt(txtDensidad.getText());
                Especie especie = new Especie(txtCodigo.getText(), txtCientifico.getText(), txtComun.getText(), densidad);
                if (dao.insertar(especie)) {
                    JOptionPane.showMessageDialog(this, "✅ Agregada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatos();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Densidad debe ser número", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

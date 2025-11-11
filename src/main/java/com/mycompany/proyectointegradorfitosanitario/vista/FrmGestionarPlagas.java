/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.vista;

import com.mycompany.proyectointegradorfitosanitario.dao.PlagaDAO;
import com.mycompany.proyectointegradorfitosanitario.modelo.Plaga;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmGestionarPlagas extends JFrame {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private PlagaDAO dao;
    
    public FrmGestionarPlagas() {
        dao = new PlagaDAO();
        inicializarComponentes();
        cargarDatos();
    }
    
    private void inicializarComponentes() {
        setTitle("Gestionar Plagas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(240, 240, 240));
        
        JLabel lblTitulo = new JLabel("GESTIÓN DE PLAGAS FITOSANITARIAS");
        lblTitulo.setBounds(20, 15, 660, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(192, 0, 0));
        panel.add(lblTitulo);
        
        String[] columnas = {"Código", "Científico", "Común", "Nivel Alerta"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Arial", Font.PLAIN, 11));
        tabla.setRowHeight(22);
        tabla.getTableHeader().setBackground(new Color(192, 0, 0));
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
        List<Plaga> plagas = dao.listarTodos();
        for (Plaga p : plagas) {
            modeloTabla.addRow(new Object[]{p.getCodigo(), p.getNombreCientifico(), p.getNombreComun(), p.getNivelAlerta()});
        }
    }
    
    private void agregar() {
        JTextField txtCodigo = new JTextField(5);
        JTextField txtCientifico = new JTextField(10);
        JTextField txtComun = new JTextField(10);
        JComboBox<String> comboNivel = new JComboBox<>(new String[]{"BAJO", "MEDIO", "ALTO"});
        Object[] msg = {"Código:", txtCodigo, "Científico:", txtCientifico, "Común:", txtComun, "Nivel:", comboNivel};
        
        if (JOptionPane.showConfirmDialog(this, msg, "Agregar Plaga", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            if (txtCodigo.getText().trim().isEmpty() || txtCientifico.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Plaga plaga = new Plaga(txtCodigo.getText(), txtCientifico.getText(), txtComun.getText(), (String) comboNivel.getSelectedItem());
            if (dao.insertar(plaga)) {
                JOptionPane.showMessageDialog(this, "✅ Agregada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
            }
        }
    }
}

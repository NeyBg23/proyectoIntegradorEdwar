/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.vista;

import com.mycompany.proyectointegradorfitosanitario.dao.DepartamentoDAO;
import com.mycompany.proyectointegradorfitosanitario.modelo.Departamento;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmGestionarDepartamentos extends JFrame {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private DepartamentoDAO dao;
    
    public FrmGestionarDepartamentos() {
        dao = new DepartamentoDAO();
        inicializarComponentes();
        cargarDatos();
    }
    
    private void inicializarComponentes() {
        setTitle("Gestionar Departamentos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(240, 240, 240));
        
        JLabel lblTitulo = new JLabel("GESTIÓN DE DEPARTAMENTOS");
        lblTitulo.setBounds(20, 15, 560, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(0, 102, 204));
        panel.add(lblTitulo);
        
        String[] columnas = {"ID", "Código DANE", "Nombre"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Arial", Font.PLAIN, 11));
        tabla.setRowHeight(25);
        tabla.getTableHeader().setBackground(new Color(0, 102, 204));
        tabla.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 60, 560, 250);
        panel.add(scroll);
        
        JButton btnAgregar = new JButton("➕ Agregar");
        btnAgregar.setBounds(20, 320, 130, 35);
        btnAgregar.setBackground(new Color(76, 175, 80));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.addActionListener(e -> agregar());
        panel.add(btnAgregar);
        
        JButton btnActualizar = new JButton("🔄 Actualizar");
        btnActualizar.setBounds(160, 320, 130, 35);
        btnActualizar.setBackground(new Color(255, 193, 7));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.addActionListener(e -> cargarDatos());
        panel.add(btnActualizar);
        
        JButton btnVolver = new JButton("⬅️ Volver");
        btnVolver.setBounds(450, 320, 130, 35);
        btnVolver.setBackground(new Color(128, 128, 128));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.addActionListener(e -> this.dispose());
        panel.add(btnVolver);
        
        setContentPane(panel);
    }
    
    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Departamento> deptos = dao.listarTodos();
        for (Departamento d : deptos) {
            modeloTabla.addRow(new Object[]{d.getId(), d.getCodigoDane(), d.getNombre()});
        }
    }
    
    private void agregar() {
        JTextField txtCodigo = new JTextField(5);
        JTextField txtNombre = new JTextField(10);
        Object[] msg = {"Código DANE:", txtCodigo, "Nombre:", txtNombre};
        
        if (JOptionPane.showConfirmDialog(this, msg, "Agregar Departamento", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            if (txtCodigo.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Departamento depto = new Departamento(txtCodigo.getText().trim(), txtNombre.getText().trim());
            if (dao.insertar(depto)) {
                JOptionPane.showMessageDialog(this, "✅ Agregado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
            }
        }
    }
}

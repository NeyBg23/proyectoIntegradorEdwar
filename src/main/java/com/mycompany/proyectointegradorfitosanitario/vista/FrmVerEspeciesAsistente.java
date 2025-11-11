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

public class FrmVerEspeciesAsistente extends JFrame {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private EspecieDAO dao;
    
    public FrmVerEspeciesAsistente() {
        dao = new EspecieDAO();
        inicializarComponentes();
        cargarDatos();
    }
    
    private void inicializarComponentes() {
        setTitle("Especies Vegetales - Asistente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 350);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(240, 240, 240));
        
        JLabel lblTitulo = new JLabel("ESPECIES VEGETALES A INSPECCIONAR");
        lblTitulo.setBounds(20, 15, 560, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitulo.setForeground(new Color(139, 69, 19));
        panel.add(lblTitulo);
        
        String[] columnas = {"Código", "Científico", "Común", "Densidad"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Arial", Font.PLAIN, 11));
        tabla.setRowHeight(22);
        tabla.getTableHeader().setBackground(new Color(139, 69, 19));
        tabla.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 60, 560, 230);
        panel.add(scroll);
        
        JButton btnVolver = new JButton("⬅️ Volver");
        btnVolver.setBounds(450, 300, 130, 30);
        btnVolver.setBackground(new Color(128, 128, 128));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.addActionListener(e -> this.dispose());
        panel.add(btnVolver);
        
        setContentPane(panel);
    }
    
    private void cargarDatos() {
        List<Especie> especies = dao.listarTodos();
        for (Especie e : especies) {
            modeloTabla.addRow(new Object[]{e.getCodigo(), e.getNombreCientifico(), e.getNombreComun(), e.getDensidadSiembra()});
        }
    }
}

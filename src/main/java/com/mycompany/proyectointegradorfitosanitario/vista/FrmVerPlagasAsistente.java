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

public class FrmVerPlagasAsistente extends JFrame {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private PlagaDAO dao;
    
    public FrmVerPlagasAsistente() {
        dao = new PlagaDAO();
        inicializarComponentes();
        cargarDatos();
    }
    
    private void inicializarComponentes() {
        setTitle("Plagas Fitosanitarias - Asistente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 350);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(240, 240, 240));
        
        JLabel lblTitulo = new JLabel("PLAGAS A DETECTAR EN INSPECCIÓN");
        lblTitulo.setBounds(20, 15, 560, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
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
        List<Plaga> plagas = dao.listarTodos();
        for (Plaga p : plagas) {
            modeloTabla.addRow(new Object[]{p.getCodigo(), p.getNombreCientifico(), p.getNombreComun(), p.getNivelAlerta()});
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.vista;

import com.mycompany.proyectointegradorfitosanitario.dao.PredioDAO;
import com.mycompany.proyectointegradorfitosanitario.dao.VeredaDAO;
import com.mycompany.proyectointegradorfitosanitario.dao.MunicipioDAO;
import com.mycompany.proyectointegradorfitosanitario.modelo.Predio;
import com.mycompany.proyectointegradorfitosanitario.modelo.Usuario;
import com.mycompany.proyectointegradorfitosanitario.modelo.Vereda;
import com.mycompany.proyectointegradorfitosanitario.modelo.Municipio;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FrmVerMisPredios extends JFrame {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private PredioDAO predioDAO;
    private VeredaDAO veredaDAO;
    private MunicipioDAO municipioDAO;
    private Usuario propietario;
    
    public FrmVerMisPredios(Usuario usuario) {
        this.propietario = usuario;
        this.predioDAO = new PredioDAO();
        this.veredaDAO = new VeredaDAO();
        this.municipioDAO = new MunicipioDAO();
        inicializarComponentes();
        cargarDatos();
    }
    
    private void inicializarComponentes() {
        setTitle("Mis Predios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 420);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(240, 240, 240));
        
        JLabel lblTitulo = new JLabel("MIS PREDIOS REGISTRADOS");
        lblTitulo.setBounds(20, 15, 860, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(0, 128, 96));
        panel.add(lblTitulo);
        
        String[] columnas = {"Nombre", "Registro ICA", "Área (ha)", "Vereda", "Municipio"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Arial", Font.PLAIN, 11));
        tabla.setRowHeight(25);
        tabla.getTableHeader().setBackground(new Color(0, 128, 96));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 60, 860, 300);
        panel.add(scroll);
        
        JButton btnVolver = new JButton("⬅️ Volver");
        btnVolver.setBounds(750, 370, 130, 30);
        btnVolver.setBackground(new Color(128, 128, 128));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("Arial", Font.BOLD, 12));
        btnVolver.addActionListener(e -> this.dispose());
        panel.add(btnVolver);
        
        setContentPane(panel);
    }
    
    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Predio> predios = predioDAO.listarPorPropietario(propietario.getId());
        
        for (Predio p : predios) {
            // Obtener vereda
            Vereda vereda = veredaDAO.buscarPorId(p.getVeredaId());
            String nombreVereda = vereda != null ? vereda.getNombre() : "N/A";
            
            // Obtener municipio
            String nombreMunicipio = "N/A";
            if (vereda != null) {
                Municipio municipio = municipioDAO.buscarPorId(vereda.getMunicipioId());
                nombreMunicipio = municipio != null ? municipio.getNombre() : "N/A";
            }
            
            // Agregar fila
            modeloTabla.addRow(new Object[]{
                p.getNombre(),
                p.getNumeroRegistro(),
                p.getAreaHectareas(),
                nombreVereda,
                nombreMunicipio
            });
        }
        
        if (predios.isEmpty()) {
            modeloTabla.addRow(new Object[]{"No tienes predios registrados", "-", "-", "-", "-"});
        }
    }
}

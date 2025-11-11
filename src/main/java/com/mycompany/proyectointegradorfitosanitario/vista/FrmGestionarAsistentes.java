/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.vista;

import com.mycompany.proyectointegradorfitosanitario.dao.AsistenteTecnicoDAO;
import com.mycompany.proyectointegradorfitosanitario.modelo.AsistenteTecnico;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Pantalla para Gestionar Asistentes Técnicos (CRUD)
 * @author Equipo Proyecto Integrador
 * @version 1.0
 */
public class FrmGestionarAsistentes extends JFrame {
    
    private JPanel panelPrincipal;
    private JTable tabla;
    private JScrollPane scrollPane;
    private JButton btnAgregar, btnEditar, btnEliminar, btnActualizar, btnVolver;
    private JLabel lblMensaje;
    
    private AsistenteTecnicoDAO dao;
    private DefaultTableModel modeloTabla;
    
    public FrmGestionarAsistentes() {
        dao = new AsistenteTecnicoDAO();
        inicializarComponentes();
        cargarDatos();
    }
    
    private void inicializarComponentes() {
        setTitle("Gestionar Asistentes Técnicos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        
        panelPrincipal = new JPanel(null);
        panelPrincipal.setBackground(new Color(240, 240, 240));
        
        JLabel lblTitulo = new JLabel("GESTIÓN DE ASISTENTES TÉCNICOS");
        lblTitulo.setBounds(20, 15, 960, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 102, 204)); // Azul
        panelPrincipal.add(lblTitulo);
        
        String[] columnas = {"ID", "Identificación", "Nombre", "Teléfono", "Correo", "Tarjeta Prof.", "Tipo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Arial", Font.PLAIN, 11));
        tabla.setRowHeight(25);
        tabla.getTableHeader().setBackground(new Color(0, 102, 204));
        tabla.getTableHeader().setForeground(Color.WHITE);
        
        scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(20, 60, 960, 350);
        panelPrincipal.add(scrollPane);
        
        lblMensaje = new JLabel("");
        lblMensaje.setBounds(20, 420, 960, 20);
        lblMensaje.setFont(new Font("Arial", Font.PLAIN, 12));
        lblMensaje.setForeground(new Color(0, 102, 204));
        panelPrincipal.add(lblMensaje);
        
        btnAgregar = crearBoton("➕ Agregar", 20, 450, 140, 35, new Color(76, 175, 80));
        btnAgregar.addActionListener(e -> agregar());
        panelPrincipal.add(btnAgregar);
        
        btnEditar = crearBoton("✏️ Editar", 170, 450, 140, 35, new Color(33, 150, 243));
        btnEditar.addActionListener(e -> editar());
        panelPrincipal.add(btnEditar);
        
        btnEliminar = crearBoton("🗑️ Eliminar", 320, 450, 140, 35, new Color(244, 67, 54));
        btnEliminar.addActionListener(e -> eliminar());
        panelPrincipal.add(btnEliminar);
        
        btnActualizar = crearBoton("🔄 Actualizar", 470, 450, 140, 35, new Color(255, 193, 7));
        btnActualizar.addActionListener(e -> cargarDatos());
        panelPrincipal.add(btnActualizar);
        
        btnVolver = crearBoton("⬅️ Volver", 840, 450, 140, 35, new Color(128, 128, 128));
        btnVolver.addActionListener(e -> this.dispose());
        panelPrincipal.add(btnVolver);
        
        setContentPane(panelPrincipal);
    }
    
    private JButton crearBoton(String texto, int x, int y, int ancho, int alto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, ancho, alto);
        boton.setFont(new Font("Arial", Font.BOLD, 11));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }
    
    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<AsistenteTecnico> asistentes = dao.listarTodos();
        for (AsistenteTecnico a : asistentes) {
            Object[] fila = {
                a.getId().toString().substring(0, 8) + "...",
                a.getIdentificacion(),
                a.getNombre(),
                a.getTelefono(),
                a.getCorreo(),
                a.getTarjetaProfesional(),
                a.getTipo()
            };
            modeloTabla.addRow(fila);
        }
        lblMensaje.setText("Total asistentes: " + asistentes.size());
    }
    
    private void agregar() {
        FrmCrearAsistenteConUsuario frameCriar = new FrmCrearAsistenteConUsuario(this);
        frameCriar.setVisible(true);

        // Cuando se cierre, recarga los datos
        frameCriar.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent e) {
                cargarDatos();
            }
        });
    }

    
    private void editar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un asistente", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String identificacion = (String) tabla.getValueAt(fila, 1);
        AsistenteTecnico asistente = dao.buscarPorIdentificacion(identificacion);
        if (asistente == null) return;
        
        JTextField txtNombre = new JTextField(asistente.getNombre(), 15);
        JTextField txtTelefono = new JTextField(asistente.getTelefono(), 15);
        JTextField txtCorreo = new JTextField(asistente.getCorreo(), 15);
        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"OFICIAL", "PARTICULAR"});
        comboTipo.setSelectedItem(asistente.getTipo());
        
        Object[] mensaje = {
            "Nombre:", txtNombre,
            "Teléfono:", txtTelefono,
            "Correo:", txtCorreo,
            "Tipo:", comboTipo
        };
        
        if (JOptionPane.showConfirmDialog(this, mensaje, "Editar Asistente", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            asistente.setNombre(txtNombre.getText().trim());
            asistente.setTelefono(txtTelefono.getText().trim());
            asistente.setCorreo(txtCorreo.getText().trim());
            asistente.setTipo((String) comboTipo.getSelectedItem());
            
            if (dao.actualizar(asistente)) {
                JOptionPane.showMessageDialog(this, "✅ Actualizado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
            }
        }
    }
    
    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un asistente", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String identificacion = (String) tabla.getValueAt(fila, 1);
        AsistenteTecnico asistente = dao.buscarPorIdentificacion(identificacion);
        if (asistente == null) return;
        
        if (JOptionPane.showConfirmDialog(this, "¿Eliminar a: " + asistente.getNombre() + "?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (dao.eliminar(asistente.getId())) {
                JOptionPane.showMessageDialog(this, "✅ Eliminado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
            }
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.vista;

import com.mycompany.proyectointegradorfitosanitario.dao.PropietarioDAO;
import com.mycompany.proyectointegradorfitosanitario.modelo.Propietario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.UUID;

/**
 * Pantalla para Gestionar Propietarios (CRUD)
 * @author Equipo Proyecto Integrador
 * @version 1.0
 */
public class FrmGestionarPropietarios extends JFrame {
    
    private JPanel panelPrincipal;
    private JTable tablaPropietarios;
    private JScrollPane scrollPane;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    private JButton btnVolver;
    private JLabel lblMensaje;
    
    private PropietarioDAO dao;
    private DefaultTableModel modeloTabla;
    
    /**
     * Constructor
     */
    public FrmGestionarPropietarios() {
        dao = new PropietarioDAO();
        inicializarComponentes();
        cargarDatos();
    }
    
    /**
     * Inicializa los componentes de la interfaz
     */
    private void inicializarComponentes() {
        setTitle("Gestionar Propietarios");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);
        panelPrincipal.setBackground(new Color(240, 240, 240));
        
        // ===== TÍTULO =====
        JLabel lblTitulo = new JLabel("GESTIÓN DE PROPIETARIOS");
        lblTitulo.setBounds(20, 15, 860, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 128, 96)); // Verde
        panelPrincipal.add(lblTitulo);
        
        // ===== TABLA DE PROPIETARIOS =====
        String[] columnas = {"ID", "Identificación", "Nombre", "Teléfono", "Correo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // La tabla no es editable directamente
            }
        };
        
        tablaPropietarios = new JTable(modeloTabla);
        tablaPropietarios.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaPropietarios.setRowHeight(25);
        tablaPropietarios.getTableHeader().setBackground(new Color(0, 128, 96));
        tablaPropietarios.getTableHeader().setForeground(Color.WHITE);
        tablaPropietarios.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        scrollPane = new JScrollPane(tablaPropietarios);
        scrollPane.setBounds(20, 60, 860, 350);
        panelPrincipal.add(scrollPane);
        
        // ===== LABEL DE MENSAJE =====
        lblMensaje = new JLabel("");
        lblMensaje.setBounds(20, 420, 860, 20);
        lblMensaje.setFont(new Font("Arial", Font.PLAIN, 12));
        lblMensaje.setForeground(new Color(0, 128, 96));
        panelPrincipal.add(lblMensaje);
        
        // ===== BOTONES =====
        btnAgregar = crearBoton("➕ Agregar", 20, 450, 140, 35, new Color(76, 175, 80));
        btnAgregar.addActionListener(e -> agregarPropietario());
        panelPrincipal.add(btnAgregar);
        
        btnEditar = crearBoton("✏️ Editar", 170, 450, 140, 35, new Color(33, 150, 243));
        btnEditar.addActionListener(e -> editarPropietario());
        panelPrincipal.add(btnEditar);
        
        btnEliminar = crearBoton("🗑️ Eliminar", 320, 450, 140, 35, new Color(244, 67, 54));
        btnEliminar.addActionListener(e -> eliminarPropietario());
        panelPrincipal.add(btnEliminar);
        
        btnActualizar = crearBoton("🔄 Actualizar", 470, 450, 140, 35, new Color(255, 193, 7));
        btnActualizar.addActionListener(e -> cargarDatos());
        panelPrincipal.add(btnActualizar);
        
        btnVolver = crearBoton("⬅️ Volver", 740, 450, 140, 35, new Color(128, 128, 128));
        btnVolver.addActionListener(e -> this.dispose());
        panelPrincipal.add(btnVolver);
        
        setContentPane(panelPrincipal);
    }
    
    /**
     * Método auxiliar para crear botones con estilo
     */
    private JButton crearBoton(String texto, int x, int y, int ancho, int alto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, ancho, alto);
        boton.setFont(new Font("Arial", Font.BOLD, 11));
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        return boton;
    }
    
    /**
     * Carga los datos de la tabla
     */
    private void cargarDatos() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        List<Propietario> propietarios = dao.listarTodos();
        
        for (Propietario p : propietarios) {
            Object[] fila = {
                p.getId().toString().substring(0, 8) + "...", // ID corto
                p.getIdentificacion(),
                p.getNombre(),
                p.getTelefono(),
                p.getCorreo()
            };
            modeloTabla.addRow(fila);
        }
        
        lblMensaje.setText("Total propietarios: " + propietarios.size());
    }
    
    /**
     * Agregar un nuevo propietario (abre la pantalla de crear propietario + usuario)
     */
    private void agregarPropietario() {
        FrmCrearPropietarioConUsuario frameCrear = new FrmCrearPropietarioConUsuario(this);
        frameCrear.setVisible(true);

        frameCrear.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent e) {
                cargarDatos();
            }
        });
    }

    /**
     * Editar propietario seleccionado
     */
    private void editarPropietario() {
        int fila = tablaPropietarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un propietario",
                "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener datos de la tabla
        String idCorto = (String) tablaPropietarios.getValueAt(fila, 0);
        String identificacion = (String) tablaPropietarios.getValueAt(fila, 1);
        String nombre = (String) tablaPropietarios.getValueAt(fila, 2);
        String telefono = (String) tablaPropietarios.getValueAt(fila, 3);
        String correo = (String) tablaPropietarios.getValueAt(fila, 4);

        // Buscar el propietario completo (para obtener el ID completo)
        Propietario propietario = dao.buscarPorIdentificacion(identificacion);

        if (propietario == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el propietario",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear formulario de edición
        JTextField txtNombre = new JTextField(nombre, 15);
        JTextField txtTelefono = new JTextField(telefono, 15);
        JTextField txtCorreo = new JTextField(correo, 15);

        Object[] mensaje = {
            "Identificación:", identificacion,
            "Nombre:", txtNombre,
            "Teléfono:", txtTelefono,
            "Correo:", txtCorreo
        };

        int resultado = JOptionPane.showConfirmDialog(this, mensaje,
            "Editar Propietario", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            String nuevoNombre = txtNombre.getText().trim();
            String nuevoTelefono = txtTelefono.getText().trim();
            String nuevoCorreo = txtCorreo.getText().trim();

            if (nuevoNombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre es obligatorio",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Actualizar el propietario
            propietario.setNombre(nuevoNombre);
            propietario.setTelefono(nuevoTelefono);
            propietario.setCorreo(nuevoCorreo);

            if (dao.actualizar(propietario)) {
                JOptionPane.showMessageDialog(this, "✅ Propietario actualizado correctamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error al actualizar propietario",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    
    /**
     * Eliminar propietario seleccionado
     */
    private void eliminarPropietario() {
        int fila = tablaPropietarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un propietario",
                "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener datos de la tabla
        String identificacion = (String) tablaPropietarios.getValueAt(fila, 1);
        String nombre = (String) tablaPropietarios.getValueAt(fila, 2);

        // Buscar el propietario completo
        Propietario propietario = dao.buscarPorIdentificacion(identificacion);

        if (propietario == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el propietario",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmar eliminación
        int confirmar = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de eliminar a: " + nombre + "?",
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            if (dao.eliminar(propietario.getId())) {
                JOptionPane.showMessageDialog(this, "✅ Propietario eliminado correctamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "❌ No se pudo eliminar el propietario",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    
    /**
     * Main para probar
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrmGestionarPropietarios frame = new FrmGestionarPropietarios();
            frame.setVisible(true);
        });
    }
}

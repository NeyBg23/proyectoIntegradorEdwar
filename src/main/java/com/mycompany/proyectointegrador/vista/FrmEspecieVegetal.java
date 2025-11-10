package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.modelo.EspecieVegetal;
import com.mycompany.proyectointegrador.dao.EspecieVegetalDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class FrmEspecieVegetal extends JFrame {

    private JTextField txtId, txtCodigo, txtNombre, txtDensidad;
    private JTextArea txtDescripcion;
    private JButton btnGuardar, btnEditar, btnEliminar, btnLimpiar, btnVolver;
    private JTable tablaEspecies;
    private DefaultTableModel modeloTabla;
    private EspecieVegetalDAO especieDAO = new EspecieVegetalDAO();
    private int especieSeleccionada = -1;

    public FrmEspecieVegetal() {
        initComponents();
        cargarEspecies();
    }

    private void initComponents() {
        setTitle("Gestión de Especies Vegetales");
        setSize(900, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Título
        JLabel lblTitulo = new JLabel("Registro de Especie Vegetal", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(41, 128, 185));
        add(lblTitulo, BorderLayout.NORTH);

        // Formulario (lado izquierdo)
        JPanel panelIzquierdo = new JPanel(new GridLayout(6, 2, 10, 10));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        panelIzquierdo.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false); // BD autogenera
        panelIzquierdo.add(txtId);

        panelIzquierdo.add(new JLabel("Código de Registro:"));
        txtCodigo = new JTextField();
        panelIzquierdo.add(txtCodigo);

        panelIzquierdo.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelIzquierdo.add(txtNombre);

        panelIzquierdo.add(new JLabel("Densidad (kg/m³):"));
        txtDensidad = new JTextField();
        panelIzquierdo.add(txtDensidad);

        panelIzquierdo.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextArea(3, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        panelIzquierdo.add(new JScrollPane(txtDescripcion));

        add(panelIzquierdo, BorderLayout.WEST);

        // Tabla
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Código", "Nombre", "Densidad", "Descripción"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaEspecies = new JTable(modeloTabla);
        tablaEspecies.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaEspecies.setRowHeight(25);
        add(new JScrollPane(tablaEspecies), BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnGuardar = new JButton("Guardar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnVolver = new JButton("Volver");

        configurarBoton(btnGuardar);
        configurarBoton(btnEditar);
        configurarBoton(btnEliminar);
        configurarBoton(btnLimpiar);
        configurarBoton(btnVolver);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH);

        // Acciones
        btnGuardar.addActionListener(e -> guardarEspecie());
        btnEditar.addActionListener(e -> editarEspecie());
        btnEliminar.addActionListener(e -> eliminarEspecie());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnVolver.addActionListener(e -> dispose());
        tablaEspecies.getSelectionModel().addListSelectionListener(e -> cargarSeleccion());
    }

    private void configurarBoton(JButton boton) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(new Color(41, 128, 185));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { boton.setBackground(new Color(31, 97, 141)); }
            public void mouseExited(java.awt.event.MouseEvent evt) { boton.setBackground(new Color(41, 128, 185)); }
        });
    }

    // =================== CRUD EN BASE DE DATOS ===================

    private void cargarEspecies() {
        modeloTabla.setRowCount(0);
        try {
            List<EspecieVegetal> lista = especieDAO.listar();
            for (EspecieVegetal e : lista) {
                modeloTabla.addRow(new Object[]{
                    e.getId(), e.getCodigo(), e.getNombre(), e.getDensidad(), e.getDescripcion()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando especies: " + e.getMessage());
        }
    }

    private void guardarEspecie() {
        try {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            float densidad = Float.parseFloat(txtDensidad.getText().trim());
            String descripcion = txtDescripcion.getText().trim();

            if (codigo.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Código y nombre son obligatorios.");
                return;
            }

            EspecieVegetal especie = new EspecieVegetal(0, codigo, nombre, densidad, descripcion);
            especieDAO.guardar(especie);
            cargarEspecies();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "✅ Especie guardada con éxito.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Verifica los campos numéricos y que no estén vacíos.");
        }
    }

    private void editarEspecie() {
        int fila = tablaEspecies.getSelectedRow();
        if (fila >= 0) {
            try {
                int id = Integer.parseInt(txtId.getText());
                String codigo = txtCodigo.getText();
                String nombre = txtNombre.getText();
                float densidad = Float.parseFloat(txtDensidad.getText());
                String descripcion = txtDescripcion.getText();

                EspecieVegetal especie = new EspecieVegetal(id, codigo, nombre, densidad, descripcion);
                especieDAO.actualizar(especie);
                cargarEspecies();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "✏️ Registro actualizado correctamente.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "❌ Error al editar. Verifica los datos.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para editar.");
        }
    }

    private void eliminarEspecie() {
        int fila = tablaEspecies.getSelectedRow();
        if (fila >= 0) {
            try {
                int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
                especieDAO.eliminar(id);
                cargarEspecies();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "🗑️ Registro eliminado correctamente.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "❌ Error al eliminar especie.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.");
        }
    }

    private void cargarSeleccion() {
        int fila = tablaEspecies.getSelectedRow();
        if (fila >= 0) {
            txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtCodigo.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtNombre.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtDensidad.setText(modeloTabla.getValueAt(fila, 3).toString());
            txtDescripcion.setText(modeloTabla.getValueAt(fila, 4).toString());
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtCodigo.setText("");
        txtNombre.setText("");
        txtDensidad.setText("");
        txtDescripcion.setText("");
        tablaEspecies.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmEspecieVegetal().setVisible(true));
    }
}

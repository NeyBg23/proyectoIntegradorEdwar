package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.modelo.Plaga;
import com.mycompany.proyectointegrador.dao.PlagaDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class FrmPlagas extends JFrame {

    private JTextField txtCodigo, txtNombre, txtDescripcion, txtNivelIncidencia, txtNivelAlerta;
    private JTable tablaPlagas;
    private DefaultTableModel modeloTabla;
    private JButton btnGuardar, btnEditar, btnEliminar, btnLimpiar, btnVolver;
    private PlagaDAO plagaDAO = new PlagaDAO();

    public FrmPlagas() {
        configurarVentana();
        crearComponentes();
        cargarPlagasDesdeBD();
    }

    private void configurarVentana() {
        setTitle("ADMINISTRACIÓN DE PLAGAS");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
    }

    private void crearComponentes() {
        // Panel título
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(41, 128, 185));

        JLabel lblTitulo = new JLabel("ADMINISTRACIÓN DE PLAGAS", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel lblSubtitulo = new JLabel("Gestión completa de información de plagas", SwingConstants.CENTER);
        lblSubtitulo.setForeground(Color.WHITE);

        panelTitulo.add(lblTitulo, BorderLayout.CENTER);
        panelTitulo.add(lblSubtitulo, BorderLayout.SOUTH);
        add(panelTitulo, BorderLayout.NORTH);

        // Panel principal dividido
        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 10, 10));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Panel Izquierdo: Datos de la Plaga ---
        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos de la Plaga"));
        panelDatos.setBackground(new Color(236, 240, 241));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblCodigo = new JLabel("Código:");
        JLabel lblNombre = new JLabel("Nombre:");
        JLabel lblDescripcion = new JLabel("Descripción:");
        JLabel lblNivelIncidencia = new JLabel("Nivel de Incidencia:");
        JLabel lblNivelAlerta = new JLabel("Nivel de Alerta:");

        txtCodigo = new JTextField(20);
        txtNombre = new JTextField(20);
        txtDescripcion = new JTextField(20);
        txtNivelIncidencia = new JTextField(20);
        txtNivelAlerta = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0; panelDatos.add(lblCodigo, gbc);
        gbc.gridx = 1; panelDatos.add(txtCodigo, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panelDatos.add(lblNombre, gbc);
        gbc.gridx = 1; panelDatos.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panelDatos.add(lblDescripcion, gbc);
        gbc.gridx = 1; panelDatos.add(txtDescripcion, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panelDatos.add(lblNivelIncidencia, gbc);
        gbc.gridx = 1; panelDatos.add(txtNivelIncidencia, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panelDatos.add(lblNivelAlerta, gbc);
        gbc.gridx = 1; panelDatos.add(txtNivelAlerta, gbc);

        // --- Panel Derecho: Tabla de Plagas ---
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createTitledBorder("Lista de Plagas Registradas"));

        modeloTabla = new DefaultTableModel(new String[]{"Código", "Nombre", "Descripción", "Incidencia", "Alerta"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaPlagas = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaPlagas);
        panelTabla.add(scrollTabla, BorderLayout.CENTER);

        panelCentral.add(panelDatos);
        panelCentral.add(panelTabla);
        add(panelCentral, BorderLayout.CENTER);

        // --- Panel Inferior: Botones ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnGuardar = new JButton("Guardar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnVolver = new JButton("Volver");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH);

        // --- Eventos CRUD ---
        btnGuardar.addActionListener(e -> guardarPlaga());
        btnEditar.addActionListener(e -> editarPlaga());
        btnEliminar.addActionListener(e -> eliminarPlaga());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnVolver.addActionListener(e -> dispose());

        // Cargar datos al hacer clic en la tabla
        tablaPlagas.getSelectionModel().addListSelectionListener(e -> cargarSeleccion());
    }

    private void cargarPlagasDesdeBD() {
        modeloTabla.setRowCount(0);
        try {
            List<Plaga> lista = plagaDAO.listar();
            for (Plaga p : lista) {
                modeloTabla.addRow(new Object[]{
                    p.getCodigo(), p.getNombre(), p.getDescripcion(),
                    p.getNivelIncidencia(), p.getNivelAlerta()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando plagas: " + e.getMessage());
        }
    }

    private void guardarPlaga() {
        try {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            String descripcion = txtDescripcion.getText().trim();
            float nivelIncidencia = Float.parseFloat(txtNivelIncidencia.getText().trim());
            String nivelAlerta = txtNivelAlerta.getText().trim();

            if (codigo.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Código y nombre son obligatorios.");
                return;
            }

            Plaga plaga = new Plaga(codigo, nombre, descripcion, nivelIncidencia, nivelAlerta);
            plagaDAO.guardar(plaga);
            cargarPlagasDesdeBD();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "✅ Plaga registrada correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Error al registrar la plaga: " + ex.getMessage());
        }
    }

    private void editarPlaga() {
        int fila = tablaPlagas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una plaga para editar.");
            return;
        }
        try {
            String codigo = txtCodigo.getText();
            String nombre = txtNombre.getText();
            String descripcion = txtDescripcion.getText();
            float nivelIncidencia = Float.parseFloat(txtNivelIncidencia.getText());
            String nivelAlerta = txtNivelAlerta.getText();

            Plaga plagaEditada = new Plaga(codigo, nombre, descripcion, nivelIncidencia, nivelAlerta);
            plagaDAO.actualizar(plagaEditada);

            cargarPlagasDesdeBD();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "✏️ Plaga actualizada correctamente.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al editar: " + ex.getMessage());
        }
    }

    private void eliminarPlaga() {
        int fila = tablaPlagas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una plaga para eliminar.");
            return;
        }
        try {
            String codigo = modeloTabla.getValueAt(fila, 0).toString();
            plagaDAO.eliminar(codigo);

            cargarPlagasDesdeBD();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "🗑️ Plaga eliminada correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar la plaga: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtNivelIncidencia.setText("");
        txtNivelAlerta.setText("");
        tablaPlagas.clearSelection();
    }

    private void cargarSeleccion() {
        int fila = tablaPlagas.getSelectedRow();
        if (fila != -1) {
            txtCodigo.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtDescripcion.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtNivelIncidencia.setText(modeloTabla.getValueAt(fila, 3).toString());
            txtNivelAlerta.setText(modeloTabla.getValueAt(fila, 4).toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmPlagas().setVisible(true));
    }
}

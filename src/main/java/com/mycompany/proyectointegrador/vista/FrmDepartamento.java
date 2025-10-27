/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.proyectointegrador.vista;

/**
 *
 * @author edward
 */


import com.mycompany.proyectointegrador.modelo.Departamento;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class FrmDepartamento extends JFrame {

    private JPanel contentPane;
    private JTextField txtId, txtCodigoDane, txtNombre;
    private JButton btnGuardar, btnEditar, btnEliminar, btnLimpiar, btnSalir;
    private JTable tblDepartamentos;
    private DefaultTableModel modeloTabla;
    private List<Departamento> listaDepartamentos;
    private int departamentoSeleccionadoIndex = -1;

    public FrmDepartamento() {
        initComponents();
        inicializarFormulario();
    }

    private void inicializarFormulario() {
        listaDepartamentos = new ArrayList<>();
        configurarVentana();
        crearComponentes();
        agregarEventos();
        limpiarFormulario();
    }

    private void configurarVentana() {
        setTitle("Gestión de Departamentos");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void crearComponentes() {
        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        // Paneles
        contentPane.add(crearPanelTitulo(), BorderLayout.NORTH);
        contentPane.add(crearPanelFormulario(), BorderLayout.WEST);
        contentPane.add(crearPanelTabla(), BorderLayout.CENTER);
        contentPane.add(crearPanelBotones(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(41, 128, 185));
        panel.setPreferredSize(new Dimension(0, 70));

        JLabel lblTitulo = new JLabel("ADMINISTRACIÓN DE DEPARTAMENTOS", JLabel.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel lblSubtitulo = new JLabel("Gestión completa de los departamentos registrados", JLabel.CENTER);
        lblSubtitulo.setForeground(Color.LIGHT_GRAY);

        JPanel panelTexto = new JPanel(new GridLayout(2, 1));
        panelTexto.setBackground(new Color(41, 128, 185));
        panelTexto.add(lblTitulo);
        panelTexto.add(lblSubtitulo);

        panel.add(panelTexto, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Departamento"));
        panel.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        panel.add(new JLabel("ID:"), crearGbc(gbc, 0, y));
        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setBackground(Color.LIGHT_GRAY);
        panel.add(txtId, crearGbc(gbc, 1, y++));

        panel.add(new JLabel("Código DANE:"), crearGbc(gbc, 0, y));
        txtCodigoDane = new JTextField();
        panel.add(txtCodigoDane, crearGbc(gbc, 1, y++));

        panel.add(new JLabel("Nombre:"), crearGbc(gbc, 0, y));
        txtNombre = new JTextField();
        panel.add(txtNombre, crearGbc(gbc, 1, y++));

        return panel;
    }

    private GridBagConstraints crearGbc(GridBagConstraints gbc, int x, int y) {
        GridBagConstraints c = (GridBagConstraints) gbc.clone();
        c.gridx = x;
        c.gridy = y;
        return c;
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Departamentos"));

        String[] columnas = {"ID", "Código DANE", "Nombre"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        tblDepartamentos = new JTable(modeloTabla);
        tblDepartamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(tblDepartamentos), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnGuardar = new JButton("Guardar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnSalir = new JButton("Salir");

        panel.add(btnGuardar);
        panel.add(btnEditar);
        panel.add(btnEliminar);
        panel.add(btnLimpiar);
        panel.add(btnSalir);
        return panel;
    }

    private void agregarEventos() {
        btnGuardar.addActionListener(e -> guardarDepartamento());
        btnEditar.addActionListener(e -> editarDepartamento());
        btnEliminar.addActionListener(e -> eliminarDepartamento());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnSalir.addActionListener(e -> dispose());

        tblDepartamentos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblDepartamentos.getSelectedRow() != -1) {
                seleccionarDepartamento();
            }
        });
    }

    private void guardarDepartamento() {
        if (!validarCampos()) return;

        if (departamentoSeleccionadoIndex == -1) {
            String id = generarId();
            Departamento d = new Departamento(
                Integer.parseInt(id.replace("DEP", "")),
                txtCodigoDane.getText().trim(),
                txtNombre.getText().trim()
            );
            listaDepartamentos.add(d);
            modeloTabla.addRow(new Object[]{d.getId(), d.getCodigoDane(), d.getNombre()});
            JOptionPane.showMessageDialog(this, "Departamento guardado exitosamente.");
        } else {
            Departamento d = listaDepartamentos.get(departamentoSeleccionadoIndex);
            d.setCodigoDane(txtCodigoDane.getText());
            d.setNombre(txtNombre.getText());
            actualizarFilaTabla(departamentoSeleccionadoIndex, d);
            JOptionPane.showMessageDialog(this, "Departamento actualizado.");
        }

        limpiarFormulario();
    }

    private void editarDepartamento() {
        if (departamentoSeleccionadoIndex != -1) {
            Departamento d = listaDepartamentos.get(departamentoSeleccionadoIndex);
            cargarFormulario(d);
        }
    }

    private void eliminarDepartamento() {
        if (departamentoSeleccionadoIndex != -1) {
            listaDepartamentos.remove(departamentoSeleccionadoIndex);
            modeloTabla.removeRow(departamentoSeleccionadoIndex);
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Departamento eliminado.");
        }
    }

    private void seleccionarDepartamento() {
        departamentoSeleccionadoIndex = tblDepartamentos.getSelectedRow();
        Departamento d = listaDepartamentos.get(departamentoSeleccionadoIndex);
        cargarFormulario(d);
    }

    private void cargarFormulario(Departamento d) {
        txtId.setText(String.valueOf(d.getId()));
        txtCodigoDane.setText(d.getCodigoDane());
        txtNombre.setText(d.getNombre());
    }

    private void actualizarFilaTabla(int fila, Departamento d) {
        modeloTabla.setValueAt(d.getId(), fila, 0);
        modeloTabla.setValueAt(d.getCodigoDane(), fila, 1);
        modeloTabla.setValueAt(d.getNombre(), fila, 2);
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtCodigoDane.setText("");
        txtNombre.setText("");
        departamentoSeleccionadoIndex = -1;
        tblDepartamentos.clearSelection();
    }

    private boolean validarCampos() {
        if (txtCodigoDane.getText().isEmpty() || txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private String generarId() {
        return "DEP" + (listaDepartamentos.size() + 1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmDepartamento().setVisible(true));
    }

    private void initComponents() {}
}



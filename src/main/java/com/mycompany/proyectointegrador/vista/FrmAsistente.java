package com.mycompany.proyectointegrador.vista;


import com.mycompany.proyectointegrador.modelo.Asistente;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class FrmAsistente extends JFrame {

    private JPanel contentPane;
    private JTextField txtId, txtNombre, txtIdentificacion, txtTelefono, txtCorreo, txtTarjetaProfesional;
    private JButton btnGuardar, btnEditar, btnEliminar, btnLimpiar, btnSalir;
    private JTable tblAsistentes;
    private DefaultTableModel modeloTabla;
    private List<Asistente> listaAsistentes;
    private int asistenteSeleccionadoIndex = -1;

    public FrmAsistente() {
        initComponents();
        inicializarFormulario();
    }

    private void inicializarFormulario() {
        listaAsistentes = new ArrayList<>();
        configurarVentana();
        crearComponentes();
        agregarEventos();
        limpiarFormulario();
    }

    private void configurarVentana() {
        setTitle("Gestión de Asistentes");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        panel.setBackground(new Color(52, 73, 94));
        panel.setPreferredSize(new Dimension(0, 70));

        JLabel lblTitulo = new JLabel("ADMINISTRACIÓN DE ASISTENTES", JLabel.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel lblSubtitulo = new JLabel("Gestión completa de los datos de los asistentes", JLabel.CENTER);
        lblSubtitulo.setForeground(Color.LIGHT_GRAY);

        JPanel panelTexto = new JPanel(new GridLayout(2, 1));
        panelTexto.setBackground(new Color(52, 73, 94));
        panelTexto.add(lblTitulo);
        panelTexto.add(lblSubtitulo);

        panel.add(panelTexto, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Asistente"));
        panel.setPreferredSize(new Dimension(350, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;
        panel.add(new JLabel("ID:"), crearGbc(gbc, 0, y));
        txtId = new JTextField();
        txtId.setEditable(false);
        txtId.setBackground(Color.LIGHT_GRAY);
        panel.add(txtId, crearGbc(gbc, 1, y++));

        panel.add(new JLabel("Nombre:"), crearGbc(gbc, 0, y));
        txtNombre = new JTextField();
        panel.add(txtNombre, crearGbc(gbc, 1, y++));

        panel.add(new JLabel("Identificación:"), crearGbc(gbc, 0, y));
        txtIdentificacion = new JTextField();
        panel.add(txtIdentificacion, crearGbc(gbc, 1, y++));

        panel.add(new JLabel("Teléfono:"), crearGbc(gbc, 0, y));
        txtTelefono = new JTextField();
        panel.add(txtTelefono, crearGbc(gbc, 1, y++));

        panel.add(new JLabel("Correo:"), crearGbc(gbc, 0, y));
        txtCorreo = new JTextField();
        panel.add(txtCorreo, crearGbc(gbc, 1, y++));

        panel.add(new JLabel("Tarjeta Profesional:"), crearGbc(gbc, 0, y));
        txtTarjetaProfesional = new JTextField();
        panel.add(txtTarjetaProfesional, crearGbc(gbc, 1, y++));

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
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Asistentes"));

        String[] columnas = {"ID", "Nombre", "Identificación", "Teléfono", "Correo", "Tarjeta Profesional"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        tblAsistentes = new JTable(modeloTabla);
        tblAsistentes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(tblAsistentes), BorderLayout.CENTER);
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
        btnGuardar.addActionListener(e -> guardarAsistente());
        btnEditar.addActionListener(e -> editarAsistente());
        btnEliminar.addActionListener(e -> eliminarAsistente());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnSalir.addActionListener(e -> dispose());
        tblAsistentes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblAsistentes.getSelectedRow() != -1) {
                seleccionarAsistente();
            }
        });
    }

    private void guardarAsistente() {
        if (!validarCampos()) return;

        if (asistenteSeleccionadoIndex == -1) {
            String id = generarId();
            Asistente a = new Asistente(
                id,
                txtNombre.getText().trim(),
                txtIdentificacion.getText().trim(),
                txtTelefono.getText().trim(),
                txtCorreo.getText().trim(),
                txtTarjetaProfesional.getText().trim()
            );
            listaAsistentes.add(a);
            modeloTabla.addRow(new Object[]{
                a.getId(), a.getNombre(), a.getIdentificacion(), a.getTelefono(), a.getCorreo(), a.getTarjetaProfesional()
            });
            JOptionPane.showMessageDialog(this, "Asistente registrado exitosamente.");
        } else {
            Asistente a = listaAsistentes.get(asistenteSeleccionadoIndex);
            a.setNombre(txtNombre.getText());
            a.setIdentificacion(txtIdentificacion.getText());
            a.setTelefono(txtTelefono.getText());
            a.setCorreo(txtCorreo.getText());
            a.setTarjetaProfesional(txtTarjetaProfesional.getText());
            actualizarFilaTabla(asistenteSeleccionadoIndex, a);
            JOptionPane.showMessageDialog(this, "Asistente actualizado.");
        }

        limpiarFormulario();
    }

    private void editarAsistente() {
        if (asistenteSeleccionadoIndex != -1) {
            Asistente a = listaAsistentes.get(asistenteSeleccionadoIndex);
            cargarFormulario(a);
        }
    }

    private void eliminarAsistente() {
        if (asistenteSeleccionadoIndex != -1) {
            listaAsistentes.remove(asistenteSeleccionadoIndex);
            modeloTabla.removeRow(asistenteSeleccionadoIndex);
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Asistente eliminado.");
        }
    }

    private void seleccionarAsistente() {
        asistenteSeleccionadoIndex = tblAsistentes.getSelectedRow();
        Asistente a = listaAsistentes.get(asistenteSeleccionadoIndex);
        cargarFormulario(a);
    }

    private void cargarFormulario(Asistente a) {
        txtId.setText(a.getId());
        txtNombre.setText(a.getNombre());
        txtIdentificacion.setText(a.getIdentificacion());
        txtTelefono.setText(a.getTelefono());
        txtCorreo.setText(a.getCorreo());
        txtTarjetaProfesional.setText(a.getTarjetaProfesional());
    }

    private void actualizarFilaTabla(int fila, Asistente a) {
        modeloTabla.setValueAt(a.getId(), fila, 0);
        modeloTabla.setValueAt(a.getNombre(), fila, 1);
        modeloTabla.setValueAt(a.getIdentificacion(), fila, 2);
        modeloTabla.setValueAt(a.getTelefono(), fila, 3);
        modeloTabla.setValueAt(a.getCorreo(), fila, 4);
        modeloTabla.setValueAt(a.getTarjetaProfesional(), fila, 5);
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtIdentificacion.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtTarjetaProfesional.setText("");
        asistenteSeleccionadoIndex = -1;
        tblAsistentes.clearSelection();
    }

    private boolean validarCampos() {
        if (txtNombre.getText().isEmpty() || txtIdentificacion.getText().isEmpty() ||
            txtTelefono.getText().isEmpty() || txtCorreo.getText().isEmpty() ||
            txtTarjetaProfesional.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private String generarId() {
        return "ASIS" + (listaAsistentes.size() + 1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmAsistente().setVisible(true));
    }

    private void initComponents() {}
}

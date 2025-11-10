package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.modelo.Departamento;
import com.mycompany.proyectointegrador.modelo.Municipio;
import com.mycompany.proyectointegrador.dao.DepartamentoDAO;
import com.mycompany.proyectointegrador.dao.MunicipioDAO;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FrmMunicipio extends JFrame {

    private JTextField txtId, txtCodigoDane, txtNombre;
    private JComboBox<Departamento> comboDepartamento;
    private JButton btnGuardar, btnEditar, btnEliminar, btnLimpiar, btnSalir;
    private JTable tablaMunicipios;
    private DefaultTableModel modeloTabla;

    private List<Departamento> listaDepartamentos = new ArrayList<>();
    private MunicipioDAO municipioDAO = new MunicipioDAO();
    private DepartamentoDAO departamentoDAO = new DepartamentoDAO();
    private int municipioSeleccionadoIndex = -1;

    public FrmMunicipio() {
        setTitle("Gestión de Municipios");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(236, 240, 241));
        setLayout(new BorderLayout(10, 10));

        cargarDepartamentos();
        inicializarComponentes();
        cargarMunicipios();
    }

    private void inicializarComponentes() {
        // Panel superior: tabla de municipios
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Código DANE", "Nombre", "Departamento"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaMunicipios = new JTable(modeloTabla);
        tablaMunicipios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tablaMunicipios);
        scroll.setPreferredSize(new Dimension(650, 130));
        add(scroll, BorderLayout.NORTH);

        // Panel central: formulario
        JPanel panelCampos = new JPanel(new GridLayout(4, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        panelCampos.setBackground(new Color(236, 240, 241));

        JLabel lblId = new JLabel("ID:");
        JLabel lblCodigoDane = new JLabel("Código DANE:");
        JLabel lblNombre = new JLabel("Nombre:");
        JLabel lblDepartamento = new JLabel("Departamento:");

        txtId = new JTextField();
        txtId.setEditable(false); // El id lo genera la BD
        txtCodigoDane = new JTextField();
        txtNombre = new JTextField();
        comboDepartamento = new JComboBox<>();

        if (listaDepartamentos.isEmpty()) {
            comboDepartamento.addItem(new Departamento(0, "", "⚠️ No hay departamentos registrados"));
            comboDepartamento.setEnabled(false);
            JOptionPane.showMessageDialog(this,
                    "No se pueden registrar municipios.\nDebe registrar al menos un Departamento primero.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            for (Departamento d : listaDepartamentos) {
                comboDepartamento.addItem(d);
            }
        }

        panelCampos.add(lblId); panelCampos.add(txtId);
        panelCampos.add(lblCodigoDane); panelCampos.add(txtCodigoDane);
        panelCampos.add(lblNombre); panelCampos.add(txtNombre);
        panelCampos.add(lblDepartamento); panelCampos.add(comboDepartamento);

        add(panelCampos, BorderLayout.CENTER);

        // Panel inferior: botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(236, 240, 241));

        btnGuardar = new JButton("Guardar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnSalir = new JButton("Salir");

        configurarBoton(btnGuardar);
        configurarBoton(btnEditar);
        configurarBoton(btnEliminar);
        configurarBoton(btnLimpiar);
        configurarBoton(btnSalir);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.SOUTH);

        agregarEventos();
    }

    private void configurarBoton(JButton boton) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(new Color(41, 128, 185));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { boton.setBackground(new Color(31, 97, 141)); }
            @Override
            public void mouseExited(MouseEvent e) { boton.setBackground(new Color(41, 128, 185)); }
        });
    }

    private void agregarEventos() {
        btnGuardar.addActionListener(e -> guardarMunicipio());
        btnEditar.addActionListener(e -> editarMunicipio());
        btnEliminar.addActionListener(e -> eliminarMunicipio());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnSalir.addActionListener(e -> dispose());

        tablaMunicipios.getSelectionModel().addListSelectionListener(e -> cargarSeleccionado());
    }

    private void guardarMunicipio() {
        if (listaDepartamentos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe registrar al menos un Departamento antes de crear un Municipio.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            String codigoDane = txtCodigoDane.getText().trim();
            String nombre = txtNombre.getText().trim();
            Departamento deptoSeleccionado = (Departamento) comboDepartamento.getSelectedItem();

            if (codigoDane.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios.",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (deptoSeleccionado == null || deptoSeleccionado.getId() == 0) {
                JOptionPane.showMessageDialog(this,
                        "Seleccione un departamento válido.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Municipio municipio = new Municipio(0, codigoDane, nombre, deptoSeleccionado.getId());
            municipioDAO.guardar(municipio);

            JOptionPane.showMessageDialog(this, "Municipio guardado correctamente en la base de datos.");
            limpiarCampos();
            cargarMunicipios();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar el municipio: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarMunicipio() {
        if (municipioSeleccionadoIndex == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un municipio de la tabla para editar.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int id = Integer.parseInt(txtId.getText());
            String codigoDane = txtCodigoDane.getText();
            String nombre = txtNombre.getText();
            Departamento depto = (Departamento) comboDepartamento.getSelectedItem();

            Municipio municipio = new Municipio(id, codigoDane, nombre, depto.getId());
            municipioDAO.actualizar(municipio);

            cargarMunicipios();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Municipio editado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error editando municipio: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarMunicipio() {
        if (municipioSeleccionadoIndex == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un municipio de la tabla para eliminar.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int id = Integer.parseInt(txtId.getText());
            municipioDAO.eliminar(id);

            cargarMunicipios();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Municipio eliminado correctamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error eliminando municipio: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtCodigoDane.setText("");
        txtNombre.setText("");
        municipioSeleccionadoIndex = -1;
        tablaMunicipios.clearSelection();
        if (!listaDepartamentos.isEmpty()) {
            comboDepartamento.setSelectedIndex(0);
        }
    }

    private void cargarSeleccionado() {
        municipioSeleccionadoIndex = tablaMunicipios.getSelectedRow();
        if (municipioSeleccionadoIndex >= 0) {
            txtId.setText(modeloTabla.getValueAt(municipioSeleccionadoIndex, 0).toString());
            txtCodigoDane.setText(modeloTabla.getValueAt(municipioSeleccionadoIndex, 1).toString());
            txtNombre.setText(modeloTabla.getValueAt(municipioSeleccionadoIndex, 2).toString());
            String nombreDepto = modeloTabla.getValueAt(municipioSeleccionadoIndex, 3).toString();
            Departamento depto = listaDepartamentos.stream()
                    .filter(d -> d.getNombre().equals(nombreDepto))
                    .findFirst().orElse(null);
            comboDepartamento.setSelectedItem(depto);
        }
    }

    private void cargarDepartamentos() {
        try {
            listaDepartamentos = departamentoDAO.listar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al leer departamentos de la BD: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            listaDepartamentos.clear();
        }
    }

    private void cargarMunicipios() {
        try {
            modeloTabla.setRowCount(0);
            List<Municipio> lista = municipioDAO.listarTodos();
            for (Municipio m : lista) {
                String nombreDepto = listaDepartamentos.stream()
                        .filter(d -> d.getId() == m.getDepartamentoId())
                        .map(Departamento::getNombre)
                        .findFirst().orElse("Sin departamento");
                modeloTabla.addRow(new Object[]{m.getId(), m.getCodigoDane(), m.getNombre(), nombreDepto});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar municipios: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmMunicipio().setVisible(true));
    }
}

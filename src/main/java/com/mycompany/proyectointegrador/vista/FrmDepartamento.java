package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.modelo.Departamento;
import com.mycompany.proyectointegrador.dao.DepartamentoDAO; // ✅ AGREGADO
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException; // ✅ AGREGADO
import java.util.List;

/**
 * Formulario de gestión de departamentos de Colombia.
 * Permite realizar operaciones CRUD sobre la tabla 'departamentos' en Supabase.
 * 
 * @author edward
 */
public class FrmDepartamento extends JFrame {

    // ==================== COMPONENTES DE LA INTERFAZ ====================
    private JPanel contentPane;
    private JTextField txtId, txtCodigoDane, txtNombre;
    private JButton btnGuardar, btnEditar, btnEliminar, btnLimpiar, btnSalir;
    private JTable tblDepartamentos;
    private DefaultTableModel modeloTabla;
    
    // ==================== CAPA DE DATOS ====================
    private DepartamentoDAO departamentoDAO; // ✅ AGREGADO
    private int departamentoSeleccionadoIndex = -1;

    /**
     * Constructor principal.
     */
    public FrmDepartamento() {
        initComponents();
        inicializarFormulario();
    }

    /**
     * Inicializa el formulario y carga datos desde la base de datos.
     */
    private void inicializarFormulario() {
        departamentoDAO = new DepartamentoDAO(); // ✅ AGREGADO
        configurarVentana();
        crearComponentes();
        agregarEventos();
        cargarDepartamentosDesdeDB(); // ✅ AGREGADO: Cargar desde BD
        limpiarFormulario();
    }

    /**
     * Configura las propiedades básicas de la ventana.
     */
    private void configurarVentana() {
        setTitle("Gestión de Departamentos");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ✅ Solo cierra esta ventana
    }

    /**
     * Crea y organiza todos los componentes visuales.
     */
    private void crearComponentes() {
        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        contentPane.add(crearPanelTitulo(), BorderLayout.NORTH);
        contentPane.add(crearPanelFormulario(), BorderLayout.WEST);
        contentPane.add(crearPanelTabla(), BorderLayout.CENTER);
        contentPane.add(crearPanelBotones(), BorderLayout.SOUTH);
    }

    /**
     * Crea el panel superior con título y subtítulo.
     */
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

    /**
     * Crea el panel izquierdo con el formulario de entrada.
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Departamento"));
        panel.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // ✅ AGREGADO: Para que los campos se expandan

        int y = 0;

        // Campo ID (autogenerado por la BD)
        panel.add(new JLabel("ID:"), crearGbc(gbc, 0, y));
        txtId = new JTextField(15); // ✅ CORREGIDO: Ancho definido
        txtId.setEditable(false);
        txtId.setBackground(Color.LIGHT_GRAY);
        panel.add(txtId, crearGbc(gbc, 1, y++));

        // Campo Código DANE (ej: 05, 11, 76)
        panel.add(new JLabel("Código DANE:"), crearGbc(gbc, 0, y));
        txtCodigoDane = new JTextField(15); // ✅ CORREGIDO
        txtCodigoDane.setEnabled(true); // ✅ AGREGADO
        panel.add(txtCodigoDane, crearGbc(gbc, 1, y++));

        // Campo Nombre (ej: Antioquia, Bogotá D.C.)
        panel.add(new JLabel("Nombre:"), crearGbc(gbc, 0, y));
        txtNombre = new JTextField(15); // ✅ CORREGIDO
        txtNombre.setEnabled(true); // ✅ AGREGADO
        panel.add(txtNombre, crearGbc(gbc, 1, y++));

        return panel;
    }

    /**
     * Crea un objeto GridBagConstraints para posicionar componentes.
     */
    private GridBagConstraints crearGbc(GridBagConstraints gbc, int x, int y) {
        GridBagConstraints c = (GridBagConstraints) gbc.clone();
        c.gridx = x;
        c.gridy = y;
        return c;
    }

    /**
     * Crea el panel central con la tabla de departamentos.
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Departamentos"));

        String[] columnas = {"ID", "Código DANE", "Nombre"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { 
                return false; 
            }
        };

        tblDepartamentos = new JTable(modeloTabla);
        tblDepartamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(tblDepartamentos), BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea el panel inferior con los botones de acción.
     */
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

    /**
     * Asigna eventos a botones y tabla.
     */
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

    // ==================== MÉTODOS CRUD CON BASE DE DATOS ====================

    /**
     * Carga todos los departamentos desde la base de datos.
     * ✅ NUEVO MÉTODO
     */
    private void cargarDepartamentosDesdeDB() {
        try {
            modeloTabla.setRowCount(0); // Limpiar tabla
            
            List<Departamento> lista = departamentoDAO.listar();
            
            for (Departamento d : lista) {
                modeloTabla.addRow(new Object[]{
                    d.getId(),
                    d.getCodigoDane(),
                    d.getNombre()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar departamentos: " + ex.getMessage(), 
                "Error de BD", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Guarda o actualiza un departamento en la base de datos.
     * ✅ MODIFICADO: Ahora usa DepartamentoDAO
     */
    private void guardarDepartamento() {
        if (!validarCampos()) return;

        try {
            if (departamentoSeleccionadoIndex == -1) {
                // ===== MODO INSERTAR (Nuevo departamento) =====
                // Nota: El ID se autogenera en la BD (SERIAL)
                Departamento d = new Departamento(
                    0, // El ID lo genera la BD automáticamente
                    txtCodigoDane.getText().trim(),
                    txtNombre.getText().trim()
                );
                
                departamentoDAO.guardar(d);
                JOptionPane.showMessageDialog(this, "Departamento guardado exitosamente.");
                
            } else {
                // ===== MODO ACTUALIZAR (Departamento existente) =====
                Departamento d = new Departamento(
                    Integer.parseInt(txtId.getText()), // Usar el ID existente
                    txtCodigoDane.getText().trim(),
                    txtNombre.getText().trim()
                );
                
                departamentoDAO.actualizar(d);
                JOptionPane.showMessageDialog(this, "Departamento actualizado exitosamente.");
            }
            
            // Recargar tabla desde BD
            cargarDepartamentosDesdeDB();
            limpiarFormulario();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al guardar departamento: " + ex.getMessage(), 
                "Error de BD", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Prepara el formulario para editar un departamento.
     */
    private void editarDepartamento() {
        if (departamentoSeleccionadoIndex == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor seleccione un departamento de la tabla.", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
        }
        // Los datos ya están cargados por seleccionarDepartamento()
    }

    /**
     * Elimina un departamento de la base de datos.
     * ✅ MODIFICADO: Ahora usa DepartamentoDAO
     */
    private void eliminarDepartamento() {
        if (departamentoSeleccionadoIndex == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor seleccione un departamento de la tabla.", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este departamento?", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText());
                
                departamentoDAO.eliminar(id);
                JOptionPane.showMessageDialog(this, "Departamento eliminado exitosamente.");
                
                cargarDepartamentosDesdeDB();
                limpiarFormulario();
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error al eliminar departamento: " + ex.getMessage(), 
                    "Error de BD", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Carga los datos del departamento seleccionado en el formulario.
     */
    private void seleccionarDepartamento() {
        departamentoSeleccionadoIndex = tblDepartamentos.getSelectedRow();
        
        txtId.setText(modeloTabla.getValueAt(departamentoSeleccionadoIndex, 0).toString());
        txtCodigoDane.setText(modeloTabla.getValueAt(departamentoSeleccionadoIndex, 1).toString());
        txtNombre.setText(modeloTabla.getValueAt(departamentoSeleccionadoIndex, 2).toString());
    }

    /**
     * Limpia todos los campos del formulario.
     */
    private void limpiarFormulario() {
        txtId.setText("");
        txtCodigoDane.setText("");
        txtNombre.setText("");
        departamentoSeleccionadoIndex = -1;
        tblDepartamentos.clearSelection();
    }

    /**
     * Valida que los campos obligatorios estén completos.
     */
    private boolean validarCampos() {
        if (txtCodigoDane.getText().trim().isEmpty() || 
            txtNombre.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, 
                "Todos los campos son obligatorios.", 
                "Error de Validación", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Método requerido por NetBeans (no borrar).
     */
    private void initComponents() {}

    /**
     * Método main para pruebas independientes.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmDepartamento().setVisible(true));
    }
}

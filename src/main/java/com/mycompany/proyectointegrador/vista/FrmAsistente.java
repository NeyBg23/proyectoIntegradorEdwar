package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.modelo.Asistente;
import com.mycompany.proyectointegrador.dao.AsistenteDAO; // ✅ AGREGADO: Importar el DAO
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException; // ✅ AGREGADO: Para manejar excepciones de BD
import java.util.List;

/**
 * Formulario de gestión de asistentes técnicos.
 * Permite realizar operaciones CRUD sobre la tabla 'asistentes' en Supabase.
 * 
 * @author edward
 */
public class FrmAsistente extends JFrame {

    // ==================== COMPONENTES DE LA INTERFAZ ====================
    private JPanel contentPane;
    private JTextField txtId, txtNombre, txtIdentificacion, txtTelefono, txtCorreo, txtTarjetaProfesional;
    private JButton btnGuardar, btnEditar, btnEliminar, btnLimpiar, btnSalir;
    private JTable tblAsistentes;
    private DefaultTableModel modeloTabla;
    
    // ==================== CAPA DE DATOS ====================
    private AsistenteDAO asistenteDAO; // ✅ AGREGADO: Objeto para acceder a la BD
    private int asistenteSeleccionadoIndex = -1;

    /**
     * Constructor principal.
     * Inicializa todos los componentes del formulario.
     */
    public FrmAsistente() {
        initComponents();
        inicializarFormulario();
    }

    /**
     * Inicializa el formulario y carga los datos desde la base de datos.
     */
    private void inicializarFormulario() {
        asistenteDAO = new AsistenteDAO(); // ✅ AGREGADO: Inicializar DAO
        configurarVentana();
        crearComponentes();
        agregarEventos();
        cargarAsistentesDesdeDB(); // ✅ CAMBIADO: Cargar desde BD en vez de lista vacía
        limpiarFormulario();
    }

    /**
     * Configura las propiedades básicas de la ventana.
     */
    private void configurarVentana() {
        setTitle("Gestión de Asistentes");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ✅ CAMBIADO: Solo cierra esta ventana
    }

    /**
     * Crea y organiza todos los componentes visuales del formulario.
     */
    private void crearComponentes() {
        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        // Agregar paneles a la ventana
        contentPane.add(crearPanelTitulo(), BorderLayout.NORTH);
        contentPane.add(crearPanelFormulario(), BorderLayout.WEST);
        contentPane.add(crearPanelTabla(), BorderLayout.CENTER);
        contentPane.add(crearPanelBotones(), BorderLayout.SOUTH);
    }

    /**
     * Crea el panel superior con el título y subtítulo.
     */
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

    /**
     * Crea el panel izquierdo con el formulario de entrada de datos.
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Asistente"));
        panel.setPreferredSize(new Dimension(350, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // ✅ AGREGADO: Para que los campos tomen todo el ancho

        int y = 0;
        
        // Campo ID (autogenerado, no editable)
        panel.add(new JLabel("ID:"), crearGbc(gbc, 0, y));
        txtId = new JTextField(20); // ✅ CORREGIDO: Ancho definido
        txtId.setEditable(false);
        txtId.setBackground(Color.LIGHT_GRAY);
        panel.add(txtId, crearGbc(gbc, 1, y++));

        // Campo Nombre
        panel.add(new JLabel("Nombre:"), crearGbc(gbc, 0, y));
        txtNombre = new JTextField(20); // ✅ CORREGIDO
        panel.add(txtNombre, crearGbc(gbc, 1, y++));

        // Campo Identificación
        panel.add(new JLabel("Identificación:"), crearGbc(gbc, 0, y));
        txtIdentificacion = new JTextField(20); // ✅ CORREGIDO
        panel.add(txtIdentificacion, crearGbc(gbc, 1, y++));

        // Campo Teléfono
        panel.add(new JLabel("Teléfono:"), crearGbc(gbc, 0, y));
        txtTelefono = new JTextField(20); // ✅ CORREGIDO
        panel.add(txtTelefono, crearGbc(gbc, 1, y++));

        // Campo Correo
        panel.add(new JLabel("Correo:"), crearGbc(gbc, 0, y));
        txtCorreo = new JTextField(20); // ✅ CORREGIDO
        panel.add(txtCorreo, crearGbc(gbc, 1, y++));

        // Campo Tarjeta Profesional
        panel.add(new JLabel("Tarjeta Profesional:"), crearGbc(gbc, 0, y));
        txtTarjetaProfesional = new JTextField(20); // ✅ CORREGIDO
        panel.add(txtTarjetaProfesional, crearGbc(gbc, 1, y++));

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
     * Crea el panel central con la tabla de asistentes.
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Asistentes"));

        String[] columnas = {"ID", "Nombre", "Identificación", "Teléfono", "Correo", "Tarjeta Profesional"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { 
                return false; // Ninguna celda es editable
            }
        };

        tblAsistentes = new JTable(modeloTabla);
        tblAsistentes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(tblAsistentes), BorderLayout.CENTER);
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
     * Asigna eventos a los botones y a la tabla.
     */
    private void agregarEventos() {
        btnGuardar.addActionListener(e -> guardarAsistente());
        btnEditar.addActionListener(e -> editarAsistente());
        btnEliminar.addActionListener(e -> eliminarAsistente());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnSalir.addActionListener(e -> dispose()); // Cierra solo esta ventana
        
        // Evento de selección de fila en la tabla
        tblAsistentes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblAsistentes.getSelectedRow() != -1) {
                seleccionarAsistente();
            }
        });
    }

    // ==================== MÉTODOS CRUD CON BASE DE DATOS ====================

    /**
     * Carga todos los asistentes desde la base de datos y los muestra en la tabla.
     * ✅ NUEVO MÉTODO: Reemplaza la carga desde ArrayList
     */
    private void cargarAsistentesDesdeDB() {
        try {
            // Limpiar tabla antes de cargar
            modeloTabla.setRowCount(0);
            
            // Obtener lista de asistentes desde BD
            List<Asistente> lista = asistenteDAO.listar();
            
            // Agregar cada asistente a la tabla
            for (Asistente a : lista) {
                modeloTabla.addRow(new Object[]{
                    a.getId(),
                    a.getNombre(),
                    a.getIdentificacion(),
                    a.getTelefono(),
                    a.getCorreo(),
                    a.getTarjetaProfesional()
                });
            }
        } catch (SQLException ex) {
            // Mostrar mensaje de error si falla la conexión
            JOptionPane.showMessageDialog(this, 
                "Error al cargar asistentes: " + ex.getMessage(), 
                "Error de BD", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Guarda o actualiza un asistente en la base de datos.
     * ✅ MODIFICADO: Ahora guarda en Supabase en vez de ArrayList
     */
    private void guardarAsistente() {
        // Validar que todos los campos estén llenos
        if (!validarCampos()) return;

        try {
            if (asistenteSeleccionadoIndex == -1) {
                // ===== MODO INSERTAR (Nuevo asistente) =====
                String id = generarId(); // Generar ID único
                Asistente a = new Asistente(
                    id,
                    txtNombre.getText().trim(),
                    txtIdentificacion.getText().trim(),
                    txtTelefono.getText().trim(),
                    txtCorreo.getText().trim(),
                    txtTarjetaProfesional.getText().trim()
                );
                
                // Guardar en la base de datos
                asistenteDAO.guardar(a);
                JOptionPane.showMessageDialog(this, "Asistente registrado exitosamente.");
                
            } else {
                // ===== MODO ACTUALIZAR (Asistente existente) =====
                Asistente a = new Asistente(
                    txtId.getText(), // Usar el ID existente
                    txtNombre.getText().trim(),
                    txtIdentificacion.getText().trim(),
                    txtTelefono.getText().trim(),
                    txtCorreo.getText().trim(),
                    txtTarjetaProfesional.getText().trim()
                );
                
                // Actualizar en la base de datos
                asistenteDAO.actualizar(a);
                JOptionPane.showMessageDialog(this, "Asistente actualizado exitosamente.");
            }
            
            // Recargar la tabla desde BD
            cargarAsistentesDesdeDB();
            limpiarFormulario();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al guardar asistente: " + ex.getMessage(), 
                "Error de BD", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Prepara el formulario para editar un asistente seleccionado.
     */
    private void editarAsistente() {
        if (asistenteSeleccionadoIndex == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor seleccione un asistente de la tabla.", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
        }
        // Los datos ya están cargados en el formulario por seleccionarAsistente()
    }

    /**
     * Elimina un asistente de la base de datos.
     * ✅ MODIFICADO: Ahora elimina de Supabase
     */
    private void eliminarAsistente() {
        if (asistenteSeleccionadoIndex == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor seleccione un asistente de la tabla.", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Confirmar eliminación
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este asistente?", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                String id = txtId.getText();
                
                // Eliminar de la base de datos
                asistenteDAO.eliminar(id);
                JOptionPane.showMessageDialog(this, "Asistente eliminado exitosamente.");
                
                // Recargar tabla y limpiar formulario
                cargarAsistentesDesdeDB();
                limpiarFormulario();
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error al eliminar asistente: " + ex.getMessage(), 
                    "Error de BD", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Carga los datos del asistente seleccionado en el formulario.
     */
    private void seleccionarAsistente() {
        asistenteSeleccionadoIndex = tblAsistentes.getSelectedRow();
        
        // Cargar datos de la fila seleccionada
        txtId.setText(modeloTabla.getValueAt(asistenteSeleccionadoIndex, 0).toString());
        txtNombre.setText(modeloTabla.getValueAt(asistenteSeleccionadoIndex, 1).toString());
        txtIdentificacion.setText(modeloTabla.getValueAt(asistenteSeleccionadoIndex, 2).toString());
        txtTelefono.setText(modeloTabla.getValueAt(asistenteSeleccionadoIndex, 3).toString());
        txtCorreo.setText(modeloTabla.getValueAt(asistenteSeleccionadoIndex, 4).toString());
        txtTarjetaProfesional.setText(modeloTabla.getValueAt(asistenteSeleccionadoIndex, 5).toString());
    }

    /**
     * Limpia todos los campos del formulario.
     */
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

    /**
     * Valida que todos los campos obligatorios estén llenos.
     * @return true si todos los campos están completos, false si falta alguno
     */
    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty() || 
            txtIdentificacion.getText().trim().isEmpty() ||
            txtTelefono.getText().trim().isEmpty() || 
            txtCorreo.getText().trim().isEmpty() ||
            txtTarjetaProfesional.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, 
                "Todos los campos son obligatorios.", 
                "Error de Validación", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Genera un ID único para un nuevo asistente.
     * Formato: ASIS + número secuencial (ej: ASIS1, ASIS2, ASIS3...)
     * ✅ MODIFICADO: Ahora consulta la BD para obtener el siguiente ID
     */
    private String generarId() {
        try {
            List<Asistente> lista = asistenteDAO.listar();
            return "ASIS" + (lista.size() + 1);
        } catch (SQLException ex) {
            // Si falla, usar un ID temporal
            return "ASIS" + System.currentTimeMillis();
        }
    }

    /**
     * Método vacío requerido por NetBeans (no borrar).
     */
    private void initComponents() {
        // Método generado automáticamente por NetBeans
    }

    /**
     * Método main para pruebas independientes del formulario.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmAsistente().setVisible(true));
    }
}

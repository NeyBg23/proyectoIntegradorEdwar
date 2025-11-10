package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.modelo.Usuario;
import com.mycompany.proyectointegrador.dao.InspeccionFitosanitariaDAO;
import com.mycompany.proyectointegrador.dao.LoteDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Formulario de Inspección Fitosanitaria Completo y Profesional
 * Registro de inspecciones en campo con control de roles
 * - ADMIN: Solo lectura (consulta)
 * - ASISTENTE: CRUD completo
 * 
 * @author edward
 */
public class FrmInspeccionFitosanitaria extends JFrame {
    
    private JTextField txtId, txtTotalPlantas;
    private JTextArea txtObservaciones;
    private JComboBox<String> comboLote, comboEstado;
    private JTable tablaInspecciones;
    private DefaultTableModel modeloTabla;
    private JButton btnGuardar, btnEditar, btnEliminar, btnLimpiar, btnVolver;
    
    private Usuario usuarioActual;
    private InspeccionFitosanitariaDAO inspeccionDAO = new InspeccionFitosanitariaDAO();
    private LoteDAO loteDAO = new LoteDAO();
    private Map<String, Integer> mapaLotes = new HashMap<>();
    
    private int inspeccionSeleccionada = -1;

    // Constructor con Usuario
    public FrmInspeccionFitosanitaria(Usuario usuario) {
        this.usuarioActual = usuario;
        
        setTitle("Registro de Inspección Fitosanitaria");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(236, 240, 241));

        // Validar permisos
        if(usuarioActual != null && !"ASISTENTE".equalsIgnoreCase(usuarioActual.getRol()) 
                                  && !"ADMIN".equalsIgnoreCase(usuarioActual.getRol())) {
            JOptionPane.showMessageDialog(this, 
                "Permiso denegado. Solo asistentes y admin pueden acceder.", 
                "Error de Acceso", 
                JOptionPane.ERROR_MESSAGE);
            this.dispose();
            return;
        }

        inicializarComponentes();
        cargarLotes();
        cargarInspecciones();
    }

    // Constructor sin parámetros
    public FrmInspeccionFitosanitaria() {
        this(null);
    }

    /**
     * Inicializa componentes visuales
     */
    private void inicializarComponentes() {
        // Panel superior: Tabla
        modeloTabla = new DefaultTableModel(
            new Object[]{"ID", "Lote", "Asistente", "Fecha", "Total Plantas", "Estado", "Observaciones"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaInspecciones = new JTable(modeloTabla);
        tablaInspecciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaInspecciones.setRowHeight(20);
        
        JScrollPane scroll = new JScrollPane(tablaInspecciones);
        scroll.setPreferredSize(new Dimension(0, 180));
        add(scroll, BorderLayout.NORTH);

        // Panel central: Formulario
        JPanel panelCampos = new JPanel(new GridLayout(6, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));
        panelCampos.setBackground(new Color(236, 240, 241));

        panelCampos.add(new JLabel("ID (Autogenerado):"));
        txtId = new JTextField();
        txtId.setEditable(false);
        panelCampos.add(txtId);

        panelCampos.add(new JLabel("Lote/Predio:"));
        comboLote = new JComboBox<>();
        panelCampos.add(comboLote);

        panelCampos.add(new JLabel("Total de Plantas:"));
        txtTotalPlantas = new JTextField();
        panelCampos.add(txtTotalPlantas);

        panelCampos.add(new JLabel("Estado:"));
        comboEstado = new JComboBox<>(new String[]{"pendiente", "aprobada", "rechazada"});
        panelCampos.add(comboEstado);

        panelCampos.add(new JLabel("Observaciones:"));
        txtObservaciones = new JTextArea(3, 20);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        panelCampos.add(new JScrollPane(txtObservaciones));

        add(panelCampos, BorderLayout.CENTER);

        // Panel inferior: Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(new Color(236, 240, 241));
        
        btnGuardar = new JButton("Guardar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnVolver = new JButton("Volver");

        configurarBoton(btnGuardar);
        configurarBoton(btnEditar);
        configurarBoton(btnEliminar);
        configurarBoton(btnLimpiar);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH);

        // Control de permisos por rol
        if(usuarioActual != null && "ADMIN".equalsIgnoreCase(usuarioActual.getRol())) {
            btnGuardar.setEnabled(false);
            btnEditar.setEnabled(false);
            btnEliminar.setEnabled(false);
            
            comboLote.setEnabled(false);
            txtTotalPlantas.setEditable(false);
            comboEstado.setEnabled(false);
            txtObservaciones.setEditable(false);
            
            setTitle("Consultar Inspecciones Fitosanitarias (Solo Lectura)");
        }

        // Eventos
        btnGuardar.addActionListener(e -> guardarInspeccion());
        btnEditar.addActionListener(e -> editarInspeccion());
        btnEliminar.addActionListener(e -> eliminarInspeccion());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnVolver.addActionListener(e -> dispose());

        tablaInspecciones.getSelectionModel().addListSelectionListener(e -> cargarSeleccion());
    }

    /**
     * Configura estilo de botón
     */
    private void configurarBoton(JButton boton) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setBackground(new Color(41, 128, 185));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { boton.setBackground(new Color(31, 97, 141)); }
            public void mouseExited(MouseEvent e) { boton.setBackground(new Color(41, 128, 185)); }
        });
    }

    /**
     * Carga lotes desde la BD
     */
    private void cargarLotes() {
        try {
            mapaLotes = loteDAO.listar();
            
            comboLote.addItem("Seleccionar lote...");
            
            if(mapaLotes.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "⚠️ No hay lotes registrados en la BD", 
                    "Advertencia", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                for(String nombreLote : mapaLotes.keySet()) {
                    comboLote.addItem(nombreLote);
                }
                System.out.println("✅ " + mapaLotes.size() + " lotes cargados");
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error cargando lotes: " + e.getMessage(),
                "Error de BD",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Carga inspecciones desde la BD
     * Si es ASISTENTE, solo carga SUS inspecciones
     * Si es ADMIN, carga TODAS
     */
    private void cargarInspecciones() {
        modeloTabla.setRowCount(0);
        try {
            List<Object[]> lista;
            
            if(usuarioActual != null && "ASISTENTE".equalsIgnoreCase(usuarioActual.getRol())) {
                // ✅ ASISTENTE: Solo ve SUS inspecciones
                lista = inspeccionDAO.listarPorAsistente(usuarioActual.getId());
            } else {
                // Admin o sin usuario: Ve todas
                lista = inspeccionDAO.listar();
            }
            
            for(Object[] fila : lista) {
                modeloTabla.addRow(fila);
            }
            System.out.println("✅ " + lista.size() + " inspecciones cargadas");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error cargando inspecciones: " + e.getMessage(),
                "Error de BD",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Guarda una nueva inspección
     */
    private void guardarInspeccion() {
        if(usuarioActual == null || !"ASISTENTE".equalsIgnoreCase(usuarioActual.getRol())) {
            JOptionPane.showMessageDialog(this, 
                "Solo los asistentes pueden crear inspecciones.", 
                "Permiso Denegado", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String lote = (String) comboLote.getSelectedItem();
            String totalPlantasStr = txtTotalPlantas.getText().trim();
            String estado = (String) comboEstado.getSelectedItem();
            String observaciones = txtObservaciones.getText().trim();

            if(lote.equals("Seleccionar lote...") || totalPlantasStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios.");
                return;
            }

            int totalPlantas = Integer.parseInt(totalPlantasStr);
            int loteId = mapaLotes.get(lote);
            String fechaHoy = LocalDate.now().toString();

            // ✅ Guardar en BD usando el DAO
            inspeccionDAO.guardar(loteId, usuarioActual.getId(), fechaHoy, 
                                 totalPlantas, observaciones, estado);

            cargarInspecciones();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "✅ Inspección guardada correctamente");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El total de plantas debe ser un número.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }
    }

    /**
     * Edita la inspección seleccionada
     */
    private void editarInspeccion() {
        if(usuarioActual == null || !"ASISTENTE".equalsIgnoreCase(usuarioActual.getRol())) {
            JOptionPane.showMessageDialog(this, "Solo asistentes pueden editar.");
            return;
        }

        if(inspeccionSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una inspección para editar.");
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText());
            int totalPlantas = Integer.parseInt(txtTotalPlantas.getText().trim());
            String estado = (String) comboEstado.getSelectedItem();
            String observaciones = txtObservaciones.getText().trim();

            inspeccionDAO.actualizar(id, totalPlantas, observaciones, estado);
            cargarInspecciones();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "✏️ Inspección actualizada");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    /**
     * Elimina la inspección seleccionada
     */
    private void eliminarInspeccion() {
        if(usuarioActual == null || !"ASISTENTE".equalsIgnoreCase(usuarioActual.getRol())) {
            JOptionPane.showMessageDialog(this, "Solo asistentes pueden eliminar.");
            return;
        }

        if(inspeccionSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una inspección para eliminar.");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this, "¿Eliminar esta inspección?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if(confirmar == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText());
                inspeccionDAO.eliminar(id);
                cargarInspecciones();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "🗑️ Inspección eliminada");

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        }
    }

    /**
     * Carga datos de la fila seleccionada
     */
    private void cargarSeleccion() {
        inspeccionSeleccionada = tablaInspecciones.getSelectedRow();
        if(inspeccionSeleccionada >= 0) {
            txtId.setText(modeloTabla.getValueAt(inspeccionSeleccionada, 0).toString());
            comboLote.setSelectedItem(modeloTabla.getValueAt(inspeccionSeleccionada, 1).toString());
            txtTotalPlantas.setText(modeloTabla.getValueAt(inspeccionSeleccionada, 4).toString());
            comboEstado.setSelectedItem(modeloTabla.getValueAt(inspeccionSeleccionada, 5).toString());
            txtObservaciones.setText(modeloTabla.getValueAt(inspeccionSeleccionada, 6).toString());
        }
    }

    /**
     * Limpia los campos
     */
    private void limpiarCampos() {
        txtId.setText("");
        txtTotalPlantas.setText("");
        comboLote.setSelectedIndex(0);
        comboEstado.setSelectedIndex(0);
        txtObservaciones.setText("");
        tablaInspecciones.clearSelection();
        inspeccionSeleccionada = -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmInspeccionFitosanitaria().setVisible(true));
    }
}

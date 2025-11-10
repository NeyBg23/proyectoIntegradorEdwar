


package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.modelo.Usuario;
import com.mycompany.proyectointegrador.modelo.Predio;
import com.mycompany.proyectointegrador.modelo.Vereda;  // ✅ AGREGADO
import com.mycompany.proyectointegrador.dao.PredioDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import com.mycompany.proyectointegrador.modelo.Propietario;
import com.mycompany.proyectointegrador.dao.PropietarioDAO;
import java.util.List;
import com.mycompany.proyectointegrador.dao.VeredaDAO;
import java.util.HashMap;
import java.util.Map;

/**
 * Formulario de Gestión de Predios
 * Solo accesible para Asistentes
 * Permite: Crear, editar, eliminar y consultar predios agrícolas
 * OPCIÓN B: Asistente crea predios seleccionando el propietario
 * 
 * @author edward
 */
public class FrmPredio extends JFrame {
    private JTextField txtId, txtNombre, txtArea;
    private JComboBox<String> comboPropietario;  
    private JComboBox<String> comboVereda;  
    private JTable tablaPredios;
    private DefaultTableModel modeloTabla;
    private JButton btnGuardar, btnEditar, btnEliminar, btnLimpiar, btnSalir;
    private Usuario usuarioActual;
    private PredioDAO predioDAO = new PredioDAO();
    private Map<String, String> mapaPropietarios = new HashMap<>();  
    private Map<String, Integer> mapaVeredas = new HashMap<>();  
    private int predioSeleccionadoIndex = -1;

    
    // Constructor con Usuario
    public FrmPredio(Usuario usuario) {
        this.usuarioActual = usuario;
        
        setTitle("Gestión de Predios - ASISTENTE");
        setSize(750, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(236, 240, 241));

        // Validar permisos
        if(usuarioActual != null && !"ASISTENTE".equalsIgnoreCase(usuarioActual.getRol())) {
            JOptionPane.showMessageDialog(this, 
                "Permiso denegado. Solo los asistentes pueden gestionar predios.", 
                "Error de Acceso", 
                JOptionPane.ERROR_MESSAGE);
            this.dispose();
            return;
        }

        inicializarComponentes();
        cargarPropietarios();
        cargarVeredas();  // ✅ AGREGADO
        cargarPredios();
    }

    // Constructor sin parámetros
    public FrmPredio() {
        this(null);
    }

    /**
     * Inicializa componentes visuales
     */
    private void inicializarComponentes() {
        // Panel superior: Tabla
        modeloTabla = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Propietario", "Vereda", "Área (m²)"}, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaPredios = new JTable(modeloTabla);
        tablaPredios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaPredios.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tablaPredios);
        scroll.setPreferredSize(new Dimension(0, 150));
        add(scroll, BorderLayout.NORTH);

        // Panel central: Formulario
        // ✅ CORREGIDO: GridLayout(5, 2) - 5 filas correctas
        JPanel panelCampos = new JPanel(new GridLayout(5, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));
        panelCampos.setBackground(new Color(236, 240, 241));
        
        panelCampos.add(new JLabel("ID (Autogenerado):"));
        txtId = new JTextField();
        txtId.setEditable(false);
        panelCampos.add(txtId);

        panelCampos.add(new JLabel("Nombre del Predio:"));
        txtNombre = new JTextField();
        panelCampos.add(txtNombre);

        // ✅ Combo para seleccionar propietario
        panelCampos.add(new JLabel("Propietario:"));
        comboPropietario = new JComboBox<>();
        comboPropietario.addItem("Seleccionar propietario...");
        panelCampos.add(comboPropietario);

        // ✅ Combo para Veredas
        panelCampos.add(new JLabel("Vereda (Ubicación):"));
        comboVereda = new JComboBox<>();
        comboVereda.addItem("Seleccionar vereda...");
        panelCampos.add(comboVereda);

        panelCampos.add(new JLabel("Área (m²):"));
        txtArea = new JTextField();
        panelCampos.add(txtArea);

        add(panelCampos, BorderLayout.CENTER);

        // Panel inferior: Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
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

        panelBotones.add(btnGuardar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnGuardar.addActionListener(e -> guardarPredio());
        btnEditar.addActionListener(e -> editarPredio());
        btnEliminar.addActionListener(e -> eliminarPredio());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnSalir.addActionListener(e -> dispose());

        tablaPredios.getSelectionModel().addListSelectionListener(e -> cargarSeleccion());
    }

    /**
    * ✅ Carga veredas desde la BD
    */
   private void cargarVeredas() {
       try {
           VeredaDAO veredaDAO = new VeredaDAO();
           mapaVeredas = veredaDAO.listarVeredas();

           if(mapaVeredas.isEmpty()) {
               JOptionPane.showMessageDialog(this, 
                   "⚠️ No hay veredas registradas en la BD.",
                   "Advertencia", 
                   JOptionPane.WARNING_MESSAGE);
           } else {
               for(String nombreVereda : mapaVeredas.keySet()) {
                   comboVereda.addItem(nombreVereda);
               }
               System.out.println("✅ " + mapaVeredas.size() + " veredas cargadas");
           }

       } catch (SQLException e) {
           System.err.println("Error cargando veredas: " + e.getMessage());
       }
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

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                boton.setBackground(new Color(31, 97, 141));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                boton.setBackground(new Color(41, 128, 185));
            }
        });
    }

    /**
     * ✅ Carga propietarios REALES de la tabla propietarios
     */
    private void cargarPropietarios() {
        try {
            PropietarioDAO propietarioDAO = new PropietarioDAO();
            List<Propietario> listaPropietarios = propietarioDAO.listar();

            if(listaPropietarios.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "⚠️ No hay propietarios registrados en la BD.",
                    "Advertencia", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                for(Propietario prop : listaPropietarios) {
                    String display = prop.getNombre() + " (" + prop.getId().substring(0, 8) + "...)";
                    comboPropietario.addItem(display);
                    mapaPropietarios.put(display, prop.getId());
                }
                System.out.println("✅ " + listaPropietarios.size() + " propietarios cargados");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "❌ Error cargando propietarios: " + e.getMessage(),
                "Error de BD",
                JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Carga predios mostrando NOMBRE del propietario
     */
    private void cargarPredios() {
        modeloTabla.setRowCount(0);
        try {
            List<Object[]> lista = predioDAO.listarConPropietario();

            for (Object[] fila : lista) {
                modeloTabla.addRow(fila);
            }
            System.out.println("✅ " + lista.size() + " predios cargados");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + e.getMessage(), 
                "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
    * Guarda un nuevo predio
    */
   private void guardarPredio() {
       try {
           String nombre = txtNombre.getText().trim();
           String areaStr = txtArea.getText().trim();
           String propietarioSeleccionado = (String) comboPropietario.getSelectedItem();
           String veredaSeleccionada = (String) comboVereda.getSelectedItem();

           // Validaciones
           if(nombre.isEmpty() || areaStr.isEmpty()) {
               JOptionPane.showMessageDialog(this, 
                   "⚠️ Todos los campos son obligatorios.", 
                   "Validación", JOptionPane.WARNING_MESSAGE);
               return;
           }

           if(propietarioSeleccionado == null || propietarioSeleccionado.equals("Seleccionar propietario...")) {
               JOptionPane.showMessageDialog(this, 
                   "⚠️ Debe seleccionar un propietario.", 
                   "Validación", JOptionPane.WARNING_MESSAGE);
               return;
           }
           
           if(veredaSeleccionada == null || veredaSeleccionada.equals("Seleccionar vereda...")) {
                JOptionPane.showMessageDialog(this, 
                    "⚠️ Debe seleccionar una vereda.", 
                    "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

           float area = Float.parseFloat(areaStr);

           // ✅ Crear predio
           Predio predio = new Predio();
           String idPredio = String.valueOf(System.nanoTime()).substring(0, 10);
           predio.setId(idPredio);
           predio.setNombre(nombre);
           predio.setArea(area);

           // ✅ Propietario
           String propietarioId = mapaPropietarios.get(propietarioSeleccionado);
           if(propietarioId == null || propietarioId.isEmpty()) {
               JOptionPane.showMessageDialog(this, 
                   "⚠️ Error: No se pudo obtener el ID del propietario.", 
                   "Error", JOptionPane.WARNING_MESSAGE);
               return;
           }
           predio.setPropietarioId(propietarioId);

           // ✅ Vereda
           Integer veredaId = mapaVeredas.get(veredaSeleccionada);
           

           // Guardar en BD
           predioDAO.guardar(predio);
           cargarPredios();
           limpiarCampos();

           JOptionPane.showMessageDialog(this, 
               "✅ Predio guardado correctamente\n\n" +
               "ID: " + idPredio + "\n" +
               "Nombre: " + nombre + "\n" +
               "Propietario: " + propietarioSeleccionado + "\n" +
               "Vereda: " + veredaSeleccionada + "\n" +
               "Área: " + area + " m²", 
               "Éxito", JOptionPane.INFORMATION_MESSAGE);

       } catch (NumberFormatException ex) {
           JOptionPane.showMessageDialog(this, 
               "❌ El área debe ser un número válido", 
               "Error", JOptionPane.ERROR_MESSAGE);
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(this, 
               "❌ Error al guardar: " + ex.getMessage(), 
               "Error de BD", JOptionPane.ERROR_MESSAGE);
           ex.printStackTrace();
       }
   }


    /**
     * Edita el predio seleccionado
     */
    private void editarPredio() {
        if(predioSeleccionadoIndex == -1) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Seleccione un predio para editar", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String id = txtId.getText();
            String nombre = txtNombre.getText().trim();
            float area = Float.parseFloat(txtArea.getText().trim());

            Predio predio = new Predio();
            predio.setId(id);
            predio.setNombre(nombre);
            predio.setArea(area);

            predioDAO.actualizar(predio);
            cargarPredios();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, 
                "✏️ Predio actualizado correctamente", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "❌ Formato inválido", 
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "❌ Error al actualizar: " + ex.getMessage(), 
                "Error de BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Elimina el predio seleccionado
     */
    private void eliminarPredio() {
        if(predioSeleccionadoIndex == -1) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Seleccione un predio para eliminar", 
                "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este predio?", 
            "Confirmar", JOptionPane.YES_NO_OPTION);

        if(confirmar == JOptionPane.YES_OPTION) {
            try {
                String id = txtId.getText();
                predioDAO.eliminar(id);
                cargarPredios();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, 
                    "🗑️ Predio eliminado correctamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, 
                    "❌ Error al eliminar: " + ex.getMessage(), 
                    "Error de BD", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Carga datos de la fila seleccionada
     */
    private void cargarSeleccion() {
        predioSeleccionadoIndex = tablaPredios.getSelectedRow();
        if(predioSeleccionadoIndex >= 0) {
            txtId.setText(modeloTabla.getValueAt(predioSeleccionadoIndex, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(predioSeleccionadoIndex, 1).toString());
            txtArea.setText(modeloTabla.getValueAt(predioSeleccionadoIndex, 4).toString());
        }
    }

    /**
     * Limpia los campos
     */
    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtArea.setText("");
        comboPropietario.setSelectedIndex(0);
        comboVereda.setSelectedIndex(0);  // ✅ AGREGADO
        tablaPredios.clearSelection();
        predioSeleccionadoIndex = -1;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new FrmPredio().setVisible(true));
    }
}

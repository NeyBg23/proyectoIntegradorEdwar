/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.modelo.Propietario;

import com.mycompany.proyectointegrador.dao.PropietarioDAO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author edward
 */
public class FrmPropietario extends javax.swing.JFrame {

    // Componentes del formulario
    private JPanel contentPane;
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtIdentificacion;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JButton btnGuardar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnSalir;
    private JTable tblPropietarios;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;
   
    // Lista para almacenar propietarios
    private List<Propietario> listaPropietarios;
    private int propietarioSeleccionadoIndex = -1;

    /**
     * Creates new form FrmPropietarios
     */
    public  FrmPropietario() {
        initComponents();
        inicializarFormulario();
        // 🟢 Cargar lista de la BD al abrir
        cargarPropietariosDesdeBD();
    }

    /**
     * Inicializa los componentes adicionales del formulario
     */
    private void inicializarFormulario() {
        listaPropietarios = new ArrayList<>();
        configurarVentana();
        crearComponentes();
        configurarTabla();
        agregarEventos();
        limpiarFormulario();
    }

    /**
     * Configura las propiedades de la ventana
     */
    private void configurarVentana() {
        setTitle("Sistema de Gestión - Administración de Propietarios");
        setLocationRelativeTo(null);
        setSize(800, 600);
    }

    /**
     * Crea y configura todos los componentes del formulario
     */
    private void crearComponentes() {
        // Panel principal
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);

        // Crear paneles
        JPanel panelTitulo = crearPanelTitulo();
        JPanel panelFormulario = crearPanelFormulario();
        JPanel panelTabla = crearPanelTabla();
        JPanel panelBotones = crearPanelBotones();

        // Agregar paneles al contenido principal
        contentPane.add(panelTitulo, BorderLayout.NORTH);
        contentPane.add(panelFormulario, BorderLayout.WEST);
        contentPane.add(panelTabla, BorderLayout.CENTER);
        contentPane.add(panelBotones, BorderLayout.SOUTH);
    }

    /**
     * Crea el panel del título
     */
    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(41, 128, 185));
        panel.setPreferredSize(new Dimension(0, 70));
        panel.setLayout(new BorderLayout());
       
        JLabel lblTitulo = new JLabel("ADMINISTRACIÓN DE PROPIETARIOS", JLabel.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
       
        JLabel lblSubtitulo = new JLabel("Gestión completa de información de propietarios", JLabel.CENTER);
        lblSubtitulo.setForeground(Color.LIGHT_GRAY);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 12));
       
        JPanel panelTexto = new JPanel(new GridLayout(2, 1));
        panelTexto.setBackground(new Color(41, 128, 185));
        panelTexto.add(lblTitulo);
        panelTexto.add(lblSubtitulo);
       
        panel.add(panelTexto, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea el panel del formulario con los campos
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Datos del Propietario"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setPreferredSize(new Dimension(350, 0));
       
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Fila 0: ID
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        panel.add(new JLabel("ID:"), gbc);
       
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        txtId = new JTextField(20);
        txtId.setEditable(false);
        txtId.setBackground(Color.LIGHT_GRAY);
        panel.add(txtId, gbc);

        // Fila 1: Nombre
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panel.add(new JLabel("Nombre completo:"), gbc);
       
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        txtNombre = new JTextField(20);
        panel.add(txtNombre, gbc);

        // Fila 2: Identificación
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        panel.add(new JLabel("Identificación:"), gbc);
       
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        txtIdentificacion = new JTextField(20);
        panel.add(txtIdentificacion, gbc);

        // Fila 3: Teléfono
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0;
        panel.add(new JLabel("Teléfono:"), gbc);
       
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 1.0;
        txtTelefono = new JTextField(20);
        panel.add(txtTelefono, gbc);

        // Fila 4: Correo
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0;
        panel.add(new JLabel("Correo electrónico:"), gbc);
       
        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 1.0;
        txtCorreo = new JTextField(20);
        panel.add(txtCorreo, gbc);

        return panel;
    }

    /**
     * Crea el panel de la tabla
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Lista de Propietarios Registrados"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Configurar modelo de tabla
        String[] columnas = {"ID", "Nombre", "Identificación", "Teléfono", "Correo"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };

        tblPropietarios = new JTable(modeloTabla);
        tblPropietarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblPropietarios.getTableHeader().setReorderingAllowed(false);
       
        // Personalizar tabla
        tblPropietarios.setRowHeight(25);
        tblPropietarios.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblPropietarios.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblPropietarios.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblPropietarios.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblPropietarios.getColumnModel().getColumn(4).setPreferredWidth(150);

        scrollTabla = new JScrollPane(tblPropietarios);
        panel.add(scrollTabla, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea el panel de botones
     */
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Botón Guardar
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(39, 174, 96));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 12));
        btnGuardar.setPreferredSize(new Dimension(100, 35));

        // Botón Editar
        btnEditar = new JButton("Editar");
        btnEditar.setBackground(new Color(243, 156, 18));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEditar.setPreferredSize(new Dimension(100, 35));
        btnEditar.setEnabled(false);

        // Botón Eliminar
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(231, 76, 60));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEliminar.setPreferredSize(new Dimension(100, 35));
        btnEliminar.setEnabled(false);

        // Botón Limpiar
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBackground(new Color(52, 152, 219));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 12));
        btnLimpiar.setPreferredSize(new Dimension(100, 35));

        // Botón Salir
        btnSalir = new JButton("Salir");
        btnSalir.setBackground(new Color(149, 165, 166));
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFont(new Font("Arial", Font.BOLD, 12));
        btnSalir.setPreferredSize(new Dimension(100, 35));

        panel.add(btnGuardar);
        panel.add(btnEditar);
        panel.add(btnEliminar);
        panel.add(btnLimpiar);
        panel.add(btnSalir);

        return panel;
    }

    /**
     * Configura la tabla
     */
    private void configurarTabla() {
        // Llenar tabla con datos de ejemplo (opcional)
        // agregarPropietarioEjemplo();
    }

    /**
     * Agrega los eventos a los componentes
     */
    private void agregarEventos() {
        // Evento para el botón Guardar
        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarPropietario();
            }
        });

        // Evento para el botón Editar
        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editarPropietario();
            }
        });

        // Evento para el botón Eliminar
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarPropietario();
            }
        });

        // Evento para el botón Limpiar
        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });

        // Evento para el botón Salir
        btnSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salir();
            }
        });

        // Evento para selección en la tabla
        tblPropietarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblPropietarios.getSelectedRow() != -1) {
                seleccionarPropietario();
            }
        });
    }

    /**
     * Método para guardar un nuevo propietario o actualizar uno existente
     */
    private void guardarPropietario() {
        if (!validarCampos()) {
            return;
        }

        PropietarioDAO propietarioDAO = new PropietarioDAO();

        try {
            String nombre = txtNombre.getText().trim();
            String identificacion = txtIdentificacion.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String correo = txtCorreo.getText().trim();

            if (propietarioSeleccionadoIndex == -1) {
                // GUARDAR NUEVO PROPIETARIO
                Propietario propietario = new Propietario(nombre, identificacion, telefono, correo);
                propietarioDAO.guardar(propietario);

                JOptionPane.showMessageDialog(this,
                    "Propietario guardado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                // ACTUALIZAR PROPIETARIO EXISTENTE (con ID)
                String id = txtId.getText().trim();
                Propietario propietario = new Propietario(id, nombre, identificacion, telefono, correo);
                propietarioDAO.actualizar(propietario);

                JOptionPane.showMessageDialog(this,
                    "Propietario actualizado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            }

            // Refrescar la tabla desde la BD
            cargarPropietariosDesdeBD();
            limpiarFormulario();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error al guardar propietario: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Mostrar error en consola para debugging
        }
    }


    
    private void cargarPropietariosDesdeBD() {
        try {
            PropietarioDAO propietarioDAO = new PropietarioDAO();
            List<Propietario> propietarios = propietarioDAO.listar();

            // Limpia la tabla
            modeloTabla.setRowCount(0);

            // Llena la tabla con los datos de Supabase
            for (Propietario p : propietarios) {
                modeloTabla.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getIdentificacion(),
                    p.getTelefono(),
                    p.getCorreo()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar la lista de propietarios: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Método para editar un propietario seleccionado
     */
    private void editarPropietario() {
        if (propietarioSeleccionadoIndex != -1) {
            try {
                // Obtener datos del formulario
                String id = txtId.getText().trim();
                String nombre = txtNombre.getText().trim();
                String identificacion = txtIdentificacion.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String correo = txtCorreo.getText().trim();

                // Validar que no estén vacíos
                if (nombre.isEmpty() || identificacion.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor llenar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Crear propietario con los datos actualizados
                Propietario propietario = new Propietario(id, nombre, identificacion, telefono, correo);

                // Actualizar en Supabase
                PropietarioDAO propietarioDAO = new PropietarioDAO();
                propietarioDAO.actualizar(propietario);

                // Refrescar la tabla
                cargarPropietariosDesdeBD();

                JOptionPane.showMessageDialog(this,
                    "Propietario actualizado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

                limpiarFormulario();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Error al actualizar propietario: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Por favor seleccione un propietario de la tabla",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
        }
    }


    /**
     * Método para eliminar un propietario seleccionado
     */
    private void eliminarPropietario() {
        if (propietarioSeleccionadoIndex != -1) {
            String id = txtId.getText().trim();
            String nombre = txtNombre.getText().trim();

            int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar este propietario?\n" + nombre,
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

            if (respuesta == JOptionPane.YES_OPTION) {
                try {
                    // Eliminar de Supabase
                    PropietarioDAO propietarioDAO = new PropietarioDAO();
                    propietarioDAO.eliminar(id);

                    // Refrescar la tabla
                    cargarPropietariosDesdeBD();

                    limpiarFormulario();

                    JOptionPane.showMessageDialog(this,
                        "Propietario eliminado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                        "Error al eliminar propietario: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Por favor seleccione un propietario de la tabla",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE);
        }
    }


    /**
     * Método para seleccionar un propietario de la tabla
     */
    private void seleccionarPropietario() {
        int filaSeleccionada = tblPropietarios.getSelectedRow();
        if (filaSeleccionada != -1) {
            propietarioSeleccionadoIndex = filaSeleccionada;

            // ✅ LEER DIRECTAMENTE DE LA TABLA (no de listaPropietarios)
            String id = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
            String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
            String identificacion = (String) modeloTabla.getValueAt(filaSeleccionada, 2);
            String telefono = (String) modeloTabla.getValueAt(filaSeleccionada, 3);
            String correo = (String) modeloTabla.getValueAt(filaSeleccionada, 4);

            // Cargar datos en el formulario
            txtId.setText(id);
            txtNombre.setText(nombre);
            txtIdentificacion.setText(identificacion);
            txtTelefono.setText(telefono);
            txtCorreo.setText(correo);

            // Habilitar botones
            btnEditar.setEnabled(true);
            btnEliminar.setEnabled(true);
            btnGuardar.setText("Actualizar");
        }
    }


    /**
     * Carga los datos de un propietario en el formulario
     */
    private void cargarDatosFormulario(Propietario propietario) {
        txtId.setText(propietario.getId());
        txtNombre.setText(propietario.getNombre());
        txtIdentificacion.setText(propietario.getIdentificacion());
        txtTelefono.setText(propietario.getTelefono());
        txtCorreo.setText(propietario.getCorreo());
    }

    /**
     * Método para validar los campos del formulario
     */
    private boolean validarCampos() {
        // Validar nombre
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarError("El campo Nombre es obligatorio");
            txtNombre.requestFocus();
            return false;
        }

        // Validar identificación
        if (txtIdentificacion.getText().trim().isEmpty()) {
            mostrarError("El campo Identificación es obligatorio");
            txtIdentificacion.requestFocus();
            return false;
        }

        // Validar teléfono
        if (txtTelefono.getText().trim().isEmpty()) {
            mostrarError("El campo Teléfono es obligatorio");
            txtTelefono.requestFocus();
            return false;
        }

        // Validar correo
        if (txtCorreo.getText().trim().isEmpty()) {
            mostrarError("El campo Correo electrónico es obligatorio");
            txtCorreo.requestFocus();
            return false;
        }

        // Validar formato de correo básico
        if (!txtCorreo.getText().trim().contains("@")) {
            mostrarError("Por favor ingrese un correo electrónico válido");
            txtCorreo.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Muestra un mensaje de error
     */
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Genera un ID automático
     */
    private String generarId() {
        return "PROP" + (listaPropietarios.size() + 1);
    }

    /**
     * Agrega una fila a la tabla
     */
    private void agregarFilaTabla(Propietario propietario) {
        modeloTabla.addRow(new Object[]{
            propietario.getId(),
            propietario.getNombre(),
            propietario.getIdentificacion(),
            propietario.getTelefono(),
            propietario.getCorreo()
        });
    }

    /**
     * Actualiza una fila en la tabla
     */
    private void actualizarFilaTabla(int fila, Propietario propietario) {
        modeloTabla.setValueAt(propietario.getId(), fila, 0);
        modeloTabla.setValueAt(propietario.getNombre(), fila, 1);
        modeloTabla.setValueAt(propietario.getIdentificacion(), fila, 2);
        modeloTabla.setValueAt(propietario.getTelefono(), fila, 3);
        modeloTabla.setValueAt(propietario.getCorreo(), fila, 4);
    }

    /**
     * Método para limpiar el formulario
     */
    private void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtIdentificacion.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
       
        propietarioSeleccionadoIndex = -1;
        tblPropietarios.clearSelection();
        btnGuardar.setText("Guardar");
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
        txtNombre.requestFocus();
    }

    /**
     * Método para salir del formulario (volver al menú)
     */
    private void salir() {
        int respuesta = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de que desea salir?",
            "Confirmar salida",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) {
            // ✅ SOLO cierra esta ventana, NO todo el programa
            this.dispose();
        }

    }

    /**
     * Agrega algunos propietarios de ejemplo (opcional)
     */
    private void agregarPropietarioEjemplo() {
        Propietario prop1 = new Propietario("PROP1", "Juan Pérez", "12345678", "3001234567", "juan@email.com");
        Propietario prop2 = new Propietario("PROP2", "María García", "87654321", "3107654321", "maria@email.com");
       
        listaPropietarios.add(prop1);
        listaPropietarios.add(prop2);
       
        agregarFilaTabla(prop1);
        agregarFilaTabla(prop2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPropietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPropietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPropietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPropietario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPropietario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}


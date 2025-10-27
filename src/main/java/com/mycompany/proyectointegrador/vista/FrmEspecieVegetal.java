/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.proyectointegrador.vista;

/**
 *
 * @author edward
 */




import com.mycompany.proyectointegrador.modelo.EspecieVegetal;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Interfaz para registrar especies vegetales.
 * @author edward
 */
public class FrmEspecieVegetal extends JFrame {

    private JTextField txtId, txtCodigo, txtNombre, txtDensidad;
    private JTextArea txtDescripcion;
    private JButton btnGuardar, btnEditar, btnEliminar, btnVolver, btnLimpiar;
    private JTable tablaEspecies;
    private DefaultTableModel modeloTabla;
    private ArrayList<EspecieVegetal> listaEspecies;
    private final String ARCHIVO = "especies.txt";

    public FrmEspecieVegetal() {
        listaEspecies = new ArrayList<>();
        initComponents();
        cargarDesdeArchivo();
    }

    private void initComponents() {
        setTitle("Gestión de Especies Vegetales");
        setSize(900, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ===== Título =====
        JLabel lblTitulo = new JLabel("Registro de Especie Vegetal", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(41, 128, 185));
        add(lblTitulo, BorderLayout.NORTH);

        // ===== Panel de formulario =====
        JPanel panelIzquierdo = new JPanel(new GridLayout(6, 2, 10, 10));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        panelIzquierdo.add(new JLabel("ID:"));
        txtId = new JTextField();
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

        // ===== Tabla =====
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Código", "Nombre", "Densidad", "Descripción"}, 0);
        tablaEspecies = new JTable(modeloTabla);
        tablaEspecies.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaEspecies.setRowHeight(25);
        tablaEspecies.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        add(new JScrollPane(tablaEspecies), BorderLayout.CENTER);

        // ===== Panel de botones =====
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnGuardar = new JButton("Guardar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnVolver = new JButton("Volver");
        btnLimpiar = new JButton("Limpiar");

        configurarBoton(btnGuardar);
        configurarBoton(btnEditar);
        configurarBoton(btnEliminar);
        configurarBoton(btnVolver);
        configurarBoton(btnLimpiar);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH);

        // ===== Eventos =====
        btnGuardar.addActionListener(e -> guardarEspecie());
        btnEditar.addActionListener(e -> editarEspecie());
        btnEliminar.addActionListener(e -> eliminarEspecie());
        btnVolver.addActionListener(e -> dispose());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        tablaEspecies.getSelectionModel().addListSelectionListener(e -> cargarSeleccion());
    }

    private void configurarBoton(JButton boton) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(new Color(41, 128, 185));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(31, 97, 141));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(41, 128, 185));
            }
        });
    }

    // ===== Funciones CRUD =====
    private void guardarEspecie() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String codigo = txtCodigo.getText();
            String nombre = txtNombre.getText();
            float densidad = Float.parseFloat(txtDensidad.getText());
            String descripcion = txtDescripcion.getText();

            EspecieVegetal especie = new EspecieVegetal(id, codigo, nombre, densidad, descripcion);
            listaEspecies.add(especie);
            modeloTabla.addRow(new Object[]{id, codigo, nombre, densidad, descripcion});
            guardarEnArchivo();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "✅ Especie guardada con éxito.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Verifica los campos numéricos (ID y Densidad).");
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

                EspecieVegetal especie = listaEspecies.get(fila);
                especie.setId(id);
                especie.setCodigo(codigo);
                especie.setNombre(nombre);
                especie.setDensidad(densidad);
                especie.setDescripcion(descripcion);

                modeloTabla.setValueAt(id, fila, 0);
                modeloTabla.setValueAt(codigo, fila, 1);
                modeloTabla.setValueAt(nombre, fila, 2);
                modeloTabla.setValueAt(densidad, fila, 3);
                modeloTabla.setValueAt(descripcion, fila, 4);

                guardarEnArchivo();
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
            listaEspecies.remove(fila);
            modeloTabla.removeRow(fila);
            guardarEnArchivo();
            JOptionPane.showMessageDialog(this, "🗑️ Registro eliminado correctamente.");
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

    // ===== Archivo =====
    private void guardarEnArchivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (EspecieVegetal e : listaEspecies) {
                bw.write(e.getId() + "," + e.getCodigo() + "," + e.getNombre() + "," + e.getDensidad() + "," + e.getDescripcion());
                bw.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar en archivo.");
        }
    }

    private void cargarDesdeArchivo() {
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                int id = Integer.parseInt(datos[0]);
                String codigo = datos[1];
                String nombre = datos[2];
                float densidad = Float.parseFloat(datos[3]);
                String descripcion = datos[4];
                EspecieVegetal especie = new EspecieVegetal(id, codigo, nombre, densidad, descripcion);
                listaEspecies.add(especie);
                modeloTabla.addRow(new Object[]{id, codigo, nombre, densidad, descripcion});
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer archivo.");
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

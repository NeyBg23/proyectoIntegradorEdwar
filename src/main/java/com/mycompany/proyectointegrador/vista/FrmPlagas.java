/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.modelo.Plaga;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;


public class FrmPlagas extends JFrame {

    private JTextField txtCodigo, txtNombre, txtDescripcion, txtNivelIncidencia, txtNivelAlerta;
    private JTable tablaPlagas;
    private DefaultTableModel modeloTabla;
    private JButton btnGuardar, btnEditar, btnEliminar, btnLimpiar, btnVolver;
    private ArrayList<Plaga> listaPlagas;

    public FrmPlagas() {
        configurarVentana();
        crearComponentes();
        listaPlagas = new ArrayList<>();
        cargarPlagasDesdeArchivo();
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

        modeloTabla = new DefaultTableModel(new String[]{"Código", "Nombre", "Descripción", "Incidencia", "Alerta"}, 0);
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

        // --- Eventos ---
        btnGuardar.addActionListener(e -> guardarPlaga());
        btnEditar.addActionListener(e -> editarPlaga());
        btnEliminar.addActionListener(e -> eliminarPlaga());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnVolver.addActionListener(e -> {
            new FrmSubmenuInspeccion().setVisible(true);
            dispose();
        });

        // Cargar datos al hacer clic en la tabla
        tablaPlagas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaPlagas.getSelectedRow() != -1) {
                int fila = tablaPlagas.getSelectedRow();
                txtCodigo.setText(modeloTabla.getValueAt(fila, 0).toString());
                txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtDescripcion.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtNivelIncidencia.setText(modeloTabla.getValueAt(fila, 3).toString());
                txtNivelAlerta.setText(modeloTabla.getValueAt(fila, 4).toString());
            }
        });
    }

    private void guardarPlaga() {
        try {
            String codigo = txtCodigo.getText();
            String nombre = txtNombre.getText();
            String descripcion = txtDescripcion.getText();
            float nivelIncidencia = Float.parseFloat(txtNivelIncidencia.getText());
            String nivelAlerta = txtNivelAlerta.getText();

            Plaga plaga = new Plaga(codigo, nombre, descripcion, nivelIncidencia, nivelAlerta);
            listaPlagas.add(plaga);
            modeloTabla.addRow(new Object[]{codigo, nombre, descripcion, nivelIncidencia, nivelAlerta});

            guardarEnArchivo();
            JOptionPane.showMessageDialog(this, "✅ Plaga registrada correctamente.");
            limpiarCampos();

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
            listaPlagas.set(fila, plagaEditada);

            modeloTabla.setValueAt(codigo, fila, 0);
            modeloTabla.setValueAt(nombre, fila, 1);
            modeloTabla.setValueAt(descripcion, fila, 2);
            modeloTabla.setValueAt(nivelIncidencia, fila, 3);
            modeloTabla.setValueAt(nivelAlerta, fila, 4);

            guardarEnArchivo();
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

        int confirmar = JOptionPane.showConfirmDialog(this, "¿Desea eliminar esta plaga?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            listaPlagas.remove(fila);
            modeloTabla.removeRow(fila);
            guardarEnArchivo();
            JOptionPane.showMessageDialog(this, "🗑️ Plaga eliminada correctamente.");
            limpiarCampos();
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

    private void guardarEnArchivo() {
        try (FileWriter writer = new FileWriter("plagas.txt")) {
            for (Plaga plaga : listaPlagas) {
                writer.write(plaga.getCodigo() + "," + plaga.getNombre() + "," + plaga.getDescripcion() + "," +
                        plaga.getNivelIncidencia() + "," + plaga.getNivelAlerta() + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar archivo: " + e.getMessage());
        }
    }

    private void cargarPlagasDesdeArchivo() {
        File archivo = new File("plagas.txt");
        if (!archivo.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 5) {
                    Plaga plaga = new Plaga(datos[0], datos[1], datos[2], Float.parseFloat(datos[3]), datos[4]);
                    listaPlagas.add(plaga);
                    modeloTabla.addRow(new Object[]{datos[0], datos[1], datos[2], datos[3], datos[4]});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las plagas: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmPlagas().setVisible(true));
    }
}
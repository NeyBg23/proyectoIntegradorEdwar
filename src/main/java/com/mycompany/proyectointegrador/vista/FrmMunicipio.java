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
import com.mycompany.proyectointegrador.modelo.Municipio;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class FrmMunicipio extends JFrame {

    private JTextField txtId, txtCodigoDane, txtNombre;
    private JComboBox<String> comboDepartamento;
    private JButton btnGuardar, btnLimpiar, btnSalir;

    private java.util.List<Departamento> listaDepartamentos = new ArrayList<>();

    public FrmMunicipio() {
        setTitle("Gestión de Municipios");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(236, 240, 241));
        setLayout(new BorderLayout(10, 10));

        cargarDepartamentos();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelCampos = new JPanel(new GridLayout(5, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panelCampos.setBackground(new Color(236, 240, 241));

        JLabel lblId = new JLabel("ID:");
        JLabel lblCodigoDane = new JLabel("Código DANE:");
        JLabel lblNombre = new JLabel("Nombre:");
        JLabel lblDepartamento = new JLabel("Departamento:");

        txtId = new JTextField();
        txtCodigoDane = new JTextField();
        txtNombre = new JTextField();
        comboDepartamento = new JComboBox<>();

        // Validación: no se pueden registrar municipios si no hay departamentos
        if (listaDepartamentos.isEmpty()) {
            comboDepartamento.addItem("⚠️ No hay departamentos registrados");
            comboDepartamento.setEnabled(false);
            JOptionPane.showMessageDialog(this,
                    "No se pueden registrar municipios.\nDebe registrar al menos un Departamento primero.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            for (Departamento d : listaDepartamentos) {
                comboDepartamento.addItem(d.getNombre());
            }
        }

        panelCampos.add(lblId);
        panelCampos.add(txtId);
        panelCampos.add(lblCodigoDane);
        panelCampos.add(txtCodigoDane);
        panelCampos.add(lblNombre);
        panelCampos.add(txtNombre);
        panelCampos.add(lblDepartamento);
        panelCampos.add(comboDepartamento);

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(236, 240, 241));

        btnGuardar = new JButton("Guardar");
        btnLimpiar = new JButton("Limpiar");
        btnSalir = new JButton("Salir");

        configurarBoton(btnGuardar);
        configurarBoton(btnLimpiar);
        configurarBoton(btnSalir);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnSalir);

        add(panelCampos, BorderLayout.CENTER);
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
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(new Color(31, 97, 141));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(new Color(41, 128, 185));
            }
        });
    }

    private void agregarEventos() {
        btnGuardar.addActionListener(e -> guardarMunicipio());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnSalir.addActionListener(e -> dispose());
    }

    private void guardarMunicipio() {
        if (listaDepartamentos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe registrar al menos un Departamento antes de crear un Municipio.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText());
            String codigoDane = txtCodigoDane.getText();
            String nombre = txtNombre.getText();
            String nombreDepto = (String) comboDepartamento.getSelectedItem();

            if (codigoDane.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios.",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Departamento deptoSeleccionado = listaDepartamentos.stream()
                    .filter(d -> d.getNombre().equals(nombreDepto))
                    .findFirst().orElse(null);

            Municipio municipio = new Municipio(id, codigoDane, nombre, deptoSeleccionado);
            guardarEnArchivo(municipio);

            JOptionPane.showMessageDialog(this, "Municipio guardado correctamente.");
            limpiarCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "El ID debe ser un número entero.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtCodigoDane.setText("");
        txtNombre.setText("");
        if (!listaDepartamentos.isEmpty()) {
            comboDepartamento.setSelectedIndex(0);
        }
    }

    private void guardarEnArchivo(Municipio municipio) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("municipios.txt", true))) {
            bw.write(municipio.getId() + "," +
                    municipio.getCodigoDane() + "," +
                    municipio.getNombre() + "," +
                    municipio.getDepartamento().getNombre());
            bw.newLine();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDepartamentos() {
        File archivo = new File("departamentos.txt");
        if (!archivo.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 3) {
                    int id = Integer.parseInt(datos[0]);
                    String codigo = datos[1];
                    String nombre = datos[2];
                    listaDepartamentos.add(new Departamento(id, codigo, nombre));
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al leer departamentos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


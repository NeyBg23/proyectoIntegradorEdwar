package com.mycompany.proyectointegradorfitosanitario.vista;

import com.mycompany.proyectointegradorfitosanitario.dao.LugarProduccionDAO;
import com.mycompany.proyectointegradorfitosanitario.dao.PredioDAO;
import com.mycompany.proyectointegradorfitosanitario.modelo.LugarProduccion;
import com.mycompany.proyectointegradorfitosanitario.modelo.Predio;
import com.mycompany.proyectointegradorfitosanitario.modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FrmCrearLugarProduccion extends JFrame {
    private Usuario propietario;
    private LugarProduccionDAO dao;
    private PredioDAO predioDAO;
    private JComboBox<Predio> comboPredios;
    private JComboBox<String> comboTipo;
    
    public FrmCrearLugarProduccion(Usuario usuario) {
        this.propietario = usuario;
        this.dao = new LugarProduccionDAO();
        this.predioDAO = new PredioDAO();
        inicializarComponentes();
        cargarPredios();
    }
    
    private void inicializarComponentes() {
        setTitle("Crear Lugar de Producción");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);  // Más alto para descripción
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(240, 240, 240));

        int y = 20;
        int altura = 30;
        int espacio = 45;

        JLabel lbl1 = new JLabel("Selecciona tu Predio:");
        lbl1.setBounds(20, y, 460, 20);
        panel.add(lbl1);
        comboPredios = new JComboBox<>();
        comboPredios.setBounds(20, y += 25, 460, altura);
        panel.add(comboPredios);

        JLabel lbl2 = new JLabel("Descripción:");
        lbl2.setBounds(20, y += espacio, 460, 20);
        panel.add(lbl2);
        JTextArea txtDescripcion = new JTextArea();
        txtDescripcion.setBounds(20, y += 25, 460, 80);
        txtDescripcion.setLineWrap(true);
        panel.add(txtDescripcion);

        JLabel lbl3 = new JLabel("Tipo de Producción:");
        lbl3.setBounds(20, y += 95, 460, 20);
        panel.add(lbl3);
        comboTipo = new JComboBox<>(new String[]{
            "Hortalizas", "Frutas", "Flores", "Café", "Cacao", "Palma de Aceite", "Otro"
        });
        comboTipo.setBounds(20, y += 25, 460, altura);
        panel.add(comboTipo);

        JButton btnGuardar = new JButton("💾 Guardar");
        btnGuardar.setBounds(100, y += espacio, 150, 35);
        btnGuardar.setBackground(new Color(76, 175, 80));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.addActionListener(e -> guardar(txtDescripcion));
        panel.add(btnGuardar);

        JButton btnCancelar = new JButton("❌ Cancelar");
        btnCancelar.setBounds(260, y, 150, 35);
        btnCancelar.setBackground(new Color(244, 67, 54));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.addActionListener(e -> this.dispose());
        panel.add(btnCancelar);

        setContentPane(panel);
    }
    
    private void cargarPredios() {
        List<Predio> predios = predioDAO.listarPorPropietario(propietario.getId());
        
        if (predios.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No tienes predios registrados.\nCrea un predio primero.", 
                "Sin Predios", 
                JOptionPane.WARNING_MESSAGE);
            this.dispose();
            return;
        }
        
        for (Predio p : predios) {
            comboPredios.addItem(p);
        }
    }
    
    private void guardar(JTextArea txtDescripcion) {
        Predio predioSeleccionado = (Predio) comboPredios.getSelectedItem();

        if (predioSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un predio", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (txtDescripcion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa descripción", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LugarProduccion nuevoLugar = new LugarProduccion(
            predioSeleccionado.getNombre(),
            txtDescripcion.getText().trim(),
            propietario.getId(),
            (String) comboTipo.getSelectedItem()
        );

        if (dao.insertar(nuevoLugar)) {
            JOptionPane.showMessageDialog(this, 
                "✅ Lugar creado: " + predioSeleccionado.getNombre() + "\n" +
                "Fecha: " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "❌ Error al crear lugar", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}

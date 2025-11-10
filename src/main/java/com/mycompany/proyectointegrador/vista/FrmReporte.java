package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Formulario de Reportes
 * Permite generar reportes según el rol del usuario
 * @author edward
 */
public class FrmReporte extends JFrame {
    
    private Usuario usuarioActual;

    // Constructor con Usuario (para sistema con roles)
    public FrmReporte(Usuario usuario) {
        this.usuarioActual = usuario;
        configurarVentana();
        crearComponentes();
    }

    // Constructor sin parámetros (para compatibilidad)
    public FrmReporte() {
        this(null);
    }

    private void configurarVentana() {
        setTitle("Generación de Reportes Fitosanitarios");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambiado de EXIT_ON_CLOSE
        setResizable(false);
    }

    private void crearComponentes() {
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(236, 240, 241));
        setContentPane(contentPane);

        // Header
        JPanel header = new JPanel();
        header.setBackground(new Color(52, 73, 94));
        header.setPreferredSize(new Dimension(0, 70));

        JLabel lblTitulo = new JLabel("Reportes del Sistema", JLabel.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.add(lblTitulo);
        contentPane.add(header, BorderLayout.NORTH);

        // Mostrar usuario si existe
        if(usuarioActual != null) {
            JLabel lblUsuario = new JLabel("Usuario: " + usuarioActual.getNombreUsuario(), JLabel.CENTER);
            lblUsuario.setForeground(Color.LIGHT_GRAY);
            lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            header.add(lblUsuario, BorderLayout.SOUTH);
        }

        // Panel central
        JPanel panel = new JPanel(new GridLayout(3, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(60, 150, 60, 150));
        panel.setBackground(new Color(236, 240, 241));

        JButton btnPredios = new JButton("Reporte de Predios");
        JButton btnInspecciones = new JButton("Reporte de Inspecciones");
        JButton btnVolver = new JButton("Volver");

        configurarBoton(btnPredios, new Color(41, 128, 185));
        configurarBoton(btnInspecciones, new Color(39, 174, 96));
        configurarBoton(btnVolver, new Color(149, 165, 166));

        btnPredios.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Generando reporte de predios...")
        );

        btnInspecciones.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Generando reporte de inspecciones...")
        );

        btnVolver.addActionListener(e -> dispose()); // Solo cerrar esta ventana

        panel.add(btnPredios);
        panel.add(btnInspecciones);
        panel.add(btnVolver);

        contentPane.add(panel, BorderLayout.CENTER);
    }

    private void configurarBoton(JButton boton, Color color) {
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(color.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(color);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmReporte().setVisible(true));
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.proyectointegrador.vista;

/**
 *
 * @author edward
 */





import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FrmPaginaPrincipal extends JFrame {

    public FrmPaginaPrincipal() {
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setTitle("Sistema de Inspección Fitosanitaria");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void crearComponentes() {
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(236, 240, 241));
        setContentPane(contentPane);

        // ======= ENCABEZADO =======
        JPanel header = new JPanel();
        header.setBackground(new Color(44, 62, 80));
        header.setPreferredSize(new Dimension(0, 70));

        JLabel lblTitulo = new JLabel("Sistema de Inspección Fitosanitaria", JLabel.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.add(lblTitulo);
        contentPane.add(header, BorderLayout.NORTH);

        // ======= PANEL PRINCIPAL =======
        JPanel panelCentral = new JPanel(new GridLayout(1, 3, 25, 25));
        panelCentral.setBackground(new Color(236, 240, 241));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));

        // Tarjetas
        panelCentral.add(crearTarjeta("Gestión de Predios",
                "Registra y administra predios y lugares de producción asociados a cada propietario.",
                "🏡",
                new Color(41, 128, 185),
                e -> abrirVentana(new FrmPredio())
        ));

        panelCentral.add(crearTarjeta("Inspecciones",
                "Registra inspecciones fitosanitarias, cultivos inspeccionados y plagas detectadas.",
                "🧪",
                new Color(39, 174, 96),
                e -> abrirVentana(new FrmSubmenuInspeccion())
        ));

        panelCentral.add(crearTarjeta("Reportes",
                "Genera informes y reportes consolidados para el análisis por parte del ICA.",
                "📊",
                new Color(230, 126, 34),
                e -> abrirVentana(new FrmReporte())
        ));

        contentPane.add(panelCentral, BorderLayout.CENTER);
    }

    // ======= MÉTODO PARA CREAR CADA TARJETA =======
    private JPanel crearTarjeta(String titulo, String descripcion, String icono, Color color, ActionListener evento) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Icono y título
        JLabel lblIcono = new JLabel(icono, JLabel.CENTER);
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        JLabel lblTitulo = new JLabel(titulo, JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(52, 73, 94));

        // Descripción
        JLabel lblDescripcion = new JLabel("<html><div style='text-align:center; width:200px;'>" + descripcion + "</div></html>", JLabel.CENTER);
        lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDescripcion.setForeground(new Color(100, 100, 100));

        // Botón
        JButton btnEntrar = new JButton("Entrar");
        configurarBoton(btnEntrar, color);
        btnEntrar.addActionListener(evento);

        // Panel de contenido
        JPanel panelTexto = new JPanel(new GridLayout(3, 1, 5, 5));
        panelTexto.setBackground(Color.WHITE);
        panelTexto.add(lblTitulo);
        panelTexto.add(lblDescripcion);
        panelTexto.add(btnEntrar);

        tarjeta.add(lblIcono, BorderLayout.NORTH);
        tarjeta.add(panelTexto, BorderLayout.CENTER);

        return tarjeta;
    }

    private void configurarBoton(JButton boton, Color color) {
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

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

    private void abrirVentana(JFrame ventana) {
        ventana.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmPaginaPrincipal().setVisible(true));
    }
}


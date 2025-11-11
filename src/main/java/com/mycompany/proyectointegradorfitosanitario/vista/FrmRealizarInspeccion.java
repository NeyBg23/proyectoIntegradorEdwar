/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.vista;

import com.mycompany.proyectointegradorfitosanitario.modelo.Usuario;
import javax.swing.*;
import java.awt.*;

public class FrmRealizarInspeccion extends JFrame {
    private Usuario usuarioLogueado;
    
    public FrmRealizarInspeccion(Usuario usuario) {
        this.usuarioLogueado = usuario;
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Realizar Inspección");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(240, 240, 240));
        
        JLabel lblTitulo = new JLabel("REALIZAR INSPECCIÓN FITOSANITARIA");
        lblTitulo.setBounds(20, 15, 560, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(255, 152, 0));
        panel.add(lblTitulo);
        
        JLabel lblMensaje = new JLabel("Funcionalidad en desarrollo...\nAquí se ingresarán datos de inspección");
        lblMensaje.setBounds(50, 100, 500, 100);
        lblMensaje.setFont(new Font("Arial", Font.PLAIN, 14));
        lblMensaje.setHorizontalAlignment(JLabel.CENTER);
        panel.add(lblMensaje);
        
        JButton btnVolver = new JButton("⬅️ Volver");
        btnVolver.setBounds(450, 350, 130, 30);
        btnVolver.setBackground(new Color(128, 128, 128));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.addActionListener(e -> this.dispose());
        panel.add(btnVolver);
        
        setContentPane(panel);
    }
}

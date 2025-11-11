/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.vista;

import com.mycompany.proyectointegradorfitosanitario.dao.PropietarioDAO;
import com.mycompany.proyectointegradorfitosanitario.dao.UsuarioDAO;
import com.mycompany.proyectointegradorfitosanitario.modelo.Propietario;
import com.mycompany.proyectointegradorfitosanitario.modelo.Usuario;
import javax.swing.*;
import java.awt.*;

/**
 * Pantalla para Crear Propietario + Usuario (juntos)
 * @author Equipo Proyecto Integrador
 * @version 1.0
 */
public class FrmCrearPropietarioConUsuario extends JFrame {
    
    private JPanel panelPrincipal;
    private JTextField txtIdentificacion;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmar;
    private JButton btnGuardar;
    private JButton btnCancelar;
    
    private UsuarioDAO usuarioDAO;
    private PropietarioDAO propietarioDAO;
    private JFrame parentFrame;
    
    public FrmCrearPropietarioConUsuario(JFrame parent) {
        this.parentFrame = parent;
        this.usuarioDAO = new UsuarioDAO();
        this.propietarioDAO = new PropietarioDAO();
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Crear Propietario + Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        panelPrincipal = new JPanel(null);
        panelPrincipal.setBackground(new Color(240, 240, 240));
        
        // ===== TÍTULO =====
        JLabel lblTitulo = new JLabel("CREAR PROPIETARIO + USUARIO");
        lblTitulo.setBounds(20, 15, 460, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(0, 128, 96)); // Verde
        panelPrincipal.add(lblTitulo);
        
        // ===== SECCIÓN 1: DATOS DEL PROPIETARIO =====
        JLabel lblSeccion1 = new JLabel("DATOS DEL PROPIETARIO:");
        lblSeccion1.setBounds(20, 50, 250, 20);
        lblSeccion1.setFont(new Font("Arial", Font.BOLD, 12));
        panelPrincipal.add(lblSeccion1);
        
        JLabel lblIdentificacion = new JLabel("Identificación:");
        lblIdentificacion.setBounds(20, 75, 150, 20);
        panelPrincipal.add(lblIdentificacion);
        txtIdentificacion = new JTextField();
        txtIdentificacion.setBounds(20, 95, 460, 30);
        panelPrincipal.add(txtIdentificacion);
        
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 130, 150, 20);
        panelPrincipal.add(lblNombre);
        txtNombre = new JTextField();
        txtNombre.setBounds(20, 150, 460, 30);
        panelPrincipal.add(txtNombre);
        
        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(20, 185, 150, 20);
        panelPrincipal.add(lblTelefono);
        txtTelefono = new JTextField();
        txtTelefono.setBounds(20, 205, 460, 30);
        panelPrincipal.add(txtTelefono);
        
        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(20, 240, 150, 20);
        panelPrincipal.add(lblCorreo);
        txtCorreo = new JTextField();
        txtCorreo.setBounds(20, 260, 460, 30);
        panelPrincipal.add(txtCorreo);
        
        // ===== SECCIÓN 2: DATOS DE USUARIO =====
        JLabel lblSeccion2 = new JLabel("CREAR USUARIO PARA LOGIN:");
        lblSeccion2.setBounds(20, 300, 250, 20);
        lblSeccion2.setFont(new Font("Arial", Font.BOLD, 12));
        panelPrincipal.add(lblSeccion2);
        
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(20, 325, 150, 20);
        panelPrincipal.add(lblUsername);
        txtUsername = new JTextField();
        txtUsername.setBounds(20, 345, 460, 30);
        panelPrincipal.add(txtUsername);
        
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(20, 380, 150, 20);
        panelPrincipal.add(lblPassword);
        txtPassword = new JPasswordField();
        txtPassword.setBounds(20, 400, 460, 30);
        panelPrincipal.add(txtPassword);
        
        JLabel lblConfirmar = new JLabel("Confirmar Contraseña:");
        lblConfirmar.setBounds(20, 435, 150, 20);
        panelPrincipal.add(lblConfirmar);
        txtConfirmar = new JPasswordField();
        txtConfirmar.setBounds(20, 455, 460, 30);
        panelPrincipal.add(txtConfirmar);
        
        // ===== BOTONES =====
        btnGuardar = new JButton("💾 Guardar");
        btnGuardar.setBounds(80, 510, 150, 35);
        btnGuardar.setBackground(new Color(76, 175, 80));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 12));
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.addActionListener(e -> guardarPropietarioYUsuario());
        panelPrincipal.add(btnGuardar);
        
        btnCancelar = new JButton("❌ Cancelar");
        btnCancelar.setBounds(270, 510, 150, 35);
        btnCancelar.setBackground(new Color(244, 67, 54));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(e -> this.dispose());
        panelPrincipal.add(btnCancelar);
        
        setContentPane(panelPrincipal);
    }
    
    private void guardarPropietarioYUsuario() {
        if (txtIdentificacion.getText().trim().isEmpty() ||
            txtNombre.getText().trim().isEmpty() ||
            txtUsername.getText().trim().isEmpty() ||
            new String(txtPassword.getPassword()).isEmpty()) {
            
            JOptionPane.showMessageDialog(this, 
                "❌ Completa todos los campos obligatorios",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String password = new String(txtPassword.getPassword());
        String confirmar = new String(txtConfirmar.getPassword());
        
        if (!password.equals(confirmar)) {
            JOptionPane.showMessageDialog(this,
                "❌ Las contraseñas no coinciden",
                "Error", JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            txtConfirmar.setText("");
            return;
        }
        
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this,
                "❌ La contraseña debe tener mínimo 6 caracteres",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Usuario usuarioExistente = usuarioDAO.buscarPorUsername(txtUsername.getText().trim());
        if (usuarioExistente != null) {
            JOptionPane.showMessageDialog(this,
                "❌ Este username ya existe",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // 1. CREAR USUARIO
            Usuario nuevoUsuario = new Usuario(
                txtUsername.getText().trim(),
                password,
                "PROPIETARIO",
                txtCorreo.getText().trim()
            );
            
            if (!usuarioDAO.insertar(nuevoUsuario)) {
                JOptionPane.showMessageDialog(this,
                    "❌ Error al crear usuario",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            System.out.println("✅ Usuario creado: " + txtUsername.getText());
            
            // 2. BUSCAR EL USUARIO RECIÉN CREADO
            Usuario usuarioCreado = usuarioDAO.buscarPorUsername(txtUsername.getText().trim());
            
            if (usuarioCreado == null) {
                JOptionPane.showMessageDialog(this,
                    "❌ Error al recuperar usuario creado",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            System.out.println("✅ Usuario recuperado con ID: " + usuarioCreado.getId());
            
            // 3. CREAR PROPIETARIO (VINCULADO AL USUARIO)
            Propietario nuevoPropietario = new Propietario(
                txtIdentificacion.getText().trim(),
                txtNombre.getText().trim(),
                txtTelefono.getText().trim(),
                txtCorreo.getText().trim()
            );
            nuevoPropietario.setId(usuarioCreado.getId()); // ← VINCULACIÓN
            
            if (!propietarioDAO.insertar(nuevoPropietario)) {
                JOptionPane.showMessageDialog(this,
                    "❌ Error al crear propietario",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            System.out.println("✅ Propietario creado y vinculado al usuario");
            
            // 4. ÉXITO
            JOptionPane.showMessageDialog(this,
                "✅ Propietario y Usuario creados correctamente\n\n" +
                "Username: " + txtUsername.getText() + "\n" +
                "Rol: PROPIETARIO\n" +
                "Nombre: " + txtNombre.getText(),
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            this.dispose();
            
        } catch (Exception ex) {
            System.err.println("❌ Error: " + ex.getMessage());
            JOptionPane.showMessageDialog(this,
                "❌ Error inesperado: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegrador.vista;

import com.mycompany.proyectointegrador.dao.UsuarioDAO;
import com.mycompany.proyectointegrador.modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import com.mycompany.proyectointegrador.vista.FrmMenuAsistente;


/**
 * Formulario de Login
 * Pantalla principal donde los usuarios se autentican
 * Soporta roles: admin, propietario, asistente
 */
public class FrmLogin extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnEntrar;
    private JButton btnSalir;
    private JLabel lblTitulo;
    private JLabel lblEstado;
    private UsuarioDAO usuarioDAO;
    private Usuario usuarioAutenticado; // Guarda el usuario que se autentica

    public FrmLogin() {
        // Configuración de la ventana
        setTitle("Sistema Fitosanitario - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null); // Centrar pantalla
        setResizable(false);
        

        // Inicializar DAO
        usuarioDAO = new UsuarioDAO();

        // Panel principal con fondo
        JPanel pnlPrincipal = new JPanel();
        pnlPrincipal.setLayout(null);
        pnlPrincipal.setBackground(new Color(240, 240, 240));

        // Título
        lblTitulo = new JLabel("INSPECCIÓN FITOSANITARIA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBounds(80, 20, 300, 30);
        pnlPrincipal.add(lblTitulo);

        // Label Subtítulo
        JLabel lblSubtitulo = new JLabel("Ingrese sus credenciales");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSubtitulo.setBounds(100, 50, 250, 20);
        pnlPrincipal.add(lblSubtitulo);

        // Label Usuario
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
        lblUsuario.setBounds(80, 90, 100, 20);
        pnlPrincipal.add(lblUsuario);

        // TextField Usuario
        txtUsuario = new JTextField();
        txtUsuario.setBounds(80, 115, 280, 30);
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
        pnlPrincipal.add(txtUsuario);

        // Label Contraseña
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setFont(new Font("Arial", Font.PLAIN, 12));
        lblContrasena.setBounds(80, 155, 100, 20);
        pnlPrincipal.add(lblContrasena);

        // PasswordField Contraseña
        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(80, 180, 280, 30);
        txtContrasena.setFont(new Font("Arial", Font.PLAIN, 12));
        pnlPrincipal.add(txtContrasena);

        // Label de Estado (mensajes de error/éxito)
        lblEstado = new JLabel("");
        lblEstado.setFont(new Font("Arial", Font.PLAIN, 11));
        lblEstado.setForeground(Color.RED);
        lblEstado.setBounds(80, 220, 280, 20);
        pnlPrincipal.add(lblEstado);

        // Botón Entrar
        btnEntrar = new JButton("Entrar");
        btnEntrar.setBounds(80, 260, 130, 35);
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEntrar.setBackground(new Color(46, 125, 50)); // Verde
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false);
        btnEntrar.addActionListener(this::autenticar);
        pnlPrincipal.add(btnEntrar);

        // Botón Salir
        btnSalir = new JButton("Salir");
        btnSalir.setBounds(230, 260, 130, 35);
        btnSalir.setFont(new Font("Arial", Font.BOLD, 12));
        btnSalir.setBackground(new Color(211, 47, 47)); // Rojo
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.addActionListener(e -> System.exit(0));
        pnlPrincipal.add(btnSalir);

        add(pnlPrincipal);
    }

    /**
     * Método de autenticación
     * Valida usuario y contraseña contra la base de datos
     */
/**
 * Método que autentica al usuario y abre el menú correspondiente
 */
private void autenticar(ActionEvent e) {
    String usuario = txtUsuario.getText().trim();
    String contrasena = new String(txtContrasena.getPassword());

    // Validación de campos vacíos
    if (usuario.isEmpty() || contrasena.isEmpty()) {
        lblEstado.setText("Error: Por favor completa todos los campos");
        lblEstado.setForeground(Color.RED);
        return;
    }

    try {
        // Intentar login
        usuarioAutenticado = usuarioDAO.login(usuario, contrasena);

        if (usuarioAutenticado != null) {
            // Login exitoso
            lblEstado.setText("✓ Autenticación exitosa...");
            lblEstado.setForeground(new Color(46, 125, 50));
            
            // Abrir pantalla según rol
            Thread.sleep(500); // Pequeña pausa para feedback visual
            abrirMenuSegunRol();
            
        } else {
            // Login fallido
            lblEstado.setText("Error: Usuario o contraseña inválidos");
            lblEstado.setForeground(Color.RED);
            txtContrasena.setText("");
        }

    } catch (SQLException ex) {
        lblEstado.setText("Error de base de datos: " + ex.getMessage());
        lblEstado.setForeground(Color.RED);
        System.err.println("Error SQL en login: " + ex.getMessage());
    } catch (InterruptedException ex) {
        Thread.currentThread().interrupt();
    }
}

    /**
     * Abre el menú correspondiente según el rol del usuario autenticado
     */
    private void abrirMenuSegunRol() {
        if (usuarioAutenticado == null) {
            JOptionPane.showMessageDialog(this, 
                "Error: No hay usuario autenticado", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        String rol = usuarioAutenticado.getRol().toUpperCase();

        switch (rol) {
            case "ADMIN":
                // Abrir menú de administrador
                FrmMenu menuAdmin = new FrmMenu(usuarioAutenticado);
                menuAdmin.setVisible(true);
                break;

            case "ASISTENTE":
                // Abrir menú de asistente técnico
                FrmMenuAsistente menuAsistente = new FrmMenuAsistente(usuarioAutenticado);
                menuAsistente.setVisible(true);
                break;

            case "PROPIETARIO":
                // Abrir menú de propietario (si lo tienes implementado)
                JOptionPane.showMessageDialog(this, 
                    "Menú de propietario en desarrollo", 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
                // FrmMenuPropietario menuPropietario = new FrmMenuPropietario(usuarioAutenticado);
                // menuPropietario.setVisible(true);
                break;

            default:
                JOptionPane.showMessageDialog(this, 
                    "Rol no reconocido: " + rol, 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
        }

        // Cerrar ventana de login después de abrir el menú
        this.dispose();
    }




    /**
     * Getter para el usuario autenticado
     */
    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    /**
     * Método main para ejecutar la aplicación
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrmLogin login = new FrmLogin();
            login.setVisible(true);
        });
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectointegradorfitosanitario.vista;

import com.mycompany.proyectointegradorfitosanitario.dao.AsistenteTecnicoDAO;
import com.mycompany.proyectointegradorfitosanitario.dao.UsuarioDAO;
import com.mycompany.proyectointegradorfitosanitario.modelo.AsistenteTecnico;
import com.mycompany.proyectointegradorfitosanitario.modelo.Usuario;
import javax.swing.*;
import java.awt.*;

/**
 * Pantalla para Crear Asistente Técnico + Usuario (juntos)
 * @author Equipo Proyecto Integrador
 * @version 1.0
 */
public class FrmCrearAsistenteConUsuario extends JFrame {
    
    private JPanel panelPrincipal;
    private JTextField txtIdentificacion;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JTextField txtTarjeta;
    private JComboBox<String> comboTipo;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmar;
    private JButton btnGuardar;
    private JButton btnCancelar;
    private JLabel lblMensaje;
    
    private UsuarioDAO usuarioDAO;
    private AsistenteTecnicoDAO asistenteDAO;
    private JFrame parentFrame;
    
    /**
     * Constructor
     */
    public FrmCrearAsistenteConUsuario(JFrame parent) {
        this.parentFrame = parent;
        this.usuarioDAO = new UsuarioDAO();
        this.asistenteDAO = new AsistenteTecnicoDAO();
        inicializarComponentes();
    }
    
    /**
     * Inicializa los componentes
     */
    private void inicializarComponentes() {
        setTitle("Crear Asistente Técnico + Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        
        panelPrincipal = new JPanel(null);
        panelPrincipal.setBackground(new Color(240, 240, 240));
        
        // ===== TÍTULO =====
        JLabel lblTitulo = new JLabel("CREAR ASISTENTE + USUARIO");
        lblTitulo.setBounds(20, 15, 460, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(0, 102, 204));
        panelPrincipal.add(lblTitulo);
        
        // ===== SECCIÓN 1: DATOS DEL ASISTENTE =====
        JLabel lblSeccion1 = new JLabel("DATOS DEL ASISTENTE:");
        lblSeccion1.setBounds(20, 50, 200, 20);
        lblSeccion1.setFont(new Font("Arial", Font.BOLD, 12));
        panelPrincipal.add(lblSeccion1);
        
        // Identificación
        JLabel lblIdentificacion = new JLabel("Identificación:");
        lblIdentificacion.setBounds(20, 75, 150, 20);
        panelPrincipal.add(lblIdentificacion);
        txtIdentificacion = new JTextField();
        txtIdentificacion.setBounds(20, 95, 460, 30);
        panelPrincipal.add(txtIdentificacion);
        
        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 130, 150, 20);
        panelPrincipal.add(lblNombre);
        txtNombre = new JTextField();
        txtNombre.setBounds(20, 150, 460, 30);
        panelPrincipal.add(txtNombre);
        
        // Teléfono
        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(20, 185, 150, 20);
        panelPrincipal.add(lblTelefono);
        txtTelefono = new JTextField();
        txtTelefono.setBounds(20, 205, 460, 30);
        panelPrincipal.add(txtTelefono);
        
        // Correo
        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(20, 240, 150, 20);
        panelPrincipal.add(lblCorreo);
        txtCorreo = new JTextField();
        txtCorreo.setBounds(20, 260, 460, 30);
        panelPrincipal.add(txtCorreo);
        
        // Tarjeta Profesional
        JLabel lblTarjeta = new JLabel("Tarjeta Profesional:");
        lblTarjeta.setBounds(20, 295, 150, 20);
        panelPrincipal.add(lblTarjeta);
        txtTarjeta = new JTextField();
        txtTarjeta.setBounds(20, 315, 460, 30);
        panelPrincipal.add(txtTarjeta);
        
        // Tipo
        JLabel lblTipo = new JLabel("Tipo:");
        lblTipo.setBounds(20, 350, 150, 20);
        panelPrincipal.add(lblTipo);
        comboTipo = new JComboBox<>(new String[]{"OFICIAL", "PARTICULAR"});
        comboTipo.setBounds(20, 370, 460, 30);
        panelPrincipal.add(comboTipo);
        
        // ===== SECCIÓN 2: DATOS DE USUARIO =====
        JLabel lblSeccion2 = new JLabel("CREAR USUARIO PARA LOGIN:");
        lblSeccion2.setBounds(20, 410, 250, 20);
        lblSeccion2.setFont(new Font("Arial", Font.BOLD, 12));
        panelPrincipal.add(lblSeccion2);
        
        // Username
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(20, 435, 150, 20);
        panelPrincipal.add(lblUsername);
        txtUsername = new JTextField();
        txtUsername.setBounds(20, 455, 460, 30);
        panelPrincipal.add(txtUsername);
        
        // Password
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(20, 490, 150, 20);
        panelPrincipal.add(lblPassword);
        txtPassword = new JPasswordField();
        txtPassword.setBounds(20, 510, 460, 30);
        panelPrincipal.add(txtPassword);
        
        // Confirmar Password
        JLabel lblConfirmar = new JLabel("Confirmar Contraseña:");
        lblConfirmar.setBounds(20, 545, 150, 20);
        panelPrincipal.add(lblConfirmar);
        txtConfirmar = new JPasswordField();
        txtConfirmar.setBounds(20, 565, 460, 30);
        panelPrincipal.add(txtConfirmar);
        
        // ===== BOTONES =====
        btnGuardar = new JButton("💾 Guardar");
        btnGuardar.setBounds(80, 605, 150, 35);
        btnGuardar.setBackground(new Color(76, 175, 80));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 12));
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.addActionListener(e -> guardarAsistenteYUsuario());
        panelPrincipal.add(btnGuardar);
        
        btnCancelar = new JButton("❌ Cancelar");
        btnCancelar.setBounds(270, 605, 150, 35);
        btnCancelar.setBackground(new Color(244, 67, 54));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(e -> this.dispose());
        panelPrincipal.add(btnCancelar);
        
        setContentPane(panelPrincipal);
    }
    
    /**
     * Guarda el asistente y el usuario juntos
     */
    private void guardarAsistenteYUsuario() {
        // Validar campos vacíos
        if (txtIdentificacion.getText().trim().isEmpty() ||
            txtNombre.getText().trim().isEmpty() ||
            txtUsername.getText().trim().isEmpty() ||
            new String(txtPassword.getPassword()).isEmpty()) {
            
            JOptionPane.showMessageDialog(this, 
                "❌ Completa todos los campos obligatorios",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validar que las contraseñas coincidan
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
        
        // Validar que el username no exista
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
                "ASISTENTE",
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
            
            // 3. CREAR ASISTENTE TÉCNICO (VINCULADO AL USUARIO)
            AsistenteTecnico nuevoAsistente = new AsistenteTecnico(
                txtIdentificacion.getText().trim(),
                txtNombre.getText().trim(),
                txtTelefono.getText().trim(),
                txtCorreo.getText().trim(),
                txtTarjeta.getText().trim(),
                (String) comboTipo.getSelectedItem()
            );
            nuevoAsistente.setId(usuarioCreado.getId()); // ← VINCULACIÓN AQUÍ
            
            if (!asistenteDAO.insertar(nuevoAsistente)) {
                JOptionPane.showMessageDialog(this,
                    "❌ Error al crear asistente",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            System.out.println("✅ Asistente creado y vinculado al usuario");
            
            // 4. ÉXITO
            JOptionPane.showMessageDialog(this,
                "✅ Asistente y Usuario creados correctamente\n\n" +
                "Username: " + txtUsername.getText() + "\n" +
                "Rol: ASISTENTE\n" +
                "Tipo: " + comboTipo.getSelectedItem(),
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

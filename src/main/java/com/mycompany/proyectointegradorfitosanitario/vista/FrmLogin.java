package com.mycompany.proyectointegradorfitosanitario.vista;

import com.mycompany.proyectointegradorfitosanitario.dao.UsuarioDAO;
import com.mycompany.proyectointegradorfitosanitario.modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Pantalla de Login del Sistema de Inspección Fitosanitaria
 * @author Equipo Proyecto Integrador
 * @version 2.0
 */
public class FrmLogin extends JFrame {
    
    private JPanel panelPrincipal;
    private JPanel panelHeader;
    private JLabel lblTituloPrincipal;
    private JLabel lblSubtitulo;
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JTextField txtUsuario;
    private JLabel lblContraseña;
    private JPasswordField txtContraseña;
    private JButton btnLogin;
    private JButton btnSalir;
    private JLabel lblMensaje;
    
    private UsuarioDAO usuarioDAO;
    private Usuario usuarioLogueado;
    
    /**
     * Constructor de la ventana de login
     */
    public FrmLogin() {
        usuarioDAO = new UsuarioDAO();
        inicializarComponentes();
    }
    
    /**
     * Inicializa todos los componentes de la interfaz gráfica
     */
    private void inicializarComponentes() {
        // Configuración de la ventana
        setTitle("Login - Sistema de Inspección Fitosanitaria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel principal
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);
        panelPrincipal.setBackground(new Color(240, 240, 240));
        
        // ===== PANEL HEADER CON NOMBRE DEL PROYECTO =====
        panelHeader = new JPanel();
        panelHeader.setBounds(0, 0, 500, 80);
        panelHeader.setBackground(new Color(33, 150, 243)); // Azul profesional
        panelHeader.setLayout(null);
        
        lblTituloPrincipal = new JLabel("SISTEMA DE INSPECCIÓN FITOSANITARIA");
        lblTituloPrincipal.setBounds(10, 10, 480, 30);
        lblTituloPrincipal.setFont(new Font("Arial", Font.BOLD, 16));
        lblTituloPrincipal.setForeground(Color.WHITE);
        lblTituloPrincipal.setHorizontalAlignment(JLabel.CENTER);
        panelHeader.add(lblTituloPrincipal);
        
        lblSubtitulo = new JLabel("Proyecto Integrador - Cuarto Semestre");
        lblSubtitulo.setBounds(10, 40, 480, 20);
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 12));
        lblSubtitulo.setForeground(new Color(220, 220, 220));
        lblSubtitulo.setHorizontalAlignment(JLabel.CENTER);
        panelHeader.add(lblSubtitulo);
        
        panelPrincipal.add(panelHeader);
        
        // Título de Login
        lblTitulo = new JLabel("INICIAR SESIÓN");
        lblTitulo.setBounds(50, 110, 400, 40);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        lblTitulo.setForeground(new Color(33, 150, 243));
        panelPrincipal.add(lblTitulo);
        
        // Label Usuario
        lblUsuario = new JLabel("👤 Usuario:");
        lblUsuario.setBounds(50, 170, 100, 25);
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        panelPrincipal.add(lblUsuario);
        
        // TextField Usuario
        txtUsuario = new JTextField();
        txtUsuario.setBounds(50, 200, 400, 35);
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        txtUsuario.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 2));
        txtUsuario.setBackground(Color.WHITE);
        panelPrincipal.add(txtUsuario);
        
        // Label Contraseña
        lblContraseña = new JLabel("🔐 Contraseña:");
        lblContraseña.setBounds(50, 245, 150, 25);
        lblContraseña.setFont(new Font("Arial", Font.PLAIN, 14));
        panelPrincipal.add(lblContraseña);
        
        // PasswordField Contraseña
        txtContraseña = new JPasswordField();
        txtContraseña.setBounds(50, 275, 400, 35);
        txtContraseña.setFont(new Font("Arial", Font.PLAIN, 14));
        txtContraseña.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 2));
        txtContraseña.setBackground(Color.WHITE);
        panelPrincipal.add(txtContraseña);
        
        // Label de mensaje
        lblMensaje = new JLabel("");
        lblMensaje.setBounds(50, 310, 400, 25);
        lblMensaje.setFont(new Font("Arial", Font.PLAIN, 12));
        lblMensaje.setForeground(Color.RED);
        lblMensaje.setHorizontalAlignment(JLabel.CENTER);
        panelPrincipal.add(lblMensaje);
        
        // Botón Login
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBounds(50, 350, 190, 40);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 13));
        btnLogin.setBackground(new Color(76, 175, 80)); // Verde
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setBorder(BorderFactory.createLineBorder(new Color(56, 142, 60), 2));
        btnLogin.addActionListener(this::btnLoginActionPerformed);
        panelPrincipal.add(btnLogin);
        
        // Botón Salir
        btnSalir = new JButton("Salir");
        btnSalir.setBounds(260, 350, 190, 40);
        btnSalir.setFont(new Font("Arial", Font.BOLD, 13));
        btnSalir.setBackground(new Color(244, 67, 54)); // Rojo
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.setBorder(BorderFactory.createLineBorder(new Color(211, 47, 47), 2));
        btnSalir.addActionListener(e -> System.exit(0));
        panelPrincipal.add(btnSalir);
        
        // Agregar panel a la ventana
        setContentPane(panelPrincipal);
    }
    
    /**
     * Acción del botón Iniciar Sesión
     */
    private void btnLoginActionPerformed(ActionEvent e) {
        String usuario = txtUsuario.getText().trim();
        String contraseña = new String(txtContraseña.getPassword());
        
        // Validar que los campos no estén vacíos
        if (usuario.isEmpty() || contraseña.isEmpty()) {
            lblMensaje.setText("❌ Por favor ingresa usuario y contraseña");
            lblMensaje.setForeground(Color.RED);
            return;
        }
        
        // Intentar login
        System.out.println("🔐 Intentando login con usuario: " + usuario);
        usuarioLogueado = usuarioDAO.login(usuario, contraseña);
        
        if (usuarioLogueado != null) {
            // Login exitoso
            lblMensaje.setText("✅ ¡Login exitoso! Bienvenido " + usuarioLogueado.getUsername());
            lblMensaje.setForeground(new Color(76, 175, 80));
            
            System.out.println("\n✅ LOGIN EXITOSO");
            System.out.println("   Usuario: " + usuarioLogueado.getUsername());
            System.out.println("   Rol: " + usuarioLogueado.getRol());
            System.out.println("   Email: " + usuarioLogueado.getEmail());
            
            // Mostrar mensaje de bienvenida con ícono según rol
            String icono = "ℹ️";
            if (usuarioLogueado.getRol().equals("ADMIN")) {
                icono = "👨‍💼";
            } else if (usuarioLogueado.getRol().equals("PROPIETARIO")) {
                icono = "🌾";
            } else if (usuarioLogueado.getRol().equals("ASISTENTE")) {
                icono = "🔧";
            }
            
            JOptionPane.showMessageDialog(this, 
                icono + " ¡Bienvenido " + usuarioLogueado.getUsername() + "!\n" +
                "Rol: " + usuarioLogueado.getRol(),
                "✅ Login Exitoso",
                JOptionPane.INFORMATION_MESSAGE);
            
            // TODO: Aquí abrir ventana principal según rol
            abrirMenuPrincipal(usuarioLogueado);
            
            // Limpiar campos
            txtUsuario.setText("");
            txtContraseña.setText("");
            
        } else {
            // Login fallido
            lblMensaje.setText("❌ Usuario o contraseña incorrectos");
            lblMensaje.setForeground(Color.RED);
            txtContraseña.setText("");
            txtContraseña.requestFocus();
            
            System.out.println("❌ LOGIN FALLIDO");
        }
    }
    
    /**
     * Abre el menú principal según el rol del usuario
     * @param usuario Usuario logueado
     */
    private void abrirMenuPrincipal(Usuario usuario) {
        String rol = usuario.getRol();
        
        System.out.println("\n🎨 Abriendo menú para rol: " + rol);
        
        // TODO: Reemplazar con las ventanas reales cuando estén creadas
        switch (rol) {
            case "ADMIN":
                System.out.println("   → Color: AZUL (Administrador)");
                this.dispose(); // Cierra login
                SwingUtilities.invokeLater(() -> {
                    FrmMenuAdmin menuAdmin = new FrmMenuAdmin(usuario);
                    menuAdmin.setVisible(true);
                });
                break;


                
            case "PROPIETARIO":
                System.out.println("   → Color: VERDE (Propietario)");
                JOptionPane.showMessageDialog(this, 
                    "Próximamente: Panel de Propietario\n(Interfaz VERDE)",
                    "Menú Propietario",
                    JOptionPane.INFORMATION_MESSAGE);
                break;
                
            case "ASISTENTE":
                System.out.println("   → Color: NARANJA (Asistente)");
                this.dispose();
                SwingUtilities.invokeLater(() -> {
                    FrmMenuAsistente menuAsistente = new FrmMenuAsistente(usuario);
                    menuAsistente.setVisible(true);
                });
                break;

        }
    }
    
    /**
     * Main para ejecutar la ventana de login
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FrmLogin frame = new FrmLogin();
            frame.setVisible(true);
        });
    }
}

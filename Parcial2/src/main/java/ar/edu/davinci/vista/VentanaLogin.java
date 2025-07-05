package ar.edu.davinci.vista;

import ar.edu.davinci.controlador.LoginControlador;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana de login del sistema.
 */
public class VentanaLogin extends JFrame {
    private final LoginControlador controlador = new LoginControlador();
    private JTextField txtEmail;
    private JPasswordField txtContrasena;
    
    public VentanaLogin() {
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Sistema de Adopción - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Email
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        add(txtEmail, gbc);
        
        // Contraseña
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Contraseña:"), gbc);
        
        gbc.gridx = 1;
        txtContrasena = new JPasswordField(20);
        add(txtContrasena, gbc);
        
        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnLogin = new JButton("Iniciar Sesión");
        btnLogin.addActionListener(e -> procesarLogin());
        panelBotones.add(btnLogin);
        
        JButton btnRegistrar = new JButton("Registrarse");
        btnRegistrar.addActionListener(e -> mostrarVentanaRegistro());
        panelBotones.add(btnRegistrar);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(panelBotones, gbc);
        
        // Info para pruebas
        gbc.gridy = 3;
        JLabel lblInfo = new JLabel("Usuario de prueba: admin@veterinaria.com / admin123");
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 10));
        add(lblInfo, gbc);
        
        pack();
        setLocationRelativeTo(null);
        
        // Enter para login
        getRootPane().setDefaultButton(btnLogin);
    }
    
    private void procesarLogin() {
        String email = txtEmail.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());
        
        if (email.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Complete todos los campos", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (controlador.iniciarSesion(email, contrasena)) {
            new VentanaPrincipal().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Credenciales incorrectas", 
                "Error", JOptionPane.ERROR_MESSAGE);
            txtContrasena.setText("");
        }
    }
    
    private void mostrarVentanaRegistro() {
        new VentanaRegistro().setVisible(true);
    }
}
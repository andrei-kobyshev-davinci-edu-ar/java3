package ar.edu.davinci.vista;

import ar.edu.davinci.controlador.LoginControlador;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana de registro de empleados.
 */
public class VentanaRegistro extends JFrame {
    private final LoginControlador controlador = new LoginControlador();
    private JTextField txtNombre;
    private JTextField txtEmail;
    private JPasswordField txtContrasena;
    private JPasswordField txtConfirmar;
    
    public VentanaRegistro() {
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Registro de Empleado");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nombre:"), gbc);
        
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        add(txtNombre, gbc);
        
        // Email
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        add(txtEmail, gbc);
        
        // Contraseña
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Contraseña:"), gbc);
        
        gbc.gridx = 1;
        txtContrasena = new JPasswordField(20);
        add(txtContrasena, gbc);
        
        // Confirmar contraseña
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Confirmar:"), gbc);
        
        gbc.gridx = 1;
        txtConfirmar = new JPasswordField(20);
        add(txtConfirmar, gbc);
        
        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> procesarRegistro());
        panelBotones.add(btnRegistrar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnCancelar);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(panelBotones, gbc);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void procesarRegistro() {
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());
        String confirmar = new String(txtConfirmar.getPassword());
        
        if (nombre.isEmpty() || email.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Complete todos los campos", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!contrasena.equals(confirmar)) {
            JOptionPane.showMessageDialog(this, 
                "Las contraseñas no coinciden", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String resultado = controlador.registrarEmpleado(nombre, email, contrasena);
        
        if (resultado.equals("Registro exitoso")) {
            JOptionPane.showMessageDialog(this, resultado, 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, resultado, 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
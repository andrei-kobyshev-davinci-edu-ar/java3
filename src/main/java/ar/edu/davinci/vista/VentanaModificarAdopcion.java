package ar.edu.davinci.vista;

import ar.edu.davinci.controlador.AdopcionControlador;
import ar.edu.davinci.modelo.*;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana para modificar los datos de una adopción existente.
 */
public class VentanaModificarAdopcion extends JFrame {
    private final Adopcion adopcionOriginal;
    private final AdopcionControlador controlador = new AdopcionControlador();
    
    // Campos editables del adoptante
    private JTextField txtNombre;
    private JSpinner spnEdad;
    private JTextField txtDireccion;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    
    public VentanaModificarAdopcion(Adopcion adopcion) {
        this.adopcionOriginal = adopcion;
        inicializarComponentes();
        cargarDatos();
    }
    
    private void inicializarComponentes() {
        setTitle("Modificar Adopción - ID: " + adopcionOriginal.getId());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Información no editable
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel lblInfo = new JLabel("Modificar datos del adoptante");
        lblInfo.setFont(lblInfo.getFont().deriveFont(Font.BOLD, 14));
        panelPrincipal.add(lblInfo, gbc);
        
        gbc.gridwidth = 1;
        
        // Mascota (no editable)
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelPrincipal.add(new JLabel("Mascota:"), gbc);
        
        gbc.gridx = 1;
        JLabel lblMascota = new JLabel(
            adopcionOriginal.getMascota().getNombre() + " (" + 
            adopcionOriginal.getMascota().getTipo() + ")");
        lblMascota.setForeground(Color.GRAY);
        panelPrincipal.add(lblMascota, gbc);
        
        // Empleado (no editable)
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelPrincipal.add(new JLabel("Empleado:"), gbc);
        
        gbc.gridx = 1;
        JLabel lblEmpleado = new JLabel(adopcionOriginal.getEmpleado().getNombre());
        lblEmpleado.setForeground(Color.GRAY);
        panelPrincipal.add(lblEmpleado, gbc);
        
        // Separador
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelPrincipal.add(new JSeparator(), gbc);
        
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        
        // Campos editables del adoptante
        gbc.gridx = 0;
        gbc.gridy = 4;
        panelPrincipal.add(new JLabel("Nombre del adoptante:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtNombre = new JTextField(20);
        panelPrincipal.add(txtNombre, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        panelPrincipal.add(new JLabel("Edad:"), gbc);
        
        gbc.gridx = 1;
        spnEdad = new JSpinner(new SpinnerNumberModel(25, 18, 100, 1));
        panelPrincipal.add(spnEdad, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        panelPrincipal.add(new JLabel("Dirección:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtDireccion = new JTextField(20);
        panelPrincipal.add(txtDireccion, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.NONE;
        panelPrincipal.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtEmail = new JTextField(20);
        panelPrincipal.add(txtEmail, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.NONE;
        panelPrincipal.add(new JLabel("Teléfono:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtTelefono = new JTextField(20);
        panelPrincipal.add(txtTelefono, gbc);
        
        add(new JScrollPane(panelPrincipal), BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        
        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.addActionListener(e -> guardarCambios());
        panelBotones.add(btnGuardar);
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnCancelar);
        
        add(panelBotones, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
    }
    
    private void cargarDatos() {
        Adoptante adoptante = adopcionOriginal.getAdoptante();
        txtNombre.setText(adoptante.getNombre());
        spnEdad.setValue(adoptante.getEdad());
        txtDireccion.setText(adoptante.getDireccion());
        
        if (adoptante.getEmail() != null) {
            txtEmail.setText(adoptante.getEmail());
        }
        
        if (adoptante.getTelefono() != null) {
            txtTelefono.setText(adoptante.getTelefono());
        }
    }
    
    private void guardarCambios() {
        String nombre = txtNombre.getText().trim();
        String direccion = txtDireccion.getText().trim();
        
        if (nombre.isEmpty() || direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "El nombre y la dirección son campos obligatorios",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Actualizar los datos del adoptante
        Adoptante adoptante = adopcionOriginal.getAdoptante();
        adoptante.setNombre(nombre);
        adoptante.setEdad((Integer) spnEdad.getValue());
        adoptante.setDireccion(direccion);
        adoptante.setEmail(txtEmail.getText().trim());
        adoptante.setTelefono(txtTelefono.getText().trim());
        
        // Actualizar la adopción
        AdopcionControlador.ResultadoAdopcion resultado = 
            controlador.actualizarAdopcion(adopcionOriginal);
        
        if (resultado.isExitoso()) {
            JOptionPane.showMessageDialog(this,
                resultado.getMensaje(),
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                resultado.getMensaje(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
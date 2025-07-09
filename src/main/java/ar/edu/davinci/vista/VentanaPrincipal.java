package ar.edu.davinci.vista;

import ar.edu.davinci.controlador.*;
import ar.edu.davinci.modelo.*;
import ar.edu.davinci.servicio.SesionServicio;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana principal del sistema.
 */
public class VentanaPrincipal extends JFrame {
    private final AdopcionControlador adopcionControlador = new AdopcionControlador();
    private final LoginControlador loginControlador = new LoginControlador();
    private JTable tablaMascotas;
    private DefaultTableModel modeloTabla;
    
    public VentanaPrincipal() {
        inicializarComponentes();
        cargarMascotas();
    }
    
    private void inicializarComponentes() {
        setTitle("Sistema de Adopción - " + 
            SesionServicio.obtenerInstancia().getEmpleadoActual().getNombre());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        
        // Menú
        crearMenu();
        
        // Pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Mascotas", crearPanelMascotas());
        tabbedPane.addTab("Registrar Mascota", crearPanelRegistroMascota());
        tabbedPane.addTab("Procesar Adopción", crearPanelAdopcion());
        tabbedPane.addTab("Gestionar Adopciones", crearPanelGestionAdopciones());
        
        add(tabbedPane);
        setLocationRelativeTo(null);
    }
    
    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");
        
        JMenuItem itemCerrarSesion = new JMenuItem("Cerrar Sesión");
        itemCerrarSesion.addActionListener(e -> {
            loginControlador.cerrarSesion();
            new VentanaLogin().setVisible(true);
            dispose();
        });
        menuArchivo.add(itemCerrarSesion);
        
        menuBar.add(menuArchivo);
        setJMenuBar(menuBar);
    }
    
    private JPanel crearPanelMascotas() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columnas = {"ID", "Nombre", "Tipo", "Peso (kg)"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaMascotas = new JTable(modeloTabla);
        panel.add(new JScrollPane(tablaMascotas), BorderLayout.CENTER);
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarMascotas());
        panel.add(btnActualizar, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelRegistroMascota() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        
        gbc.gridx = 1;
        JTextField txtNombre = new JTextField(20);
        panel.add(txtNombre, gbc);
        
        // Tipo
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Tipo:"), gbc);
        
        gbc.gridx = 1;
        JComboBox<String> cmbTipo = new JComboBox<>(ar.edu.davinci.factory.MascotaFactory.getTiposDisponibles());
        panel.add(cmbTipo, gbc);
        
        // Peso
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Peso (kg):"), gbc);
        
        gbc.gridx = 1;
        JSpinner spnPeso = new JSpinner(new SpinnerNumberModel(5.0, 0.1, 100.0, 0.1));
        panel.add(spnPeso, gbc);
        
        // Botón registrar
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton btnRegistrar = new JButton("Registrar Mascota");
        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String tipo = (String) cmbTipo.getSelectedItem();
            double peso = (Double) spnPeso.getValue();
            
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el nombre");
                return;
            }
            
            String resultado = adopcionControlador.registrarMascota(nombre, tipo, peso);
            JOptionPane.showMessageDialog(this, resultado);
            
            if (resultado.contains("exitosamente")) {
                txtNombre.setText("");
                spnPeso.setValue(5.0);
                cargarMascotas();
            }
        });
        panel.add(btnRegistrar, gbc);
        
        return panel;
    }
    
    private JPanel crearPanelAdopcion() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Mascota
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Mascota:"), gbc);
        
        gbc.gridx = 1;
        JComboBox<ComboMascota> cmbMascotas = new JComboBox<>();
        actualizarComboMascotas(cmbMascotas);
        panel.add(cmbMascotas, gbc);
        
        // Nombre adoptante
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Nombre Adoptante:"), gbc);
        
        gbc.gridx = 1;
        JTextField txtNombre = new JTextField(20);
        panel.add(txtNombre, gbc);
        
        // Edad
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Edad:"), gbc);
        
        gbc.gridx = 1;
        JSpinner spnEdad = new JSpinner(new SpinnerNumberModel(25, 18, 100, 1));
        panel.add(spnEdad, gbc);
        
        // Dirección
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Dirección:"), gbc);
        
        gbc.gridx = 1;
        JTextField txtDireccion = new JTextField(20);
        panel.add(txtDireccion, gbc);
        
        // Email
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        JTextField txtEmail = new JTextField(20);
        panel.add(txtEmail, gbc);
        
        // Teléfono
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Teléfono:"), gbc);
        
        gbc.gridx = 1;
        JTextField txtTelefono = new JTextField(20);
        panel.add(txtTelefono, gbc);
        
        // Botón procesar
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        JButton btnProcesar = new JButton("Procesar Adopción");
        btnProcesar.addActionListener(e -> {
            ComboMascota mascotaSel = (ComboMascota) cmbMascotas.getSelectedItem();
            if (mascotaSel == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una mascota");
                return;
            }
            
            String nombre = txtNombre.getText().trim();
            String direccion = txtDireccion.getText().trim();
            
            if (nombre.isEmpty() || direccion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Complete los campos obligatorios");
                return;
            }
            
            AdopcionControlador.ResultadoAdopcion resultado = 
                adopcionControlador.procesarAdopcion(
                    mascotaSel.mascota.getId(),
                    nombre,
                    (Integer) spnEdad.getValue(),
                    direccion,
                    txtEmail.getText().trim(),
                    txtTelefono.getText().trim()
                );
            
            if (resultado.isExitoso()) {
                JOptionPane.showMessageDialog(this, resultado.getMensaje());
                new VentanaTicket(resultado.getAdopcion()).setVisible(true);
                
                // Limpiar campos
                txtNombre.setText("");
                txtDireccion.setText("");
                txtEmail.setText("");
                txtTelefono.setText("");
                cargarMascotas();
                actualizarComboMascotas(cmbMascotas);
            } else {
                JOptionPane.showMessageDialog(this, resultado.getMensaje(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(btnProcesar, gbc);
        
        return panel;
    }
    
    private void cargarMascotas() {
        modeloTabla.setRowCount(0);
        List<Mascota> mascotas = adopcionControlador.obtenerMascotasDisponibles();
        
        for (Mascota mascota : mascotas) {
            Object[] fila = {
                mascota.getId(),
                mascota.getNombre(),
                mascota.getTipo(),
                mascota.getPeso()
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void actualizarComboMascotas(JComboBox<ComboMascota> combo) {
        combo.removeAllItems();
        List<Mascota> mascotas = adopcionControlador.obtenerMascotasDisponibles();
        
        for (Mascota mascota : mascotas) {
            combo.addItem(new ComboMascota(mascota));
        }
    }
    
    // Clase auxiliar para el combo
    private static class ComboMascota {
        final Mascota mascota;
        
        ComboMascota(Mascota mascota) {
            this.mascota = mascota;
        }
        
        @Override
        public String toString() {
            return mascota.getNombre() + " - " + mascota.getTipo();
        }
    }
    
    private JPanel crearPanelGestionAdopciones() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Panel con información
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("Gestión de Adopciones");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInfo.add(lblTitulo);
        
        panelInfo.add(Box.createVerticalStrut(20));
        
        JTextArea txtInfo = new JTextArea(
            "Desde aquí puede:\n\n" +
            "• Visualizar todas las adopciones realizadas\n" +
            "• Ver los detalles completos de cada adopción\n" +
            "• Modificar datos de adoptantes\n" +
            "• Eliminar adopciones registradas\n" +
            "• Generar tickets de adopción"
        );
        txtInfo.setEditable(false);
        txtInfo.setBackground(panel.getBackground());
        txtInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInfo.add(txtInfo);
        
        panelInfo.add(Box.createVerticalStrut(30));
        
        JButton btnAbrir = new JButton("Abrir Gestión de Adopciones");
        btnAbrir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAbrir.addActionListener(e -> new VentanaGestionAdopciones().setVisible(true));
        panelInfo.add(btnAbrir);
        
        panel.add(panelInfo, BorderLayout.CENTER);
        
        return panel;
    }
}
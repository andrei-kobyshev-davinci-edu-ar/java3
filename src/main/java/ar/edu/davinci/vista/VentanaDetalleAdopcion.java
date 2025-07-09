package ar.edu.davinci.vista;

import ar.edu.davinci.modelo.*;
import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

/**
 * Ventana para mostrar los detalles completos de una adopción.
 */
public class VentanaDetalleAdopcion extends JFrame {
    private final Adopcion adopcion;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    public VentanaDetalleAdopcion(Adopcion adopcion) {
        this.adopcion = adopcion;
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Detalles de Adopción - ID: " + adopcion.getId());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 600);
        setLayout(new BorderLayout());
        
        // Panel principal con scroll
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Información general
        panelPrincipal.add(crearPanelInformacionGeneral());
        panelPrincipal.add(Box.createVerticalStrut(10));
        
        // Información del adoptante
        panelPrincipal.add(crearPanelAdoptante());
        panelPrincipal.add(Box.createVerticalStrut(10));
        
        // Información de la mascota
        panelPrincipal.add(crearPanelMascota());
        panelPrincipal.add(Box.createVerticalStrut(10));
        
        // Información del empleado
        panelPrincipal.add(crearPanelEmpleado());
        panelPrincipal.add(Box.createVerticalStrut(10));
        
        // Recomendaciones
        panelPrincipal.add(crearPanelRecomendaciones());
        
        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        
        JButton btnTicket = new JButton("Ver Ticket");
        btnTicket.addActionListener(e -> mostrarTicket());
        panelBotones.add(btnTicket);
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        panelBotones.add(btnCerrar);
        
        add(panelBotones, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
    }
    
    private JPanel crearPanelInformacionGeneral() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Información General"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("ID de Adopción:"), gbc);
        
        gbc.gridx = 1;
        JLabel lblId = new JLabel(String.valueOf(adopcion.getId()));
        lblId.setFont(lblId.getFont().deriveFont(Font.BOLD));
        panel.add(lblId, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Fecha de Adopción:"), gbc);
        
        gbc.gridx = 1;
        panel.add(new JLabel(adopcion.getFechaAdopcion().format(FORMATO_FECHA)), gbc);
        
        return panel;
    }
    
    private JPanel crearPanelAdoptante() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Adoptante"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        Adoptante adoptante = adopcion.getAdoptante();
        
        agregarCampo(panel, gbc, 0, "Nombre:", adoptante.getNombre());
        agregarCampo(panel, gbc, 1, "Edad:", adoptante.getEdad() + " años");
        agregarCampo(panel, gbc, 2, "Dirección:", adoptante.getDireccion());
        
        if (adoptante.getEmail() != null && !adoptante.getEmail().isEmpty()) {
            agregarCampo(panel, gbc, 3, "Email:", adoptante.getEmail());
        }
        
        if (adoptante.getTelefono() != null && !adoptante.getTelefono().isEmpty()) {
            agregarCampo(panel, gbc, 4, "Teléfono:", adoptante.getTelefono());
        }
        
        return panel;
    }
    
    private JPanel crearPanelMascota() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos de la Mascota"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        Mascota mascota = adopcion.getMascota();
        
        agregarCampo(panel, gbc, 0, "Nombre:", mascota.getNombre());
        agregarCampo(panel, gbc, 1, "Tipo:", mascota.getTipo());
        agregarCampo(panel, gbc, 2, "Peso:", String.format("%.2f kg", mascota.getPeso()));
        agregarCampo(panel, gbc, 3, "Estado de salud:", 
            mascota.getEstado().getTipo().getDescripcion());
        
        return panel;
    }
    
    private JPanel crearPanelEmpleado() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Empleado Responsable"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        Empleado empleado = adopcion.getEmpleado();
        
        agregarCampo(panel, gbc, 0, "Nombre:", empleado.getNombre());
        agregarCampo(panel, gbc, 1, "Email:", empleado.getEmail());
        
        return panel;
    }
    
    private JPanel crearPanelRecomendaciones() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Recomendaciones de Cuidado"));
        
        JTextArea txtRecomendaciones = new JTextArea();
        txtRecomendaciones.setEditable(false);
        txtRecomendaciones.setFont(new Font("Arial", Font.PLAIN, 12));
        
        StringBuilder sb = new StringBuilder();
        for (String recomendacion : adopcion.getMascota().getRecomendaciones()) {
            sb.append("• ").append(recomendacion).append("\n");
        }
        
        txtRecomendaciones.setText(sb.toString());
        
        JScrollPane scrollPane = new JScrollPane(txtRecomendaciones);
        scrollPane.setPreferredSize(new Dimension(450, 150));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void agregarCampo(JPanel panel, GridBagConstraints gbc, 
                              int fila, String etiqueta, String valor) {
        gbc.gridx = 0;
        gbc.gridy = fila;
        panel.add(new JLabel(etiqueta), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel(valor), gbc);
        
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
    }
    
    private void mostrarTicket() {
        new VentanaTicket(adopcion).setVisible(true);
    }
}
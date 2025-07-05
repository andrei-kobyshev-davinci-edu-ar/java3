package ar.edu.davinci.vista;

import ar.edu.davinci.modelo.*;
import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Ventana para mostrar el ticket de adopción.
 */
public class VentanaTicket extends JFrame {
    private final Adopcion adopcion;
    
    public VentanaTicket(Adopcion adopcion) {
        this.adopcion = adopcion;
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Ticket de Adopción");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JTextArea txtTicket = new JTextArea();
        txtTicket.setEditable(false);
        txtTicket.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("        TICKET DE ADOPCIÓN\n");
        sb.append("=========================================\n\n");
        
        sb.append("Fecha: ").append(adopcion.getFechaAdopcion()
            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n\n");
        
        sb.append("DATOS DEL ADOPTANTE:\n");
        sb.append("-------------------\n");
        sb.append("Nombre: ").append(adopcion.getAdoptante().getNombre()).append("\n");
        sb.append("Edad: ").append(adopcion.getAdoptante().getEdad()).append(" años\n");
        sb.append("Dirección: ").append(adopcion.getAdoptante().getDireccion()).append("\n");
        if (adopcion.getAdoptante().getEmail() != null && !adopcion.getAdoptante().getEmail().isEmpty()) {
            sb.append("Email: ").append(adopcion.getAdoptante().getEmail()).append("\n");
        }
        if (adopcion.getAdoptante().getTelefono() != null && !adopcion.getAdoptante().getTelefono().isEmpty()) {
            sb.append("Teléfono: ").append(adopcion.getAdoptante().getTelefono()).append("\n");
        }
        
        sb.append("\nDATOS DE LA MASCOTA:\n");
        sb.append("-------------------\n");
        sb.append("Nombre: ").append(adopcion.getMascota().getNombre()).append("\n");
        sb.append("Tipo: ").append(adopcion.getMascota().getTipo()).append("\n");
        sb.append("Peso: ").append(adopcion.getMascota().getPeso()).append(" kg\n");
        
        sb.append("\nEMPLEADO RESPONSABLE:\n");
        sb.append("--------------------\n");
        sb.append("Nombre: ").append(adopcion.getEmpleado().getNombre()).append("\n");
        
        sb.append("\nRECOMENDACIONES DE CUIDADO:\n");
        sb.append("--------------------------\n");
        List<String> recomendaciones = adopcion.getMascota().getRecomendaciones();
        for (String recomendacion : recomendaciones) {
            sb.append("• ").append(recomendacion).append("\n");
        }
        
        sb.append("\n=========================================\n");
        sb.append("¡Gracias por adoptar!\n");
        sb.append("=========================================\n");
        
        txtTicket.setText(sb.toString());
        
        JScrollPane scrollPane = new JScrollPane(txtTicket);
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel();
        JButton btnImprimir = new JButton("Imprimir");
        btnImprimir.addActionListener(e -> {
            try {
                txtTicket.print();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error al imprimir: " + ex.getMessage());
            }
        });
        panelBotones.add(btnImprimir);
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        panelBotones.add(btnCerrar);
        
        add(panelBotones, BorderLayout.SOUTH);
        
        setSize(500, 600);
        setLocationRelativeTo(null);
    }
}
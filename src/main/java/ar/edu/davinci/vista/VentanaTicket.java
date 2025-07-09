package ar.edu.davinci.vista;

import ar.edu.davinci.modelo.*;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana para mostrar el ticket de adopción.
 * Usa el modelo Ticket para generar el contenido.
 */
public class VentanaTicket extends JFrame {
    private final Ticket ticket;
    
    public VentanaTicket(Adopcion adopcion) {
        this.ticket = new Ticket(adopcion);
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        setTitle("Ticket de Adopción");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JTextArea txtTicket = new JTextArea();
        txtTicket.setEditable(false);
        txtTicket.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        // Usar el modelo Ticket para generar el contenido
        txtTicket.setText(ticket.generarContenido());
        
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
        
        setSize(500, 700);
        setLocationRelativeTo(null);
    }
    
    /**
     * Obtiene el ticket asociado a esta ventana.
     * @return El ticket
     */
    public Ticket getTicket() {
        return ticket;
    }
}
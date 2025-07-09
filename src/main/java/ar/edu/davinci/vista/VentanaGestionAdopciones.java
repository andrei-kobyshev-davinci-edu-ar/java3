package ar.edu.davinci.vista;

import ar.edu.davinci.controlador.AdopcionControlador;
import ar.edu.davinci.modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Ventana para gestionar adopciones existentes.
 * Permite visualizar, modificar y eliminar adopciones.
 */
public class VentanaGestionAdopciones extends JFrame {
    private final AdopcionControlador controlador = new AdopcionControlador();
    private JTable tablaAdopciones;
    private DefaultTableModel modeloTabla;
    private List<Adopcion> adopcionesActuales;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    public VentanaGestionAdopciones() {
        inicializarComponentes();
        cargarAdopciones();
    }
    
    private void inicializarComponentes() {
        setTitle("Gestión de Adopciones");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLayout(new BorderLayout());
        
        // Panel superior con título
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("Gestión de Adopciones Realizadas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelTitulo.add(lblTitulo);
        add(panelTitulo, BorderLayout.NORTH);
        
        // Tabla de adopciones
        crearTabla();
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarAdopciones());
        panelBotones.add(btnActualizar);
        
        JButton btnVer = new JButton("Ver Detalles");
        btnVer.addActionListener(e -> verDetallesAdopcion());
        panelBotones.add(btnVer);
        
        JButton btnModificar = new JButton("Modificar");
        btnModificar.addActionListener(e -> modificarAdopcion());
        panelBotones.add(btnModificar);
        
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setForeground(Color.RED);
        btnEliminar.addActionListener(e -> eliminarAdopcion());
        panelBotones.add(btnEliminar);
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        panelBotones.add(btnCerrar);
        
        add(panelBotones, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
    }
    
    private void crearTabla() {
        String[] columnas = {
            "ID", "Fecha", "Mascota", "Tipo", "Adoptante", 
            "Edad", "Dirección", "Empleado"
        };
        
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaAdopciones = new JTable(modeloTabla);
        tablaAdopciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaAdopciones.getTableHeader().setReorderingAllowed(false);
        
        // Ajustar anchos de columnas
        tablaAdopciones.getColumnModel().getColumn(0).setMaxWidth(50);  // ID
        tablaAdopciones.getColumnModel().getColumn(1).setPreferredWidth(120); // Fecha
        tablaAdopciones.getColumnModel().getColumn(3).setMaxWidth(80);  // Tipo
        tablaAdopciones.getColumnModel().getColumn(5).setMaxWidth(50);  // Edad
        
        // Doble clic para ver detalles
        tablaAdopciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    verDetallesAdopcion();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tablaAdopciones);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void cargarAdopciones() {
        modeloTabla.setRowCount(0);
        adopcionesActuales = controlador.obtenerTodasLasAdopciones();
        
        for (Adopcion adopcion : adopcionesActuales) {
            Object[] fila = {
                adopcion.getId(),
                adopcion.getFechaAdopcion().format(FORMATO_FECHA),
                adopcion.getMascota().getNombre(),
                adopcion.getMascota().getTipo(),
                adopcion.getAdoptante().getNombre(),
                adopcion.getAdoptante().getEdad(),
                adopcion.getAdoptante().getDireccion(),
                adopcion.getEmpleado().getNombre()
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void verDetallesAdopcion() {
        int filaSeleccionada = tablaAdopciones.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione una adopción para ver sus detalles",
                "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Adopcion adopcion = adopcionesActuales.get(filaSeleccionada);
        new VentanaDetalleAdopcion(adopcion).setVisible(true);
    }
    
    private void modificarAdopcion() {
        int filaSeleccionada = tablaAdopciones.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione una adopción para modificar",
                "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Adopcion adopcion = adopcionesActuales.get(filaSeleccionada);
        VentanaModificarAdopcion ventanaModificar = new VentanaModificarAdopcion(adopcion);
        ventanaModificar.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                cargarAdopciones(); // Recargar cuando se cierre la ventana de modificación
            }
        });
        ventanaModificar.setVisible(true);
    }
    
    private void eliminarAdopcion() {
        int filaSeleccionada = tablaAdopciones.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, 
                "Seleccione una adopción para eliminar",
                "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Adopcion adopcion = adopcionesActuales.get(filaSeleccionada);
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            String.format("¿Está seguro de eliminar la adopción?\n\n" +
                "Mascota: %s\n" +
                "Adoptante: %s\n" +
                "Fecha: %s",
                adopcion.getMascota().getNombre(),
                adopcion.getAdoptante().getNombre(),
                adopcion.getFechaAdopcion().format(FORMATO_FECHA)),
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            AdopcionControlador.ResultadoAdopcion resultado = 
                controlador.eliminarAdopcion(adopcion.getId());
            
            if (resultado.isExitoso()) {
                JOptionPane.showMessageDialog(this, 
                    resultado.getMensaje(),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarAdopciones();
            } else {
                JOptionPane.showMessageDialog(this, 
                    resultado.getMensaje(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
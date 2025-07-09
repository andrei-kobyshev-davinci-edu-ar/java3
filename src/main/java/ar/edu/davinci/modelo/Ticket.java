package ar.edu.davinci.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Representa un ticket de adopción.
 * Encapsula toda la información necesaria para generar el comprobante.
 */
public class Ticket {
    private final Adopcion adopcion;
    private final LocalDateTime fechaEmision;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    public Ticket(Adopcion adopcion) {
        this.adopcion = adopcion;
        this.fechaEmision = LocalDateTime.now();
    }
    
    /**
     * Genera el contenido formateado del ticket.
     * @return String con el contenido del ticket
     */
    public String generarContenido() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("=========================================\n");
        sb.append("        TICKET DE ADOPCIÓN\n");
        sb.append("=========================================\n\n");
        
        sb.append("Fecha de emisión: ").append(fechaEmision.format(FORMATO_FECHA)).append("\n");
        sb.append("Fecha de adopción: ").append(adopcion.getFechaAdopcion().format(FORMATO_FECHA)).append("\n\n");
        
        sb.append(generarSeccionAdoptante());
        sb.append(generarSeccionMascota());
        sb.append(generarSeccionEmpleado());
        sb.append(generarSeccionRecomendaciones());
        
        sb.append("\n=========================================\n");
        sb.append("¡Gracias por adoptar!\n");
        sb.append("=========================================\n");
        
        return sb.toString();
    }
    
    private String generarSeccionAdoptante() {
        Adoptante adoptante = adopcion.getAdoptante();
        StringBuilder sb = new StringBuilder();
        
        sb.append("DATOS DEL ADOPTANTE:\n");
        sb.append("-------------------\n");
        sb.append("Nombre: ").append(adoptante.getNombre()).append("\n");
        sb.append("Edad: ").append(adoptante.getEdad()).append(" años\n");
        sb.append("Dirección: ").append(adoptante.getDireccion()).append("\n");
        
        if (adoptante.getEmail() != null && !adoptante.getEmail().isEmpty()) {
            sb.append("Email: ").append(adoptante.getEmail()).append("\n");
        }
        
        if (adoptante.getTelefono() != null && !adoptante.getTelefono().isEmpty()) {
            sb.append("Teléfono: ").append(adoptante.getTelefono()).append("\n");
        }
        
        sb.append("\n");
        return sb.toString();
    }
    
    private String generarSeccionMascota() {
        Mascota mascota = adopcion.getMascota();
        StringBuilder sb = new StringBuilder();
        
        sb.append("DATOS DE LA MASCOTA:\n");
        sb.append("-------------------\n");
        sb.append("Nombre: ").append(mascota.getNombre()).append("\n");
        sb.append("Tipo: ").append(mascota.getTipo()).append("\n");
        sb.append("Peso: ").append(String.format("%.2f kg", mascota.getPeso())).append("\n");
        sb.append("Estado de salud: ").append(mascota.getEstado().getTipo().getDescripcion()).append("\n");
        
        sb.append("\n");
        return sb.toString();
    }
    
    private String generarSeccionEmpleado() {
        Empleado empleado = adopcion.getEmpleado();
        StringBuilder sb = new StringBuilder();
        
        sb.append("EMPLEADO RESPONSABLE:\n");
        sb.append("--------------------\n");
        sb.append("Nombre: ").append(empleado.getNombre()).append("\n");
        sb.append("Email: ").append(empleado.getEmail()).append("\n");
        
        sb.append("\n");
        return sb.toString();
    }
    
    private String generarSeccionRecomendaciones() {
        List<String> recomendaciones = adopcion.getMascota().getRecomendaciones();
        StringBuilder sb = new StringBuilder();
        
        sb.append("RECOMENDACIONES DE CUIDADO:\n");
        sb.append("--------------------------\n");
        
        for (String recomendacion : recomendaciones) {
            sb.append("• ").append(recomendacion).append("\n");
        }
        
        // Agregar recomendaciones adicionales según el tipo de mascota
        agregarRecomendacionesEspecificas(sb, adopcion.getMascota());
        
        return sb.toString();
    }
    
    private void agregarRecomendacionesEspecificas(StringBuilder sb, Mascota mascota) {
        // Usar instanceof en lugar de switch para aplicar Tell Don't Ask
        if (mascota instanceof Perro) {
            sb.append("\nRecomendaciones específicas para perros:\n");
            sb.append("• Proporcione ejercicio diario adecuado\n");
            sb.append("• Mantenga las vacunas al día\n");
        } else if (mascota instanceof Gato) {
            sb.append("\nRecomendaciones específicas para gatos:\n");
            sb.append("• Mantenga la caja de arena limpia\n");
            sb.append("• Proporcione lugares altos para trepar\n");
        } else if (mascota instanceof Conejo) {
            sb.append("\nRecomendaciones específicas para conejos:\n");
            sb.append("• Proporcione heno ilimitado\n");
            sb.append("• Evite cambios bruscos de temperatura\n");
        } else if (mascota instanceof Pajaro) {
            sb.append("\nRecomendaciones específicas para pájaros:\n");
            sb.append("• Mantenga la jaula en un lugar sin corrientes\n");
            sb.append("• Proporcione variedad en la dieta\n");
        }
    }
    
    // Getters
    public Adopcion getAdopcion() {
        return adopcion;
    }
    
    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }
}
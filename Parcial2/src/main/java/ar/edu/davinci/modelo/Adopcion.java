package ar.edu.davinci.modelo;

import java.time.LocalDateTime;

/**
 * Representa una adopción.
 */
public class Adopcion {
    private Long id;
    private Adoptante adoptante;
    private Mascota mascota;
    private Empleado empleado;
    private LocalDateTime fechaAdopcion;
    
    public Adopcion() {
        this.fechaAdopcion = LocalDateTime.now();
    }
    
    public Adopcion(Adoptante adoptante, Mascota mascota, Empleado empleado) {
        this();
        this.adoptante = adoptante;
        this.mascota = mascota;
        this.empleado = empleado;
    }
    
    // Getters y setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Adoptante getAdoptante() {
        return adoptante;
    }
    
    public void setAdoptante(Adoptante adoptante) {
        this.adoptante = adoptante;
    }
    
    public Mascota getMascota() {
        return mascota;
    }
    
    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }
    
    public Empleado getEmpleado() {
        return empleado;
    }
    
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    
    public LocalDateTime getFechaAdopcion() {
        return fechaAdopcion;
    }
    
    public void setFechaAdopcion(LocalDateTime fechaAdopcion) {
        this.fechaAdopcion = fechaAdopcion;
    }
}
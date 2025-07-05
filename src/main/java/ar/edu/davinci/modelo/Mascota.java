package ar.edu.davinci.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta que representa una mascota.
 * Implementa MascotaI según feedback del profesor.
 */
public abstract class Mascota implements MascotaI {
    protected Long id;
    protected String nombre;
    protected String tipo;
    protected double peso;
    protected LocalDate fechaNacimiento;
    protected EstadoMascota estado;
    
    public Mascota() {
        this.fechaNacimiento = LocalDate.now().minusYears(1); // Por defecto 1 año
        this.estado = new EstadoSaludable();
    }
    
    public Mascota(String nombre, String tipo, double peso) {
        this();
        this.nombre = nombre;
        this.tipo = tipo;
        this.peso = peso;
    }
    
    public Mascota(String nombre, LocalDate fechaNacimiento, double peso) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.peso = peso;
        this.estado = new EstadoSaludable();
        this.tipo = this.getClass().getSimpleName();
    }
    
    // Implementación de MascotaI y Cosa
    @Override
    public boolean esMascota() {
        return true;
    }
    
    @Override
    public EstadoMascota getEstado() {
        return estado;
    }
    
    @Override
    public void cambiarEstado(EstadoMascota nuevoEstado) {
        System.out.printf("%s cambió de estado: %s -> %s%n",
            nombre, estado.getTipo().getDescripcion(), nuevoEstado.getTipo().getDescripcion());
        this.estado = nuevoEstado;
    }
    
    @Override
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    @Override
    public List<String> getRecomendaciones() {
        return estado.obtenerRecomendaciones(getCuidadosBase());
    }
    
    protected abstract List<String> getCuidadosBase();
    
    // Métodos adicionales
    public boolean quiereJugar() {
        boolean resultado = estado.quiereJugar();
        System.out.printf("%s (Estado: %s) - ¿Quiere jugar? %s%n",
            nombre, estado.getTipo().getDescripcion(), resultado ? "¡Sí!" : "No");
        return resultado;
    }
    
    public void registrarCambioTemperatura(double temperatura) {
        estado.cambiarTemperatura(this, temperatura);
    }
    
    public void mejorar() {
        cambiarEstado(new EstadoSaludable());
    }
    
    public void empeorar() {
        // Delegamos la lógica al estado actual (Tell, don't ask)
        estado.empeorar(this);
    }
    
    // Getters y setters originales
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public double getPeso() {
        return peso;
    }
    
    public void setPeso(double peso) {
        this.peso = peso;
    }
}
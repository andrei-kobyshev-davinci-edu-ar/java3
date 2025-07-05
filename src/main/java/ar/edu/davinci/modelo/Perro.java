package ar.edu.davinci.modelo;

import java.time.LocalDate;
import java.util.List;

/**
 * Clase concreta que representa un perro.
 */
public class Perro extends Mascota {
    
    public Perro() {
        super();
        this.tipo = "Perro";
    }
    
    public Perro(String nombre, double peso) {
        super(nombre, "Perro", peso);
    }
    
    public Perro(String nombre, LocalDate fechaNacimiento, double peso) {
        super(nombre, fechaNacimiento, peso);
        this.tipo = "Perro";
    }
    
    @Override
    protected List<String> getCuidadosBase() {
        return RegistroCuidados.obtenerCuidadosBase(this.getClass());
    }
}
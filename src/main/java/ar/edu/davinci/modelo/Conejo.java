package ar.edu.davinci.modelo;

import java.time.LocalDate;
import java.util.List;

/**
 * Clase concreta que representa un conejo.
 */
public class Conejo extends Mascota {
    
    public Conejo() {
        super();
        this.tipo = "Conejo";
    }
    
    public Conejo(String nombre, double peso) {
        super(nombre, "Conejo", peso);
    }
    
    public Conejo(String nombre, LocalDate fechaNacimiento, double peso) {
        super(nombre, fechaNacimiento, peso);
        this.tipo = "Conejo";
    }
    
    @Override
    protected List<String> getCuidadosBase() {
        return RegistroCuidados.obtenerCuidadosBase(this.getClass());
    }
}
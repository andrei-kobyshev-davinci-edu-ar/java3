package ar.edu.davinci.modelo;

import java.time.LocalDate;
import java.util.List;

/**
 * Clase concreta que representa un gato.
 */
public class Gato extends Mascota {
    
    public Gato() {
        super();
        this.tipo = "Gato";
    }
    
    public Gato(String nombre, double peso) {
        super(nombre, "Gato", peso);
    }
    
    public Gato(String nombre, LocalDate fechaNacimiento, double peso) {
        super(nombre, fechaNacimiento, peso);
        this.tipo = "Gato";
    }
    
    @Override
    protected List<String> getCuidadosBase() {
        return RegistroCuidados.obtenerCuidadosBase(this.getClass());
    }
}
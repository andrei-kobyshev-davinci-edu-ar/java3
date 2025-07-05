package ar.edu.davinci.modelo;

import java.time.LocalDate;
import java.util.List;

/**
 * Clase concreta que representa un pájaro.
 */
public class Pajaro extends Mascota {
    
    public Pajaro() {
        super();
        this.tipo = "Pajaro";
    }
    
    public Pajaro(String nombre, double peso) {
        super(nombre, "Pajaro", peso);
    }
    
    public Pajaro(String nombre, LocalDate fechaNacimiento, double peso) {
        super(nombre, fechaNacimiento, peso);
        this.tipo = "Pajaro";
    }
    
    @Override
    protected List<String> getCuidadosBase() {
        return RegistroCuidados.obtenerCuidadosBase(this.getClass());
    }
}
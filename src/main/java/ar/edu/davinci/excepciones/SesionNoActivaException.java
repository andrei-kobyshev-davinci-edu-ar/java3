package ar.edu.davinci.excepciones;

/**
 * Excepción cuando se intenta realizar una operación sin sesión activa.
 */
public class SesionNoActivaException extends AdopcionException {
    public SesionNoActivaException() {
        super("No hay una sesión activa. Por favor inicie sesión.");
    }
}
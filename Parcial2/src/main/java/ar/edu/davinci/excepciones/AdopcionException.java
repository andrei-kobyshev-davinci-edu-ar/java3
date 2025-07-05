package ar.edu.davinci.excepciones;

/**
 * Excepción base para el sistema de adopciones.
 */
public class AdopcionException extends Exception {
    public AdopcionException(String mensaje) {
        super(mensaje);
    }
}
package ar.edu.davinci.excepciones;

/**
 * Excepción para datos inválidos.
 */
public class DatosInvalidosException extends AdopcionException {
    public DatosInvalidosException(String mensaje) {
        super(mensaje);
    }
}
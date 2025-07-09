package ar.edu.davinci.excepciones;

/**
 * Excepción cuando no se encuentra una entidad en la base de datos.
 */
public class EntidadNoEncontradaException extends AdopcionException {
    public EntidadNoEncontradaException(String tipoEntidad, Long id) {
        super(String.format("%s con ID %d no encontrada", tipoEntidad, id));
    }
    
    public EntidadNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
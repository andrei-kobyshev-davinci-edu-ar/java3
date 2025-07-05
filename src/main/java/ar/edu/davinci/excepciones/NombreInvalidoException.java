package ar.edu.davinci.excepciones;

/**
 * Excepción para nombres inválidos.
 */
public class NombreInvalidoException extends DatosInvalidosException {
    public NombreInvalidoException() {
        super("El nombre no puede estar vacío");
    }
}
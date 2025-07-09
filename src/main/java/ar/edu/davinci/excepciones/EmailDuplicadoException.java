package ar.edu.davinci.excepciones;

/**
 * Excepción cuando se intenta registrar un email que ya existe.
 */
public class EmailDuplicadoException extends AdopcionException {
    public EmailDuplicadoException(String email) {
        super("El email " + email + " ya está registrado en el sistema");
    }
}
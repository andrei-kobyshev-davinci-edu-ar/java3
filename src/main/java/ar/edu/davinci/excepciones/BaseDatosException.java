package ar.edu.davinci.excepciones;

/**
 * Excepción para errores relacionados con la base de datos.
 */
public class BaseDatosException extends RuntimeException {
    public BaseDatosException(String mensaje) {
        super(mensaje);
    }
    
    public BaseDatosException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
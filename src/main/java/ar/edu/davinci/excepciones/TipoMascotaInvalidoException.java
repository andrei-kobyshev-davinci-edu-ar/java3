package ar.edu.davinci.excepciones;

/**
 * Excepción cuando se especifica un tipo de mascota inválido.
 */
public class TipoMascotaInvalidoException extends DatosInvalidosException {
    public TipoMascotaInvalidoException(String tipoInvalido) {
        super("Tipo de mascota inválido: " + tipoInvalido + ". Use: Perro, Gato, Conejo o Pájaro");
    }
}
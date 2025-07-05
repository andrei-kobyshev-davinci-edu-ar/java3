package ar.edu.davinci.modelo;

/**
 * Enum que representa los tipos de campos para validación.
 */
public enum TipoCampo implements DescribableI {
    NOMBRE("nombre"),
    EDAD("edad"),
    PESO("peso"),
    DIRECCION("dirección"),
    FECHA_NACIMIENTO("fecha de nacimiento");

    private final String descripcion;

    TipoCampo(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }
}
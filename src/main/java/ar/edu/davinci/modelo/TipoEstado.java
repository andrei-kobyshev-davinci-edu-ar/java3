package ar.edu.davinci.modelo;

/**
 * Enum que representa los tipos de estado de salud de una mascota.
 */
public enum TipoEstado implements DescribableI {
    SALUDABLE("Saludable"),
    REQUIERE_CUIDADOS_ESPECIALES("Requiere cuidados especiales"),
    EN_OBSERVACION("En observación");

    private final String descripcion;

    TipoEstado(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }
}
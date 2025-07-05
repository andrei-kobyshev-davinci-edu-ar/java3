package ar.edu.davinci.modelo;

/**
 * Enum que representa los pasos del proceso de adopción.
 */
public enum TipoPasoAdopcion {
    DATOS_ADOPTANTE("Datos del adoptante registrados: %s"),
    EMPLEADO_ENCARGADO("Empleado encargado: %s - %s"),
    MASCOTA_REGISTRADA("Mascota a adoptar: %s (%s)"),
    ADOPCION_FINALIZADA("Proceso de adopción finalizado exitosamente");

    private final String formato;

    TipoPasoAdopcion(String formato) {
        this.formato = formato;
    }

    public String formatear(Object... args) {
        return String.format(formato, args);
    }
}
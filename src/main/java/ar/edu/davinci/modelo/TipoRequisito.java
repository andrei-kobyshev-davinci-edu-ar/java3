package ar.edu.davinci.modelo;

/**
 * Enum que representa los tipos de requisitos para adopción.
 */
public enum TipoRequisito implements DescribableI {
    VACUNA_RABIA("Vacuna antirrábica"),
    VACUNA_PARVOVIRUS("Vacuna contra parvovirus"),
    VACUNA_MOQUILLO("Vacuna contra moquillo"),
    VACUNA_TRIPLE_FELINA("Vacuna triple felina"),
    VACUNA_LEUCEMIA_FELINA("Vacuna contra leucemia felina"),
    VACUNA_MIXOMATOSIS("Vacuna contra mixomatosis"),
    MICROCHIP("Microchip implantado"),
    ESTERILIZACION("Esterilización realizada"),
    SALUD_DENTAL("Salud dental verificada"),
    EXAMEN_PLUMAS("Examen de plumas"),
    DOCUMENTACION_CITES("Documentación CITES");

    private final String descripcion;

    TipoRequisito(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }
}
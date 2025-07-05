package ar.edu.davinci.modelo;

/**
 * Enum que representa los tipos de cuidados para mascotas.
 */
public enum TipoCuidado implements DescribableI {
    // Cuidados base por especie
    PASEOS_DIARIOS("Paseos diarios"),
    DIETA_BALANCEADA("Dieta balanceada"),
    VISITAS_VETERINARIO("Visitas regulares al veterinario"),
    CAJA_ARENA("Caja de arena"),
    POSTE_RASCAR("Poste para rascar"),
    CEPILLADO_REGULAR("Cepillado regular"),
    JAULA_ESPACIOSA("Jaula espaciosa"),
    HENO_FRESCO("Heno fresco"),
    EJERCICIO_REGULAR("Ejercicio regular"),
    JAULA_APROPIADA("Jaula apropiada"),
    DIETA_SEMILLAS("Dieta basada en semillas"),
    INTERACCION_SOCIAL("Interacción social"),

    // Cuidados especiales
    MEJOR_ALIMENTO("Darle un mejor alimento"),
    CONTROL_HIDRATACION("Controlar su hidratación"),

    // Cuidados observación
    MONITOREO_TEMPERATURA("Monitoreo constante de temperatura"),
    MEDICACION_PRESCRITA("Administrar medicación según prescripción");

    private final String descripcion;

    TipoCuidado(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }
}
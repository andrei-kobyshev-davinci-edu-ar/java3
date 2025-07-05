package ar.edu.davinci.modelo;

/**
 * Enum que representa los tipos de servicios adicionales.
 */
public enum TipoServicio implements DescribableI {
    COLLAR_CORREA("Collar y correa de regalo"),
    GUIA_ADIESTRAMIENTO("Guía de adiestramiento básico"),
    KIT_ARENERO("Kit de arenero completo"),
    RASCADOR("Rascador para gatos"),
    GUIA_CUIDADOS_JAULA("Guía de cuidados de jaula"),
    SUMINISTRO_HENO("Suministro inicial de heno"),
    MEZCLA_SEMILLAS("Mezcla premium de semillas"),
    JUGUETES_ESTIMULACION("Juguetes de estimulación mental");

    private final String descripcion;

    TipoServicio(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }
}
package ar.edu.davinci.modelo;

import java.util.*;

/**
 * Registro centralizado de cuidados para mascotas.
 */
public class RegistroCuidados {
    private static final Map<Class<? extends Mascota>, Set<TipoCuidado>> CUIDADOS_BASE = new HashMap<>();
    private static final Set<TipoCuidado> CUIDADOS_ESPECIALES = EnumSet.of(
        TipoCuidado.MEJOR_ALIMENTO,
        TipoCuidado.CONTROL_HIDRATACION
    );
    private static final Set<TipoCuidado> CUIDADOS_OBSERVACION = EnumSet.of(
        TipoCuidado.MONITOREO_TEMPERATURA,
        TipoCuidado.MEDICACION_PRESCRITA
    );

    static {
        CUIDADOS_BASE.put(Perro.class, EnumSet.of(
            TipoCuidado.PASEOS_DIARIOS,
            TipoCuidado.DIETA_BALANCEADA,
            TipoCuidado.VISITAS_VETERINARIO
        ));

        CUIDADOS_BASE.put(Gato.class, EnumSet.of(
            TipoCuidado.CAJA_ARENA,
            TipoCuidado.POSTE_RASCAR,
            TipoCuidado.CEPILLADO_REGULAR
        ));

        CUIDADOS_BASE.put(Conejo.class, EnumSet.of(
            TipoCuidado.JAULA_ESPACIOSA,
            TipoCuidado.HENO_FRESCO,
            TipoCuidado.EJERCICIO_REGULAR
        ));

        CUIDADOS_BASE.put(Pajaro.class, EnumSet.of(
            TipoCuidado.JAULA_APROPIADA,
            TipoCuidado.DIETA_SEMILLAS,
            TipoCuidado.INTERACCION_SOCIAL
        ));
    }

    public static List<String> obtenerCuidadosBase(Class<? extends Mascota> tipoMascota) {
        return CUIDADOS_BASE.getOrDefault(tipoMascota, EnumSet.noneOf(TipoCuidado.class))
            .stream()
            .map(TipoCuidado::getDescripcion)
            .collect(ArrayList::new, (list, item) -> list.add(item), ArrayList::addAll);
    }

    public static List<String> obtenerCuidadosEspeciales() {
        return CUIDADOS_ESPECIALES.stream()
            .map(TipoCuidado::getDescripcion)
            .collect(ArrayList::new, (list, item) -> list.add(item), ArrayList::addAll);
    }

    public static List<String> obtenerCuidadosObservacion() {
        return CUIDADOS_OBSERVACION.stream()
            .map(TipoCuidado::getDescripcion)
            .collect(ArrayList::new, (list, item) -> list.add(item), ArrayList::addAll);
    }
}
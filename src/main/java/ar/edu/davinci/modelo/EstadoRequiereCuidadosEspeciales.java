package ar.edu.davinci.modelo;

import java.util.List;
import java.util.Random;

/**
 * Estado que representa una mascota que requiere cuidados especiales.
 */
public class EstadoRequiereCuidadosEspeciales extends EstadoConCuidadosBase {
    private static final double TEMPERATURA_CRITICA_ALTA = 40.0;
    private static final double TEMPERATURA_CRITICA_BAJA = 37.0;
    private static final double TEMPERATURA_NORMAL_BAJA = 38.0;
    private static final double TEMPERATURA_NORMAL_ALTA = 39.0;
    private final Random random = new Random();

    @Override
    public List<String> obtenerRecomendaciones(List<String> recomendacionesBase) {
        return agregarCuidadosAdicionales(recomendacionesBase, RegistroCuidados.obtenerCuidadosEspeciales());
    }

    @Override
    public boolean quiereJugar() {
        return random.nextBoolean();
    }

    @Override
    public void cambiarTemperatura(Mascota mascota, double nuevaTemperatura) {
        if (nuevaTemperatura > TEMPERATURA_CRITICA_ALTA || nuevaTemperatura < TEMPERATURA_CRITICA_BAJA) {
            mascota.cambiarEstado(new EstadoEnObservacion());
        } else if (nuevaTemperatura >= TEMPERATURA_NORMAL_BAJA && nuevaTemperatura <= TEMPERATURA_NORMAL_ALTA) {
            mascota.cambiarEstado(new EstadoSaludable());
        }
    }

    @Override
    public TipoEstado getTipo() {
        return TipoEstado.REQUIERE_CUIDADOS_ESPECIALES;
    }
}
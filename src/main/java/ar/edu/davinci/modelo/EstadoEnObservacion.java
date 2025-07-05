package ar.edu.davinci.modelo;

import java.util.List;

/**
 * Estado que representa una mascota en observación.
 */
public class EstadoEnObservacion extends EstadoConCuidadosBase {
    private static final double TEMPERATURA_RECUPERACION_BAJA = 38.0;
    private static final double TEMPERATURA_RECUPERACION_ALTA = 39.0;

    @Override
    public List<String> obtenerRecomendaciones(List<String> recomendacionesBase) {
        List<String> recomendaciones = agregarCuidadosAdicionales(
            recomendacionesBase,
            RegistroCuidados.obtenerCuidadosEspeciales()
        );
        recomendaciones.addAll(RegistroCuidados.obtenerCuidadosObservacion());
        return recomendaciones;
    }

    @Override
    public boolean quiereJugar() {
        return false;
    }

    @Override
    public void cambiarTemperatura(Mascota mascota, double nuevaTemperatura) {
        notificarVeterinario(mascota, nuevaTemperatura);

        if (nuevaTemperatura >= TEMPERATURA_RECUPERACION_BAJA &&
            nuevaTemperatura <= TEMPERATURA_RECUPERACION_ALTA) {
            mascota.cambiarEstado(new EstadoRequiereCuidadosEspeciales());
        }
    }

    private void notificarVeterinario(Mascota mascota, double temperatura) {
        // En la versión simplificada, solo imprimimos
        System.out.printf("ALERTA: %s tiene temperatura de %.1f°C%n", 
            mascota.getNombre(), temperatura);
    }

    @Override
    public TipoEstado getTipo() {
        return TipoEstado.EN_OBSERVACION;
    }
    
    @Override
    public void empeorar(Mascota mascota) {
        // Ya está en el peor estado, no hace nada
    }
}
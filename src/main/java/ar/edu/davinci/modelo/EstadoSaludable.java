package ar.edu.davinci.modelo;

import java.util.List;

/**
 * Estado que representa una mascota saludable.
 */
public class EstadoSaludable extends EstadoMascota {
    private static final double TEMPERATURA_ALTA = 40.0;
    private static final double TEMPERATURA_BAJA = 37.0;

    @Override
    public List<String> obtenerRecomendaciones(List<String> recomendacionesBase) {
        return recomendacionesBase;
    }

    @Override
    public boolean quiereJugar() {
        return true;
    }

    @Override
    public void cambiarTemperatura(Mascota mascota, double nuevaTemperatura) {
        if (nuevaTemperatura > TEMPERATURA_ALTA || nuevaTemperatura < TEMPERATURA_BAJA) {
            mascota.cambiarEstado(new EstadoRequiereCuidadosEspeciales());
        }
    }

    @Override
    public TipoEstado getTipo() {
        return TipoEstado.SALUDABLE;
    }
    
    @Override
    public void empeorar(Mascota mascota) {
        mascota.cambiarEstado(new EstadoRequiereCuidadosEspeciales());
    }
}
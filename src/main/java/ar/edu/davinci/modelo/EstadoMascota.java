package ar.edu.davinci.modelo;

import java.util.List;

/**
 * Clase abstracta que representa el estado de salud de una mascota.
 * Implementa el patrón State.
 */
public abstract class EstadoMascota {
    public abstract List<String> obtenerRecomendaciones(List<String> recomendacionesBase);
    public abstract boolean quiereJugar();
    public abstract void cambiarTemperatura(Mascota mascota, double nuevaTemperatura);
    public abstract TipoEstado getTipo();
    public abstract void empeorar(Mascota mascota);
}
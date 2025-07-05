package ar.edu.davinci.modelo;

import java.time.LocalDate;
import java.util.List;

/**
 * Interfaz para mascotas que extiende Cosa.
 * Solicitada específicamente por el profesor.
 */
public interface MascotaI extends Cosa {
    LocalDate getFechaNacimiento();
    double getPeso();
    List<String> getRecomendaciones();
    EstadoMascota getEstado();
    void cambiarEstado(EstadoMascota nuevoEstado);
}
package ar.edu.davinci.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta base para estados que requieren cuidados adicionales.
 */
public abstract class EstadoConCuidadosBase extends EstadoMascota {
    protected List<String> agregarCuidadosAdicionales(List<String> recomendacionesBase, List<String> cuidadosAdicionales) {
        List<String> recomendaciones = new ArrayList<>(recomendacionesBase);
        recomendaciones.addAll(cuidadosAdicionales);
        return recomendaciones;
    }
    
    // Implementación por defecto que las subclases pueden sobrescribir
    @Override
    public void empeorar(Mascota mascota) {
        // Por defecto no hace nada, las subclases definen su comportamiento
    }
}
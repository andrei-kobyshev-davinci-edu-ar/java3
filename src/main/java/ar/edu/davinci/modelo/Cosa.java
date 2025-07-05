package ar.edu.davinci.modelo;

/**
 * Interfaz base para objetos que pueden ser contenidos.
 * Parte del feedback del profesor sobre usar interfaces.
 */
public interface Cosa {
    /**
     * Obtiene el nombre del objeto.
     * @return nombre del objeto
     */
    String getNombre();
    
    /**
     * Indica si el objeto es una mascota.
     * @return true si es mascota, false en caso contrario
     */
    boolean esMascota();
}
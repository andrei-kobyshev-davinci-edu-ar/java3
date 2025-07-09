package ar.edu.davinci.controlador;

import ar.edu.davinci.excepciones.*;
import ar.edu.davinci.modelo.*;
import ar.edu.davinci.servicio.AdopcionServicio;
import java.util.List;

/**
 * Controlador para manejar adopciones.
 * Delega la lógica de negocio al servicio.
 */
public class AdopcionControlador {
    private final AdopcionServicio adopcionServicio = new AdopcionServicio();
    
    public List<Mascota> obtenerMascotasDisponibles() {
        return adopcionServicio.obtenerMascotasDisponibles();
    }
    
    public String registrarMascota(String nombre, String tipo, double peso) {
        try {
            adopcionServicio.registrarMascota(nombre, tipo, peso);
            return "Mascota registrada exitosamente";
        } catch (TipoMascotaInvalidoException | NombreInvalidoException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Error al registrar mascota: " + e.getMessage();
        }
    }
    
    public ResultadoAdopcion procesarAdopcion(Long mascotaId, String nombreAdoptante, 
            int edadAdoptante, String direccionAdoptante, String emailAdoptante, 
            String telefonoAdoptante) {
        
        try {
            Adopcion adopcion = adopcionServicio.procesarAdopcion(
                mascotaId, nombreAdoptante, edadAdoptante, 
                direccionAdoptante, emailAdoptante, telefonoAdoptante
            );
            return new ResultadoAdopcion(true, "Adopción exitosa", adopcion);
        } catch (AdopcionException e) {
            return new ResultadoAdopcion(false, e.getMessage());
        } catch (Exception e) {
            return new ResultadoAdopcion(false, "Error inesperado: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene todas las adopciones realizadas.
     * @return Lista de adopciones
     */
    public List<Adopcion> obtenerTodasLasAdopciones() {
        return adopcionServicio.obtenerTodasLasAdopciones();
    }
    
    /**
     * Actualiza una adopción existente.
     * @param adopcion La adopción con los datos actualizados
     * @return Resultado de la operación
     */
    public ResultadoAdopcion actualizarAdopcion(Adopcion adopcion) {
        try {
            Adopcion actualizada = adopcionServicio.actualizarAdopcion(adopcion);
            return new ResultadoAdopcion(true, "Adopción actualizada exitosamente", actualizada);
        } catch (AdopcionException e) {
            return new ResultadoAdopcion(false, e.getMessage());
        } catch (Exception e) {
            return new ResultadoAdopcion(false, "Error inesperado: " + e.getMessage());
        }
    }
    
    /**
     * Elimina una adopción.
     * @param id ID de la adopción a eliminar
     * @return Resultado de la operación
     */
    public ResultadoAdopcion eliminarAdopcion(Long id) {
        try {
            boolean eliminado = adopcionServicio.eliminarAdopcion(id);
            if (eliminado) {
                return new ResultadoAdopcion(true, "Adopción eliminada exitosamente");
            } else {
                return new ResultadoAdopcion(false, "No se pudo eliminar la adopción");
            }
        } catch (AdopcionException e) {
            return new ResultadoAdopcion(false, e.getMessage());
        } catch (Exception e) {
            return new ResultadoAdopcion(false, "Error inesperado: " + e.getMessage());
        }
    }
    
    public static class ResultadoAdopcion {
        private final boolean exitoso;
        private final String mensaje;
        private final Adopcion adopcion;
        
        public ResultadoAdopcion(boolean exitoso, String mensaje) {
            this(exitoso, mensaje, null);
        }
        
        public ResultadoAdopcion(boolean exitoso, String mensaje, Adopcion adopcion) {
            this.exitoso = exitoso;
            this.mensaje = mensaje;
            this.adopcion = adopcion;
        }
        
        public boolean isExitoso() {
            return exitoso;
        }
        
        public String getMensaje() {
            return mensaje;
        }
        
        public Adopcion getAdopcion() {
            return adopcion;
        }
    }
}
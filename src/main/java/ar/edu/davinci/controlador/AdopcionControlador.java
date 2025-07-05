package ar.edu.davinci.controlador;

import ar.edu.davinci.dao.*;
import ar.edu.davinci.modelo.*;
import ar.edu.davinci.servicio.SesionServicio;
import java.util.List;

/**
 * Controlador para manejar adopciones.
 */
public class AdopcionControlador {
    private final MascotaDAO mascotaDAO = new MascotaDAO();
    private final AdoptanteDAO adoptanteDAO = new AdoptanteDAO();
    private final AdopcionDAO adopcionDAO = new AdopcionDAO();
    private final SesionServicio sesionServicio = SesionServicio.obtenerInstancia();
    
    public List<Mascota> obtenerMascotasDisponibles() {
        return mascotaDAO.buscarTodas();
    }
    
    public String registrarMascota(String nombre, String tipo, double peso) {
        try {
            Mascota mascota = new Mascota(nombre, tipo, peso);
            mascotaDAO.guardar(mascota);
            return "Mascota registrada exitosamente";
        } catch (Exception e) {
            return "Error al registrar mascota: " + e.getMessage();
        }
    }
    
    public ResultadoAdopcion procesarAdopcion(Long mascotaId, String nombreAdoptante, 
            int edadAdoptante, String direccionAdoptante, String emailAdoptante, 
            String telefonoAdoptante) {
        
        try {
            if (!sesionServicio.haySesionActiva()) {
                return new ResultadoAdopcion(false, "No hay sesión activa");
            }
            
            Mascota mascota = mascotaDAO.buscarPorId(mascotaId);
            if (mascota == null) {
                return new ResultadoAdopcion(false, "Mascota no encontrada");
            }
            
            Adoptante adoptante = new Adoptante(nombreAdoptante, edadAdoptante, direccionAdoptante);
            adoptante.setEmail(emailAdoptante);
            adoptante.setTelefono(telefonoAdoptante);
            adoptante = adoptanteDAO.guardar(adoptante);
            
            Empleado empleado = sesionServicio.getEmpleadoActual();
            Adopcion adopcion = new Adopcion(adoptante, mascota, empleado);
            adopcionDAO.guardar(adopcion);
            
            // La mascota ya no estará disponible porque está en una adopción
            
            return new ResultadoAdopcion(true, "Adopción exitosa", adopcion);
            
        } catch (Exception e) {
            return new ResultadoAdopcion(false, "Error: " + e.getMessage());
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
package ar.edu.davinci.servicio;

import ar.edu.davinci.dao.*;
import ar.edu.davinci.excepciones.*;
import ar.edu.davinci.factory.MascotaFactory;
import ar.edu.davinci.modelo.*;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que encapsula la lógica de negocio para adopciones.
 * Separa la responsabilidad del controlador para mantener SRP.
 */
public class AdopcionServicio {
    private final MascotaDAO mascotaDAO = new MascotaDAO();
    private final AdoptanteDAO adoptanteDAO = new AdoptanteDAO();
    private final AdopcionDAO adopcionDAO = new AdopcionDAO();
    private final SesionServicio sesionServicio = SesionServicio.obtenerInstancia();
    
    /**
     * Obtiene todas las mascotas disponibles para adopción.
     * @return Lista de mascotas disponibles
     */
    public List<Mascota> obtenerMascotasDisponibles() {
        return mascotaDAO.buscarMascotasDisponibles();
    }
    
    /**
     * Registra una nueva mascota en el sistema.
     * @param nombre Nombre de la mascota
     * @param tipo Tipo de mascota
     * @param peso Peso de la mascota
     * @return La mascota registrada
     * @throws TipoMascotaInvalidoException Si el tipo no es válido
     * @throws NombreInvalidoException Si el nombre está vacío
     */
    public Mascota registrarMascota(String nombre, String tipo, double peso) 
            throws TipoMascotaInvalidoException, NombreInvalidoException {
        
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new NombreInvalidoException();
        }
        
        Mascota mascota = MascotaFactory.crearMascota(tipo, nombre.trim(), peso);
        return mascotaDAO.guardar(mascota);
    }
    
    /**
     * Procesa una nueva adopción.
     * @param mascotaId ID de la mascota a adoptar
     * @param nombreAdoptante Nombre del adoptante
     * @param edadAdoptante Edad del adoptante
     * @param direccionAdoptante Dirección del adoptante
     * @param emailAdoptante Email del adoptante (opcional)
     * @param telefonoAdoptante Teléfono del adoptante (opcional)
     * @return La adopción procesada
     * @throws SesionNoActivaException Si no hay sesión activa
     * @throws EntidadNoEncontradaException Si no se encuentra la mascota
     * @throws DatosInvalidosException Si los datos del adoptante son inválidos
     */
    public Adopcion procesarAdopcion(Long mascotaId, String nombreAdoptante, 
            int edadAdoptante, String direccionAdoptante, String emailAdoptante, 
            String telefonoAdoptante) throws AdopcionException {
        
        // Validar sesión
        if (!sesionServicio.haySesionActiva()) {
            throw new SesionNoActivaException();
        }
        
        // Validar datos del adoptante
        validarDatosAdoptante(nombreAdoptante, edadAdoptante, direccionAdoptante);
        
        // Buscar mascota
        Optional<Mascota> mascotaOpt = mascotaDAO.buscarPorId(mascotaId);
        if (!mascotaOpt.isPresent()) {
            throw new EntidadNoEncontradaException("Mascota", mascotaId);
        }
        
        // Crear y guardar adoptante
        Adoptante adoptante = new Adoptante(nombreAdoptante.trim(), edadAdoptante, direccionAdoptante.trim());
        if (emailAdoptante != null && !emailAdoptante.trim().isEmpty()) {
            adoptante.setEmail(emailAdoptante.trim());
        }
        if (telefonoAdoptante != null && !telefonoAdoptante.trim().isEmpty()) {
            adoptante.setTelefono(telefonoAdoptante.trim());
        }
        adoptante = adoptanteDAO.guardar(adoptante);
        
        // Crear y guardar adopción
        Empleado empleado = sesionServicio.getEmpleadoActual();
        Adopcion adopcion = new Adopcion(adoptante, mascotaOpt.get(), empleado);
        return adopcionDAO.guardar(adopcion);
    }
    
    /**
     * Obtiene todas las adopciones realizadas.
     * @return Lista de adopciones
     */
    public List<Adopcion> obtenerTodasLasAdopciones() {
        return adopcionDAO.buscarTodasConRelaciones();
    }
    
    /**
     * Busca una adopción por su ID.
     * @param id ID de la adopción
     * @return Optional con la adopción si existe
     */
    public Optional<Adopcion> buscarAdopcionPorId(Long id) {
        return adopcionDAO.buscarPorId(id);
    }
    
    /**
     * Actualiza una adopción existente.
     * @param adopcion La adopción con los datos actualizados
     * @return La adopción actualizada
     * @throws SesionNoActivaException Si no hay sesión activa
     * @throws EntidadNoEncontradaException Si no se encuentra la adopción
     */
    public Adopcion actualizarAdopcion(Adopcion adopcion) throws AdopcionException {
        if (!sesionServicio.haySesionActiva()) {
            throw new SesionNoActivaException();
        }
        
        if (!adopcionDAO.buscarPorId(adopcion.getId()).isPresent()) {
            throw new EntidadNoEncontradaException("Adopción", adopcion.getId());
        }
        
        // Actualizar primero el adoptante
        adoptanteDAO.actualizar(adopcion.getAdoptante());
        
        // Luego actualizar la adopción
        return adopcionDAO.actualizar(adopcion);
    }
    
    /**
     * Elimina una adopción.
     * @param id ID de la adopción a eliminar
     * @return true si se eliminó correctamente
     * @throws SesionNoActivaException Si no hay sesión activa
     * @throws EntidadNoEncontradaException Si no se encuentra la adopción
     */
    public boolean eliminarAdopcion(Long id) throws AdopcionException {
        if (!sesionServicio.haySesionActiva()) {
            throw new SesionNoActivaException();
        }
        
        if (!adopcionDAO.buscarPorId(id).isPresent()) {
            throw new EntidadNoEncontradaException("Adopción", id);
        }
        
        return adopcionDAO.eliminar(id);
    }
    
    /**
     * Valida los datos del adoptante.
     */
    private void validarDatosAdoptante(String nombre, int edad, String direccion) 
            throws DatosInvalidosException {
        
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatosInvalidosException("El nombre del adoptante no puede estar vacío");
        }
        
        if (edad < 18) {
            throw new DatosInvalidosException("El adoptante debe ser mayor de edad");
        }
        
        if (direccion == null || direccion.trim().isEmpty()) {
            throw new DatosInvalidosException("La dirección no puede estar vacía");
        }
    }
}
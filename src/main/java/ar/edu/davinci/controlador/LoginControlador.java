package ar.edu.davinci.controlador;

import ar.edu.davinci.dao.EmpleadoDAO;
import ar.edu.davinci.modelo.Empleado;
import ar.edu.davinci.servicio.SesionServicio;
import ar.edu.davinci.util.EncriptadorContrasena;
import java.util.Optional;

/**
 * Controlador para manejar la lógica de login.
 */
public class LoginControlador {
    private final EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    private final SesionServicio sesionServicio = SesionServicio.obtenerInstancia();
    
    public boolean iniciarSesion(String email, String contrasena) {
        String contrasenaEncriptada = EncriptadorContrasena.encriptar(contrasena);
        
        if (empleadoDAO.validarCredenciales(email, contrasenaEncriptada)) {
            Optional<Empleado> empleado = empleadoDAO.buscarPorEmail(email);
            if (empleado.isPresent()) {
                sesionServicio.iniciarSesion(empleado.get());
                return true;
            }
        }
        return false;
    }
    
    public String registrarEmpleado(String nombre, String email, String contrasena) {
        try {
            if (empleadoDAO.buscarPorEmail(email).isPresent()) {
                return "El email ya está registrado";
            }
            
            Empleado nuevoEmpleado = new Empleado(nombre, email, 
                EncriptadorContrasena.encriptar(contrasena));
            
            empleadoDAO.guardar(nuevoEmpleado);
            return "Registro exitoso";
            
        } catch (Exception e) {
            return "Error al registrar: " + e.getMessage();
        }
    }
    
    public void cerrarSesion() {
        sesionServicio.cerrarSesion();
    }
}
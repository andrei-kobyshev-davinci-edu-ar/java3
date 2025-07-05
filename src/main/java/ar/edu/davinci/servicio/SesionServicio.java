package ar.edu.davinci.servicio;

import ar.edu.davinci.modelo.Empleado;

/**
 * Servicio Singleton para mantener la sesión del empleado logueado.
 */
public class SesionServicio {
    private static SesionServicio instancia;
    private Empleado empleadoActual;
    
    private SesionServicio() {}
    
    public static synchronized SesionServicio obtenerInstancia() {
        if (instancia == null) {
            instancia = new SesionServicio();
        }
        return instancia;
    }
    
    public void iniciarSesion(Empleado empleado) {
        this.empleadoActual = empleado;
    }
    
    public void cerrarSesion() {
        this.empleadoActual = null;
    }
    
    public Empleado getEmpleadoActual() {
        return empleadoActual;
    }
    
    public boolean haySesionActiva() {
        return empleadoActual != null;
    }
}
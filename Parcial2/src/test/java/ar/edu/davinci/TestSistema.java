package ar.edu.davinci;

import ar.edu.davinci.controlador.*;
import ar.edu.davinci.dao.*;
import ar.edu.davinci.modelo.*;
import ar.edu.davinci.servicio.SesionServicio;
import ar.edu.davinci.util.EncriptadorContrasena;

/**
 * Clase de prueba del sistema.
 */
public class TestSistema {
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DEL SISTEMA DE ADOPCIÓN ===\n");
        
        // Inicializar BD
        ConexionBD.obtenerInstancia();
        
        // Probar login
        System.out.println("1. Probando login...");
        LoginControlador loginCtrl = new LoginControlador();
        boolean loginOk = loginCtrl.iniciarSesion("admin@veterinaria.com", "admin123");
        System.out.println("Login exitoso: " + loginOk);
        
        if (loginOk) {
            Empleado empleado = SesionServicio.obtenerInstancia().getEmpleadoActual();
            System.out.println("Empleado logueado: " + empleado.getNombre());
        }
        
        // Probar registro de mascota
        System.out.println("\n2. Probando registro de mascota...");
        AdopcionControlador adopcionCtrl = new AdopcionControlador();
        String resultado = adopcionCtrl.registrarMascota("Firulais", "Perro", 12.5);
        System.out.println("Resultado: " + resultado);
        
        // Listar mascotas
        System.out.println("\n3. Mascotas disponibles:");
        for (Mascota m : adopcionCtrl.obtenerMascotasDisponibles()) {
            System.out.println("- " + m.getNombre() + " (" + m.getTipo() + ") - " + m.getPeso() + " kg");
        }
        
        // Probar adopción
        System.out.println("\n4. Probando proceso de adopción...");
        Mascota primeraMascota = adopcionCtrl.obtenerMascotasDisponibles().get(0);
        AdopcionControlador.ResultadoAdopcion resultadoAdopcion = 
            adopcionCtrl.procesarAdopcion(
                primeraMascota.getId(),
                "Juan Pérez",
                30,
                "Av. Siempre Viva 123",
                "juan@email.com",
                "555-1234"
            );
        
        System.out.println("Adopción exitosa: " + resultadoAdopcion.isExitoso());
        System.out.println("Mensaje: " + resultadoAdopcion.getMensaje());
        
        if (resultadoAdopcion.isExitoso()) {
            Adopcion adopcion = resultadoAdopcion.getAdopcion();
            System.out.println("\nDETALLES DE LA ADOPCIÓN:");
            System.out.println("Mascota: " + adopcion.getMascota().getNombre());
            System.out.println("Adoptante: " + adopcion.getAdoptante().getNombre());
            System.out.println("Empleado: " + adopcion.getEmpleado().getNombre());
            System.out.println("Fecha: " + adopcion.getFechaAdopcion());
        }
        
        System.out.println("\n=== FIN DE LA PRUEBA ===");
    }
}
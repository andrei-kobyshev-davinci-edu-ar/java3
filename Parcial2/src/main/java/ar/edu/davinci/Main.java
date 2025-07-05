package ar.edu.davinci;

import ar.edu.davinci.dao.ConexionBD;
import ar.edu.davinci.vista.VentanaLogin;
import javax.swing.*;

/**
 * Clase principal de la aplicación.
 */
public class Main {
    public static void main(String[] args) {
        // Inicializar la base de datos
        ConexionBD.obtenerInstancia();
        
        // Configurar Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Mostrar ventana de login
        SwingUtilities.invokeLater(() -> {
            VentanaLogin ventana = new VentanaLogin();
            ventana.setVisible(true);
        });
    }
}
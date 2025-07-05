package ar.edu.davinci.dao;

import ar.edu.davinci.modelo.Adopcion;
import java.sql.*;

/**
 * DAO para adopciones.
 */
public class AdopcionDAO {
    private final ConexionBD conexionBD = ConexionBD.obtenerInstancia();
    
    public Adopcion guardar(Adopcion adopcion) {
        String sql = "INSERT INTO adopciones (adoptante_id, mascota_id, empleado_id) VALUES (?, ?, ?)";
        
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setLong(1, adopcion.getAdoptante().getId());
            pstmt.setLong(2, adopcion.getMascota().getId());
            pstmt.setLong(3, adopcion.getEmpleado().getId());
            
            pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                adopcion.setId(generatedKeys.getLong(1));
            }
            
            return adopcion;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar adopción", e);
        }
    }
}
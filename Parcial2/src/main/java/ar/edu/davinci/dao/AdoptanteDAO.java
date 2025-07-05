package ar.edu.davinci.dao;

import ar.edu.davinci.modelo.Adoptante;
import java.sql.*;

/**
 * DAO para adoptantes.
 */
public class AdoptanteDAO {
    private final ConexionBD conexionBD = ConexionBD.obtenerInstancia();
    
    public Adoptante guardar(Adoptante adoptante) {
        String sql = "INSERT INTO adoptantes (nombre, edad, direccion, email, telefono) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, adoptante.getNombre());
            pstmt.setInt(2, adoptante.getEdad());
            pstmt.setString(3, adoptante.getDireccion());
            pstmt.setString(4, adoptante.getEmail());
            pstmt.setString(5, adoptante.getTelefono());
            
            pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                adoptante.setId(generatedKeys.getLong(1));
            }
            
            return adoptante;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar adoptante", e);
        }
    }
}
package ar.edu.davinci.dao;

import ar.edu.davinci.modelo.Empleado;
import java.sql.*;
import java.util.Optional;

/**
 * DAO para empleados.
 */
public class EmpleadoDAO {
    private final ConexionBD conexionBD = ConexionBD.obtenerInstancia();
    
    public Optional<Empleado> buscarPorEmail(String email) {
        String sql = "SELECT * FROM empleados WHERE email = ?";
        
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setId(rs.getLong("id"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setEmail(rs.getString("email"));
                empleado.setContrasena(rs.getString("contrasena"));
                return Optional.of(empleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    public boolean validarCredenciales(String email, String contrasena) {
        String sql = "SELECT COUNT(*) FROM empleados WHERE email = ? AND contrasena = ?";
        
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            pstmt.setString(2, contrasena);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public Empleado guardar(Empleado empleado) {
        String sql = "INSERT INTO empleados (nombre, email, contrasena) VALUES (?, ?, ?)";
        
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, empleado.getNombre());
            pstmt.setString(2, empleado.getEmail());
            pstmt.setString(3, empleado.getContrasena());
            
            pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                empleado.setId(generatedKeys.getLong(1));
            }
            
            return empleado;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar empleado", e);
        }
    }
}
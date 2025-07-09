package ar.edu.davinci.dao;

import ar.edu.davinci.excepciones.BaseDatosException;
import ar.edu.davinci.modelo.Empleado;
import java.sql.*;
import java.util.Optional;

/**
 * DAO para empleados.
 */
public class EmpleadoDAO extends BaseDAO<Empleado, Long> {
    
    public Optional<Empleado> buscarPorEmail(String email) {
        String sql = "SELECT * FROM empleados WHERE email = ?";
        
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapearDesdeResultSet(rs));
            }
        } catch (SQLException e) {
            throw new BaseDatosException("Error al buscar empleado por email", e);
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
            throw new BaseDatosException("Error al validar credenciales", e);
        }
        
        return false;
    }
    
    @Override
    protected String getNombreTabla() {
        return "empleados";
    }
    
    @Override
    protected String getColumnaId() {
        return "id";
    }
    
    @Override
    protected Empleado mapearDesdeResultSet(ResultSet rs) throws SQLException {
        Empleado empleado = new Empleado();
        empleado.setId(rs.getLong("id"));
        empleado.setNombre(rs.getString("nombre"));
        empleado.setEmail(rs.getString("email"));
        empleado.setContrasena(rs.getString("contrasena"));
        return empleado;
    }
    
    @Override
    protected void prepararInsercion(PreparedStatement pstmt, Empleado empleado) throws SQLException {
        pstmt.setString(1, empleado.getNombre());
        pstmt.setString(2, empleado.getEmail());
        pstmt.setString(3, empleado.getContrasena());
    }
    
    @Override
    protected void prepararActualizacion(PreparedStatement pstmt, Empleado empleado) throws SQLException {
        pstmt.setString(1, empleado.getNombre());
        pstmt.setString(2, empleado.getEmail());
        pstmt.setString(3, empleado.getContrasena());
        pstmt.setLong(4, empleado.getId());
    }
    
    @Override
    protected Long obtenerId(Empleado empleado) {
        return empleado.getId();
    }
    
    @Override
    protected void establecerId(Empleado empleado, Long id) {
        empleado.setId(id);
    }
    
    @Override
    protected String getSqlCrearTabla() {
        return "CREATE TABLE IF NOT EXISTS empleados (" +
               "    id BIGINT AUTO_INCREMENT PRIMARY KEY," +
               "    nombre VARCHAR(100) NOT NULL," +
               "    email VARCHAR(100) UNIQUE NOT NULL," +
               "    contrasena VARCHAR(255) NOT NULL" +
               ")";
    }
    
    @Override
    protected String construirSqlInsercion() {
        return "INSERT INTO empleados (nombre, email, contrasena) VALUES (?, ?, ?)";
    }
    
    @Override
    protected String construirSqlActualizacion() {
        return "UPDATE empleados SET nombre = ?, email = ?, contrasena = ? WHERE id = ?";
    }
}
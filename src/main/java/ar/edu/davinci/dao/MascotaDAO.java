package ar.edu.davinci.dao;

import ar.edu.davinci.modelo.Mascota;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para mascotas.
 */
public class MascotaDAO {
    private final ConexionBD conexionBD = ConexionBD.obtenerInstancia();
    
    public List<Mascota> buscarTodas() {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT m.* FROM mascotas m " +
                     "WHERE NOT EXISTS (SELECT 1 FROM adopciones a WHERE a.mascota_id = m.id) " +
                     "ORDER BY m.nombre";
        
        try (Connection conexion = conexionBD.obtenerConexion();
             Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Mascota mascota = new Mascota();
                mascota.setId(rs.getLong("id"));
                mascota.setNombre(rs.getString("nombre"));
                mascota.setTipo(rs.getString("tipo"));
                mascota.setPeso(rs.getDouble("peso"));
                mascotas.add(mascota);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return mascotas;
    }
    
    public Mascota buscarPorId(Long id) {
        String sql = "SELECT * FROM mascotas WHERE id = ?";
        
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Mascota mascota = new Mascota();
                mascota.setId(rs.getLong("id"));
                mascota.setNombre(rs.getString("nombre"));
                mascota.setTipo(rs.getString("tipo"));
                mascota.setPeso(rs.getDouble("peso"));
                return mascota;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public Mascota guardar(Mascota mascota) {
        String sql = "INSERT INTO mascotas (nombre, tipo, peso) VALUES (?, ?, ?)";
        
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, mascota.getNombre());
            pstmt.setString(2, mascota.getTipo());
            pstmt.setDouble(3, mascota.getPeso());
            
            pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                mascota.setId(generatedKeys.getLong(1));
            }
            
            return mascota;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar mascota", e);
        }
    }
    
    // Ya no necesitamos eliminar mascotas, solo se marcan como adoptadas
}
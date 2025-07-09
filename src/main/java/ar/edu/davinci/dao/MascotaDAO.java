package ar.edu.davinci.dao;

import ar.edu.davinci.modelo.*;
import ar.edu.davinci.excepciones.BaseDatosException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DAO para mascotas.
 */
public class MascotaDAO extends BaseDAO<Mascota, Long> {
    
    public List<Mascota> buscarMascotasDisponibles() {
        List<Mascota> mascotas = new ArrayList<>();
        String sql = "SELECT m.* FROM mascotas m " +
                     "WHERE NOT EXISTS (SELECT 1 FROM adopciones a WHERE a.mascota_id = m.id) " +
                     "ORDER BY m.nombre";
        
        try (Connection conexion = conexionBD.obtenerConexion();
             Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Mascota mascota = mapearDesdeResultSet(rs);
                mascotas.add(mascota);
            }
        } catch (SQLException e) {
            throw new BaseDatosException("Error al buscar mascotas disponibles", e);
        }
        
        return mascotas;
    }
    
    @Override
    protected String getNombreTabla() {
        return "mascotas";
    }
    
    @Override
    protected String getColumnaId() {
        return "id";
    }
    
    @Override
    protected Mascota mapearDesdeResultSet(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        String nombre = rs.getString("nombre");
        double peso = rs.getDouble("peso");
        
        Mascota mascota;
        switch (tipo.toLowerCase()) {
            case "perro":
                mascota = new Perro(nombre, peso);
                break;
            case "gato":
                mascota = new Gato(nombre, peso);
                break;
            case "conejo":
                mascota = new Conejo(nombre, peso);
                break;
            case "pajaro":
            case "pájaro":
                mascota = new Pajaro(nombre, peso);
                break;
            default:
                // Por compatibilidad, creamos un Perro por defecto
                mascota = new Perro(nombre, peso);
                mascota.setTipo(tipo);
        }
        
        mascota.setId(rs.getLong("id"));
        return mascota;
    }
    
    @Override
    protected void prepararInsercion(PreparedStatement pstmt, Mascota mascota) throws SQLException {
        pstmt.setString(1, mascota.getNombre());
        pstmt.setString(2, mascota.getTipo());
        pstmt.setDouble(3, mascota.getPeso());
    }
    
    @Override
    protected void prepararActualizacion(PreparedStatement pstmt, Mascota mascota) throws SQLException {
        pstmt.setString(1, mascota.getNombre());
        pstmt.setString(2, mascota.getTipo());
        pstmt.setDouble(3, mascota.getPeso());
        pstmt.setLong(4, mascota.getId());
    }
    
    @Override
    protected Long obtenerId(Mascota mascota) {
        return mascota.getId();
    }
    
    @Override
    protected void establecerId(Mascota mascota, Long id) {
        mascota.setId(id);
    }
    
    @Override
    protected String getSqlCrearTabla() {
        return "CREATE TABLE IF NOT EXISTS mascotas (" +
               "    id BIGINT AUTO_INCREMENT PRIMARY KEY," +
               "    nombre VARCHAR(100) NOT NULL," +
               "    tipo VARCHAR(50) NOT NULL," +
               "    peso DECIMAL(10, 2) NOT NULL" +
               ")";
    }
    
    @Override
    protected String construirSqlInsercion() {
        return "INSERT INTO mascotas (nombre, tipo, peso) VALUES (?, ?, ?)";
    }
    
    @Override
    protected String construirSqlActualizacion() {
        return "UPDATE mascotas SET nombre = ?, tipo = ?, peso = ? WHERE id = ?";
    }
}
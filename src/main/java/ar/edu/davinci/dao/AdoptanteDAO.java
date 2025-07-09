package ar.edu.davinci.dao;

import ar.edu.davinci.modelo.Adoptante;
import java.sql.*;

/**
 * DAO para adoptantes.
 */
public class AdoptanteDAO extends BaseDAO<Adoptante, Long> {
    
    @Override
    protected String getNombreTabla() {
        return "adoptantes";
    }
    
    @Override
    protected String getColumnaId() {
        return "id";
    }
    
    @Override
    protected Adoptante mapearDesdeResultSet(ResultSet rs) throws SQLException {
        Adoptante adoptante = new Adoptante();
        adoptante.setId(rs.getLong("id"));
        adoptante.setNombre(rs.getString("nombre"));
        adoptante.setEdad(rs.getInt("edad"));
        adoptante.setDireccion(rs.getString("direccion"));
        adoptante.setEmail(rs.getString("email"));
        adoptante.setTelefono(rs.getString("telefono"));
        return adoptante;
    }
    
    @Override
    protected void prepararInsercion(PreparedStatement pstmt, Adoptante adoptante) throws SQLException {
        pstmt.setString(1, adoptante.getNombre());
        pstmt.setInt(2, adoptante.getEdad());
        pstmt.setString(3, adoptante.getDireccion());
        pstmt.setString(4, adoptante.getEmail());
        pstmt.setString(5, adoptante.getTelefono());
    }
    
    @Override
    protected void prepararActualizacion(PreparedStatement pstmt, Adoptante adoptante) throws SQLException {
        pstmt.setString(1, adoptante.getNombre());
        pstmt.setInt(2, adoptante.getEdad());
        pstmt.setString(3, adoptante.getDireccion());
        pstmt.setString(4, adoptante.getEmail());
        pstmt.setString(5, adoptante.getTelefono());
        pstmt.setLong(6, adoptante.getId());
    }
    
    @Override
    protected Long obtenerId(Adoptante adoptante) {
        return adoptante.getId();
    }
    
    @Override
    protected void establecerId(Adoptante adoptante, Long id) {
        adoptante.setId(id);
    }
    
    @Override
    protected String getSqlCrearTabla() {
        return "CREATE TABLE IF NOT EXISTS adoptantes (" +
               "    id BIGINT AUTO_INCREMENT PRIMARY KEY," +
               "    nombre VARCHAR(100) NOT NULL," +
               "    edad INTEGER NOT NULL," +
               "    direccion VARCHAR(255) NOT NULL," +
               "    email VARCHAR(100)," +
               "    telefono VARCHAR(20)" +
               ")";
    }
    
    @Override
    protected String construirSqlInsercion() {
        return "INSERT INTO adoptantes (nombre, edad, direccion, email, telefono) VALUES (?, ?, ?, ?, ?)";
    }
    
    @Override
    protected String construirSqlActualizacion() {
        return "UPDATE adoptantes SET nombre = ?, edad = ?, direccion = ?, email = ?, telefono = ? WHERE id = ?";
    }
}
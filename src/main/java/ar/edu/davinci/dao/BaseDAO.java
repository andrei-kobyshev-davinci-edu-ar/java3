package ar.edu.davinci.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Clase abstracta base para todos los DAOs.
 * Implementa operaciones comunes para evitar código repetido.
 * @param <T> Tipo de entidad
 * @param <ID> Tipo del identificador
 */
public abstract class BaseDAO<T, ID> implements DAO<T, ID> {
    protected final ConexionBD conexionBD = ConexionBD.obtenerInstancia();
    
    /**
     * Obtiene el nombre de la tabla.
     * @return Nombre de la tabla
     */
    protected abstract String getNombreTabla();
    
    /**
     * Obtiene el nombre de la columna ID.
     * @return Nombre de la columna ID
     */
    protected abstract String getColumnaId();
    
    /**
     * Mapea un ResultSet a una entidad.
     * @param rs ResultSet con los datos
     * @return La entidad mapeada
     * @throws SQLException Si ocurre un error al leer los datos
     */
    protected abstract T mapearDesdeResultSet(ResultSet rs) throws SQLException;
    
    /**
     * Prepara un PreparedStatement para insertar una entidad.
     * @param pstmt PreparedStatement a preparar
     * @param entidad Entidad a insertar
     * @throws SQLException Si ocurre un error al preparar
     */
    protected abstract void prepararInsercion(PreparedStatement pstmt, T entidad) throws SQLException;
    
    /**
     * Prepara un PreparedStatement para actualizar una entidad.
     * @param pstmt PreparedStatement a preparar
     * @param entidad Entidad a actualizar
     * @throws SQLException Si ocurre un error al preparar
     */
    protected abstract void prepararActualizacion(PreparedStatement pstmt, T entidad) throws SQLException;
    
    /**
     * Obtiene el ID de una entidad.
     * @param entidad La entidad
     * @return El ID de la entidad
     */
    protected abstract ID obtenerId(T entidad);
    
    /**
     * Establece el ID de una entidad.
     * @param entidad La entidad
     * @param id El ID a establecer
     */
    protected abstract void establecerId(T entidad, ID id);
    
    /**
     * Obtiene el SQL para crear la tabla.
     * @return SQL de creación de tabla
     */
    protected abstract String getSqlCrearTabla();
    
    @Override
    public T guardar(T entidad) {
        String sql = construirSqlInsercion();
        
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            prepararInsercion(pstmt, entidad);
            pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                @SuppressWarnings("unchecked")
                ID id = (ID) Long.valueOf(generatedKeys.getLong(1));
                establecerId(entidad, id);
            }
            
            return entidad;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar " + getNombreTabla(), e);
        }
    }
    
    @Override
    public T actualizar(T entidad) {
        String sql = construirSqlActualizacion();
        
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            prepararActualizacion(pstmt, entidad);
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas == 0) {
                throw new RuntimeException("No se encontró la entidad para actualizar");
            }
            
            return entidad;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar " + getNombreTabla(), e);
        }
    }
    
    @Override
    public boolean eliminar(ID id) {
        String sql = "DELETE FROM " + getNombreTabla() + " WHERE " + getColumnaId() + " = ?";
        
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            pstmt.setObject(1, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar de " + getNombreTabla(), e);
        }
    }
    
    @Override
    public Optional<T> buscarPorId(ID id) {
        String sql = "SELECT * FROM " + getNombreTabla() + " WHERE " + getColumnaId() + " = ?";
        
        try (Connection conexion = conexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            pstmt.setObject(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapearDesdeResultSet(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar por ID en " + getNombreTabla(), e);
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<T> buscarTodos() {
        List<T> resultados = new ArrayList<>();
        String sql = "SELECT * FROM " + getNombreTabla() + " ORDER BY " + getColumnaId();
        
        try (Connection conexion = conexionBD.obtenerConexion();
             Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                resultados.add(mapearDesdeResultSet(rs));
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar todos en " + getNombreTabla(), e);
        }
        
        return resultados;
    }
    
    @Override
    public void crearTabla() {
        try (Connection conexion = conexionBD.obtenerConexion();
             Statement stmt = conexion.createStatement()) {
            
            stmt.execute(getSqlCrearTabla());
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear tabla " + getNombreTabla(), e);
        }
    }
    
    /**
     * Construye el SQL de inserción basado en la implementación de prepararInsercion.
     * Las subclases deben sobrescribir este método.
     * @return SQL de inserción
     */
    protected abstract String construirSqlInsercion();
    
    /**
     * Construye el SQL de actualización basado en la implementación de prepararActualizacion.
     * Las subclases deben sobrescribir este método.
     * @return SQL de actualización
     */
    protected abstract String construirSqlActualizacion();
}
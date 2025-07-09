package ar.edu.davinci.dao;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz genérica para DAOs.
 * Define las operaciones básicas CRUD.
 * @param <T> Tipo de entidad
 * @param <ID> Tipo del identificador
 */
public interface DAO<T, ID> {
    /**
     * Guarda una entidad en la base de datos.
     * @param entidad La entidad a guardar
     * @return La entidad guardada con su ID asignado
     */
    T guardar(T entidad);
    
    /**
     * Actualiza una entidad existente.
     * @param entidad La entidad a actualizar
     * @return La entidad actualizada
     */
    T actualizar(T entidad);
    
    /**
     * Elimina una entidad por su ID.
     * @param id El ID de la entidad a eliminar
     * @return true si se eliminó correctamente
     */
    boolean eliminar(ID id);
    
    /**
     * Busca una entidad por su ID.
     * @param id El ID de la entidad
     * @return Optional con la entidad si existe
     */
    Optional<T> buscarPorId(ID id);
    
    /**
     * Busca todas las entidades.
     * @return Lista con todas las entidades
     */
    List<T> buscarTodos();
    
    /**
     * Crea la tabla si no existe.
     */
    void crearTabla();
}
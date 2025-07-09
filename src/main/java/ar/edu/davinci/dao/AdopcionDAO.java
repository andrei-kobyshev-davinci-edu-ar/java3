package ar.edu.davinci.dao;

import ar.edu.davinci.modelo.*;
import ar.edu.davinci.excepciones.BaseDatosException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para adopciones.
 */
public class AdopcionDAO extends BaseDAO<Adopcion, Long> {
    private final EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    private final AdoptanteDAO adoptanteDAO = new AdoptanteDAO();
    private final MascotaDAO mascotaDAO = new MascotaDAO();
    
    @Override
    protected String getNombreTabla() {
        return "adopciones";
    }
    
    @Override
    protected String getColumnaId() {
        return "id";
    }
    
    @Override
    protected Adopcion mapearDesdeResultSet(ResultSet rs) throws SQLException {
        Adopcion adopcion = new Adopcion();
        adopcion.setId(rs.getLong("id"));
        adopcion.setFechaAdopcion(rs.getTimestamp("fecha_adopcion").toLocalDateTime());
        
        // Cargar las entidades relacionadas
        Long empleadoId = rs.getLong("empleado_id");
        Long adoptanteId = rs.getLong("adoptante_id");
        Long mascotaId = rs.getLong("mascota_id");
        
        empleadoDAO.buscarPorId(empleadoId).ifPresent(adopcion::setEmpleado);
        adoptanteDAO.buscarPorId(adoptanteId).ifPresent(adopcion::setAdoptante);
        mascotaDAO.buscarPorId(mascotaId).ifPresent(adopcion::setMascota);
        
        return adopcion;
    }
    
    @Override
    protected void prepararInsercion(PreparedStatement pstmt, Adopcion adopcion) throws SQLException {
        pstmt.setLong(1, adopcion.getAdoptante().getId());
        pstmt.setLong(2, adopcion.getMascota().getId());
        pstmt.setLong(3, adopcion.getEmpleado().getId());
    }
    
    @Override
    protected void prepararActualizacion(PreparedStatement pstmt, Adopcion adopcion) throws SQLException {
        pstmt.setLong(1, adopcion.getAdoptante().getId());
        pstmt.setLong(2, adopcion.getMascota().getId());
        pstmt.setLong(3, adopcion.getEmpleado().getId());
        pstmt.setTimestamp(4, Timestamp.valueOf(adopcion.getFechaAdopcion()));
        pstmt.setLong(5, adopcion.getId());
    }
    
    @Override
    protected Long obtenerId(Adopcion adopcion) {
        return adopcion.getId();
    }
    
    @Override
    protected void establecerId(Adopcion adopcion, Long id) {
        adopcion.setId(id);
    }
    
    @Override
    protected String getSqlCrearTabla() {
        return "CREATE TABLE IF NOT EXISTS adopciones (" +
               "    id BIGINT AUTO_INCREMENT PRIMARY KEY," +
               "    adoptante_id BIGINT NOT NULL," +
               "    mascota_id BIGINT NOT NULL," +
               "    empleado_id BIGINT NOT NULL," +
               "    fecha_adopcion TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
               "    FOREIGN KEY (adoptante_id) REFERENCES adoptantes(id)," +
               "    FOREIGN KEY (mascota_id) REFERENCES mascotas(id) ON DELETE CASCADE," +
               "    FOREIGN KEY (empleado_id) REFERENCES empleados(id)" +
               ")";
    }
    
    @Override
    protected String construirSqlInsercion() {
        return "INSERT INTO adopciones (adoptante_id, mascota_id, empleado_id) VALUES (?, ?, ?)";
    }
    
    @Override
    protected String construirSqlActualizacion() {
        return "UPDATE adopciones SET adoptante_id = ?, mascota_id = ?, empleado_id = ?, fecha_adopcion = ? WHERE id = ?";
    }
    
    /**
     * Busca todas las adopciones con sus entidades relacionadas.
     * @return Lista de adopciones completas
     */
    public List<Adopcion> buscarTodasConRelaciones() {
        return buscarTodos();
    }
}
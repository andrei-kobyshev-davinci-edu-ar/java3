package ar.edu.davinci.dao;

import java.sql.*;

/**
 * Gestiona la conexión con la base de datos H2.
 */
public class ConexionBD {
    private static ConexionBD instancia;
    private static final String URL = "jdbc:h2:./data/veterinaria;DB_CLOSE_DELAY=-1";
    private static final String USUARIO = "sa";
    private static final String CONTRASENA = "";
    
    private ConexionBD() {
        inicializarBD();
    }
    
    public static synchronized ConexionBD obtenerInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }
    
    public Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
    
    private void inicializarBD() {
        try (Connection conexion = obtenerConexion();
             Statement stmt = conexion.createStatement()) {
            
            // Crear tablas directamente aquí para evitar dependencia circular
            // Tabla empleados
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS empleados (" +
                "    id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "    nombre VARCHAR(100) NOT NULL," +
                "    email VARCHAR(100) UNIQUE NOT NULL," +
                "    contrasena VARCHAR(255) NOT NULL" +
                ")"
            );
            
            // Tabla mascotas
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS mascotas (" +
                "    id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "    nombre VARCHAR(100) NOT NULL," +
                "    tipo VARCHAR(50) NOT NULL," +
                "    peso DECIMAL(10, 2) NOT NULL" +
                ")"
            );
            
            // Tabla adoptantes
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS adoptantes (" +
                "    id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "    nombre VARCHAR(100) NOT NULL," +
                "    edad INTEGER NOT NULL," +
                "    direccion VARCHAR(255) NOT NULL," +
                "    email VARCHAR(100)," +
                "    telefono VARCHAR(20)" +
                ")"
            );
            
            // Tabla adopciones
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS adopciones (" +
                "    id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "    adoptante_id BIGINT NOT NULL," +
                "    mascota_id BIGINT NOT NULL," +
                "    empleado_id BIGINT NOT NULL," +
                "    fecha_adopcion TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "    FOREIGN KEY (adoptante_id) REFERENCES adoptantes(id)," +
                "    FOREIGN KEY (mascota_id) REFERENCES mascotas(id) ON DELETE CASCADE," +
                "    FOREIGN KEY (empleado_id) REFERENCES empleados(id)" +
                ")"
            );
            
            // Insertar datos de prueba
            insertarDatosPrueba();
            
        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar la base de datos", e);
        }
    }
    
    private void insertarDatosPrueba() {
        try (Connection conexion = obtenerConexion();
             Statement stmt = conexion.createStatement()) {
            
            // Insertar empleado de prueba si no existe
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM empleados");
            rs.next();
            if (rs.getInt(1) == 0) {
                stmt.execute(
                    "INSERT INTO empleados (nombre, email, contrasena) VALUES " +
                    "('Admin', 'admin@veterinaria.com', '" + 
                    "240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9" + // admin123
                    "')"
                );
            }
            
            // Insertar mascotas de prueba si no existen
            rs = stmt.executeQuery("SELECT COUNT(*) FROM mascotas");
            rs.next();
            if (rs.getInt(1) == 0) {
                stmt.execute(
                    "INSERT INTO mascotas (nombre, tipo, peso) VALUES " +
                    "('Max', 'Perro', 15.5), " +
                    "('Luna', 'Gato', 4.2), " +
                    "('Coco', 'Conejo', 2.1), " +
                    "('Pipo', 'Pajaro', 0.3), " +
                    "('Rocky', 'Perro', 25.0), " +
                    "('Michi', 'Gato', 3.8)"
                );
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar datos de prueba", e);
        }
    }
}
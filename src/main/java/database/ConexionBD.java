package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {
    private static final String URL = "jdbc:sqlite:tareas.db";
    private static Connection conexion = null;
    
    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL);
                crearTablas();
            }
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return conexion;
    }
    
    private static void crearTablas() {
        try (Statement stmt = conexion.createStatement()) {
            //tabla usuarios
            String sqlUsuarios = "CREATE TABLE IF NOT EXISTS usuarios (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL UNIQUE," +
                    "password TEXT NOT NULL)";
            stmt.execute(sqlUsuarios);
            
            //tabla tareas
            String sqlTareas = "CREATE TABLE IF NOT EXISTS tareas (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "titulo TEXT NOT NULL," +
                    "descripcion TEXT," +
                    "fecha_limite TEXT," +
                    "completada INTEGER DEFAULT 0," +
                    "usuario_id INTEGER," +
                    "FOREIGN KEY (usuario_id) REFERENCES usuarios(id))";
            stmt.execute(sqlTareas);
            
            //usuario admin
            String sqlUsuarioDefault = "INSERT OR IGNORE INTO usuarios (id, username, password) " +
                    "VALUES (1, 'admin', 'admin123')";
            stmt.execute(sqlUsuarioDefault);
            
            System.out.println("Tablas creadas/verificadas correctamente");
        } catch (SQLException e) {
            System.err.println("Error al crear tablas: " + e.getMessage());
        }
    }
    
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}
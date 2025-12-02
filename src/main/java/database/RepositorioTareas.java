package database;

import modelo.Tarea;
import modelo.Usuario;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioTareas implements IRepositorio {
    
    //Validar login
    @Override
    public Usuario validarUsuario(String username, String password) {
        String sql = "SELECT * FROM usuarios WHERE username = ?";
    
        try (Connection conn = ConexionBD.getConexion();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
        
            if (rs.next()) {
                String passwordBD = rs.getString("password");
            
                // Intentar comparar directamente (texto plano)
                if (passwordBD.equals(password)) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("username"),
                        passwordBD
                    );
                }
            
                // Intentar comparar con Base64 (encriptada)
                String passwordEncriptada = java.util.Base64.getEncoder()
                .encodeToString(password.getBytes());
            
                if (passwordBD.equals(passwordEncriptada)) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("username"),
                        passwordBD
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al validar usuario: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public boolean registrarUsuario(String username, String password) {
    // Verificar si el usuario ya existe
    String sqlVerificar = "SELECT COUNT(*) FROM usuarios WHERE username = ?";
    
    try (Connection conn = ConexionBD.getConexion();
         PreparedStatement pstmtVerificar = conn.prepareStatement(sqlVerificar)) {
        
        pstmtVerificar.setString(1, username);
        ResultSet rs = pstmtVerificar.executeQuery();
        
        if (rs.next() && rs.getInt(1) > 0) {
            return false; // Usuario ya existe
        }
    } catch (SQLException e) {
        System.err.println("Error al verificar usuario: " + e.getMessage());
        return false;
    }
    
    // Encriptación básica (Base64)
    String passwordEncriptada = java.util.Base64.getEncoder()
        .encodeToString(password.getBytes());
    
    // Insertar nuevo usuario
    String sqlInsertar = "INSERT INTO usuarios (username, password) VALUES (?, ?)";
    
    try (Connection conn = ConexionBD.getConexion();
         PreparedStatement pstmtInsertar = conn.prepareStatement(sqlInsertar)) {
        
        pstmtInsertar.setString(1, username);
        pstmtInsertar.setString(2, passwordEncriptada);
        
        return pstmtInsertar.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error al registrar usuario: " + e.getMessage());
        return false;
    }
}
    
    //Crear tarea
    @Override
    public boolean crearTarea(Tarea tarea) {
        String sql = "INSERT INTO tareas (titulo, descripcion, fecha_limite, completada, usuario_id) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, tarea.getTitulo());
            pstmt.setString(2, tarea.getDescripcion());
            pstmt.setString(3, tarea.getFechaLimite().toString());
            pstmt.setInt(4, tarea.isCompletada() ? 1 : 0);
            pstmt.setInt(5, tarea.getUsuarioId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al crear tarea: " + e.getMessage());
            return false;
        }
    }
    
    //Obtener todas las tareas
    @Override
    public List<Tarea> obtenerTareasPorUsuario(int usuarioId) {
        List<Tarea> tareas = new ArrayList<>();
        String sql = "SELECT * FROM tareas WHERE usuario_id = ? ORDER BY fecha_limite";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, usuarioId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Tarea tarea = new Tarea(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("descripcion"),
                    LocalDate.parse(rs.getString("fecha_limite")),
                    rs.getInt("completada") == 1,
                    rs.getInt("usuario_id")
                );
                tareas.add(tarea);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener tareas: " + e.getMessage());
        }
        return tareas;
    }
    
    //Actualizar estado de tarea
    @Override
    public boolean actualizarEstadoTarea(int tareaId, boolean completada) {
        String sql = "UPDATE tareas SET completada = ? WHERE id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, completada ? 1 : 0);
            pstmt.setInt(2, tareaId);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar tarea: " + e.getMessage());
            return false;
        }
    }
    
    //Eliminar una tarea
    @Override
    public boolean eliminarTarea(int tareaId) {
        String sql = "DELETE FROM tareas WHERE id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, tareaId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar tarea: " + e.getMessage());
            return false;
        }
    }
    
    //Actualizar tarea
    @Override
    public boolean actualizarTarea(Tarea tarea) {
        String sql = "UPDATE tareas SET titulo = ?, descripcion = ?, fecha_limite = ? WHERE id = ?";
        
        try (Connection conn = ConexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, tarea.getTitulo());
            pstmt.setString(2, tarea.getDescripcion());
            pstmt.setString(3, tarea.getFechaLimite().toString());
            pstmt.setInt(4, tarea.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar tarea: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public List<Tarea> buscarTareasPorTexto(int usuarioId, String texto) {
        List<Tarea> tareas = new ArrayList<>();
        String sql = "SELECT * FROM tareas WHERE usuario_id = ? " +
                 "AND (titulo LIKE ? OR descripcion LIKE ?) " +
                 "ORDER BY fecha_limite";
    
        try (Connection conn = ConexionBD.getConexion();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
            pstmt.setInt(1, usuarioId);
            pstmt.setString(2, "%" + texto + "%");
            pstmt.setString(3, "%" + texto + "%");
        
            ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            Tarea tarea = new Tarea(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("descripcion"),
                LocalDate.parse(rs.getString("fecha_limite")),
                rs.getInt("completada") == 1,
                rs.getInt("usuario_id")
            );
            tareas.add(tarea);
        }
    } catch (SQLException e) {
        System.err.println("Error al buscar tareas: " + e.getMessage());
    }
    return tareas;
}

    @Override
    public List<Tarea> filtrarTareasPorEstado(int usuarioId, boolean completadas) {
        List<Tarea> tareas = new ArrayList<>();
        String sql = "SELECT * FROM tareas WHERE usuario_id = ? AND completada = ? " +
                 "ORDER BY fecha_limite";
    
        try (Connection conn = ConexionBD.getConexion();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
            pstmt.setInt(1, usuarioId);
            pstmt.setInt(2, completadas ? 1 : 0);
        
            ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            Tarea tarea = new Tarea(
                rs.getInt("id"),
                rs.getString("titulo"),
                rs.getString("descripcion"),
                LocalDate.parse(rs.getString("fecha_limite")),
                rs.getInt("completada") == 1,
                rs.getInt("usuario_id")
            );
            tareas.add(tarea);
        }
    } catch (SQLException e) {
        System.err.println("Error al filtrar tareas: " + e.getMessage());
    }
    return tareas;
}
}
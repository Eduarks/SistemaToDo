package database;

import modelo.Tarea;
import modelo.Usuario;
import java.util.List;

public interface IRepositorio {
    
    Usuario validarUsuario(String username, String password);
    boolean registrarUsuario(String username, String password);
    boolean crearTarea(Tarea tarea);
    List<Tarea> obtenerTareasPorUsuario(int usuarioId);
    boolean actualizarEstadoTarea(int tareaId, boolean completada);
    boolean eliminarTarea(int tareaId);
    boolean actualizarTarea(Tarea tarea);
}
package database;

import modelo.Tarea;
import modelo.Usuario;
import java.util.List;

public interface IRepositorio {
    
    Usuario validarUsuario(String username, String password);
    boolean crearTarea(Tarea tarea);
    List<Tarea> obtenerTareasPorUsuario(int usuarioId);
    boolean actualizarEstadoTarea(int tareaId, boolean completada);
    boolean eliminarTarea(int tareaId);
    boolean actualizarTarea(Tarea tarea);
    List<Tarea> buscarTareasPorTexto(int usuarioId, String texto);
    List<Tarea> filtrarTareasPorEstado(int usuarioId, boolean completadas);
}
package util;

import modelo.Tarea;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportadorTareas {
    public static boolean exportarCSV(List<Tarea> tareas, String rutaArchivo) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            // Encabezados
            writer.append("ID,Título,Descripción,Fecha Límite,Estado\n");
            
            // Datos
            for (Tarea tarea : tareas) {
                writer.append(String.valueOf(tarea.getId())).append(",");
                writer.append("\"").append(tarea.getTitulo()).append("\",");
                writer.append("\"").append(tarea.getDescripcion()).append("\",");
                writer.append(tarea.getFechaLimite().toString()).append(",");
                writer.append(tarea.isCompletada() ? "Completada" : "Pendiente");
                writer.append("\n");
            }
            
            return true;
        } catch (IOException e) {
            System.err.println("Error al exportar CSV: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean exportarTXT(List<Tarea> tareas, String rutaArchivo) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            writer.write("========================================\n");
            writer.write("       REPORTE DE TAREAS\n");
            writer.write("========================================\n\n");
            
            for (Tarea tarea : tareas) {
                writer.write("ID: " + tarea.getId() + "\n");
                writer.write("Título: " + tarea.getTitulo() + "\n");
                writer.write("Descripción: " + tarea.getDescripcion() + "\n");
                writer.write("Fecha Límite: " + tarea.getFechaLimite().toString() + "\n");
                writer.write("Estado: " + (tarea.isCompletada() ? "Completada" : "Pendiente") + "\n");
                writer.write("----------------------------------------\n\n");
            }
            
            writer.write("Total de tareas: " + tareas.size() + "\n");
            
            return true;
        } catch (IOException e) {
            System.err.println("Error al exportar TXT: " + e.getMessage());
            return false;
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vistas;

import database.IRepositorio;
import database.RepositorioTareas;
import modelo.Tarea;
import modelo.Usuario;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import util.ExportadorTareas;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PrincipalFrame extends javax.swing.JFrame {
    
    private Usuario usuarioActual;
    private IRepositorio repositorio; //Uso de interfaz IRepositorio
    private DefaultTableModel modeloTabla;
    
    public PrincipalFrame(Usuario usuario) {
        this.usuarioActual = usuario;
        this.repositorio = new RepositorioTareas();
        
        initComponents();
        configurarFiltros();
        this.setLocationRelativeTo(null);
        
        lblUsuario.setText("Bienvenido, " + usuario.getUsername());
        getContentPane().setBackground(new java.awt.Color(255, 255, 255));
        configurarTabla();
        cargarTareas();
    }
    
    
    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(
            new Object[]{"ID", "Título", "Descripción", "Fecha Límite", "Estado"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer tabla no editable
            }
        };
        tablaTareas.setModel(modeloTabla);
        
        tablaTareas.getColumnModel().getColumn(0).setMinWidth(0);
        tablaTareas.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaTareas.getColumnModel().getColumn(0).setWidth(0);

        tablaTareas.getColumnModel().getColumn(1).setPreferredWidth(150);
        tablaTareas.getColumnModel().getColumn(2).setPreferredWidth(300);
        tablaTareas.getColumnModel().getColumn(3).setPreferredWidth(120);
        tablaTareas.getColumnModel().getColumn(4).setPreferredWidth(100);

        tablaTareas.setRowHeight(40);
        tablaTareas.setFont(new java.awt.Font("Segoe UI", 0, 13));
        tablaTareas.getTableHeader().setFont(new java.awt.Font("Segoe UI", 1, 12));
        tablaTareas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        tablaTareas.setSelectionBackground(new java.awt.Color(237, 233, 254));
        tablaTareas.setSelectionForeground(new java.awt.Color(51, 51, 51));
    }
    
    

    private void configurarFiltros() {
        // Listener para búsqueda en tiempo real
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                aplicarFiltros();
            }
        });
    
        // Listener para ComboBox
        cmbFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aplicarFiltros();
            }
        });
    }
    
    private void aplicarFiltros() {
        String textoBusqueda = txtBuscar.getText().trim();
        String filtroSeleccionado = (String) cmbFiltro.getSelectedItem();
    
        modeloTabla.setRowCount(0);
        List<Tarea> tareas;
    
        // Si hay texto de búsqueda
        if (!textoBusqueda.isEmpty()) {
        tareas = repositorio.buscarTareasPorTexto(usuarioActual.getId(), textoBusqueda);
        }
        // Si hay filtro por estado
        else if ("Pendientes".equals(filtroSeleccionado)) {
            tareas = repositorio.filtrarTareasPorEstado(usuarioActual.getId(), false);
        } else if ("Completadas".equals(filtroSeleccionado)) {
            tareas = repositorio.filtrarTareasPorEstado(usuarioActual.getId(), true);
        }
        // Mostrar todas
        else {
            tareas = repositorio.obtenerTareasPorUsuario(usuarioActual.getId());
        }
    
        // Cargar tareas filtradas en la tabla
        for (Tarea tarea : tareas) {
            String estado = tarea.isCompletada() ? "Completada" : "Pendiente";
            Object[] fila = {
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getDescripcion(),
                tarea.getFechaLimite().toString(),
                estado
            };
            modeloTabla.addRow(fila);
        }
    }

    private void cargarTareas() {
        modeloTabla.setRowCount(0);
        List<Tarea> tareas = repositorio.obtenerTareasPorUsuario(usuarioActual.getId());
        
        for (Tarea tarea : tareas) {
            String estado = tarea.isCompletada() ? "Completada" : "Pendiente";
            Object[] fila = {
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getDescripcion(),
                tarea.getFechaLimite().toString(),
                estado
            };
            modeloTabla.addRow(fila);
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaTareas = new javax.swing.JTable();
        btnNuevaTarea = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCompletada = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmbFiltro = new javax.swing.JComboBox<>();
        btnLimpiarFiltros = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI Variable", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(124, 128, 237));
        jLabel1.setText("Mis Tareas");

        lblUsuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N

        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 220, 220), 1, true));

        tablaTareas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaTareas);

        btnNuevaTarea.setBackground(new java.awt.Color(124, 128, 237));
        btnNuevaTarea.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        btnNuevaTarea.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevaTarea.setText("Nueva Tarea");
        btnNuevaTarea.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 220, 220), 1, true));
        btnNuevaTarea.setPreferredSize(new java.awt.Dimension(100, 30));
        btnNuevaTarea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaTareaActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(220, 220, 220));
        btnEditar.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 220, 220), 1, true));
        btnEditar.setPreferredSize(new java.awt.Dimension(100, 30));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(220, 220, 220));
        btnEliminar.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 220, 220), 1, true));
        btnEliminar.setPreferredSize(new java.awt.Dimension(100, 30));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnCompletada.setBackground(new java.awt.Color(0, 193, 0));
        btnCompletada.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        btnCompletada.setForeground(new java.awt.Color(255, 255, 255));
        btnCompletada.setText("Marcar Completada");
        btnCompletada.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 220, 220), 1, true));
        btnCompletada.setPreferredSize(new java.awt.Dimension(154, 30));
        btnCompletada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompletadaActionPerformed(evt);
            }
        });

        btnCerrarSesion.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        btnCerrarSesion.setForeground(new java.awt.Color(204, 0, 0));
        btnCerrarSesion.setText("Cerrar Sesión");
        btnCerrarSesion.setBorder(null);
        btnCerrarSesion.setFocusable(false);
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(220, 220, 220));

        jLabel2.setText("Buscar:");

        txtBuscar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 220, 220), 1, true));
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });

        jLabel3.setText("Filtrar:");

        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFiltroActionPerformed(evt);
            }
        });

        btnLimpiarFiltros.setBackground(new java.awt.Color(220, 220, 220));
        btnLimpiarFiltros.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        btnLimpiarFiltros.setText("Clean Filter");
        btnLimpiarFiltros.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 220, 220), 1, true));
        btnLimpiarFiltros.setPreferredSize(new java.awt.Dimension(100, 30));
        btnLimpiarFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarFiltrosActionPerformed(evt);
            }
        });

        btnExportar.setBackground(new java.awt.Color(25, 141, 25));
        btnExportar.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        btnExportar.setForeground(new java.awt.Color(255, 255, 255));
        btnExportar.setText("Exportar Tareas");
        btnExportar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 220, 220), 1, true));
        btnExportar.setPreferredSize(new java.awt.Dimension(154, 30));
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUsuario)
                .addGap(19, 19, 19)
                .addComponent(btnCerrarSesion)
                .addGap(28, 28, 28))
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnNuevaTarea, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCompletada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(8, 8, 8)
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnLimpiarFiltros, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblUsuario)
                    .addComponent(btnCerrarSesion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevaTarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCompletada, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtBuscar)
                    .addComponent(jLabel3)
                    .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiarFiltros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevaTareaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaTareaActionPerformed
        FormularioTareaFrame formulario = new FormularioTareaFrame(this, usuarioActual, null);
        formulario.setVisible(true);
    }//GEN-LAST:event_btnNuevaTareaActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        int filaSeleccionada = tablaTareas.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una tarea", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int tareaId = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        
        List<Tarea> tareas = repositorio.obtenerTareasPorUsuario(usuarioActual.getId());
        Tarea tareaSeleccionada = null;
        for (Tarea t : tareas) {
            if (t.getId() == tareaId) {
                tareaSeleccionada = t;
                break;
            }
        }
        
        if (tareaSeleccionada != null) {
            FormularioTareaFrame formulario = new FormularioTareaFrame(this, usuarioActual, tareaSeleccionada);
            formulario.setVisible(true);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int filaSeleccionada = tablaTareas.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una tarea", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar esta tarea?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            int tareaId = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            
            if (repositorio.eliminarTarea(tareaId)) {
                JOptionPane.showMessageDialog(this, "Tarea eliminada correctamente");
                cargarTareas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar tarea", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCompletadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompletadaActionPerformed
        int filaSeleccionada = tablaTareas.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione una tarea", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int tareaId = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String estadoActual = (String) modeloTabla.getValueAt(filaSeleccionada, 4);
        
        boolean nuevoEstado = !estadoActual.equals("Completada");
        
        if (repositorio.actualizarEstadoTarea(tareaId, nuevoEstado)) {
            JOptionPane.showMessageDialog(this, "Estado actualizado correctamente");
            cargarTareas();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar estado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCompletadaActionPerformed

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        LoginFrame login = new LoginFrame();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void cmbFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFiltroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbFiltroActionPerformed

    private void btnLimpiarFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarFiltrosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiarFiltrosActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
            List<Tarea> tareas = repositorio.obtenerTareasPorUsuario(usuarioActual.getId());

        if (tareas.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No hay tareas para exportar", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Diálogo para elegir formato
        String[] opciones = {"CSV", "TXT", "Cancelar"};
        int formato = JOptionPane.showOptionDialog(this,
            "¿En qué formato desea exportar?",
            "Elegir formato",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]);

        if (formato == 2 || formato == JOptionPane.CLOSED_OPTION) {
            return; // Cancelar
        }

        // Selector de archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar archivo");

        if (formato == 0) { // CSV
            fileChooser.setFileFilter(new FileNameExtensionFilter("Archivo CSV", "csv"));
            fileChooser.setSelectedFile(new java.io.File("mis_tareas.csv"));
        } else { // TXT
            fileChooser.setFileFilter(new FileNameExtensionFilter("Archivo de texto", "txt"));
            fileChooser.setSelectedFile(new java.io.File("mis_tareas.txt"));
        }

        int resultado = fileChooser.showSaveDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();

            // Agregar extensión si no la tiene
            if (formato == 0 && !rutaArchivo.endsWith(".csv")) {
                rutaArchivo += ".csv";
            } else if (formato == 1 && !rutaArchivo.endsWith(".txt")) {
                rutaArchivo += ".txt";
            }

            boolean exito;
            if (formato == 0) {
                exito = ExportadorTareas.exportarCSV(tareas, rutaArchivo);
            } else {
                exito = ExportadorTareas.exportarTXT(tareas, rutaArchivo);
            }

            if (exito) {
                JOptionPane.showMessageDialog(this, 
                    "Tareas exportadas exitosamente en:\n" + rutaArchivo, 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al exportar las tareas", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnExportarActionPerformed

    public void actualizarTabla() {
        cargarTareas();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnCompletada;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnLimpiarFiltros;
    private javax.swing.JButton btnNuevaTarea;
    private javax.swing.JComboBox<String> cmbFiltro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tablaTareas;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}

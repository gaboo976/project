package lenguaje_Project.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 * Panel base de todas las pestañas: una tabla no editable y los botones
 * Nuevo / Editar / Eliminar. Cada panel hijo dice qué datos mostrar y qué
 * hacer con cada botón.
 */
public abstract class PanelBase<T> extends JPanel {
    protected final DefaultTableModel modelo;
    protected final JTable tabla;
    protected final JScrollPane scroll;
    protected final JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
    protected final JButton btnNuevo = new JButton("Nuevo");
    protected final JButton btnEditar = new JButton("Editar");
    protected final JButton btnEliminar = new JButton("Eliminar");

    // Elementos que se están mostrando, en el mismo orden que las filas
    private List<T> elementos = new ArrayList<T>();

    protected PanelBase(String[] columnas) {
        super(new BorderLayout(5, 5));
        // Tabla no editable: la edición se hace siempre desde un diálogo
        modelo = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int fila, int columna) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(22);
        scroll = new JScrollPane(tabla);

        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnNuevo.addActionListener(e -> {
            nuevo();
            refrescar();
        });
        btnEditar.addActionListener(e -> {
            T t = seleccionado();
            if (t != null) {
                editar(t);
                refrescar();
            }
        });
        btnEliminar.addActionListener(e -> {
            T t = seleccionado();
            if (t == null) return;
            int r = JOptionPane.showConfirmDialog(this, "¿Seguro que querés eliminar el elemento seleccionado?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (r != JOptionPane.YES_OPTION) return;
            try {
                eliminar(t);
            } catch (IllegalArgumentException ex) {
                Util.error(this, ex.getMessage());
            }
            refrescar();
        });
    }

    // ----- lo que cada panel hijo tiene que definir -----
    protected abstract List<T> obtenerDatos();
    protected abstract Object[] aFila(T elemento);
    protected abstract void nuevo();
    protected abstract void editar(T elemento);
    protected abstract void eliminar(T elemento);

    /** Se ejecuta antes de volver a cargar la tabla (ej: refrescar un combo). */
    protected void antesDeRefrescar() { }

    /** Se ejecuta después de cargar la tabla (ej: actualizar totales). */
    protected void despuesDeRefrescar() { }

    /** Vuelve a pedir los datos y recarga la tabla, conservando la fila seleccionada si sigue existiendo. */
    public void refrescar() {
        antesDeRefrescar();
        T anterior = null;
        int sel = tabla.getSelectedRow();
        if (sel >= 0 && sel < elementos.size()) anterior = elementos.get(sel);

        elementos = new ArrayList<T>(obtenerDatos());
        modelo.setRowCount(0);
        for (T t : elementos) modelo.addRow(aFila(t));

        int idx = anterior == null ? -1 : elementos.indexOf(anterior);
        if (idx >= 0) tabla.setRowSelectionInterval(idx, idx);
        despuesDeRefrescar();
    }

    /** Devuelve la fila elegida, o avisa al usuario si no hay ninguna. */
    protected T seleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila < 0 || fila >= elementos.size()) {
            Util.info(this, "Seleccioná una fila de la tabla primero.");
            return null;
        }
        return elementos.get(fila);
    }

    /** Elemento elegido sin mostrar ningún aviso (null si no hay). */
    protected T seleccionadoSilencioso() {
        int fila = tabla.getSelectedRow();
        return (fila < 0 || fila >= elementos.size()) ? null : elementos.get(fila);
    }

    protected Window ventana() {
        return SwingUtilities.getWindowAncestor(this);
    }
}

package lenguaje_Project.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import lenguaje_Project.GestorBaseDeDatos;


/** Ventana principal: una pestaña por cada área. Al cerrar guarda los datos. */
public class VentanaPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;

    private final GestorBaseDeDatos veterinaria;
    private final JTabbedPane pestanias = new JTabbedPane();

    public VentanaPrincipal(GestorBaseDeDatos veterinaria) {
        super("Gestión de Veterinaria");
        this.veterinaria = veterinaria;

        pestanias.addTab("Dueños", new PanelDuenios(veterinaria));
        pestanias.addTab("Veterinarios", new PanelVeterinarios(veterinaria));
        pestanias.addTab("Mascotas", new PanelMascotas(veterinaria));
        pestanias.addTab("Turnos", new PanelTurnos(veterinaria));
        pestanias.addTab("Historia clínica", new PanelHistoria(veterinaria));
        add(pestanias);

        // Al cambiar de pestaña se recargan los datos (otra pestaña pudo haberlos cambiado)
        pestanias.addChangeListener(e -> {
            java.awt.Component c = pestanias.getSelectedComponent();
            if (c instanceof PanelBase) ((PanelBase<?>) c).refrescar();
        });

        // Guardamos nosotros al cerrar, por eso DO_NOTHING
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cerrar();
            }
        });

        setSize(950, 600);
        setLocationRelativeTo(null);
    }

    private void cerrar() {
        try {
            veterinaria.guardar();
        } catch (IOException ex) {
            int r = JOptionPane.showConfirmDialog(this,
                    "No se pudieron guardar los datos: " + ex.getMessage() + "\n¿Salir igual y perderlos?",
                    "Error al guardar", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
            if (r != JOptionPane.YES_OPTION) return;
        }
        dispose();
        System.exit(0);
    }
}

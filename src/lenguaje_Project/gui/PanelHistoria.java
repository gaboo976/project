package lenguaje_Project.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import lenguaje_Project.Consulta;
import lenguaje_Project.Mascota;
import lenguaje_Project.Tratamiento;
import lenguaje_Project.GestorBaseDeDatos;

/**
 * Pestaña de historia clínica: se elige una mascota y se ven sus consultas.
 * Abajo se muestran los tratamientos de la consulta seleccionada.
 */
public class PanelHistoria extends PanelBase<Consulta> {
    private static final long serialVersionUID = 1L;

    private final GestorBaseDeDatos vet;
    private final JComboBox<Mascota> cmbMascota = new JComboBox<Mascota>();
    private final JLabel lblTotal = new JLabel();
    private final JButton btnTratamiento = new JButton("Agregar tratamiento");
    private final DefaultTableModel modeloTratamientos;
    private boolean cargandoCombo = false;

    public PanelHistoria(GestorBaseDeDatos vet) {
        super(new String[] {"Fecha", "Diagnóstico", "Veterinario", "Costo"});
        this.vet = vet;

        Util.formatearComboMascotas(cmbMascota);
        // Reutilizamos los botones de la base con otros nombres
        btnNuevo.setText("Nueva consulta");
        btnEditar.setVisible(false);
        btnEliminar.setVisible(false);
        panelBotones.add(btnTratamiento);

        // Parte de arriba: mascota elegida y costo total
        JPanel norte = new JPanel(new FlowLayout(FlowLayout.LEFT));
        norte.add(new JLabel("Mascota:"));
        norte.add(cmbMascota);
        norte.add(lblTotal);
        add(norte, BorderLayout.NORTH);

        // Parte de abajo: tratamientos de la consulta elegida
        modeloTratamientos = new DefaultTableModel(
                new String[] {"Descripción", "Tipo", "Duración (días)", "Costo"}, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int fila, int columna) { return false; }
        };
        JTable tablaTratamientos = new JTable(modeloTratamientos);
        tablaTratamientos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaTratamientos.setFillsViewportHeight(true);
        JScrollPane scrollTrat = new JScrollPane(tablaTratamientos);
        scrollTrat.setBorder(BorderFactory.createTitledBorder("Tratamientos de la consulta seleccionada"));
        scroll.setBorder(BorderFactory.createTitledBorder("Consultas"));
        remove(scroll);
        JSplitPane division = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scroll, scrollTrat);
        division.setResizeWeight(0.6);
        add(division, BorderLayout.CENTER);

        cmbMascota.addActionListener(e -> {
            if (!cargandoCombo) refrescar();
        });
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cargarTratamientos();
        });
        btnTratamiento.addActionListener(e -> {
            Consulta c = seleccionado();
            if (c == null) return;
            new DialogoTratamiento(ventana(), vet, c).setVisible(true);
            refrescar();
        });
        refrescar();
    }

    /** Vuelve a llenar el combo de mascotas solo si cambió la lista. */
    @Override
    protected void antesDeRefrescar() {
        List<Mascota> actuales = vet.obtenerMascotas();
        List<Mascota> enCombo = new ArrayList<Mascota>();
        for (int i = 0; i < cmbMascota.getItemCount(); i++) enCombo.add(cmbMascota.getItemAt(i));
        if (actuales.equals(enCombo)) return;

        Mascota elegida = (Mascota) cmbMascota.getSelectedItem();
        cargandoCombo = true;
        cmbMascota.removeAllItems();
        for (Mascota m : actuales) cmbMascota.addItem(m);
        if (elegida != null && actuales.contains(elegida)) cmbMascota.setSelectedItem(elegida);
        cargandoCombo = false;
    }

    @Override
    protected List<Consulta> obtenerDatos() {
        Mascota m = (Mascota) cmbMascota.getSelectedItem();
        if (m == null) return Collections.emptyList();
        return m.getHistoria().getConsultas();
    }

    @Override
    protected Object[] aFila(Consulta c) {
        return new Object[] {c.getFecha().format(Util.FECHA), c.getDiagnostico(), c.getVeterinario() == null ? "" : Util.etiquetaVeterinario(c.getVeterinario()),
                Util.dinero(c.calcularCostoTotal())};
    }

    @Override
    protected void despuesDeRefrescar() {
        double total = 0;
        for (Consulta c : obtenerDatos()) total += c.calcularCostoTotal();
        lblTotal.setText("   Costo total de la historia: " + Util.dinero(total));
        cargarTratamientos();
    }

    private void cargarTratamientos() {
        modeloTratamientos.setRowCount(0);
        Consulta c = seleccionadoSilencioso();
        if (c == null) return;
        for (Tratamiento t : c.getTratamientos()) {
            modeloTratamientos.addRow(new Object[] {t.getDescripcion(), t.getTipo(), t.getDuracionDias(),
                    Util.dinero(t.calcularCosto())});
        }
    }

    @Override
    protected void nuevo() {
        Mascota m = (Mascota) cmbMascota.getSelectedItem();
        if (m == null) {
            Util.error(this, "Primero tenés que cargar y elegir una mascota.");
            return;
        }
        if (vet.obtenerVeterinarios().isEmpty()) {
            Util.error(this, "Primero tenés que cargar al menos un veterinario.");
            return;
        }
        new DialogoConsulta(ventana(), vet, m).setVisible(true);
    }

    // Los botones Editar y Eliminar están ocultos en esta pestaña
    @Override
    protected void editar(Consulta c) { }

    @Override
    protected void eliminar(Consulta c) { }
}

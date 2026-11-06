package lenguaje_Project.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JButton;
import lenguaje_Project.Turno;
import lenguaje_Project.GestorBaseDeDatos;

/** Pestaña de turnos, con botones para cancelar y marcar como atendido. */
public class PanelTurnos extends PanelBase<Turno> {
    private static final long serialVersionUID = 1L;

    private final GestorBaseDeDatos vet;
    private final JButton btnCancelarTurno = new JButton("Cancelar turno");
    private final JButton btnAtendido = new JButton("Marcar atendido");

    public PanelTurnos(GestorBaseDeDatos vet) {
        super(new String[] {"Fecha", "Hora", "Mascota", "Veterinario", "Motivo", "Estado"});
        this.vet = vet;

        panelBotones.add(btnCancelarTurno);
        panelBotones.add(btnAtendido);
        btnCancelarTurno.addActionListener(e -> {
            Turno t = seleccionado();
            if (t == null) return;
            try {
                vet.cancelarTurno(t);
            } catch (IllegalArgumentException ex) {
                Util.error(this, ex.getMessage());
            }
            refrescar();
        });
        btnAtendido.addActionListener(e -> {
            Turno t = seleccionado();
            if (t == null) return;
            try {
                vet.marcarTurnoAtendido(t);
            } catch (IllegalArgumentException ex) {
                Util.error(this, ex.getMessage());
            }
            refrescar();
        });
        refrescar();
    }

    @Override
    protected List<Turno> obtenerDatos() {
        // Ordenados por fecha y hora
        List<Turno> lista = new ArrayList<Turno>(vet.obtenerTurnos());
        Collections.sort(lista, new Comparator<Turno>() {
            @Override
            public int compare(Turno a, Turno b) {
                int c = a.getFecha().compareTo(b.getFecha());
                return c != 0 ? c : a.getHora().compareTo(b.getHora());
            }
        });
        return lista;
    }

    @Override
    protected Object[] aFila(Turno t) {
        return new Object[] {t.getFecha().format(Util.FECHA), t.getHora().format(Util.HORA),
                Util.etiquetaMascota(t.getMascota()), Util.etiquetaVeterinario(t.getVeterinario()), t.getMotivo(), t.getEstado()};
    }

    @Override
    protected void nuevo() {
        if (vet.obtenerMascotas().isEmpty()) {
            Util.error(this, "Primero tenés que cargar al menos una mascota.");
            return;
        }
        if (vet.obtenerVeterinarios().isEmpty()) {
            Util.error(this, "Primero tenés que cargar al menos un veterinario.");
            return;
        }
        new DialogoTurno(ventana(), vet, null).setVisible(true);
    }

    @Override
    protected void editar(Turno t) { new DialogoTurno(ventana(), vet, t).setVisible(true); }

    @Override
    protected void eliminar(Turno t) { vet.eliminarTurno(t); }
}

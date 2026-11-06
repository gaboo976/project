package lenguaje_Project.gui;

import java.awt.Window;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import lenguaje_Project.Mascota;
import lenguaje_Project.Turno;
import lenguaje_Project.GestorBaseDeDatos;
import lenguaje_Project.Veterinario;

/** Alta y edición de un turno (fecha dd/MM/yyyy y hora HH:mm). */
public class DialogoTurno extends DialogoBase {
    private static final long serialVersionUID = 1L;

    private final GestorBaseDeDatos vet;
    private final Turno existente;
    private final JComboBox<Mascota> cmbMascota = new JComboBox<Mascota>();
    private final JComboBox<Veterinario> cmbVeterinario = new JComboBox<Veterinario>();
    private final JTextField txtFecha = new JTextField(20);
    private final JTextField txtHora = new JTextField(20);
    private final JTextField txtMotivo = new JTextField(20);

    public DialogoTurno(Window owner, GestorBaseDeDatos vet, Turno existente) {
        super(owner, existente == null ? "Nuevo turno" : "Editar turno");
        this.vet = vet;
        this.existente = existente;

        // Los combos se llenan cada vez que se abre el diálogo
        for (Mascota m : vet.obtenerMascotas()) cmbMascota.addItem(m);
        for (Veterinario v : vet.obtenerVeterinarios()) cmbVeterinario.addItem(v);

        Util.formatearComboMascotas(cmbMascota);
        agregarCampo("Mascota:", cmbMascota);
        Util.formatearCombo(cmbVeterinario, Util::etiquetaVeterinario);
        agregarCampo("Veterinario:", cmbVeterinario);
        agregarCampo("Fecha (dd/MM/yyyy):", txtFecha);
        agregarCampo("Hora (HH:mm):", txtHora);
        agregarCampo("Motivo:", txtMotivo);

        if (existente != null) {
            cmbMascota.setSelectedItem(existente.getMascota());
            cmbVeterinario.setSelectedItem(existente.getVeterinario());
            txtFecha.setText(existente.getFecha().format(Util.FECHA));
            txtHora.setText(existente.getHora().format(Util.HORA));
            txtMotivo.setText(existente.getMotivo());
        }
        armar();
    }

    @Override
    protected void guardar() {
        Mascota mascota = (Mascota) cmbMascota.getSelectedItem();
        Veterinario veterinario = (Veterinario) cmbVeterinario.getSelectedItem();
        if (mascota == null) throw new IllegalArgumentException("Elegí una mascota.");
        if (veterinario == null) throw new IllegalArgumentException("Elegí un veterinario.");
        LocalDate fecha = Util.fecha(txtFecha, "Fecha");
        LocalTime hora = Util.hora(txtHora, "Hora");
        String motivo = Util.texto(txtMotivo, "Motivo");

        if (existente == null) {
            vet.registrarTurno(new Turno(fecha, hora, motivo, veterinario, mascota));
            return;
        }
        // Edición: se guardan los valores viejos por si el gestor rechaza el cambio
        LocalDate viejaFecha = existente.getFecha();
        LocalTime viejaHora = existente.getHora();
        String viejoMotivo = existente.getMotivo();
        Mascota viejaMascota = existente.getMascota();
        Veterinario viejoVet = existente.getVeterinario();

        existente.setFecha(fecha);
        existente.setHora(hora);
        existente.setMotivo(motivo);
        existente.setMascota(mascota);
        existente.setVeterinario(veterinario);
        try {
            vet.modificarTurno(existente);
        } catch (IllegalArgumentException ex) {
            existente.setFecha(viejaFecha);
            existente.setHora(viejaHora);
            existente.setMotivo(viejoMotivo);
            existente.setMascota(viejaMascota);
            existente.setVeterinario(viejoVet);
            throw ex;
        }
    }
}

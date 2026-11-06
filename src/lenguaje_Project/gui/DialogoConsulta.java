package lenguaje_Project.gui;

import java.awt.Window;
import java.time.LocalDate;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import lenguaje_Project.Consulta;
import lenguaje_Project.Mascota;
import lenguaje_Project.GestorBaseDeDatos;
import lenguaje_Project.Veterinario;

/** Alta de una consulta en la historia clínica de una mascota. */
public class DialogoConsulta extends DialogoBase {
    private static final long serialVersionUID = 1L;

    private final GestorBaseDeDatos vet;
    private final Mascota mascota;
    private final JTextField txtFecha = new JTextField(20);
    private final JTextField txtDiagnostico = new JTextField(20);
    private final JTextArea txtObservaciones = new JTextArea(4, 20);
    private final JComboBox<Veterinario> cmbVeterinario = new JComboBox<Veterinario>();

    public DialogoConsulta(Window owner, GestorBaseDeDatos vet, Mascota mascota) {
        super(owner, "Nueva consulta de " + mascota.getNombre());
        this.vet = vet;
        this.mascota = mascota;

        for (Veterinario v : vet.obtenerVeterinarios()) cmbVeterinario.addItem(v);
        txtFecha.setText(LocalDate.now().format(Util.FECHA));
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);

        agregarCampo("Fecha (dd/MM/yyyy):", txtFecha);
        agregarCampo("Diagnóstico:", txtDiagnostico);
        agregarCampo("Observaciones:", new JScrollPane(txtObservaciones));
        Util.formatearCombo(cmbVeterinario, Util::etiquetaVeterinario);
        agregarCampo("Veterinario:", cmbVeterinario);
        armar();
    }

    @Override
    protected void guardar() {
        LocalDate fecha = Util.fecha(txtFecha, "Fecha");
        String diagnostico = Util.texto(txtDiagnostico, "Diagnóstico");
        String observaciones = txtObservaciones.getText().trim();
        Veterinario veterinario = (Veterinario) cmbVeterinario.getSelectedItem();
        if (veterinario == null) throw new IllegalArgumentException("Elegí un veterinario.");

        vet.agregarConsulta(mascota, new Consulta(fecha, diagnostico, observaciones, veterinario));
    }
}

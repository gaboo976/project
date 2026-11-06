package lenguaje_Project.gui;

import java.awt.Window;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import lenguaje_Project.Consulta;
import lenguaje_Project.TipoTratamiento;
import lenguaje_Project.Tratamiento;
import lenguaje_Project.GestorBaseDeDatos;

/** Alta de un tratamiento sobre una consulta. */
public class DialogoTratamiento extends DialogoBase {
    private static final long serialVersionUID = 1L;

    private final GestorBaseDeDatos vet;
    private final Consulta consulta;
    private final JTextField txtDescripcion = new JTextField(20);
    private final JComboBox<TipoTratamiento> cmbTipo = new JComboBox<TipoTratamiento>(TipoTratamiento.values());
    private final JTextField txtDuracion = new JTextField(20);
    private final JTextField txtCosto = new JTextField(20);

    public DialogoTratamiento(Window owner, GestorBaseDeDatos vet, Consulta consulta) {
        super(owner, "Agregar tratamiento");
        this.vet = vet;
        this.consulta = consulta;

        agregarCampo("Descripción:", txtDescripcion);
        agregarCampo("Tipo:", cmbTipo);
        agregarCampo("Duración (días):", txtDuracion);
        agregarCampo("Costo ($):", txtCosto);
        armar();
    }

    @Override
    protected void guardar() {
        String descripcion = Util.texto(txtDescripcion, "Descripción");
        TipoTratamiento tipo = (TipoTratamiento) cmbTipo.getSelectedItem();
        int duracion = Util.entero(txtDuracion, "Duración");
        double costo = Util.decimal(txtCosto, "Costo");
        if (duracion < 0) throw new IllegalArgumentException("La duración no puede ser negativa.");
        if (costo < 0) throw new IllegalArgumentException("El costo no puede ser negativo.");

        vet.agregarTratamiento(consulta, new Tratamiento(descripcion, tipo, duracion, costo));
    }
}

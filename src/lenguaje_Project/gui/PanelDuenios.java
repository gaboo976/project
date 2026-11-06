package lenguaje_Project.gui;

import java.util.List;
import lenguaje_Project.Dueño;
import lenguaje_Project.GestorBaseDeDatos;

/** Pestaña de dueños. */
public class PanelDuenios extends PanelBase<Dueño> {
    private static final long serialVersionUID = 1L;

    private final GestorBaseDeDatos vet;

    public PanelDuenios(GestorBaseDeDatos vet) {
        super(new String[] {"Nombre", "Apellido", "DNI", "Teléfono", "Email", "Dirección"});
        this.vet = vet;
        refrescar();
    }

    @Override
    protected List<Dueño> obtenerDatos() { return vet.obtenerDuenios(); }

    @Override
    protected Object[] aFila(Dueño d) {
        return new Object[] {d.getNombre(), d.getApellido(), d.getDni(), d.getTelefono(), d.getEmail(), d.getDireccion()};
    }

    @Override
    protected void nuevo() { new DialogoDuenio(ventana(), vet, null).setVisible(true); }

    @Override
    protected void editar(Dueño d) { new DialogoDuenio(ventana(), vet, d).setVisible(true); }

    @Override
    protected void eliminar(Dueño d) { vet.eliminarDuenio(d); }
}

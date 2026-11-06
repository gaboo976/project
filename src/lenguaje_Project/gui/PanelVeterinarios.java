package lenguaje_Project.gui;

import java.util.List;
import lenguaje_Project.GestorBaseDeDatos;
import lenguaje_Project.Veterinario;

/** Pestaña de veterinarios. */
public class PanelVeterinarios extends PanelBase<Veterinario> {
    private static final long serialVersionUID = 1L;

    private final GestorBaseDeDatos vet;

    public PanelVeterinarios(GestorBaseDeDatos vet) {
        super(new String[] {"Nombre", "Apellido", "DNI", "Teléfono", "Email", "Matrícula", "Especialidad"});
        this.vet = vet;
        refrescar();
    }

    @Override
    protected List<Veterinario> obtenerDatos() { return vet.obtenerVeterinarios(); }

    @Override
    protected Object[] aFila(Veterinario v) {
        return new Object[] {v.getNombre(), v.getApellido(), v.getDni(), v.getTelefono(), v.getEmail(),
                v.getMatriculaProfesional(), v.getEspecialidad()};
    }

    @Override
    protected void nuevo() { new DialogoVeterinario(ventana(), vet, null).setVisible(true); }

    @Override
    protected void editar(Veterinario v) { new DialogoVeterinario(ventana(), vet, v).setVisible(true); }

    @Override
    protected void eliminar(Veterinario v) { vet.eliminarVeterinario(v); }
}

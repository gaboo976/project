package lenguaje_Project.gui;

import java.util.List;
import lenguaje_Project.Gato;
import lenguaje_Project.Mascota;
import lenguaje_Project.Otro;
import lenguaje_Project.Perro;
import lenguaje_Project.GestorBaseDeDatos;

/** Pestaña de mascotas. */
public class PanelMascotas extends PanelBase<Mascota> {
    private static final long serialVersionUID = 1L;

    private final GestorBaseDeDatos vet;

    public PanelMascotas(GestorBaseDeDatos vet) {
        super(new String[] {"Especie", "Nombre", "Edad", "Peso (kg)", "Raza", "Dueño", "Datos propios"});
        this.vet = vet;
        refrescar();
    }

    @Override
    protected List<Mascota> obtenerDatos() { return vet.obtenerMascotas(); }

    @Override
    protected Object[] aFila(Mascota m) {
        return new Object[] {m.getEspecie(), m.getNombre(), m.getEdad(), m.getPeso(), m.getRaza(),
                m.getDueño() == null ? "" : Util.etiquetaDuenio(m.getDueño()), datosPropios(m)};
    }

    /** Resumen de lo que es propio de cada especie. */
    private String datosPropios(Mascota m) {
        if (m instanceof Perro) {
            Perro p = (Perro) m;
            return "Tamaño " + p.getTamaño() + ", vacunas " + (p.isVacunasActualizadas() ? "al día" : "pendientes");
        }
        if (m instanceof Gato) {
            Gato g = (Gato) m;
            return "Tamaño " + g.getTamanio() + ", vacunas " + (g.isVacunasActualizadas() ? "al día" : "pendientes");
        }
        if (m instanceof Otro) return ((Otro) m).getTipoAnimal();
        return "";
    }

    @Override
    protected void nuevo() {
        if (vet.obtenerDuenios().isEmpty()) {
            Util.error(this, "Primero tenés que cargar al menos un dueño.");
            return;
        }
        new DialogoMascota(ventana(), vet, null).setVisible(true);
    }

    @Override
    protected void editar(Mascota m) { new DialogoMascota(ventana(), vet, m).setVisible(true); }

    @Override
    protected void eliminar(Mascota m) { vet.eliminarMascota(m); }
}

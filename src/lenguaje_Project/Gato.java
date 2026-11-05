package lenguaje_Project;

public class Gato extends Mascota {

    private static final long serialVersionUID = 1L;

    private String tamaño;
    private boolean vacunasActualizadas;

    public Gato(String nombre, int edad, double peso, String raza,
                Dueño dueño, String tamaño, boolean vacunasActualizadas) {

        super(nombre, edad, peso, raza, dueño);

        setTamaño(tamaño);
        setVacunasActualizadas(vacunasActualizadas);
    }

    public String getTamanio() {
        return tamaño;
    }

    public boolean isVacunasActualizadas() {
        return vacunasActualizadas;
    }

    public void setTamaño(String tamaño) {
        if (tamaño == null || tamaño.trim().isEmpty()) {
            throw new IllegalArgumentException("El tamaño no puede estar vacío.");
        }
        this.tamaño = tamaño.trim();
    }

    public void setVacunasActualizadas(boolean vacunasActualizadas) {
        this.vacunasActualizadas = vacunasActualizadas;
    }

    @Override
    public String getEspecie() {
        return "Gato";
    }

    @Override
    public String toString() {
        return super.toString()
                + ", Tamaño: " + tamaño
                + ", Vacunas actualizadas: " + vacunasActualizadas;
    }
}

package lenguaje_Project;

public class Perro extends Mascota {

    private static final long serialVersionUID = 1L;

    private String tamaño;
    private boolean vacunasActualizadas;

    public Perro(String nombre, int edad, double peso, String raza,
                 Dueño dueño, String tamaño, boolean vacunasActualizadas) {

        super(nombre, edad, peso, raza, dueño);

        setTamaño(tamaño);
        setVacunasActualizadas(vacunasActualizadas);
    }

    public String getTamaño() {
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
        return "Perro";
    }

    @Override
    public String toString() {
        return super.toString()
                + ", Tamaño: " + tamaño
                + ", Vacunas actualizadas: " + vacunasActualizadas;
    }
}

package lenguaje_Project;

public class Otro extends Mascota {

    private static final long serialVersionUID = 1L;

    private String tipoAnimal;

    public Otro(String nombre, int edad, double peso, String raza,
                Dueño dueño, String tipoAnimal) {

        super(nombre, edad, peso, raza, dueño);

        setTipoAnimal(tipoAnimal);
    }

    public String getTipoAnimal() {
        return tipoAnimal;
    }

    public void setTipoAnimal(String tipoAnimal) {
        if (tipoAnimal == null || tipoAnimal.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe especificar el tipo de animal.");
        }
        this.tipoAnimal = tipoAnimal.trim();
    }

    @Override
    public String getEspecie() {
        return tipoAnimal;
    }

    @Override
    public String toString() {
        return super.toString()
                + ", Tipo de animal: " + tipoAnimal;
    }
}

package lenguaje_Project;

import java.io.Serializable;

public abstract class Mascota implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private int edad;
    private double peso;
    private String raza;
    private Dueño dueño;

    public Mascota(String nombre, int edad, double peso, String raza, Dueño dueño) {
        setNombre(nombre);
        setEdad(edad);
        setPeso(peso);
        setRaza(raza);
        setDueño(dueño);
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public double getPeso() {
        return peso;
    }

    public String getRaza() {
        return raza;
    }

    public Dueño getDueño() {
        return dueño;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public void setEdad(int edad) {
        if (edad < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa.");
        }
        this.edad = edad;
    }

    public void setPeso(double peso) {
        if (!Double.isFinite(peso) || peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que cero.");
        }
        this.peso = peso;
    }

    public void setRaza(String raza) {
        if (raza == null || raza.trim().isEmpty()) {
            throw new IllegalArgumentException("La raza no puede estar vacía.");
        }
        this.raza = raza.trim();
    }

    public void setDueño(Dueño dueño) {
        if (dueño == null) {
            throw new IllegalArgumentException("La mascota debe tener un dueño.");
        }
        this.dueño = dueño;
    }

    public abstract String getEspecie();

    @Override
    public String toString() {
        return "Nombre: " + nombre
                + ", Especie: " + getEspecie()
                + ", Edad: " + edad
                + ", Peso: " + peso + " kg"
                + ", Raza: " + raza
                + ", Dueño: " + dueño.getNombre();
    }
}


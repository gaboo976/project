// MODIFICADO en la copia de la GUI: se agregó package y Serializable (sin eso no compila ni guarda).
package lenguaje_Project;

import java.util.ArrayList;
import java.util.List;

//1: Persona

public abstract class Persona implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;

    public Persona() {
    }

    public Persona(String dni, String nombre, String apellido, String telefono, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Métodos abstractos para aplicar polimorfismo en las subclases
    public abstract String obtenerRol();
    public abstract void mostrarInformacion();

    @Override
    public String toString() {
        return String.format("[%s] %s (DNI: %s) | Tel: %s | Email: %s",
                obtenerRol(), getNombreCompleto(), dni, telefono, email);
    }
}

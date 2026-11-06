// MODIFICADO en la copia de la GUI: se agregó package e imports y la clase se llama Dueño (la versión nueva del grupo la llamaba Dueno, pero el resto del código y el archivo usan Dueño).
package lenguaje_Project;

import java.util.ArrayList;
import java.util.List;

//2: Dueño.java

public class Dueño extends Persona {
    private String direccion;
    private boolean clienteFrecuente;
    private List<String> mascotasRegistradas; // Puedes cambiar String por la clase Mascota de tu equipo

    public Dueño() {
        super();
        this.mascotasRegistradas = new ArrayList<>();
    }

    public Dueño(String dni, String nombre, String apellido, String telefono,
                 String email, String direccion, boolean clienteFrecuente) {
        super(dni, nombre, apellido, telefono, email);
        this.direccion = direccion;
        this.clienteFrecuente = clienteFrecuente;
        this.mascotasRegistradas = new ArrayList<>();
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isClienteFrecuente() {
        return clienteFrecuente;
    }

    public void setClienteFrecuente(boolean clienteFrecuente) {
        this.clienteFrecuente = clienteFrecuente;
    }

    public List<String> getMascotasRegistradas() {
        return mascotasRegistradas;
    }

    public void setMascotasRegistradas(List<String> mascotasRegistradas) {
        this.mascotasRegistradas = mascotasRegistradas;
    }

    public void agregarMascota(String nombreMascota) {
        if (nombreMascota != null && !nombreMascota.isBlank() && !mascotasRegistradas.contains(nombreMascota)) {
            mascotasRegistradas.add(nombreMascota);
        }
    }

    public boolean eliminarMascota(String nombreMascota) {
        return mascotasRegistradas.removeIf(m -> m.equalsIgnoreCase(nombreMascota));
    }

    public double calcularDescuento() {
        return clienteFrecuente ? 0.15 : 0.0;
    }

    @Override
    public String obtenerRol() {
        return "Dueño";
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("==========================================");
        System.out.println("FICHA DE DUEÑO");
        System.out.println("Nombre: " + getNombreCompleto() + " (DNI: " + getDni() + ")");
        System.out.println("Contacto: " + getTelefono() + " | " + getEmail());
        System.out.println("Dirección: " + direccion);
        System.out.println("Cliente Frecuente: " + (clienteFrecuente ? "Sí (15% desc.)" : "No"));
        System.out.println("Mascotas registradas: " + (mascotasRegistradas.isEmpty() ? "Ninguna" : String.join(", ", mascotasRegistradas)));
        System.out.println("==========================================");
    }
}
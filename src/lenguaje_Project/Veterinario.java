// MODIFICADO en la copia de la GUI: se agregó package.
package lenguaje_Project;

public class Veterinario extends Persona {
    private String matriculaProfesional;
    private String especialidad;
    private String turno;
    private boolean disponible;
    private int consultasRealizadas;

    public Veterinario() {
        super();
        this.disponible = true;
        this.consultasRealizadas = 0;
    }

    public Veterinario(String dni, String nombre, String apellido, String telefono,
                       String email, String matriculaProfesional, String especialidad, String turno) {
        super(dni, nombre, apellido, telefono, email);
        this.matriculaProfesional = matriculaProfesional;
        this.especialidad = especialidad;
        this.turno = turno;
        this.disponible = true;
        this.consultasRealizadas = 0;
    }

    public String getMatriculaProfesional() {
        return matriculaProfesional;
    }

    public void setMatriculaProfesional(String matriculaProfesional) {
        this.matriculaProfesional = matriculaProfesional;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getConsultasRealizadas() {
        return consultasRealizadas;
    }

    public void setConsultasRealizadas(int consultasRealizadas) {
        this.consultasRealizadas = consultasRealizadas;
    }

    public void registrarConsulta(String nombreMascota, String diagnostico, String tratamiento) {
        if (!disponible) {
            System.out.println("El Dr./Dra. " + getNombreCompleto() + " no se encuentra disponible.");
            return;
        }
        consultasRealizadas++;
        System.out.println("Consulta registrada por Dr./Dra. " + getNombreCompleto()
                + " (Mat. " + matriculaProfesional + ") para paciente: " + nombreMascota
                + " | Diagnóstico: " + diagnostico + " | Tratamiento: " + tratamiento);
    }

    @Override
    public String obtenerRol() {
        return "Veterinario";
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("==========================================");
        System.out.println("FICHA DE VETERINARIO");
        System.out.println("Profesional: Dr./Dra. " + getNombreCompleto() + " (DNI: " + getDni() + ")");
        System.out.println("Matrícula: " + matriculaProfesional + " | Especialidad: " + especialidad);
        System.out.println("Turno: " + turno + " | Disponible: " + (disponible ? "Sí" : "No"));
        System.out.println("Contacto: " + getTelefono() + " | " + getEmail());
        System.out.println("Consultas realizadas: " + consultasRealizadas);
        System.out.println("==========================================");
    }
}
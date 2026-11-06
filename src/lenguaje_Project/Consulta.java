package lenguaje_Project;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Consulta implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDate fecha;
    private String diagnostico;
    private String observaciones;
    private Veterinario veterinario;
    private List<Tratamiento> tratamientos;

    // Constructor principal
    public Consulta(LocalDate fecha, String diagnostico, String observaciones, Veterinario veterinario) {
        this.fecha = fecha;
        this.diagnostico = diagnostico;
        this.observaciones = observaciones;
        this.veterinario = veterinario;
        this.tratamientos = new ArrayList<>();
    }

    // Constructor sobrecargado (permite omitir el veterinario si se pasa null en pruebas)
    public Consulta(LocalDate fecha, String diagnostico, String observaciones) {
        this(fecha, diagnostico, observaciones, null);
    }

    // Agrega un tratamiento a la consulta
    public void agregarTratamiento(Tratamiento tratamiento) {
        if (tratamiento != null) {
            this.tratamientos.add(tratamiento);
        }
    }

    // Suma el costo de todos los tratamientos asignados en esta consulta
    public double calcularCostoTotal() {
        double total = 0.0;
        if (tratamientos != null) {
            for (Tratamiento t : tratamientos) {
                total += t.calcularCosto();
            }
        }
        return total;
    }

    // Getters y Setters
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public List<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }
}
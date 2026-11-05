package lenguaje_Project;

import java.io.Serializable;

public class Tratamiento implements Serializable {
    private static final long serialVersionUID = 1L;

    private String descripcion;
    private TipoTratamiento tipo;
    private int duracionDias;
    private double costo;

    public Tratamiento(String descripcion, TipoTratamiento tipo, int duracionDias, double costo) {
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.duracionDias = duracionDias;
        this.costo = costo;
    }

    public double calcularCosto() {
        return this.costo;
    }

    // Getters y Setters
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoTratamiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoTratamiento tipo) {
        this.tipo = tipo;
    }

    public int getDuracionDias() {
        return duracionDias;
    }

    public void setDuracionDias(int duracionDias) {
        this.duracionDias = duracionDias;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
}
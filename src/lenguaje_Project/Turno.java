package lenguaje_Project;

import java.time.LocalDate;
import java.time.LocalTime;

public class Turno {

    private LocalDate fecha;
    private LocalTime hora;
    private String motivo;
    private EstadoTurno estado;

    private Veterinario veterinario;
    private Mascota mascota;

    public Turno(LocalDate fecha, LocalTime hora, String motivo, Veterinario veterinario, Mascota mascota) {
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.veterinario = veterinario;
        this.mascota = mascota;
        this.estado = EstadoTurno.PENDIENTE;
    }

    public void cancelar() {
        if (this.estado == EstadoTurno.ATENDIDO) {
            throw new IllegalStateException("No se puede cancelar un turno que ya fue atendido.");
        }
        this.estado = EstadoTurno.CANCELADO;
    }

    public void marcarAtendido() {
        if (this.estado == EstadoTurno.CANCELADO) {
            throw new IllegalStateException("No se puede atender un turno que está cancelado.");
        }
        this.estado = EstadoTurno.ATENDIDO;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public EstadoTurno getEstado() {
        return estado;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }
}
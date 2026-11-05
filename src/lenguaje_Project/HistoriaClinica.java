package lenguaje_Project;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HistoriaClinica implements Serializable {
    private static final long serialVersionUID = 1L;

    private Mascota mascota; // Vincula la historia a su respectiva mascota
    private LocalDate fechaApertura;
    private List<Consulta> consultas;

    public HistoriaClinica(Mascota mascota) {
        this.mascota = mascota;
        this.fechaApertura = LocalDate.now();
        this.consultas = new ArrayList<>();
    }

    public void agregarConsulta(Consulta consulta) {
        if (consulta != null) {
            this.consultas.add(consulta);
        }
    }

    public Mascota getMascota() { return mascota; }
    public LocalDate getFechaApertura() { return fechaApertura; }
    public List<Consulta> getConsultas() { return consultas; }
}
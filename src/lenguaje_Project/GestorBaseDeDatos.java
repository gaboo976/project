package lenguaje_Project;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

// MODIFICADO en la copia de la GUI: se mantiene el motor de archivo del grupo (cargarDatos / guardarDatos),
// y se agregaron los métodos que la GUI necesita (dueños, veterinarios, modificar, eliminar, reglas, historia).
// Ver PENDIENTES_GUI.md.
public class GestorBaseDeDatos {
    private final String ARCHIVO_DB = "veterinaria_datos.dat";

    // Tus "tablas" en memoria
    private ArrayList<Persona> listaPersonas;
    private ArrayList<Mascota> listaMascotas;
    private ArrayList<Turno> listaTurnos;
    private ArrayList<HistoriaClinica> listaHistorias;

    public GestorBaseDeDatos() {
        cargarDatos();
    }

    // --- MÉTODOS PARA DUEÑOS (agregados) ---
    public ArrayList<Dueño> obtenerDuenios() {
        ArrayList<Dueño> lista = new ArrayList<>();
        for (Persona p : listaPersonas) {
            if (p instanceof Dueño) lista.add((Dueño) p);
        }
        return lista;
    }

    public void registrarDuenio(Dueño d) {
        validarDni(d);
        listaPersonas.add(d);
        guardarDatos();
    }

    // El objeto ya viene modificado: solo se revisan las reglas y se guarda
    public void modificarDuenio(Dueño d) {
        validarDni(d);
        guardarDatos();
    }

    public void eliminarDuenio(Dueño d) {
        for (Mascota m : listaMascotas) {
            if (m.getDueño() == d) {
                throw new IllegalArgumentException("El dueño tiene mascotas registradas; eliminá primero las mascotas.");
            }
        }
        listaPersonas.remove(d);
        guardarDatos();
    }

    // --- MÉTODOS PARA VETERINARIOS (agregados) ---
    public ArrayList<Veterinario> obtenerVeterinarios() {
        ArrayList<Veterinario> lista = new ArrayList<>();
        for (Persona p : listaPersonas) {
            if (p instanceof Veterinario) lista.add((Veterinario) p);
        }
        return lista;
    }

    public void registrarVeterinario(Veterinario v) {
        validarVeterinario(v);
        listaPersonas.add(v);
        guardarDatos();
    }

    public void modificarVeterinario(Veterinario v) {
        validarVeterinario(v);
        guardarDatos();
    }

    public void eliminarVeterinario(Veterinario v) {
        for (Turno t : listaTurnos) {
            if (t.getVeterinario() == v && t.getEstado() == EstadoTurno.PENDIENTE) {
                throw new IllegalArgumentException("El veterinario tiene turnos pendientes.");
            }
        }
        listaPersonas.remove(v);
        guardarDatos();
    }

    // --- MÉTODOS PARA MASCOTAS ---
    public void registrarMascota(Mascota mascota) {
        listaMascotas.add(mascota);
        guardarDatos();
    }

    public ArrayList<Mascota> obtenerMascotas() {
        return listaMascotas;
    }

    public void modificarMascota(Mascota mascota) {
        guardarDatos();
    }

    public void eliminarMascota(Mascota mascota) {
        for (Turno t : listaTurnos) {
            if (t.getMascota() == mascota && t.getEstado() == EstadoTurno.PENDIENTE) {
                throw new IllegalArgumentException("La mascota tiene turnos pendientes.");
            }
        }
        listaMascotas.remove(mascota);
        guardarDatos();
    }

    // --- MÉTODOS PARA TURNOS ---
    public void registrarTurno(Turno turno) {
        validarTurno(turno);
        listaTurnos.add(turno);
        guardarDatos();
    }

    public ArrayList<Turno> obtenerTurnos() {
        return listaTurnos;
    }

    public void modificarTurno(Turno turno) {
        if (turno.getEstado() != EstadoTurno.PENDIENTE) {
            throw new IllegalArgumentException("Solo se pueden modificar turnos pendientes.");
        }
        validarTurno(turno);
        guardarDatos();
    }

    public void eliminarTurno(Turno turno) {
        listaTurnos.remove(turno);
        guardarDatos();
    }

    public void cancelarTurno(Turno turno) {
        if (turno.getEstado() != EstadoTurno.PENDIENTE) {
            throw new IllegalArgumentException("Solo se pueden cancelar turnos pendientes.");
        }
        turno.cancelar();
        guardarDatos();
    }

    public void marcarTurnoAtendido(Turno turno) {
        if (turno.getEstado() != EstadoTurno.PENDIENTE) {
            throw new IllegalArgumentException("Solo se pueden atender turnos pendientes.");
        }
        turno.marcarAtendido();
        guardarDatos();
    }

    // --- MÉTODOS PARA HISTORIA CLÍNICA (agregados) ---
    public void agregarConsulta(Mascota mascota, Consulta consulta) {
        if (mascota == null) throw new IllegalArgumentException("Elegí una mascota.");
        if (consulta.getVeterinario() == null) throw new IllegalArgumentException("Elegí un veterinario.");
        mascota.getHistoria().agregarConsulta(consulta);
        guardarDatos();
    }

    public void agregarTratamiento(Consulta consulta, Tratamiento tratamiento) {
        if (consulta == null) throw new IllegalArgumentException("Elegí una consulta.");
        if (tratamiento.getCosto() < 0) throw new IllegalArgumentException("El costo no puede ser negativo.");
        if (tratamiento.getDuracionDias() < 0) throw new IllegalArgumentException("La duración no puede ser negativa.");
        consulta.agregarTratamiento(tratamiento);
        guardarDatos();
    }

    public void vaciarBaseDeDatos() {
        // Vacía las listas en memoria
        listaPersonas.clear();
        listaMascotas.clear();
        listaTurnos.clear();
        listaHistorias.clear();

        // Sobrescribe el archivo con las listas vacías
        guardarDatos();
        System.out.println("Base de datos reiniciada con éxito.");
    }

    // --- REGLAS (agregadas) ---
    private void validarDni(Persona p) {
        for (Persona otra : listaPersonas) {
            if (otra != p && otra.getClass() == p.getClass() && otra.getDni().equals(p.getDni())) {
                throw new IllegalArgumentException("Ya existe un " + p.obtenerRol().toLowerCase() + " con el DNI " + p.getDni() + ".");
            }
        }
    }

    private void validarVeterinario(Veterinario v) {
        validarDni(v);
        for (Veterinario otro : obtenerVeterinarios()) {
            if (otro != v && otro.getMatriculaProfesional().equalsIgnoreCase(v.getMatriculaProfesional())) {
                throw new IllegalArgumentException("Ya existe un veterinario con la matrícula " + v.getMatriculaProfesional() + ".");
            }
        }
    }

    private void validarTurno(Turno t) {
        if (t.getMascota() == null) throw new IllegalArgumentException("Elegí una mascota.");
        if (t.getVeterinario() == null) throw new IllegalArgumentException("Elegí un veterinario.");
        if (LocalDateTime.of(t.getFecha(), t.getHora()).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("No se puede sacar un turno en una fecha pasada.");
        }
        for (Turno otro : listaTurnos) {
            if (otro != t && otro.getEstado() == EstadoTurno.PENDIENTE
                    && otro.getVeterinario() == t.getVeterinario()
                    && otro.getFecha().equals(t.getFecha())
                    && otro.getHora().equals(t.getHora())) {
                throw new IllegalArgumentException("El veterinario ya tiene un turno a esa fecha y hora.");
            }
        }
    }

    // --- MOTOR DE SERIALIZACIÓN (EL NÚCLEO DE TU DB) ---
    // Público para que la ventana pueda guardar al cerrar y avisar si falla
    public void guardar() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DB))) {
            // Guardamos todas las listas secuencialmente en el mismo archivo
            oos.writeObject(listaPersonas);
            oos.writeObject(listaMascotas);
            oos.writeObject(listaTurnos);
            oos.writeObject(listaHistorias);
        }
    }

    private void guardarDatos() {
        try {
            guardar();
        } catch (IOException e) {
            System.err.println("Error guardando la base de datos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void cargarDatos() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_DB))) {
            // El orden de lectura DEBE ser exactamente igual al de escritura
            listaPersonas = (ArrayList<Persona>) ois.readObject();
            listaMascotas = (ArrayList<Mascota>) ois.readObject();
            listaTurnos = (ArrayList<Turno>) ois.readObject();
            listaHistorias = (ArrayList<HistoriaClinica>) ois.readObject();
        } catch (Exception e) {
            // Si el archivo no existe (primera ejecución), inicializamos listas vacías
            listaPersonas = new ArrayList<>();
            listaMascotas = new ArrayList<>();
            listaTurnos = new ArrayList<>();
            listaHistorias = new ArrayList<>();
        }
    }
}

package lenguaje_Project;
import java.io.*;
import java.util.ArrayList;

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

    // --- MÉTODOS PARA MASCOTAS ---
    public void registrarMascota(Mascota mascota) {
        listaMascotas.add(mascota);
        guardarDatos(); 
    }

    public ArrayList<Mascota> obtenerMascotas() {
        return listaMascotas;
    }

    // --- MÉTODOS PARA TURNOS ---
    public void registrarTurno(Turno turno) {
        listaTurnos.add(turno);
        guardarDatos();
    }

    public ArrayList<Turno> obtenerTurnos() {
        return listaTurnos;
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

    // --- MOTOR DE SERIALIZACIÓN (EL NÚCLEO DE TU DB) ---
    private void guardarDatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DB))) {
            // Guardamos todas las listas secuencialmente en el mismo archivo
            oos.writeObject(listaPersonas);
            oos.writeObject(listaMascotas);
            oos.writeObject(listaTurnos);
            oos.writeObject(listaHistorias);
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

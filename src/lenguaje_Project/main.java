package lenguaje_Project;

public class main {
    public static void main(String[] args) {
        // 1. Instanciar tu base de datos (carga o inicializa la lista)
        GestorBaseDeDatos db = new GestorBaseDeDatos();
        Dueño duenoPrueba = new Dueño();

        // 2. Instanciar Perro y Gato con los 7 parámetros de sus constructores
        Perro miPerro = new Perro("Thor", 4, 12.5, "Golden Retriever", duenoPrueba, "Grande", true);
        Gato miGato = new Gato("Michi", 2, 4.3, "Siamés", duenoPrueba, "Mediano", true);

        // 3. Registrar en tu gestor (guarda en memoria y actualiza el archivo .dat)
        db.registrarMascota(miPerro);
        db.registrarMascota(miGato);

        // 4. Comprobar que los datos se guardaron correctamente
        System.out.println("Total de mascotas registradas: " + db.obtenerMascotas().size());
        
        for (Mascota m : db.obtenerMascotas()) {
            System.out.println("-> Guardado: " + m.getNombre() + " | Especie: " + m.getClass().getSimpleName());
        }
    }
}
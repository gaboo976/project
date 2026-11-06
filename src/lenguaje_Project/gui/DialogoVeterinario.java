package lenguaje_Project.gui;

import java.awt.Window;
import javax.swing.JTextField;
import lenguaje_Project.GestorBaseDeDatos;
import lenguaje_Project.Veterinario;

/** Alta y edición de un veterinario. Si recibe un veterinario lo edita; si recibe null crea uno nuevo. */
public class DialogoVeterinario extends DialogoBase {
    private static final long serialVersionUID = 1L;

    private final GestorBaseDeDatos vet;
    private final Veterinario existente;
    private final JTextField txtNombre = new JTextField(20);
    private final JTextField txtApellido = new JTextField(20);
    private final JTextField txtDni = new JTextField(20);
    private final JTextField txtTelefono = new JTextField(20);
    private final JTextField txtEmail = new JTextField(20);
    private final JTextField txtMatricula = new JTextField(20);
    private final JTextField txtEspecialidad = new JTextField(20);

    public DialogoVeterinario(Window owner, GestorBaseDeDatos vet, Veterinario existente) {
        super(owner, existente == null ? "Nuevo veterinario" : "Editar veterinario");
        this.vet = vet;
        this.existente = existente;

        agregarCampo("Nombre:", txtNombre);
        agregarCampo("Apellido:", txtApellido);
        agregarCampo("DNI:", txtDni);
        agregarCampo("Teléfono:", txtTelefono);
        agregarCampo("Email:", txtEmail);
        agregarCampo("Matrícula:", txtMatricula);
        agregarCampo("Especialidad:", txtEspecialidad);

        if (existente != null) {
            txtNombre.setText(existente.getNombre());
            txtApellido.setText(existente.getApellido());
            txtDni.setText(String.valueOf(existente.getDni()));
            txtTelefono.setText(existente.getTelefono());
            txtEmail.setText(existente.getEmail());
            txtMatricula.setText(existente.getMatriculaProfesional());
            txtEspecialidad.setText(existente.getEspecialidad());
        }
        armar();
    }

    @Override
    protected void guardar() {
        String nombre = Util.texto(txtNombre, "Nombre");
        String apellido = Util.texto(txtApellido, "Apellido");
        String dni = Util.dni(txtDni, "DNI");
        String telefono = Util.texto(txtTelefono, "Teléfono");
        String email = Util.texto(txtEmail, "Email");
        String matricula = Util.texto(txtMatricula, "Matrícula");
        String especialidad = Util.texto(txtEspecialidad, "Especialidad");
        if (!email.contains("@")) throw new IllegalArgumentException("El email no es válido.");

        if (existente == null) {
            vet.registrarVeterinario(
                    new Veterinario(dni, nombre, apellido, telefono, email, matricula, especialidad, ""));
            return;
        }
        // Edición: guardamos los valores viejos por si el gestor rechaza el cambio
        Veterinario copia = new Veterinario(existente.getDni(), existente.getNombre(), existente.getApellido(),
                existente.getTelefono(), existente.getEmail(), existente.getMatriculaProfesional(), existente.getEspecialidad(), existente.getTurno());
        // Los setters del modelo también validan, por eso van dentro del try
        try {
            existente.setNombre(nombre);
            existente.setApellido(apellido);
            existente.setDni(dni);
            existente.setTelefono(telefono);
            existente.setEmail(email);
            existente.setMatriculaProfesional(matricula);
            existente.setEspecialidad(especialidad);
            vet.modificarVeterinario(existente);
        } catch (IllegalArgumentException ex) {
            existente.setNombre(copia.getNombre());
            existente.setApellido(copia.getApellido());
            existente.setDni(copia.getDni());
            existente.setTelefono(copia.getTelefono());
            existente.setEmail(copia.getEmail());
            existente.setMatriculaProfesional(copia.getMatriculaProfesional());
            existente.setEspecialidad(copia.getEspecialidad());
            throw ex;
        }
    }
}

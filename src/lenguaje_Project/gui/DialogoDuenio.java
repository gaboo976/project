package lenguaje_Project.gui;

import java.awt.Window;
import javax.swing.JTextField;
import lenguaje_Project.Dueño;
import lenguaje_Project.GestorBaseDeDatos;

/** Alta y edición de un dueño. Si recibe un dueño, lo edita; si recibe null, crea uno nuevo. */
public class DialogoDuenio extends DialogoBase {
    private static final long serialVersionUID = 1L;

    private final GestorBaseDeDatos vet;
    private final Dueño existente;
    private final JTextField txtNombre = new JTextField(20);
    private final JTextField txtApellido = new JTextField(20);
    private final JTextField txtDni = new JTextField(20);
    private final JTextField txtTelefono = new JTextField(20);
    private final JTextField txtEmail = new JTextField(20);
    private final JTextField txtDireccion = new JTextField(20);

    public DialogoDuenio(Window owner, GestorBaseDeDatos vet, Dueño existente) {
        super(owner, existente == null ? "Nuevo dueño" : "Editar dueño");
        this.vet = vet;
        this.existente = existente;

        agregarCampo("Nombre:", txtNombre);
        agregarCampo("Apellido:", txtApellido);
        agregarCampo("DNI:", txtDni);
        agregarCampo("Teléfono:", txtTelefono);
        agregarCampo("Email:", txtEmail);
        agregarCampo("Dirección:", txtDireccion);

        if (existente != null) {
            txtNombre.setText(existente.getNombre());
            txtApellido.setText(existente.getApellido());
            txtDni.setText(String.valueOf(existente.getDni()));
            txtTelefono.setText(existente.getTelefono());
            txtEmail.setText(existente.getEmail());
            txtDireccion.setText(existente.getDireccion());
        }
        armar();
    }

    @Override
    protected void guardar() {
        // Primero se valida el formato de todos los campos
        String nombre = Util.texto(txtNombre, "Nombre");
        String apellido = Util.texto(txtApellido, "Apellido");
        String dni = Util.dni(txtDni, "DNI");
        String telefono = Util.texto(txtTelefono, "Teléfono");
        String email = Util.texto(txtEmail, "Email");
        String direccion = Util.texto(txtDireccion, "Dirección");
        if (!email.contains("@")) throw new IllegalArgumentException("El email no es válido.");

        if (existente == null) {
            vet.registrarDuenio(new Dueño(dni, nombre, apellido, telefono, email, direccion, false));
            return;
        }
        // Edición: guardamos los valores viejos por si el gestor rechaza el cambio
        Dueño copia = new Dueño(existente.getDni(), existente.getNombre(), existente.getApellido(),
                existente.getTelefono(), existente.getEmail(), existente.getDireccion(), existente.isClienteFrecuente());
        // Los setters del modelo también validan, por eso van dentro del try
        try {
            existente.setNombre(nombre);
            existente.setApellido(apellido);
            existente.setDni(dni);
            existente.setTelefono(telefono);
            existente.setEmail(email);
            existente.setDireccion(direccion);
            vet.modificarDuenio(existente);
        } catch (IllegalArgumentException ex) {
            existente.setNombre(copia.getNombre());
            existente.setApellido(copia.getApellido());
            existente.setDni(copia.getDni());
            existente.setTelefono(copia.getTelefono());
            existente.setEmail(copia.getEmail());
            existente.setDireccion(copia.getDireccion());
            throw ex;
        }
    }
}

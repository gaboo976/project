package lenguaje_Project.gui;

import java.awt.Window;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import lenguaje_Project.Dueño;
import lenguaje_Project.Gato;
import lenguaje_Project.Mascota;
import lenguaje_Project.Otro;
import lenguaje_Project.Perro;
import lenguaje_Project.GestorBaseDeDatos;

/**
 * Alta y edición de una mascota. El combo de especie muestra u oculta los campos
 * propios de cada una. Al editar no se puede cambiar la especie.
 */
public class DialogoMascota extends DialogoBase {
    private static final long serialVersionUID = 1L;
    private static final String[] ESPECIES = {"Perro", "Gato", "Otro"};
    private static final String[] TAMANIOS = {"Chico", "Mediano", "Grande"};

    private final GestorBaseDeDatos vet;
    private final Mascota existente;

    private final JComboBox<String> cmbEspecie = new JComboBox<String>(ESPECIES);
    private final JTextField txtNombre = new JTextField(20);
    private final JTextField txtEdad = new JTextField(20);
    private final JTextField txtPeso = new JTextField(20);
    private final JTextField txtRaza = new JTextField(20);
    private final JComboBox<Dueño> cmbDuenio = new JComboBox<Dueño>();
    // Campos propios de Perro y Gato
    private final JComboBox<String> cmbTamanio = new JComboBox<String>(TAMANIOS);
    private final JCheckBox chkVacunas = new JCheckBox("Vacunas actualizadas");
    private final JLabel lblTamanio;
    private final JLabel lblVacunas;
    // Campo propio de Otro
    private final JTextField txtTipoAnimal = new JTextField(20);
    private final JLabel lblTipoAnimal;

    public DialogoMascota(Window owner, GestorBaseDeDatos vet, Mascota existente) {
        super(owner, existente == null ? "Nueva mascota" : "Editar mascota");
        this.vet = vet;
        this.existente = existente;

        // Los dueños se piden al abrir el diálogo, no se guardan copias
        List<Dueño> duenios = vet.obtenerDuenios();
        for (Dueño d : duenios) cmbDuenio.addItem(d);

        agregarCampo("Especie:", cmbEspecie);
        agregarCampo("Nombre:", txtNombre);
        agregarCampo("Edad (años):", txtEdad);
        agregarCampo("Peso (kg):", txtPeso);
        agregarCampo("Raza:", txtRaza);
        Util.formatearCombo(cmbDuenio, Util::etiquetaDuenio);
        agregarCampo("Dueño:", cmbDuenio);
        lblTamanio = agregarCampo("Tamaño:", cmbTamanio);
        lblVacunas = agregarCampo("Vacunas:", chkVacunas);
        lblTipoAnimal = agregarCampo("Tipo de animal:", txtTipoAnimal);

        cmbEspecie.addActionListener(e -> {
            actualizarCamposEspecie();
            pack();
        });

        if (existente != null) {
            // Otro.getEspecie() devuelve el tipo de animal, por eso se usa instanceof
            cmbEspecie.setSelectedItem(existente instanceof Perro ? "Perro" : existente instanceof Gato ? "Gato" : "Otro");
            cmbEspecie.setEnabled(false);
            txtNombre.setText(existente.getNombre());
            txtEdad.setText(String.valueOf(existente.getEdad()));
            txtPeso.setText(String.valueOf(existente.getPeso()));
            txtRaza.setText(existente.getRaza());
            cmbDuenio.setSelectedItem(existente.getDueño());
            if (existente instanceof Perro) {
                Perro p = (Perro) existente;
                cmbTamanio.setSelectedItem(p.getTamaño());
                chkVacunas.setSelected(p.isVacunasActualizadas());
            } else if (existente instanceof Gato) {
                Gato g = (Gato) existente;
                cmbTamanio.setSelectedItem(g.getTamanio());
                chkVacunas.setSelected(g.isVacunasActualizadas());
            } else if (existente instanceof Otro) {
                txtTipoAnimal.setText(((Otro) existente).getTipoAnimal());
            }
        }
        actualizarCamposEspecie();
        armar();
    }

    /** Muestra solo los campos que corresponden a la especie elegida. */
    private void actualizarCamposEspecie() {
        boolean esOtro = "Otro".equals(cmbEspecie.getSelectedItem());
        lblTamanio.setVisible(!esOtro);
        cmbTamanio.setVisible(!esOtro);
        lblVacunas.setVisible(!esOtro);
        chkVacunas.setVisible(!esOtro);
        lblTipoAnimal.setVisible(esOtro);
        txtTipoAnimal.setVisible(esOtro);
    }

    @Override
    protected void guardar() {
        String especie = (String) cmbEspecie.getSelectedItem();
        String nombre = Util.texto(txtNombre, "Nombre");
        int edad = Util.entero(txtEdad, "Edad");
        double peso = Util.decimal(txtPeso, "Peso");
        String raza = Util.texto(txtRaza, "Raza");
        Dueño duenio = (Dueño) cmbDuenio.getSelectedItem();
        if (duenio == null) throw new IllegalArgumentException("Elegí un dueño (es obligatorio).");
        String tamanio = (String) cmbTamanio.getSelectedItem();
        boolean vacunas = chkVacunas.isSelected();
        String tipoAnimal = "Otro".equals(especie) ? Util.texto(txtTipoAnimal, "Tipo de animal") : null;

        if (existente == null) {
            Mascota m;
            if ("Perro".equals(especie)) {
                m = new Perro(nombre, edad, peso, raza, duenio, tamanio, vacunas);
            } else if ("Gato".equals(especie)) {
                m = new Gato(nombre, edad, peso, raza, duenio, tamanio, vacunas);
            } else {
                m = new Otro(nombre, edad, peso, raza, duenio, tipoAnimal);
            }
            vet.registrarMascota(m);
            return;
        }

        // Edición: se guardan los valores viejos por si el gestor rechaza el cambio
        String viejoNombre = existente.getNombre();
        int viejaEdad = existente.getEdad();
        double viejoPeso = existente.getPeso();
        String viejaRaza = existente.getRaza();
        Dueño viejoDuenio = existente.getDueño();
        String viejoTamanio = null;
        boolean viejasVacunas = false;
        String viejoTipo = null;
        if (existente instanceof Perro) {
            viejoTamanio = ((Perro) existente).getTamaño();
            viejasVacunas = ((Perro) existente).isVacunasActualizadas();
        } else if (existente instanceof Gato) {
            viejoTamanio = ((Gato) existente).getTamanio();
            viejasVacunas = ((Gato) existente).isVacunasActualizadas();
        } else if (existente instanceof Otro) {
            viejoTipo = ((Otro) existente).getTipoAnimal();
        }

        // Los setters del modelo también validan, por eso aplicar() va dentro del try
        try {
            aplicar(existente, nombre, edad, peso, raza, duenio, tamanio, vacunas, tipoAnimal);
            vet.modificarMascota(existente);
        } catch (IllegalArgumentException ex) {
            aplicar(existente, viejoNombre, viejaEdad, viejoPeso, viejaRaza, viejoDuenio,
                    viejoTamanio, viejasVacunas, viejoTipo);
            throw ex;
        }
    }

    /** Copia los valores a la mascota según su clase. */
    private void aplicar(Mascota m, String nombre, int edad, double peso, String raza, Dueño duenio,
                         String tamanio, boolean vacunas, String tipoAnimal) {
        m.setNombre(nombre);
        m.setEdad(edad);
        m.setPeso(peso);
        m.setRaza(raza);
        m.setDueño(duenio);
        if (m instanceof Perro) {
            ((Perro) m).setTamaño(tamanio);
            ((Perro) m).setVacunasActualizadas(vacunas);
        } else if (m instanceof Gato) {
            ((Gato) m).setTamaño(tamanio);
            ((Gato) m).setVacunasActualizadas(vacunas);
        } else if (m instanceof Otro) {
            ((Otro) m).setTipoAnimal(tipoAnimal);
        }
    }
}

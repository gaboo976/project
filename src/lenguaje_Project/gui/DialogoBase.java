package lenguaje_Project.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Diálogo modal con formulario y botones Guardar / Cancelar.
 * El hijo agrega sus campos, implementa guardar() y al final llama a armar().
 * Si guardar() lanza IllegalArgumentException se muestra el mensaje y el diálogo sigue abierto.
 */
public abstract class DialogoBase extends JDialog {
    private static final long serialVersionUID = 1L;

    private final JPanel formulario = new JPanel(new GridBagLayout());
    private int fila = 0;
    private boolean guardado = false;

    protected DialogoBase(Window owner, String titulo) {
        super(owner, titulo, ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        formulario.setBorder(BorderFactory.createEmptyBorder(12, 12, 6, 12));
    }

    /** Agrega una fila "etiqueta + campo". Devuelve la etiqueta (sirve para ocultar la fila). */
    protected JLabel agregarCampo(String etiqueta, JComponent campo) {
        JLabel lbl = new JLabel(etiqueta);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = fila;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(4, 0, 4, 10);
        formulario.add(lbl, c);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = fila;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(4, 0, 4, 0);
        formulario.add(campo, c);
        fila++;
        return lbl;
    }

    /** Cada hijo implementa el guardado; debe lanzar IllegalArgumentException si algo está mal. */
    protected abstract void guardar();

    /** Agrega los botones, ajusta el tamaño y centra el diálogo. Llamar al final del constructor. */
    protected void armar() {
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.add(btnGuardar);
        botones.add(btnCancelar);

        btnGuardar.addActionListener(e -> {
            try {
                guardar();
                guardado = true;
                dispose();
            } catch (IllegalArgumentException ex) {
                Util.error(this, ex.getMessage());
            } catch (RuntimeException ex) {
                Util.error(this, "Ocurrió un error inesperado: " + ex.getMessage());
            }
        });
        btnCancelar.addActionListener(e -> dispose());

        setLayout(new BorderLayout());
        add(formulario, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(btnGuardar);
        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(getOwner());
    }

    public boolean fueGuardado() { return guardado; }
}

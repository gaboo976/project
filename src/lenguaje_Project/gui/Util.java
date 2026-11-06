package lenguaje_Project.gui;

import java.awt.Component;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/** Utilidades de la GUI: formatos de fecha/hora, lectura de campos y mensajes de error. */
public final class Util {
    // STRICT para rechazar fechas imposibles como 31/02/2025
    public static final DateTimeFormatter FECHA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
    public static final DateTimeFormatter HORA =
            DateTimeFormatter.ofPattern("HH:mm").withResolverStyle(ResolverStyle.STRICT);

    private Util() { }

    public static void error(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(padre, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void info(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(padre, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Texto obligatorio: lanza IllegalArgumentException si está vacío. */
    public static String texto(JTextField campo, String nombre) {
        String s = campo.getText().trim();
        if (s.isEmpty()) throw new IllegalArgumentException("El campo " + nombre + " es obligatorio.");
        return s;
    }

    public static int entero(JTextField campo, String nombre) {
        String s = texto(campo, nombre);
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo " + nombre + " debe ser un número entero.");
        }
    }

    public static double decimal(JTextField campo, String nombre) {
        String s = texto(campo, nombre).replace(',', '.');
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo " + nombre + " debe ser un número (ej: 12.5).");
        }
    }

    public static LocalDate fecha(JTextField campo, String nombre) {
        String s = texto(campo, nombre);
        try {
            return LocalDate.parse(s, FECHA);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("El campo " + nombre + " debe tener formato dd/MM/yyyy y ser una fecha válida.");
        }
    }

    public static LocalTime hora(JTextField campo, String nombre) {
        String s = texto(campo, nombre);
        try {
            return LocalTime.parse(s, HORA);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("El campo " + nombre + " debe tener formato HH:mm (ej: 14:30).");
        }
    }

    /** DNI como texto (el modelo del grupo lo guarda como String): obligatorio y solo dígitos. */
    public static String dni(JTextField campo, String nombre) {
        String s = texto(campo, nombre);
        if (!s.matches("\\d+")) {
            throw new IllegalArgumentException("El campo " + nombre + " debe tener solo números.");
        }
        return s;
    }

    /** Texto corto de un dueño para tablas y combos (el toString del grupo es muy largo). */
    public static String etiquetaDuenio(lenguaje_Project.Dueño d) {
        return d.getApellido() + ", " + d.getNombre() + " (DNI " + d.getDni() + ")";
    }

    /** Texto corto de un veterinario para tablas y combos. */
    public static String etiquetaVeterinario(lenguaje_Project.Veterinario v) {
        return v.getApellido() + ", " + v.getNombre() + " (Mat. " + v.getMatriculaProfesional() + ")";
    }

    /** Hace que cualquier combo muestre el texto que devuelve f en vez del toString del objeto. */
    @SuppressWarnings("unchecked")
    public static <T> void formatearCombo(javax.swing.JComboBox<T> combo, java.util.function.Function<T, String> f) {
        combo.setRenderer(new javax.swing.DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;
            @Override
            public Component getListCellRendererComponent(javax.swing.JList<?> lista, Object valor, int indice,
                                                          boolean seleccionado, boolean foco) {
                super.getListCellRendererComponent(lista, valor, indice, seleccionado, foco);
                if (valor != null) setText(f.apply((T) valor));
                return this;
            }
        });
    }

    /** Texto corto de una mascota para tablas y combos (el toString del grupo es muy largo). */
    public static String etiquetaMascota(lenguaje_Project.Mascota m) {
        return m.getNombre() + " (" + m.getEspecie() + ")";
    }

    /** Hace que un combo de mascotas muestre la etiqueta corta en vez del toString largo. */
    public static void formatearComboMascotas(javax.swing.JComboBox<lenguaje_Project.Mascota> combo) {
        combo.setRenderer(new javax.swing.DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;
            @Override
            public Component getListCellRendererComponent(javax.swing.JList<?> lista, Object valor, int indice,
                                                          boolean seleccionado, boolean foco) {
                super.getListCellRendererComponent(lista, valor, indice, seleccionado, foco);
                if (valor instanceof lenguaje_Project.Mascota) {
                    setText(etiquetaMascota((lenguaje_Project.Mascota) valor));
                }
                return this;
            }
        });
    }

    public static String dinero(double monto) {
        return String.format(Locale.US, "$%.2f", monto);
    }
}

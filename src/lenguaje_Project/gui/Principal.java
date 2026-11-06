package lenguaje_Project.gui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import lenguaje_Project.GestorBaseDeDatos;

/** Punto de entrada de la GUI (el main de prueba del grupo queda aparte). */
public class Principal {
    public static void main(String[] args) {
        // Look and feel Nimbus; si no está disponible se usa el de por defecto
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // seguimos con el look and feel por defecto
        }

        SwingUtilities.invokeLater(() -> {
            // El gestor carga el archivo al crearse (o arranca vacío si no existe)
            GestorBaseDeDatos gestor = new GestorBaseDeDatos();
            new VentanaPrincipal(gestor).setVisible(true);
        });
    }
}

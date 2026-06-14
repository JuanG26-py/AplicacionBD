import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardlayout;
    private JPanel contenedor;

    public MainFrame() {
        setTitle("Aerolinea -Gestion de reservas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        cardlayout = new CardLayout();
        contenedor = new JPanel(cardlayout);

        contenedor.add(new PanelVuelos().getMainPanel(), "vuelos");

        contenedor.add(new LoginPanel(this), "login");
        setContentPane(contenedor);
        cardlayout.show(contenedor,"vuelos");
    }

    public void mostrarPanel(String nombre){
        cardlayout.show(contenedor, nombre);
    }

    public void agregarPanel(JPanel panel, String nombre){
        contenedor.add(panel,nombre);
    }

    static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}

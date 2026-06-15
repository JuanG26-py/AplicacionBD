import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardlayout;
    private JPanel contenedor;
    private JPanel menuLateral;

    public MainFrame() {
        setTitle("Aerolinea - Gestion de reservas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        cardlayout = new CardLayout();
        contenedor = new JPanel(cardlayout);

        // Paneles
        contenedor.add(new LoginPanel(this), "login");
        contenedor.add(new PanelVuelos().getMainPanel(), "vuelos");
        contenedor.add(new PanelClientes().getMainPanel(), "clientes");
        contenedor.add(new PanelPaquetes().getMainPanel(), "paquetes");
        contenedor.add(new PanelDestinos().getMainPanel(), "destinos");
        contenedor.add(new PanelReportes().getMainPanel(), "reportes");
        contenedor.add(new PanelAgente().getMainPanel(), "agente");
        contenedor.add(new PanelBuscarVuelos().getMainPanel(), "buscarVuelos");
        contenedor.add(new PanelNuevaReserva().getMainPanel(), "nuevaReserva");
        contenedor.add(new PanelMisReservas().getMainPanel(),  "misReservas");

        // Menú lateral (oculto al inicio)
        menuLateral = crearMenuLateral();
        menuLateral.setVisible(false);

        setLayout(new BorderLayout());
        add(menuLateral, BorderLayout.WEST);
        add(contenedor, BorderLayout.CENTER);

        cardlayout.show(contenedor, "login");
    }

    private JPanel crearMenuLateral() {
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setPreferredSize(new Dimension(160, 0));

        JButton btnVuelos = new JButton("Vuelos");
        JButton btnClientes = new JButton("Clientes");
        JButton btnPaquetes = new JButton("Paquetes");
        JButton btnDestinos = new JButton("Destinos");
        JButton btnReportes = new JButton("Reportes");
        JButton btnAgente = new JButton("Gestion Agente");
        JButton btnBuscarVuelos = new JButton("Buscar vuelos");
        JButton btnMisReservas  = new JButton("Mis reservas");
        JButton btnCerrarSesion = new JButton("Cerrar sesion");

        btnVuelos.addActionListener(e -> mostrarPanel("vuelos"));
        btnClientes.addActionListener(e -> mostrarPanel("clientes"));
        btnPaquetes.addActionListener(e -> mostrarPanel("paquetes"));
        btnDestinos.addActionListener(e -> mostrarPanel("destinos"));
        btnReportes.addActionListener(e -> mostrarPanel("reportes"));
        btnAgente.addActionListener(e -> mostrarPanel("agente"));
        btnBuscarVuelos.addActionListener(e -> mostrarPanel("buscarVuelos"));
        btnMisReservas .addActionListener(e -> mostrarPanel("misReservas"));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        menu.add(btnVuelos);
        menu.add(btnClientes);
        menu.add(btnPaquetes);
        menu.add(btnDestinos);
        menu.add(btnReportes);
        menu.add(btnAgente);
        menu.add(btnBuscarVuelos);
        menu.add(btnMisReservas);
        menu.add(Box.createVerticalGlue());
        menu.add(btnCerrarSesion);

        return menu;
    }

    public void mostrarPanel(String nombre) {
        cardlayout.show(contenedor, nombre);
    }

    // Se llama desde LoginPanel al iniciar sesión correctamente
    public void iniciarSesionExitosa(String nombrePanel) {
        menuLateral.setVisible(true);
        mostrarPanel(nombrePanel);
    }

    private void cerrarSesion() {
        menuLateral.setVisible(false);
        mostrarPanel("login");
    }

    public void agregarPanel(JPanel panel, String nombre) {
        contenedor.add(panel, nombre);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}

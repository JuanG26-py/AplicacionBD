import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel{
    private JPanel mainPanel;
    private JTextField textField2;
    private JButton INICIARSESIONButton;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JButton SALIRButton;

    private MainFrame frame;

    public LoginPanel(MainFrame frame) {
        this.frame = frame;

        setLayout(new java.awt.BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        INICIARSESIONButton.addActionListener(e -> iniciarSesion());
        SALIRButton.addActionListener(e -> System.exit(0));
    }

    public void iniciarSesion(){
        String usuario = textField1.getText().trim();
        String contraseña= textField2.getText().trim();
        String rol = (String)comboBox1.getSelectedItem();

        if (usuario.isEmpty() || contraseña.isEmpty()){
            JOptionPane.showMessageDialog(mainPanel, "Complete todos los campos.", "Aviso",  JOptionPane.WARNING_MESSAGE);
            return;
        }

        switch(rol){
            case "Administrador" -> frame.mostrarPanel("admin");
            case "Agente" -> frame.mostrarPanel("agente");
            case "Cliente" -> frame.mostrarPanel("cliente");
            default -> JOptionPane.showMessageDialog(mainPanel, "Seleccione un rol");
        }
    }
}

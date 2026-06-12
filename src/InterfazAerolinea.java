import javax.swing.*;

public class InterfazAerolinea {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JTextField textField2;
    private JButton INICIARSESIONButton;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JButton salirButton;

    static void main(String[] args) {
        JFrame frame = new JFrame("AplicacionBD");
        frame.setContentPane(new InterfazAerolinea().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(500, 500);
    }
}

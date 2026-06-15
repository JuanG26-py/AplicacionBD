import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelDestinos {

    private JTabbedPane tabsDestinos;
    private JPanel mainPanel;

    // Pestaña Países
    private JTextField txtNombrePais;
    private JButton btnGuardarPais;
    private JButton btnEliminarPais;
    private JButton btnLimpiarPais;
    private JTable tablaPaises;

    // Pestaña Departamentos
    private JComboBox cmbPaisDepto;
    private JTextField txtNombreDepto;
    private JButton btnGuardarDepto;
    private JButton btnEliminarDepto;
    private JButton btnLimpiarDepto;
    private JTable tablaDepartamentos;

    // Pestaña Ciudades
    private JComboBox cmbDeptoCiudad;
    private JTextField txtNombreCiudad;
    private JButton btnGuardarCiudad;
    private JButton btnEliminarCiudad;
    private JButton btnLimpiarCiudad;
    private JTable tablaCiudades;

    private DefaultTableModel modeloPaises;
    private DefaultTableModel modeloDepartamentos;
    private DefaultTableModel modeloCiudades;

    public PanelDestinos() {
        configurarTablas();
        cargarPaises();
        cargarDepartamentos();
        cargarCiudades();

        // Listeners Países
        btnGuardarPais.addActionListener(e -> guardarPais());
        btnEliminarPais.addActionListener(e -> eliminarPais());
        btnLimpiarPais.addActionListener(e -> txtNombrePais.setText(""));
        tablaPaises.getSelectionModel().addListSelectionListener(e -> cargarSeleccionPais());

        // Listeners Departamentos
        btnGuardarDepto.addActionListener(e -> guardarDepartamento());
        btnEliminarDepto.addActionListener(e -> eliminarDepartamento());
        btnLimpiarDepto.addActionListener(e -> txtNombreDepto.setText(""));
        tablaDepartamentos.getSelectionModel().addListSelectionListener(e -> cargarSeleccionDepto());

        // Listeners Ciudades
        btnGuardarCiudad.addActionListener(e -> guardarCiudad());
        btnEliminarCiudad.addActionListener(e -> eliminarCiudad());
        btnLimpiarCiudad.addActionListener(e -> txtNombreCiudad.setText(""));
        tablaCiudades.getSelectionModel().addListSelectionListener(e -> cargarSeleccionCiudad());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void configurarTablas() {
        modeloPaises = new DefaultTableModel(new Object[]{"Id", "Nombre"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaPaises.setModel(modeloPaises);

        modeloDepartamentos = new DefaultTableModel(new Object[]{"Id", "Nombre", "Pais"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaDepartamentos.setModel(modeloDepartamentos);

        modeloCiudades = new DefaultTableModel(new Object[]{"Id", "Nombre", "Departamento"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaCiudades.setModel(modeloCiudades);
    }

    // ===== PAÍSES =====
    private void cargarPaises() {
        // TODO: SELECT * FROM pais
        modeloPaises.setRowCount(0);
        modeloPaises.addRow(new Object[]{1, "Colombia"});
        modeloPaises.addRow(new Object[]{2, "Peru"});

        actualizarComboPaises();
    }

    private void actualizarComboPaises() {
        cmbPaisDepto.removeAllItems();
        for (int i = 0; i < modeloPaises.getRowCount(); i++) {
            cmbPaisDepto.addItem(modeloPaises.getValueAt(i, 1));
        }
    }

    private void cargarSeleccionPais() {
        int fila = tablaPaises.getSelectedRow();
        if (fila == -1) return;
        txtNombrePais.setText(modeloPaises.getValueAt(fila, 1).toString());
    }

    private void guardarPais() {
        String nombre = txtNombrePais.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "El nombre del pais es obligatorio");
            return;
        }

        // TODO: INSERT INTO pais (nombre) VALUES (?)
        int nuevoId = modeloPaises.getRowCount() + 1;
        modeloPaises.addRow(new Object[]{nuevoId, nombre});
        actualizarComboPaises();
        txtNombrePais.setText("");
    }

    private void eliminarPais() {
        int fila = tablaPaises.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(mainPanel, "Seleccione un pais de la tabla");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(mainPanel,
                "Eliminar el pais seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmar != JOptionPane.YES_OPTION) return;

        // TODO: DELETE FROM pais WHERE id = ?
        modeloPaises.removeRow(fila);
        actualizarComboPaises();
        txtNombrePais.setText("");
    }

    // ===== DEPARTAMENTOS =====
    private void cargarDepartamentos() {
        // TODO: SELECT * FROM departamento
        modeloDepartamentos.setRowCount(0);
        modeloDepartamentos.addRow(new Object[]{1, "Valle del Cauca", "Colombia"});
        modeloDepartamentos.addRow(new Object[]{2, "Antioquia", "Colombia"});

        actualizarComboDepartamentos();
    }

    private void actualizarComboDepartamentos() {
        cmbDeptoCiudad.removeAllItems();
        for (int i = 0; i < modeloDepartamentos.getRowCount(); i++) {
            cmbDeptoCiudad.addItem(modeloDepartamentos.getValueAt(i, 1));
        }
    }

    private void cargarSeleccionDepto() {
        int fila = tablaDepartamentos.getSelectedRow();
        if (fila == -1) return;
        txtNombreDepto.setText(modeloDepartamentos.getValueAt(fila, 1).toString());
        cmbPaisDepto.setSelectedItem(modeloDepartamentos.getValueAt(fila, 2));
    }

    private void guardarDepartamento() {
        String nombre = txtNombreDepto.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "El nombre del departamento es obligatorio");
            return;
        }
        if (cmbPaisDepto.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(mainPanel, "Seleccione un pais");
            return;
        }

        // TODO: INSERT INTO departamento (nombre, pais_id) VALUES (?, ?)
        int nuevoId = modeloDepartamentos.getRowCount() + 1;
        modeloDepartamentos.addRow(new Object[]{nuevoId, nombre, cmbPaisDepto.getSelectedItem()});
        actualizarComboDepartamentos();
        txtNombreDepto.setText("");
    }

    private void eliminarDepartamento() {
        int fila = tablaDepartamentos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(mainPanel, "Seleccione un departamento de la tabla");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(mainPanel,
                "Eliminar el departamento seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmar != JOptionPane.YES_OPTION) return;

        // TODO: DELETE FROM departamento WHERE id = ?
        modeloDepartamentos.removeRow(fila);
        actualizarComboDepartamentos();
        txtNombreDepto.setText("");
    }

    // ===== CIUDADES =====
    private void cargarCiudades() {
        // TODO: SELECT * FROM ciudad
        modeloCiudades.setRowCount(0);
        modeloCiudades.addRow(new Object[]{1, "Cali", "Valle del Cauca"});
        modeloCiudades.addRow(new Object[]{2, "Medellin", "Antioquia"});
    }

    private void cargarSeleccionCiudad() {
        int fila = tablaCiudades.getSelectedRow();
        if (fila == -1) return;
        txtNombreCiudad.setText(modeloCiudades.getValueAt(fila, 1).toString());
        cmbDeptoCiudad.setSelectedItem(modeloCiudades.getValueAt(fila, 2));
    }

    private void guardarCiudad() {
        String nombre = txtNombreCiudad.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "El nombre de la ciudad es obligatorio");
            return;
        }
        if (cmbDeptoCiudad.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(mainPanel, "Seleccione un departamento");
            return;
        }

        // TODO: INSERT INTO ciudad (nombre, departamento_id) VALUES (?, ?)
        int nuevoId = modeloCiudades.getRowCount() + 1;
        modeloCiudades.addRow(new Object[]{nuevoId, nombre, cmbDeptoCiudad.getSelectedItem()});
        txtNombreCiudad.setText("");
    }

    private void eliminarCiudad() {
        int fila = tablaCiudades.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(mainPanel, "Seleccione una ciudad de la tabla");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(mainPanel,
                "Eliminar la ciudad seleccionada?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmar != JOptionPane.YES_OPTION) return;

        // TODO: DELETE FROM ciudad WHERE id = ?
        modeloCiudades.removeRow(fila);
        txtNombreCiudad.setText("");
    }
}

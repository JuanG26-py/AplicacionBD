import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelClientes {
    private JPanel mainPanel;
    private JTextField txtIdentificacion;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtCorreo;
    private JTextField txtDireccion;
    private JComboBox cmbPais;
    private JComboBox cmbDepartamento;
    private JComboBox cmbCiudad;
    private JTextField txtTelefonoPrincipal;
    private JTextField txtTelefonoAlterno;
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JTable tablaClientes;



    private DefaultTableModel modeloTabla;

    public PanelClientes() {
        configurarTabla();
        cargarPaises();
        cargarClientes();

        cmbPais.addActionListener(e -> cargarDepartamentos());
        cmbDepartamento.addActionListener(e -> cargarCiudades());

        btnGuardar.addActionListener(e -> guardarCliente());
        btnActualizar.addActionListener(e -> actualizarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        tablaClientes.getSelectionModel().addListSelectionListener(e -> cargarSeleccion());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(
                new Object[]{"Identificacion", "Nombres", "Apellidos", "Correo", "Direccion",
                        "Pais", "Departamento", "Ciudad", "Tel. Principal", "Tel. Alterno"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaClientes.setModel(modeloTabla);
    }

    private void cargarPaises() {
        // TODO: reemplazar con SELECT * FROM pais
        String[] paises = new String[]{"Colombia", "Peru", "Ecuador"};
        cmbPais.setModel(new DefaultComboBoxModel<>(paises));
        cargarDepartamentos();
    }

    private void cargarDepartamentos() {
        // TODO: reemplazar con SELECT * FROM departamento WHERE pais_id = ?
        String paisSeleccionado = (String) cmbPais.getSelectedItem();
        String[] departamentos;

        if ("Colombia".equals(paisSeleccionado)) {
            departamentos = new String[]{"Valle del Cauca", "Antioquia", "Cundinamarca"};
        } else if ("Peru".equals(paisSeleccionado)) {
            departamentos = new String[]{"Lima", "Cusco"};
        } else {
            departamentos = new String[]{"Pichincha", "Guayas"};
        }

        cmbDepartamento.setModel(new DefaultComboBoxModel<>(departamentos));
        cargarCiudades();
    }

    private void cargarCiudades() {
        // TODO: reemplazar con SELECT * FROM ciudad WHERE departamento_id = ?
        String departamentoSeleccionado = (String) cmbDepartamento.getSelectedItem();
        String[] ciudades;

        if ("Valle del Cauca".equals(departamentoSeleccionado)) {
            ciudades = new String[]{"Cali", "Yumbo", "Palmira"};
        } else if ("Antioquia".equals(departamentoSeleccionado)) {
            ciudades = new String[]{"Medellin", "Envigado"};
        } else if ("Cundinamarca".equals(departamentoSeleccionado)) {
            ciudades = new String[]{"Bogota", "Soacha"};
        } else {
            ciudades = new String[]{"Ciudad 1", "Ciudad 2"};
        }

        cmbCiudad.setModel(new DefaultComboBoxModel<>(ciudades));
    }

    private void cargarClientes() {
        // TODO: reemplazar con SELECT * FROM cliente
        modeloTabla.setRowCount(0);
    }

    private void cargarSeleccion() {
        int fila = tablaClientes.getSelectedRow();
        if (fila == -1) return;

        txtIdentificacion.setText(modeloTabla.getValueAt(fila, 0).toString());
        txtNombres.setText(modeloTabla.getValueAt(fila, 1).toString());
        txtApellidos.setText(modeloTabla.getValueAt(fila, 2).toString());
        txtCorreo.setText(modeloTabla.getValueAt(fila, 3).toString());
        txtDireccion.setText(modeloTabla.getValueAt(fila, 4).toString());
        cmbPais.setSelectedItem(modeloTabla.getValueAt(fila, 5));
        cmbDepartamento.setSelectedItem(modeloTabla.getValueAt(fila, 6));
        cmbCiudad.setSelectedItem(modeloTabla.getValueAt(fila, 7));
        txtTelefonoPrincipal.setText(modeloTabla.getValueAt(fila, 8).toString());
        txtTelefonoAlterno.setText(modeloTabla.getValueAt(fila, 9).toString());
    }

    private boolean validarCampos() {
        if (txtIdentificacion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "La identificacion es obligatoria");
            return false;
        }
        if (txtNombres.getText().trim().isEmpty() || txtApellidos.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Nombres y apellidos son obligatorios");
            return false;
        }
        if (txtCorreo.getText().trim().isEmpty() || !txtCorreo.getText().contains("@")) {
            JOptionPane.showMessageDialog(mainPanel, "Ingrese un correo valido");
            return false;
        }
        if (txtTelefonoPrincipal.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "El telefono principal es obligatorio");
            return false;
        }
        return true;
    }

    private void guardarCliente() {
        if (!validarCampos()) return;

        // TODO: INSERT INTO cliente (...)
        modeloTabla.addRow(new Object[]{
                txtIdentificacion.getText().trim(),
                txtNombres.getText().trim(),
                txtApellidos.getText().trim(),
                txtCorreo.getText().trim(),
                txtDireccion.getText().trim(),
                cmbPais.getSelectedItem(),
                cmbDepartamento.getSelectedItem(),
                cmbCiudad.getSelectedItem(),
                txtTelefonoPrincipal.getText().trim(),
                txtTelefonoAlterno.getText().trim()
        });

        limpiarFormulario();
    }

    private void actualizarCliente() {
        int fila = tablaClientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(mainPanel, "Seleccione un cliente de la tabla");
            return;
        }
        if (!validarCampos()) return;

        // TODO: UPDATE cliente SET ... WHERE identificacion = ?
        modeloTabla.setValueAt(txtIdentificacion.getText().trim(), fila, 0);
        modeloTabla.setValueAt(txtNombres.getText().trim(), fila, 1);
        modeloTabla.setValueAt(txtApellidos.getText().trim(), fila, 2);
        modeloTabla.setValueAt(txtCorreo.getText().trim(), fila, 3);
        modeloTabla.setValueAt(txtDireccion.getText().trim(), fila, 4);
        modeloTabla.setValueAt(cmbPais.getSelectedItem(), fila, 5);
        modeloTabla.setValueAt(cmbDepartamento.getSelectedItem(), fila, 6);
        modeloTabla.setValueAt(cmbCiudad.getSelectedItem(), fila, 7);
        modeloTabla.setValueAt(txtTelefonoPrincipal.getText().trim(), fila, 8);
        modeloTabla.setValueAt(txtTelefonoAlterno.getText().trim(), fila, 9);
    }

    private void eliminarCliente() {
        int fila = tablaClientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(mainPanel, "Seleccione un cliente de la tabla");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(mainPanel,
                "Eliminar el cliente seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmar != JOptionPane.YES_OPTION) return;

        // TODO: DELETE FROM cliente WHERE identificacion = ?
        modeloTabla.removeRow(fila);
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        txtIdentificacion.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        txtTelefonoPrincipal.setText("");
        txtTelefonoAlterno.setText("");
        cmbPais.setSelectedIndex(0);
        tablaClientes.clearSelection();
    }
}
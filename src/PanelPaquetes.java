import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelPaquetes {
    private JPanel mainPanel;
    private JTextField txtNombre;
    private JComboBox cmbTipo;
    private JTextArea txtDescripcion;
    private JComboBox cmbDestino;
    private JTextField txtPrecio;
    private JComboBox cmbEstado;
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JTable tablaPaquetes;

    private DefaultTableModel modeloTabla;

    public PanelPaquetes() {
        configurarCombos();
        configurarTabla();
        cargarDestinos();
        cargarPaquetes();

        btnGuardar.addActionListener(e -> guardarPaquete());
        btnActualizar.addActionListener(e -> actualizarPaquete());
        btnEliminar.addActionListener(e -> eliminarPaquete());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        tablaPaquetes.getSelectionModel().addListSelectionListener(e -> cargarSeleccion());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void configurarCombos() {
        cmbTipo.setModel(new DefaultComboBoxModel<>(new String[]{
                "Alojamiento", "Transporte", "Tour", "Combinado"
        }));

        cmbEstado.setModel(new DefaultComboBoxModel<>(new String[]{
                "Disponible", "No disponible"
        }));
    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(
                new Object[]{"Nombre", "Tipo", "Descripcion", "Destino", "Precio", "Estado"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaPaquetes.setModel(modeloTabla);
    }

    private void cargarDestinos() {
        // TODO: reemplazar con SELECT * FROM ciudad
        String[] destinos = new String[]{"Cali", "Cartagena", "Medellin", "Bogota", "San Andres"};
        cmbDestino.setModel(new DefaultComboBoxModel<>(destinos));
    }

    private void cargarPaquetes() {
        // TODO: reemplazar con SELECT * FROM paquete_turistico
        modeloTabla.setRowCount(0);
    }

    private void cargarSeleccion() {
        int fila = tablaPaquetes.getSelectedRow();
        if (fila == -1) return;

        txtNombre.setText(modeloTabla.getValueAt(fila, 0).toString());
        cmbTipo.setSelectedItem(modeloTabla.getValueAt(fila, 1));
        txtDescripcion.setText(modeloTabla.getValueAt(fila, 2).toString());
        cmbDestino.setSelectedItem(modeloTabla.getValueAt(fila, 3));
        txtPrecio.setText(modeloTabla.getValueAt(fila, 4).toString());
        cmbEstado.setSelectedItem(modeloTabla.getValueAt(fila, 5));
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "El nombre del paquete es obligatorio");
            return false;
        }
        if (txtDescripcion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "La descripcion es obligatoria");
            return false;
        }
        try {
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            if (precio < 0) {
                JOptionPane.showMessageDialog(mainPanel, "El precio no puede ser negativo");
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mainPanel, "El precio debe ser numerico");
            return false;
        }
        return true;
    }

    private void guardarPaquete() {
        if (!validarCampos()) return;

        // TODO: INSERT INTO paquete_turistico (...)
        modeloTabla.addRow(new Object[]{
                txtNombre.getText().trim(),
                cmbTipo.getSelectedItem(),
                txtDescripcion.getText().trim(),
                cmbDestino.getSelectedItem(),
                txtPrecio.getText().trim(),
                cmbEstado.getSelectedItem()
        });

        limpiarFormulario();
    }

    private void actualizarPaquete() {
        int fila = tablaPaquetes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(mainPanel, "Seleccione un paquete de la tabla");
            return;
        }
        if (!validarCampos()) return;

        // TODO: UPDATE paquete_turistico SET ... WHERE id = ?
        modeloTabla.setValueAt(txtNombre.getText().trim(), fila, 0);
        modeloTabla.setValueAt(cmbTipo.getSelectedItem(), fila, 1);
        modeloTabla.setValueAt(txtDescripcion.getText().trim(), fila, 2);
        modeloTabla.setValueAt(cmbDestino.getSelectedItem(), fila, 3);
        modeloTabla.setValueAt(txtPrecio.getText().trim(), fila, 4);
        modeloTabla.setValueAt(cmbEstado.getSelectedItem(), fila, 5);
    }

    private void eliminarPaquete() {
        int fila = tablaPaquetes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(mainPanel, "Seleccione un paquete de la tabla");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(mainPanel,
                "Eliminar el paquete seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmar != JOptionPane.YES_OPTION) return;

        // TODO: DELETE FROM paquete_turistico WHERE id = ?
        modeloTabla.removeRow(fila);
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        cmbTipo.setSelectedIndex(0);
        cmbDestino.setSelectedIndex(0);
        cmbEstado.setSelectedIndex(0);
        tablaPaquetes.clearSelection();
    }
}
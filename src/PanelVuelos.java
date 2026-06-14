import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Calendar;
import java.util.Date;


public class PanelVuelos {
    private JPanel mainPanel;
    private JTextField txtCodigo;
    private JComboBox cmbOrigen;
    private JComboBox cmbDestino;
    private JSpinner spnSalida;
    private JSpinner spnLlegada;
    private JTextField txtCapacidad;
    private JTextField txtPrecioBase;
    private JComboBox cmbEstado;
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JTable tablaVuelos;

    private DefaultTableModel modeloTabla;

    public PanelVuelos() {
        configurarSpinners();
        configurarComboEstado();
        configurarTabla();
        cargarCiudades();
        cargarVuelos();

        btnGuardar.addActionListener(e -> guardarVuelo());
        btnActualizar.addActionListener(e -> actualizarVuelo());
        btnEliminar.addActionListener(e -> eliminarVuelo());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        tablaVuelos.getSelectionModel().addListSelectionListener(e -> cargarSeleccion());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void configurarSpinners() {
        SpinnerDateModel modeloSalida = new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE);
        spnSalida.setModel(modeloSalida);
        spnSalida.setEditor(new JSpinner.DateEditor(spnSalida, "dd/MM/yyyy HH:mm"));

        SpinnerDateModel modeloLlegada = new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE);
        spnLlegada.setModel(modeloLlegada);
        spnLlegada.setEditor(new JSpinner.DateEditor(spnLlegada, "dd/MM/yyyy HH:mm"));
    }

    private void configurarComboEstado() {
        cmbEstado.setModel(new DefaultComboBoxModel<>(new String[]{
                "Programado", "Abordado", "En vuelo", "Finalizado", "Cancelado"
        }));
    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(
                new Object[]{"Codigo", "Origen", "Destino", "Salida", "Llegada", "Capacidad", "Precio", "Estado"}, 0
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaVuelos.setModel(modeloTabla);
    }

    private void cargarCiudades() {
        //TODO: reemplazar con consulta real a la BD (tabla ciudad)
        String[] ciudades = new String[]{"Bogota", "Medellin", "Cali","Cartagena", "Barranquilla"};
        cmbOrigen.setModel(new DefaultComboBoxModel<>(ciudades));
        cmbDestino.setModel(new DefaultComboBoxModel<>(ciudades));
    }

    private void cargarVuelos() {
        // TODO: reemplazar con consulta SELECT * FROM vuelo
        modeloTabla.setRowCount(0);
    }

    private void cargarSeleccion() {
        int fila = tablaVuelos.getSelectedRow();
        if (fila == -1) return;

        txtCodigo.setText(modeloTabla.getValueAt(fila, 0).toString());
        cmbOrigen.setSelectedItem(modeloTabla.getValueAt(fila, 1));
        cmbDestino.setSelectedItem(modeloTabla.getValueAt(fila, 2));
        // fechas y demás campos se cargan según el formato que usen al guardar

        txtCapacidad.setText(modeloTabla.getValueAt(fila, 5).toString());
        txtPrecioBase.setText(modeloTabla.getValueAt(fila, 6).toString());
        cmbEstado.setSelectedItem(modeloTabla.getValueAt(fila, 7));
    }

    private boolean validarCampos() {
        if (txtCodigo.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(mainPanel, "El codigo del vuelo es obligatorio");
            return false;
        }

        if (cmbOrigen.getSelectedItem().equals(cmbDestino.getSelectedItem())) {
            JOptionPane.showMessageDialog(mainPanel, "Origen y destino no pueden ser iguales");
            return false;
        }

        try{
            Integer.parseInt(txtCapacidad.getText().trim());
            Double.parseDouble(txtPrecioBase.getText().trim());
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(mainPanel, "Capacidad y precio deben ser numericos");
            return false;
        }
        return true;
    }

    private void guardarVuelo() {
        if (!validarCampos()) return;

        // TODO: INSERT INTO vuelo (...)
        modeloTabla.addRow(new Object[]{
                txtCodigo.getText().trim(),
                cmbOrigen.getSelectedItem(),
                cmbDestino.getSelectedItem(),
                spnSalida.getValue(),
                spnLlegada.getValue(),
                txtCapacidad.getText().trim(),
                txtPrecioBase.getText().trim(),
                cmbEstado.getSelectedItem()
        });

        limpiarFormulario();
    }

    private void actualizarVuelo() {
        int fila =  tablaVuelos.getSelectedRow();
        if (fila == -1){
            JOptionPane.showMessageDialog(mainPanel, "Seleccione un vuelo de la tabla");
            return;
        }
        if (!validarCampos()) return;
        // TODO: UPDATE vuelo SET ... WHERE codigo = ?

        modeloTabla.setValueAt(txtCodigo.getText().trim(), fila, 0);
        modeloTabla.setValueAt(cmbOrigen.getSelectedItem(), fila, 1);
        modeloTabla.setValueAt(cmbDestino.getSelectedItem(), fila, 2);
        modeloTabla.setValueAt(txtCapacidad.getText(), fila, 5);
        modeloTabla.setValueAt(txtPrecioBase.getText().trim(), fila, 6);
        modeloTabla.setValueAt(cmbEstado.getSelectedItem(), fila, 7);
    }

    private void eliminarVuelo() {
        int fila = tablaVuelos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(mainPanel, "Seleccione un vuelo de la tabla");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(mainPanel,
                "Eliminar el vuelo seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmar != JOptionPane.YES_OPTION) return;

        // TODO: DELETE FROM vuelo WHERE codigo = ?
        modeloTabla.removeRow(fila);
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtCapacidad.setText("");
        txtPrecioBase.setText("");
        cmbOrigen.setSelectedIndex(0);
        cmbDestino.setSelectedIndex(0);
        cmbEstado.setSelectedIndex(0);
        tablaVuelos.clearSelection();
    }
}

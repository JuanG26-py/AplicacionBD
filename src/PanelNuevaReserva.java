import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelNuevaReserva {
    private JPanel mainPanel;
    private JTextField txtCodigoVuelo;
    private JTextField txtOrigen;
    private JTextField txtFechaSalida;
    private JTextField txtPrecioBase;
    private JComboBox cmbClase;
    private JTextField txtAsiento;
    private JCheckBox chkPaquete1;
    private JCheckBox chkPaquete2;
    private JCheckBox chkPaquete3;
    private JCheckBox chkPaquete4;
    private JTextField txtSubtotalVuelo;
    private JTextField txtSubtotalPaquetes;
    private JTextField txtTotal;
    private JButton btnConfirmar;
    private JButton btnLimpiar;
    private JButton btnVolver;
    private JTextField txtDestino;
    private JPanel panelPaquetes;

    // Tabla de paquetes disponibles (referencia interna)
    private DefaultTableModel modeloPaquetes;

    // Precio base del vuelo seleccionado
    private double precioBaseVuelo = 0;

    public PanelNuevaReserva() {
        configurarCombos();
        configurarPaquetes();

        cmbClase.addActionListener(e -> recalcularTotal());
        chkPaquete1.addActionListener(e -> recalcularTotal());
        chkPaquete2.addActionListener(e -> recalcularTotal());
        chkPaquete3.addActionListener(e -> recalcularTotal());
        chkPaquete4.addActionListener(e -> recalcularTotal());

        btnConfirmar.addActionListener(e -> confirmarReserva());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnVolver.addActionListener(e -> volverBusqueda());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Llamado desde PanelBuscarVuelos cuando el usuario elige un vuelo.
     * Precarga los campos del vuelo seleccionado.
     */
    public void cargarVuelo(String codigo, String origen, String destino, String fechaSalida, double precio) {
        txtCodigoVuelo.setText(codigo);
        txtOrigen.setText(origen);
        txtDestino.setText(destino);
        txtFechaSalida.setText(fechaSalida);
        txtPrecioBase.setText(String.format("$%.0f", precio));
        precioBaseVuelo = precio;
        recalcularTotal();
    }

    private void configurarCombos() {
        cmbClase.setModel(new DefaultComboBoxModel<>(new String[]{
                "Economica", "Ejecutiva", "Primera clase"
        }));
    }

    private void configurarPaquetes() {
        // TODO: reemplazar con SELECT * FROM paqueteturistico WHERE estado = 'Disponible'
        // Paquetes de ejemplo; en la versión real se generan dinámicamente
        chkPaquete1.setText("Tour de la Candelaria — $80.000");
        chkPaquete2.setText("Hotel + Desayuno 2 noches — $320.000");
        chkPaquete3.setText("Traslado aeropuerto — $45.000");
        chkPaquete4.setText("Seguro de viaje — $30.000");
    }

    private double getPrecioClase() {
        String clase = (String) cmbClase.getSelectedItem();
        return switch (clase) {
            case "Ejecutiva"     -> precioBaseVuelo * 1.5;
            case "Primera clase" -> precioBaseVuelo * 2.5;
            default              -> precioBaseVuelo;        // Economica
        };
    }

    private double getPrecioPaquetes() {
        double total = 0;
        // TODO: cuando vengan de BD, leer el precio del modelo; por ahora hardcodeado
        if (chkPaquete1.isSelected()) total += 80000;
        if (chkPaquete2.isSelected()) total += 320000;
        if (chkPaquete3.isSelected()) total += 45000;
        if (chkPaquete4.isSelected()) total += 30000;
        return total;
    }

    private void recalcularTotal() {
        double subtotalVuelo    = getPrecioClase();
        double subtotalPaquetes = getPrecioPaquetes();
        double total            = subtotalVuelo + subtotalPaquetes;

        txtSubtotalVuelo.setText(String.format("$%.0f", subtotalVuelo));
        txtSubtotalPaquetes.setText(String.format("$%.0f", subtotalPaquetes));
        txtTotal.setText(String.format("$%.0f", total));
    }

    private boolean validarCampos() {
        if (txtCodigoVuelo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Primero busque y seleccione un vuelo");
            return false;
        }
        if (txtAsiento.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "El número de asiento es obligatorio");
            return false;
        }
        return true;
    }

    private void confirmarReserva() {
        if (!validarCampos()) return;

        String codigoVuelo = txtCodigoVuelo.getText().trim();
        String clase       = (String) cmbClase.getSelectedItem();
        String asiento     = txtAsiento.getText().trim();
        String total       = txtTotal.getText();

        // TODO: (1) INSERT INTO reserva (fecha_hora_reserva, valor_total, id_cliente, codigo_vuelo)
        //           VALUES (NOW(), ?, ?, ?)          -- id_cliente viene de la sesión activa
        //       (2) INSERT INTO tiquete (num_asiento, clase, precio_final, id_reserva)
        //           VALUES (?, ?, ?, ?)
        //       (3) Por cada paquete marcado:
        //           INSERT INTO reserva_paquete (id_reserva, id_paquete) VALUES (?, ?)
        //       (4) INSERT INTO historialestadoreserva (id_reserva, id_estado, fecha_hora_cambio)
        //           VALUES (?, (SELECT id_estado FROM estadoreserva WHERE nombre_estado='Reservada'), NOW())

        JOptionPane.showMessageDialog(mainPanel,
                "Reserva creada exitosamente.\nVuelo: " + codigoVuelo +
                        "\nAsiento: " + asiento + " (" + clase + ")" +
                        "\nTotal: " + total,
                "Confirmación", JOptionPane.INFORMATION_MESSAGE);

        limpiarFormulario();
    }

    private void limpiarFormulario() {
        txtCodigoVuelo.setText("");
        txtOrigen.setText("");
        txtDestino.setText("");
        txtFechaSalida.setText("");
        txtPrecioBase.setText("");
        txtAsiento.setText("");
        txtSubtotalVuelo.setText("$0");
        txtSubtotalPaquetes.setText("$0");
        txtTotal.setText("$0");
        precioBaseVuelo = 0;
        cmbClase.setSelectedIndex(0);
        chkPaquete1.setSelected(false);
        chkPaquete2.setSelected(false);
        chkPaquete3.setSelected(false);
        chkPaquete4.setSelected(false);
    }

    private void volverBusqueda() {
        // TODO: frame.mostrarPanel("buscarVuelos")
        limpiarFormulario();
    }

}

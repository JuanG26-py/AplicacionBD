import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelMisReservas {
    private JPanel mainPanel;
    private JComboBox cmbFiltroEstado;
    private JButton btnActualizar;

    private JTable tablaReservas;
    private DefaultTableModel modeloReservas;

    private JTextField txtCodigoVuelo;
    private JTextField txtOrigen;
    private JTextField txtDestino;
    private JTextField txtFechaSalida;
    private JTextField txtTotal;
    private JTextArea txtTiquetes;
    private JTextField txtCausaCancelacion;
    private JButton btnCancelar;
    private JTextField txtEstadoActual;

    public PanelMisReservas() {
        configurarCombos();
        configurarTabla();
        cargarReservas();

        btnActualizar.addActionListener(e -> cargarReservas());
        btnCancelar.addActionListener(e -> cancelarReserva());
        tablaReservas.getSelectionModel().addListSelectionListener(e -> cargarDetalle());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void configurarCombos() {
        cmbFiltroEstado.setModel(new DefaultComboBoxModel<>(new String[]{
                "Todas", "Reservada", "Confirmada", "Cancelada", "Expirada"
        }));
    }

    private void configurarTabla() {
        modeloReservas = new DefaultTableModel(
                new Object[]{"ID", "Vuelo", "Origen", "Destino", "Fecha reserva", "Fecha vuelo", "Estado", "Total"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaReservas.setModel(modeloReservas);
    }

    private void cargarReservas() {
        String filtroEstado = (String) cmbFiltroEstado.getSelectedItem();

        // TODO: SELECT r.id_reserva, v.codigo_vuelo, co.nombre AS origen, cd.nombre AS destino,
        //              r.fecha_hora_reserva, v.fecha_salida, er.nombre_estado, r.valor_total
        //        FROM reserva r
        //        JOIN vuelo v    ON r.codigo_vuelo = v.codigo_vuelo
        //        JOIN ciudad co  ON v.id_ciudad_origen  = co.id_ciudad
        //        JOIN ciudad cd  ON v.id_ciudad_destino = cd.id_ciudad
        //        JOIN (
        //            SELECT DISTINCT ON (id_reserva) id_reserva, id_estado
        //            FROM historialestadoreserva
        //            ORDER BY id_reserva, fecha_hora_cambio DESC
        //        ) ul ON r.id_reserva = ul.id_reserva
        //        JOIN estadoreserva er ON ul.id_estado = er.id_estado
        //        WHERE r.id_cliente = ?                               -- id del cliente en sesión
        //          AND (er.nombre_estado = ? OR ? = 'Todas')
        //        ORDER BY r.fecha_hora_reserva DESC

        modeloReservas.setRowCount(0);
        limpiarDetalle();

        // Ejemplo de fila esperada:
        // modeloReservas.addRow(new Object[]{1, "AV101", "Bogota", "Cali", "2026-06-01 10:30", "2026-08-01", "Confirmada", "$250.000"});
    }

    private void cargarDetalle() {
        int fila = tablaReservas.getSelectedRow();
        if (fila == -1) return;

        Object idReserva   = modeloReservas.getValueAt(fila, 0);
        String codigoVuelo = modeloReservas.getValueAt(fila, 1).toString();
        String estado      = modeloReservas.getValueAt(fila, 6).toString();

        txtCodigoVuelo.setText(codigoVuelo);
        txtOrigen.setText(modeloReservas.getValueAt(fila, 2).toString());
        txtDestino.setText(modeloReservas.getValueAt(fila, 3).toString());
        txtFechaSalida.setText(modeloReservas.getValueAt(fila, 5).toString());
        txtEstadoActual.setText(estado);
        txtTotal.setText(modeloReservas.getValueAt(fila, 7).toString());
        txtCausaCancelacion.setText("");

        // Habilitar cancelar solo si la reserva está en estado Reservada o Confirmada
        boolean cancelable = estado.equals("Reservada") || estado.equals("Confirmada");
        btnCancelar.setEnabled(cancelable);
        txtCausaCancelacion.setEnabled(cancelable);

        // TODO: SELECT t.num_asiento, t.clase, t.precio_final
        //        FROM tiquete t
        //        WHERE t.id_reserva = ?
        txtTiquetes.setText("Tiquetes de la reserva " + idReserva + ":\n");
        // Ejemplo: txtTiquetes.append("  Asiento A3 — Ejecutiva — $375.000\n");
    }

    private void cancelarReserva() {
        int fila = tablaReservas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(mainPanel, "Seleccione una reserva de la tabla");
            return;
        }

        String causa = txtCausaCancelacion.getText().trim();
        if (causa.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Ingrese el motivo de cancelación");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(mainPanel,
                "¿Desea cancelar esta reserva?\nMotivo: " + causa,
                "Confirmar cancelación", JOptionPane.YES_NO_OPTION);
        if (confirmar != JOptionPane.YES_OPTION) return;

        Object idReserva = modeloReservas.getValueAt(fila, 0);

        // TODO: (1) UPDATE reserva SET causa_cancelacion = ? WHERE id_reserva = ?
        //       (2) INSERT INTO historialestadoreserva (id_reserva, id_estado, fecha_hora_cambio)
        //           VALUES (?, (SELECT id_estado FROM estadoreserva WHERE nombre_estado='Cancelada'), NOW())

        modeloReservas.setValueAt("Cancelada", fila, 6);
        btnCancelar.setEnabled(false);
        txtCausaCancelacion.setEnabled(false);
        txtEstadoActual.setText("Cancelada");

        JOptionPane.showMessageDialog(mainPanel, "Reserva " + idReserva + " cancelada.");
    }

    private void limpiarDetalle() {
        txtCodigoVuelo.setText("");
        txtOrigen.setText("");
        txtDestino.setText("");
        txtFechaSalida.setText("");
        txtEstadoActual.setText("");
        txtTotal.setText("");
        txtTiquetes.setText("");
        txtCausaCancelacion.setText("");
        btnCancelar.setEnabled(false);
        txtCausaCancelacion.setEnabled(false);
    }




}

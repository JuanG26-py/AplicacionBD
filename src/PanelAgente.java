import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelAgente {
    private JPanel mainPanel;
    private JTabbedPane tabsAgente;

    // Pestaña Reservas
    private JTextField txtBuscarReserva;
    private JButton btnBuscarReserva;
    private JTable tablaReservas;
    private JComboBox cmbNuevoEstado;
    private JButton btnCambiarEstado;
    private JTextArea txtHistorialEstados;

    // Pestaña Tiquetes
    private JLabel lblReservaSeleccionada;
    private JComboBox cmbClase;
    private JTextField txtAsiento;
    private JTextField txtPrecioFinal;
    private JButton btnAsignarTiquete;
    private JTable tablaTiquetes;

    private DefaultTableModel modeloReservas;
    private DefaultTableModel modeloTiquetes;

    public PanelAgente() {
        configurarCombos();
        configurarTablas();

        btnBuscarReserva.addActionListener(e -> buscarReservas());
        btnCambiarEstado.addActionListener(e -> cambiarEstadoReserva());
        btnAsignarTiquete.addActionListener(e -> asignarTiquete());

        tablaReservas.getSelectionModel().addListSelectionListener(e -> cargarReservaSeleccionada());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void configurarCombos() {
        cmbNuevoEstado.setModel(new DefaultComboBoxModel<>(new String[]{
                "Reservada", "Confirmada", "Cancelada", "Expirada"
        }));

        cmbClase.setModel(new DefaultComboBoxModel<>(new String[]{
                "Economica", "Ejecutiva", "Primera clase"
        }));
    }

    private void configurarTablas() {
        modeloReservas = new DefaultTableModel(
                new Object[]{"Id", "Cliente", "Vuelo", "Fecha", "Estado", "Total"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaReservas.setModel(modeloReservas);

        modeloTiquetes = new DefaultTableModel(
                new Object[]{"Id", "Asiento", "Clase", "Precio"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaTiquetes.setModel(modeloTiquetes);
    }

    private void buscarReservas() {
        String criterio = txtBuscarReserva.getText().trim();
        if (criterio.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Ingrese un ID de reserva o cedula del cliente");
            return;
        }

        // TODO: SELECT * FROM reserva WHERE id = ? OR cliente_id = ?
        modeloReservas.setRowCount(0);
        // Ejemplo de fila esperada:
        // modeloReservas.addRow(new Object[]{1, "Juan Perez", "AV-2041", "2026-06-10", "Reservada", "350000"});

        txtHistorialEstados.setText("");
    }

    private void cargarReservaSeleccionada() {
        int fila = tablaReservas.getSelectedRow();
        if (fila == -1) return;

        Object idReserva = modeloReservas.getValueAt(fila, 0);
        Object estadoActual = modeloReservas.getValueAt(fila, 4);

        cmbNuevoEstado.setSelectedItem(estadoActual);
        lblReservaSeleccionada.setText("Reserva: " + idReserva);

        // TODO: SELECT estado, fecha_cambio FROM historial_estado_reserva WHERE reserva_id = ?
        txtHistorialEstados.setText("Historial de la reserva " + idReserva + ":\n");

        cargarTiquetesDeReserva(idReserva);
    }

    private void cargarTiquetesDeReserva(Object idReserva) {
        // TODO: SELECT * FROM tiquete WHERE reserva_id = ?
        modeloTiquetes.setRowCount(0);
    }

    private void cambiarEstadoReserva() {
        int fila = tablaReservas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(mainPanel, "Seleccione una reserva de la tabla");
            return;
        }

        String nuevoEstado = (String) cmbNuevoEstado.getSelectedItem();

        // TODO: UPDATE reserva SET estado = ? WHERE id = ?
        // TODO: INSERT INTO historial_estado_reserva (reserva_id, estado, fecha_cambio) VALUES (?, ?, NOW())
        modeloReservas.setValueAt(nuevoEstado, fila, 4);

        JOptionPane.showMessageDialog(mainPanel, "Estado actualizado a: " + nuevoEstado);
    }

    private boolean validarTiquete() {
        if (txtAsiento.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "El numero de asiento es obligatorio");
            return false;
        }
        try {
            double precio = Double.parseDouble(txtPrecioFinal.getText().trim());
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

    private void asignarTiquete() {
        int filaReserva = tablaReservas.getSelectedRow();
        if (filaReserva == -1) {
            JOptionPane.showMessageDialog(mainPanel, "Seleccione una reserva primero");
            return;
        }
        if (!validarTiquete()) return;

        Object idReserva = modeloReservas.getValueAt(filaReserva, 0);

        // TODO: INSERT INTO tiquete (reserva_id, numero_asiento, clase, precio_final) VALUES (?, ?, ?, ?)
        int nuevoId = modeloTiquetes.getRowCount() + 1;
        modeloTiquetes.addRow(new Object[]{
                nuevoId,
                txtAsiento.getText().trim(),
                cmbClase.getSelectedItem(),
                txtPrecioFinal.getText().trim()
        });

        txtAsiento.setText("");
        txtPrecioFinal.setText("");
    }
}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Calendar;
import java.util.Date;

public class PanelBuscarVuelos {
    private JPanel mainPanel;
    private JComboBox cmbOrigen;
    private JComboBox cmbDestino;
    private JSpinner spnFecha;
    private JComboBox cmbClase;
    private JButton btnBuscar;
    private JTable tablaVuelos;
    private JButton btnSeleccionar;
    private JLabel lblResultados;
    private JButton btnLimpiar;

    private DefaultTableModel modeloTabla;

    public PanelBuscarVuelos() {
        configurarSpinner();
        configurarCombos();
        configurarTabla();
        cargarCiudades();

        btnBuscar.addActionListener(e -> buscarVuelos());
        btnLimpiar.addActionListener(e -> limpiarFiltros());
        btnSeleccionar.addActionListener(e -> seleccionarVuelo());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void configurarSpinner() {
        SpinnerDateModel modelo = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        spnFecha.setModel(modelo);
        spnFecha.setEditor(new JSpinner.DateEditor(spnFecha, "dd/MM/yyyy"));
    }

    private void configurarCombos() {
        cmbClase.setModel(new DefaultComboBoxModel<>(new String[]{
                "Todas", "Economica", "Ejecutiva", "Primera clase"
        }));
    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel(
                new Object[]{"Codigo", "Origen", "Destino", "Fecha salida", "Hora salida",
                        "Fecha llegada", "Hora llegada", "Capacidad disponible", "Precio base", "Estado"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaVuelos.setModel(modeloTabla);
    }

    private void cargarCiudades() {
        // TODO: reemplazar con SELECT * FROM ciudad
        String[] ciudades = new String[]{"-- Cualquier origen --", "Bogota", "Medellin", "Cali", "Cartagena", "Barranquilla"};
        cmbOrigen.setModel(new DefaultComboBoxModel<>(ciudades));

        String[] destinos = new String[]{"-- Cualquier destino --", "Bogota", "Medellin", "Cali", "Cartagena", "Barranquilla"};
        cmbDestino.setModel(new DefaultComboBoxModel<>(destinos));
    }

    private void buscarVuelos() {
        String origen = (String) cmbOrigen.getSelectedItem();
        String destino = (String) cmbDestino.getSelectedItem();
        Date fecha = (Date) spnFecha.getValue();

        if (origen != null && origen.equals(destino) && !origen.startsWith("--")) {
            JOptionPane.showMessageDialog(mainPanel, "El origen y el destino no pueden ser iguales");
            return;
        }

        // TODO: SELECT v.*, (v.capacidad_total - COUNT(t.id_tiquete)) AS disponibles
        //        FROM vuelo v
        //        LEFT JOIN reserva r ON v.codigo_vuelo = r.codigo_vuelo
        //        LEFT JOIN tiquete t ON r.id_reserva = t.id_reserva
        //        JOIN ciudad co ON v.id_ciudad_origen = co.id_ciudad
        //        JOIN ciudad cd ON v.id_ciudad_destino = cd.id_ciudad
        //        WHERE (co.nombre = ? OR ? LIKE '--%')
        //          AND (cd.nombre = ? OR ? LIKE '--%')
        //          AND v.fecha_salida = ?
        //          AND v.estado_vuelo = 'Programado'
        //        GROUP BY v.codigo_vuelo, co.nombre, cd.nombre
        //        ORDER BY v.hora_salida

        modeloTabla.setRowCount(0);
        lblResultados.setText("0 vuelos encontrados");

        // Ejemplo de fila esperada:
        // modeloTabla.addRow(new Object[]{"AV101", "Bogota", "Cali", "2026-08-01", "08:00", "2026-08-01", "09:30", 45, "$250.000", "Programado"});
    }

    private void seleccionarVuelo() {
        int fila = tablaVuelos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(mainPanel, "Seleccione un vuelo de la tabla");
            return;
        }

        String codigoVuelo = modeloTabla.getValueAt(fila, 0).toString();
        String origen = modeloTabla.getValueAt(fila, 1).toString();
        String destino = modeloTabla.getValueAt(fila, 2).toString();
        String precio = modeloTabla.getValueAt(fila, 8).toString();

        // Navegar al panel de nueva reserva con el vuelo preseleccionado
        // TODO: frame.getPanel("reserva", new PanelNuevaReserva(codigoVuelo, origen, destino, precio))
        JOptionPane.showMessageDialog(mainPanel,
                "Vuelo seleccionado: " + codigoVuelo + "\n" + origen + " → " + destino + "\n" + precio,
                "Continuar reserva", JOptionPane.INFORMATION_MESSAGE);
    }

    private void limpiarFiltros() {
        cmbOrigen.setSelectedIndex(0);
        cmbDestino.setSelectedIndex(0);
        spnFecha.setValue(new Date());
        cmbClase.setSelectedIndex(0);
        modeloTabla.setRowCount(0);
        lblResultados.setText("");
    }
}

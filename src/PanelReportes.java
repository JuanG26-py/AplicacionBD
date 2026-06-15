import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Calendar;
import java.util.Date;

public class PanelReportes {
    private JPanel mainPanel;
    private JComboBox cmbTipoReporte;
    private JSpinner spnFechaInicio;
    private JSpinner spnFechaFin;
    private JButton btnGenerar;
    private JTable tablaReporte;
    private JLabel lblResumen;

    private DefaultTableModel modeloTabla;

    public PanelReportes() {
        configurarCombo();
        configurarSpinners();
        configurarTabla();

        btnGenerar.addActionListener(e -> generarReporte());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void configurarCombo() {
        cmbTipoReporte.setModel(new DefaultComboBoxModel<>(new String[]{
                "Ingresos mensuales por destino",
                "Reservas por vuelo y mes",
                "Clientes frecuentes",
                "Vuelos por destino (pais/depto/ciudad)",
                "Destinos mas vendidos",
                "Historial de reservas por cliente",
                "Reservas canceladas y su causa",
                "Tiempo promedio reserva-confirmacion"
        }));
    }

    private void configurarSpinners() {
        SpinnerDateModel modeloInicio = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        spnFechaInicio.setModel(modeloInicio);
        spnFechaInicio.setEditor(new JSpinner.DateEditor(spnFechaInicio, "dd/MM/yyyy"));

        SpinnerDateModel modeloFin = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        spnFechaFin.setModel(modeloFin);
        spnFechaFin.setEditor(new JSpinner.DateEditor(spnFechaFin, "dd/MM/yyyy"));
    }

    private void configurarTabla() {
        modeloTabla = new DefaultTableModel();
        tablaReporte.setModel(modeloTabla);
    }

    private void generarReporte() {
        String tipo = (String) cmbTipoReporte.getSelectedItem();
        Date inicio = (Date) spnFechaInicio.getValue();
        Date fin = (Date) spnFechaFin.getValue();

        if (inicio.after(fin)) {
            JOptionPane.showMessageDialog(mainPanel, "La fecha de inicio no puede ser mayor a la fecha fin");
            return;
        }

        // TODO: ejecutar la consulta SQL correspondiente segun "tipo" y el rango de fechas
        // Cada reporte tiene columnas distintas, por eso se reconfigura el modelo aqui

        switch (tipo) {
            case "Ingresos mensuales por destino" -> {
                modeloTabla.setDataVector(new Object[0][0], new Object[]{"Destino", "Mes", "Ingresos"});
                lblResumen.setText("Total de ingresos: $0");
            }
            case "Reservas por vuelo y mes" -> {
                modeloTabla.setDataVector(new Object[0][0], new Object[]{"Vuelo", "Mes", "Cantidad de reservas"});
                lblResumen.setText("Total reservas: 0");
            }
            case "Clientes frecuentes" -> {
                modeloTabla.setDataVector(new Object[0][0], new Object[]{"Cliente", "Cantidad de reservas"});
                lblResumen.setText("Clientes en el periodo: 0");
            }
            case "Vuelos por destino (pais/depto/ciudad)" -> {
                modeloTabla.setDataVector(new Object[0][0], new Object[]{"Pais", "Departamento", "Ciudad", "Cantidad de vuelos"});
                lblResumen.setText("");
            }
            case "Destinos mas vendidos" -> {
                modeloTabla.setDataVector(new Object[0][0], new Object[]{"Destino", "Sector", "Cantidad vendida"});
                lblResumen.setText("");
            }
            case "Historial de reservas por cliente" -> {
                modeloTabla.setDataVector(new Object[0][0], new Object[]{"Cliente", "Reserva", "Fecha", "Estado", "Total"});
                lblResumen.setText("");
            }
            case "Reservas canceladas y su causa" -> {
                modeloTabla.setDataVector(new Object[0][0], new Object[]{"Reserva", "Cliente", "Fecha cancelacion", "Causa"});
                lblResumen.setText("Total canceladas: 0");
            }
            case "Tiempo promedio reserva-confirmacion" -> {
                modeloTabla.setDataVector(new Object[0][0], new Object[]{"Vuelo", "Tiempo promedio (horas)"});
                lblResumen.setText("Promedio general: 0 horas");
            }
        }
    }
}
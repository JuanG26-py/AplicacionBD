import javax.swing.*;

public class PanelPerfil {

    private JPanel mainPanel;
    private JTextField txtIdentificacion;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtCorreo;
    private JTextField txtDireccion;
    private JTextField txtTelefonoPrincipal;
    private JTextField txtTelefonoAlterno;
    private JComboBox cmbPais;
    private JComboBox cmbDepartamento;
    private JComboBox cmbCiudad;
    private JButton btnCancelar;
    private JButton btnGuardar;
    private JTextField txtContrasenaActual;
    private JTextField txtNuevaContrasena;
    private JTextField txtConfirmarContrasena;
    private JButton btnCambiarContrasena;

    public PanelPerfil() {
        txtIdentificacion.setEditable(false);

        cargarPaises();
        cargarDatosSesion();

        cmbPais.addActionListener(e -> cargarDepartamentos());
        cmbDepartamento.addActionListener(e -> cargarCiudades());

        btnGuardar.addActionListener(e -> guardarCambios());
        btnCancelar.addActionListener(e -> cargarDatosSesion());
        btnCambiarContrasena.addActionListener(e -> cambiarContrasena());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void cargarPaises() {
        // TODO: SELECT * FROM pais ORDER BY nombre
        String[] paises = new String[]{"Colombia", "Peru", "Ecuador"};
        cmbPais.setModel(new DefaultComboBoxModel<>(paises));
        cargarDepartamentos();
    }

    private void cargarDepartamentos() {
        // TODO: SELECT * FROM departamento WHERE id_pais = ? ORDER BY nombre
        String pais = (String) cmbPais.getSelectedItem();
        String[] departamentos;
        if ("Colombia".equals(pais)) {
            departamentos = new String[]{"Valle del Cauca", "Antioquia", "Cundinamarca"};
        } else if ("Peru".equals(pais)) {
            departamentos = new String[]{"Lima", "Cusco"};
        } else {
            departamentos = new String[]{"Pichincha", "Guayas"};
        }
        cmbDepartamento.setModel(new DefaultComboBoxModel<>(departamentos));
        cargarCiudades();
    }

    private void cargarCiudades() {
        // TODO: SELECT * FROM ciudad WHERE id_departamento = ? ORDER BY nombre
        String depto = (String) cmbDepartamento.getSelectedItem();
        String[] ciudades;
        if ("Valle del Cauca".equals(depto)) {
            ciudades = new String[]{"Cali", "Palmira", "Yumbo"};
        } else if ("Antioquia".equals(depto)) {
            ciudades = new String[]{"Medellin", "Envigado"};
        } else if ("Cundinamarca".equals(depto)) {
            ciudades = new String[]{"Bogota", "Soacha"};
        } else {
            ciudades = new String[]{"Ciudad 1", "Ciudad 2"};
        }
        cmbCiudad.setModel(new DefaultComboBoxModel<>(ciudades));
    }

    private void cargarDatosSesion() {
        // TODO: SELECT c.* , ci.nombre AS ciudad, d.nombre AS departamento, p.nombre AS pais
        //        FROM cliente c
        //        JOIN ciudad ci       ON c.id_ciudad = ci.id_ciudad
        //        JOIN departamento d  ON ci.id_departamento = d.id_departamento
        //        JOIN pais p          ON d.id_pais = p.id_pais
        //        WHERE c.id_cliente = ?          -- id del cliente en sesión
        //
        // Por ahora se limpian los campos para simular carga desde BD:
        txtIdentificacion.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        txtTelefonoPrincipal.setText("");
        txtTelefonoAlterno.setText("");
        cmbPais.setSelectedIndex(0);
        limpiarContrasenas();
    }

    private boolean validarCampos() {
        if (txtNombres.getText().trim().isEmpty() || txtApellidos.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Nombres y apellidos son obligatorios");
            return false;
        }
        if (txtCorreo.getText().trim().isEmpty() || !txtCorreo.getText().contains("@")) {
            JOptionPane.showMessageDialog(mainPanel, "Ingrese un correo válido");
            return false;
        }
        if (txtTelefonoPrincipal.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "El teléfono principal es obligatorio");
            return false;
        }
        return true;
    }

    private void guardarCambios() {
        if (!validarCampos()) return;

        // TODO: UPDATE cliente
        //        SET nombres = ?, apellidos = ?, email = ?, direccion = ?,
        //            telefono_principal = ?, telefono_alterno = ?, id_ciudad = ?
        //        WHERE id_cliente = ?          -- id del cliente en sesión

        JOptionPane.showMessageDialog(mainPanel, "Datos actualizados correctamente",
                "Perfil", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cambiarContrasena() {
        /// En esta parte es para Contraseña paro esta con un get password y eso es con el controller
        /// Y eso se verefica con la conexion de la base de datos con el java
        /*
        String actual = new String(txtContrasenaActual.getPassword()).trim();
        String nueva = new String(txtNuevaContrasena.getPassword()).trim();
        String confirmar = new String(txtConfirmarContrasena.getPassword()).trim();

        if (actual.isEmpty() || nueva.isEmpty() || confirmar.isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Complete todos los campos de contraseña");
            return;
        }
        if (!nueva.equals(confirmar)) {
            JOptionPane.showMessageDialog(mainPanel, "La nueva contraseña y su confirmación no coinciden");
            return;
        }
        if (nueva.length() < 6) {
            JOptionPane.showMessageDialog(mainPanel, "La contraseña debe tener al menos 6 caracteres");
            return;
        }

        // TODO: (1) Verificar que la contraseña actual sea correcta (consulta a BD o tabla agente/cliente)
        //       (2) UPDATE <tabla_usuario> SET contrasena = ? WHERE id = ?

        limpiarContrasenas();
        JOptionPane.showMessageDialog(mainPanel, "Contraseña actualizada correctamente",
                "Perfil", JOptionPane.INFORMATION_MESSAGE);

     */
    }

    private void limpiarContrasenas() {
        txtContrasenaActual.setText("");
        txtNuevaContrasena.setText("");
        txtConfirmarContrasena.setText("");
    }
}

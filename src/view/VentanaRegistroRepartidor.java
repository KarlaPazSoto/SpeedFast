package view;

import dao.RepartidorDAO;
import javax.swing.*;
import java.awt.*;

public class VentanaRegistroRepartidor extends JFrame {
    private JTextField txtNombre;
    private JButton btnGuardar;
    private RepartidorDAO repartidorDAO;

    public VentanaRegistroRepartidor() {
        setTitle("Registrar Repartidor");
        setSize(350, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        repartidorDAO = new RepartidorDAO();

        // Componentes
        add(new JLabel("  Nombre del Repartidor:"));
        txtNombre = new JTextField();
        add(txtNombre);

        // Espacio vacío para alinear el botón
        add(new JLabel());

        btnGuardar = new JButton("Guardar");
        add(btnGuardar);

        // Evento del botón
        btnGuardar.addActionListener(e -> registrarRepartidor());
    }

    private void registrarRepartidor() {
        String nombre = txtNombre.getText().trim();

        // Validación básica
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Llamada al DAO para guardar en la base de datos
        boolean exito = repartidorDAO.create(nombre); // O el método que uses para insertar repartidores

        if (exito) {
            JOptionPane.showMessageDialog(this, "¡Repartidor registrado con éxito!");
            txtNombre.setText(""); // Limpiar campo
            dispose(); // Cierra la ventana actual
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el repartidor en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
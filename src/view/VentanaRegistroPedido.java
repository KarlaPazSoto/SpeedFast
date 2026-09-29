package view;

import database.PedidoDAO;
import javax.swing.*;
import java.awt.*;

public class VentanaRegistroPedido extends JFrame {
    public VentanaRegistroPedido() {
        setTitle("Registrar Nuevo Pedido");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 5, 5));

        JLabel lblDireccion = new JLabel("Dirección:");
        JTextField txtDireccion = new JTextField();

        JLabel lblTipo = new JLabel("Tipo:");
        JComboBox<String> cmbTipo = new JComboBox<>(new String[]{"COMIDA", "ENCOMIENDA", "EXPRESS"});

        JButton btnGuardar = new JButton("Guardar");

        add(lblDireccion);
        add(txtDireccion);
        add(lblTipo);
        add(cmbTipo);
        add(new JLabel()); // Espacio vacío
        add(btnGuardar);

        PedidoDAO pedidoDAO = new PedidoDAO();

        btnGuardar.addActionListener(e -> {
            String direccion = txtDireccion.getText().trim();
            String tipo = (String) cmbTipo.getSelectedItem();

            if (direccion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La dirección no puede estar vacía.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean exito = pedidoDAO.insertarPedido(direccion, tipo);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Pedido registrado con éxito en la base de datos.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
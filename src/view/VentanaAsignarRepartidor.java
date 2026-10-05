package view;

import dao.EntregaDAO;
import dao.PedidoDAO;
import dao.RepartidorDAO;
import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class VentanaAsignarRepartidor extends JFrame {
    private JComboBox<String> cmbPedidos;
    private JComboBox<String> cmbRepartidores;
    private PedidoDAO pedidoDAO;
    private RepartidorDAO repartidorDAO;
    private EntregaDAO entregaDAO;

    public VentanaAsignarRepartidor() {
        setTitle("Asignar Repartidor / Iniciar Entrega");
        setSize(400, 220);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        pedidoDAO = new PedidoDAO();
        repartidorDAO = new RepartidorDAO();
        entregaDAO = new EntregaDAO();

        add(new JLabel("Pedido Pendiente:"));
        cmbPedidos = new JComboBox<>();
        add(cmbPedidos);

        add(new JLabel("Repartidor:"));
        cmbRepartidores = new JComboBox<>();
        add(cmbRepartidores);

        JButton btnAsignar = new JButton("Asignar");
        add(new JLabel());
        add(btnAsignar);

        cargarDatosCombos();

        btnAsignar.addActionListener(e -> procesarAsignacion());
    }

    private void cargarDatosCombos() {
        // Cargar pedidos y filtrar solo los PENDIENTES desde el DAO
        List<String[]> pedidos = pedidoDAO.obtenerPedidos();
        for (String[] p : pedidos) {
            if ("PENDIENTE".equals(p[3])) {
                cmbPedidos.addItem(p[0] + " - " + p[1]); // ID - Dirección
            }
        }

        // Cargar repartidores desde el DAO
        List<String[]> repartidores = repartidorDAO.readAll();
        for (String[] r : repartidores) {
            cmbRepartidores.addItem(r[0] + " - " + r[1]); // ID - Nombre
        }
    }

    private void procesarAsignacion() {
        String pedidoSel = (String) cmbPedidos.getSelectedItem();
        String repartidorSel = (String) cmbRepartidores.getSelectedItem();

        if (pedidoSel == null || repartidorSel == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un pedido y un repartidor.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idPedido = Integer.parseInt(pedidoSel.split(" - ")[0]);
        int idRepartidor = Integer.parseInt(repartidorSel.split(" - ")[0]);

        boolean exito = entregaDAO.registrarEntrega(
                idPedido,
                idRepartidor,
                Date.valueOf(LocalDate.now()),
                Time.valueOf(LocalTime.now())
        );

        if (exito) {
            JOptionPane.showMessageDialog(this, "¡Entrega registrada y asignada con éxito!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al procesar la asignación en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
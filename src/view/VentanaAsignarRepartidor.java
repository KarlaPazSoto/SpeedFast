package view;

import database.ConexionBD;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class VentanaAsignarRepartidor extends JFrame {
    private JComboBox<String> cmbPedidos;
    private JComboBox<String> cmbRepartidores;

    public VentanaAsignarRepartidor() {
        setTitle("Asignar Repartidor / Iniciar Entrega");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Seleccionar Pedido (Pendiente):"));
        cmbPedidos = new JComboBox<>();
        add(cmbPedidos);

        add(new JLabel("Seleccionar Repartidor:"));
        cmbRepartidores = new JComboBox<>();
        add(cmbRepartidores);

        JButton btnAsignar = new JButton("Asignar e Iniciar");
        add(new JLabel()); // Espacio vacío
        add(btnAsignar);

        cargarDatosCombos();

        btnAsignar.addActionListener(e -> asignarEntrega());
    }

    private void cargarDatosCombos() {
        try (Connection conn = ConexionBD.obtenerConexion()) {
            // Cargar solo pedidos pendientes
            String sqlPedido = "SELECT id, direccion FROM pedido WHERE estado = 'PENDIENTE'";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlPedido)) {
                while (rs.next()) {
                    cmbPedidos.addItem(rs.getInt("id") + " - " + rs.getString("direccion"));
                }
            }

            // Cargar repartidores
            String sqlRepartidor = "SELECT id, nombre FROM repartidor";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlRepartidor)) {
                while (rs.next()) {
                    cmbRepartidores.addItem(rs.getInt("id") + " - " + rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar combos: " + e.getMessage());
        }
    }

    private void asignarEntrega() {
        String pedidoSeleccionado = (String) cmbPedidos.getSelectedItem();
        String repartidorSeleccionado = (String) cmbRepartidores.getSelectedItem();

        if (pedidoSeleccionado == null || repartidorSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un pedido y un repartidor.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idPedido = Integer.parseInt(pedidoSeleccionado.split(" - ")[0]);
        int idRepartidor = Integer.parseInt(repartidorSeleccionado.split(" - ")[0]);

        try (Connection conn = ConexionBD.obtenerConexion()) {
            conn.setAutoCommit(false);

            // 1. Insertar en la tabla entrega incluyendo fecha y hora
            String sqlEntrega = "INSERT INTO entrega (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt1 = conn.prepareStatement(sqlEntrega)) {
                pstmt1.setInt(1, idPedido);
                pstmt1.setInt(2, idRepartidor);
                pstmt1.setDate(3, java.sql.Date.valueOf(LocalDate.now())); // Fecha actual
                pstmt1.setTime(4, java.sql.Time.valueOf(LocalTime.now())); // Hora actual
                pstmt1.executeUpdate();
            }

            // 2. Actualizar el estado del pedido a EN_REPARTO
            String sqlUpdatePedido = "UPDATE pedido SET estado = 'EN_REPARTO' WHERE id = ?";
            try (PreparedStatement pstmt2 = conn.prepareStatement(sqlUpdatePedido)) {
                pstmt2.setInt(1, idPedido);
                pstmt2.executeUpdate();
            }

            conn.commit();
            JOptionPane.showMessageDialog(this, "¡Repartidor asignado con éxito! Entrega iniciada.");
            dispose();

        } catch (SQLException e) {
            System.err.println("Error al procesar asignación: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error de base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
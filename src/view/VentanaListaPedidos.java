package view;

import database.PedidoDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaListaPedidos extends JFrame {
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private PedidoDAO pedidoDAO;

    public VentanaListaPedidos() {
        setTitle("Listado de Pedidos");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        pedidoDAO = new PedidoDAO();

        String[] columnas = {"ID", "Dirección", "Tipo", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaPedidos = new JTable(modeloTabla);

        add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);

        JButton btnRefrescar = new JButton("Refrescar Tabla");
        add(btnRefrescar, BorderLayout.SOUTH);

        btnRefrescar.addActionListener(e -> cargarDatos());

        cargarDatos(); // Cargar al iniciar
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        List<String[]> pedidos = pedidoDAO.obtenerPedidos();
        for (String[] p : pedidos) {
            modeloTabla.addRow(p);
        }
    }
}
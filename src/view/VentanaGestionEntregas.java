package view;

import dao.EntregaDAO;
import dao.PedidoDAO;
import dao.RepartidorDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VentanaGestionEntregas extends JFrame {
    private JComboBox<ItemCombo> cmbPedidos;
    private JComboBox<ItemCombo> cmbRepartidores;
    private JTextField txtFecha, txtHora;
    private JTable tablaEntregas;
    private DefaultTableModel modeloTabla;
    private EntregaDAO entregaDAO;
    private PedidoDAO pedidoDAO;
    private RepartidorDAO repartidorDAO;

    public VentanaGestionEntregas() {
        setTitle("Gestión de Entregas");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        entregaDAO = new EntregaDAO();
        pedidoDAO = new PedidoDAO();
        repartidorDAO = new RepartidorDAO();

        // --- PANEL DE REGISTRO (SUPERIOR) ---
        JPanel panelRegistro = new JPanel(new GridLayout(3, 2, 5, 5));
        panelRegistro.setBorder(BorderFactory.createTitledBorder("Registrar Nueva Entrega"));

        cmbPedidos = new JComboBox<>();
        cmbRepartidores = new JComboBox<>();
        txtFecha = new JTextField(LocalDate.now().toString()); // Fecha actual por defecto
        txtHora = new JTextField(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))); // Hora actual

        panelRegistro.add(new JLabel("Seleccionar Pedido:"));
        panelRegistro.add(cmbPedidos);
        panelRegistro.add(new JLabel("Seleccionar Repartidor:"));
        panelRegistro.add(cmbRepartidores);

        JButton btnGuardar = new JButton("Registrar Entrega");
        panelRegistro.add(new JLabel("Fecha (YYYY-MM-DD) / Hora: " + txtFecha.getText())); // O usar campos separados
        panelRegistro.add(btnGuardar);

        add(panelRegistro, BorderLayout.NORTH);

        // --- TABLA DE ENTREGAS ---
        String[] columnas = {"ID Entrega", "ID Pedido", "Dirección", "ID Repartidor", "Repartidor", "Fecha", "Hora", "Acción"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7; // Solo la columna de acción
            }
        };

        tablaEntregas = new JTable(modeloTabla);
        cargarCombos();
        cargarTablaEntregas();

        // Evento botón registrar
        btnGuardar.addActionListener(e -> registrarEntrega());

        // Evento para eliminar entrega desde la tabla
        tablaEntregas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = tablaEntregas.rowAtPoint(e.getPoint());
                int columna = tablaEntregas.columnAtPoint(e.getPoint());

                if (columna == 7 && fila != -1) {
                    int idEntrega = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
                    int confirmar = JOptionPane.showConfirmDialog(null, "¿Eliminar registro de entrega ID " + idEntrega + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (confirmar == JOptionPane.YES_OPTION) {
                        if (entregaDAO.eliminarEntrega(idEntrega)) {
                            JOptionPane.showMessageDialog(null, "Entrega eliminada correctamente.");
                            cargarTablaEntregas();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al eliminar la entrega.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        add(new JScrollPane(tablaEntregas), BorderLayout.CENTER);
    }

    private void cargarCombos() {
        cmbPedidos.removeAllItems();
        List<String[]> pedidos = pedidoDAO.obtenerPedidos();
        for (String[] p : pedidos) {
            // Mostrar formato legible ID - Dirección, pero guardar el ID internamente
            cmbPedidos.addItem(new ItemCombo(Integer.parseInt(p[0]), "Pedido #" + p[0] + " (" + p[1] + ")"));
        }

        cmbRepartidores.removeAllItems();
        List<String[]> repartidores = repartidorDAO.readAll();
        for (String[] r : repartidores) {
            cmbRepartidores.addItem(new ItemCombo(Integer.parseInt(r[0]), r[1]));
        }
    }

    private void cargarTablaEntregas() {
        modeloTabla.setRowCount(0);
        List<String[]> lista = entregaDAO.obtenerEntregas();
        for (String[] e : lista) {
            Object[] fila = {e[0], e[1], e[2], e[3], e[4], e[5], e[6], "[ Eliminar ]"};
            modeloTabla.addRow(fila);
        }
    }

    private void registrarEntrega() {
        ItemCombo pedidoSel = (ItemCombo) cmbPedidos.getSelectedItem();
        ItemCombo repartidorSel = (ItemCombo) cmbRepartidores.getSelectedItem();

        if (pedidoSel == null || repartidorSel == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un pedido y un repartidor válidos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String fecha = txtFecha.getText().trim();
        String hora = txtHora.getText().trim();

        if (fecha.isEmpty() || hora.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Los campos de fecha y hora no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean exito = entregaDAO.registrarEntrega(pedidoSel.getId(), repartidorSel.getId(), fecha, hora);
        if (exito) {
            JOptionPane.showMessageDialog(this, "¡Entrega registrada exitosamente!");
            cargarTablaEntregas();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Clase auxiliar para manejar IDs dentro de los JComboBox limpiamente
    private static class ItemCombo {
        private final int id;
        private final String texto;

        public ItemCombo(int id, String texto) {
            this.id = id;
            this.texto = texto;
        }

        public int getId() { return id; }

        @Override
        public String toString() { return texto; }
    }
}
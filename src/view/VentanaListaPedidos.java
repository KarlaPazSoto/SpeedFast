package view;

import dao.PedidoDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

public class VentanaListaPedidos extends JFrame {
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private PedidoDAO pedidoDAO;
    private JComboBox<String> cmbFiltroEstado;

    public VentanaListaPedidos() {
        setTitle("Gestión de Pedidos");
        setSize(750, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        pedidoDAO = new PedidoDAO();

        // --- PANEL DE FILTROS SUPERIOR ---
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltros.add(new JLabel("Filtrar por Estado:"));
        cmbFiltroEstado = new JComboBox<>(new String[]{"TODOS", "PENDIENTE", "EN_REPARTO", "ENTREGADO"});
        panelFiltros.add(cmbFiltroEstado);

        JButton btnFiltrar = new JButton("Filtrar");
        panelFiltros.add(btnFiltrar);
        add(panelFiltros, BorderLayout.NORTH);

        // --- TABLA ---
        String[] columnas = {"ID", "Dirección", "Tipo", "Estado", "Acción Entrega", "Opciones CRUD"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4 || column == 5; // Columnas interactivas
            }
        };

        tablaPedidos = new JTable(modeloTabla);
        cargarTabla("TODOS");

        btnFiltrar.addActionListener(e -> {
            String estadoSeleccionado = (String) cmbFiltroEstado.getSelectedItem();
            cargarTabla(estadoSeleccionado);
        });

        // Renderer y Editor para la columna 4 (Marcar Entregado)
        configurarColumnaEntrega();

        // Renderer y Editor para la columna 5 (Editar / Eliminar Pedido)
        configurarColumnaCrud();

        add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);
    }

    public void cargarTabla(String filtroEstado) {
        modeloTabla.setRowCount(0);
        List<String[]> pedidos = pedidoDAO.obtenerPedidos();

        for (String[] p : pedidos) {
            if ("TODOS".equals(filtroEstado) || filtroEstado.equals(p[3])) {
                Object[] fila = {p[0], p[1], p[2], p[3], "", "[ Editar / Eliminar ]"};
                modeloTabla.addRow(fila);
            }
        }
    }

    private void configurarColumnaEntrega() {
        tablaPedidos.getColumnModel().getColumn(4).setCellRenderer(new TableCellRenderer() {
            private final JButton boton = new JButton("Marcar Entregado");
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                String estado = (String) table.getValueAt(row, 3);
                boton.setEnabled("EN_REPARTO".equals(estado));
                return boton;
            }
        });

        tablaPedidos.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            private final JButton boton = new JButton("Marcar Entregado");
            private int filaActual;
            {
                boton.addActionListener(e -> {
                    fireEditingStopped();
                    String estadoActual = (String) tablaPedidos.getValueAt(filaActual, 3);
                    if ("EN_REPARTO".equals(estadoActual)) {
                        int idPedido = Integer.parseInt(tablaPedidos.getValueAt(filaActual, 0).toString());
                        if (pedidoDAO.actualizarEstado(idPedido, "ENTREGADO")) {
                            JOptionPane.showMessageDialog(null, "¡Pedido ENTREGADO!");
                            cargarTabla((String) cmbFiltroEstado.getSelectedItem());
                        }
                    }
                });
            }
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                filaActual = row;
                boton.setEnabled("EN_REPARTO".equals((String) table.getValueAt(row, 3)));
                return boton;
            }
        });
    }

    private void configurarColumnaCrud() {
        tablaPedidos.getColumnModel().getColumn(5).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> new JButton("[ Editar / Eliminar ]"));

        tablaPedidos.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            private final JButton boton = new JButton("[ Editar / Eliminar ]");
            private int filaActual;
            {
                boton.addActionListener(e -> {
                    fireEditingStopped();
                    int idPedido = Integer.parseInt(tablaPedidos.getValueAt(filaActual, 0).toString());
                    String dirActual = (String) tablaPedidos.getValueAt(filaActual, 1).toString();
                    String tipoActual = (String) tablaPedidos.getValueAt(filaActual, 2).toString();

                    String[] opciones = {"Editar", "Eliminar"};
                    int seleccion = JOptionPane.showOptionDialog(null, "¿Qué deseas hacer con el pedido ID " + idPedido + "?",
                            "CRUD Pedido", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

                    if (seleccion == 0) { // Editar
                        String nuevaDir = JOptionPane.showInputDialog(null, "Nueva Dirección:", dirActual);
                        String nuevoTipo = (String) JOptionPane.showInputDialog(null, "Seleccione Tipo:", "Tipo",
                                JOptionPane.QUESTION_MESSAGE, null, new String[]{"COMIDA", "ENCOMIENDA", "EXPRESS"}, tipoActual);

                        if (nuevaDir != null && !nuevaDir.trim().isEmpty() && nuevoTipo != null) {
                            if (pedidoDAO.actualizarPedido(idPedido, nuevaDir.trim(), nuevoTipo)) {
                                JOptionPane.showMessageDialog(null, "Pedido actualizado con éxito.");
                                cargarTabla((String) cmbFiltroEstado.getSelectedItem());
                            }
                        }
                    } else if (seleccion == 1) { // Eliminar
                        int confirmar = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas eliminar este pedido?", "Confirmar", JOptionPane.YES_NO_OPTION);
                        if (confirmar == JOptionPane.YES_OPTION) {
                            if (pedidoDAO.eliminarPedido(idPedido)) {
                                JOptionPane.showMessageDialog(null, "Pedido eliminado.");
                                cargarTabla((String) cmbFiltroEstado.getSelectedItem());
                            } else {
                                JOptionPane.showMessageDialog(null, "No se pudo eliminar (puede tener una entrega asociada).", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                });
            }
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                filaActual = row;
                return boton;
            }
        });
    }
}
package view;

import dao.RepartidorDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaListaRepartidores extends JFrame {
    private JTable tablaRepartidores;
    private DefaultTableModel modeloTabla;
    private RepartidorDAO repartidorDAO;

    public VentanaListaRepartidores() {
        setTitle("Gestión de Repartidores");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        repartidorDAO = new RepartidorDAO();

        String[] columnas = {"ID", "Nombre", "Acciones"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Solo la columna de acciones interactúa
            }
        };

        tablaRepartidores = new JTable(modeloTabla);
        cargarTabla();

        // Panel superior para agregar nuevo (opcional, si ya lo tienes en otra ventana puedes omitirlo)
        JButton btnRegistrar = new JButton("Registrar Nuevo Repartidor");
        btnRegistrar.addActionListener(e -> {
            new VentanaRegistroRepartidor().setVisible(true);
            // Nota: Idealmente refrescarías esta tabla al cerrar el registro
        });
        add(btnRegistrar, BorderLayout.NORTH);
        add(new JScrollPane(tablaRepartidores), BorderLayout.CENTER);

        // Listener para botones de Editar / Eliminar usando clic simple en la columna 2
        tablaRepartidores.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = tablaRepartidores.rowAtPoint(e.getPoint());
                int columna = tablaRepartidores.columnAtPoint(e.getPoint());

                if (columna == 2 && fila != -1) {
                    int idRepartidor = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
                    String nombreActual = modeloTabla.getValueAt(fila, 1).toString();

                    String[] opciones = {"Editar", "Eliminar"};
                    int seleccion = JOptionPane.showOptionDialog(null,
                            "¿Qué acción deseas realizar con " + nombreActual + "?",
                            "Opciones de Repartidor",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

                    if (seleccion == 0) { // Editar
                        String nuevoNombre = JOptionPane.showInputDialog(null, "Nuevo nombre:", nombreActual);
                        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                            if (repartidorDAO.update(idRepartidor, nuevoNombre.trim())) {
                                JOptionPane.showMessageDialog(null, "Repartidor actualizado con éxito.");
                                cargarTabla();
                            } else {
                                JOptionPane.showMessageDialog(null, "Error al actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else if (seleccion == 1) { // Eliminar
                        int confirmar = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar a este repartidor?", "Confirmar", JOptionPane.YES_NO_OPTION);
                        if (confirmar == JOptionPane.YES_OPTION) {
                            if (repartidorDAO.delete(idRepartidor)) {
                                JOptionPane.showMessageDialog(null, "Repartidor eliminado.");
                                cargarTabla();
                            } else {
                                JOptionPane.showMessageDialog(null, "Error al eliminar (puede tener entregas asociadas).", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }
        });
    }

    public void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<String[]> lista = repartidorDAO.readAll(); // Asegúrate de tener este método en tu RepartidorDAO
        for (String[] r : lista) {
            Object[] fila = {r[0], r[1], "[ Administrar ]"};
            modeloTabla.addRow(fila);
        }
    }
}
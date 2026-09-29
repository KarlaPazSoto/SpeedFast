package view;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    public VentanaPrincipal() {
        setTitle("SpeedFast - Gestión de Entregas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnRegistrar = new JButton("Registrar Pedido");
        JButton btnListar = new JButton("Listar Pedidos");
        JButton btnAsignar = new JButton("Asignar Repartidor / Iniciar");

        add(btnRegistrar);
        add(btnListar);
        add(btnAsignar);

        btnRegistrar.addActionListener(e -> new VentanaRegistroPedido().setVisible(true));
        btnListar.addActionListener(e -> new VentanaListaPedidos().setVisible(true));
    }
}
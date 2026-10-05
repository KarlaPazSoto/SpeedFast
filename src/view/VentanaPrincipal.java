package view;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    public VentanaPrincipal() {
        setTitle("SpeedFast - Gestión de Entregas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 10, 10));

        JButton btnRegistrar = new JButton("Registrar Pedido");
        JButton btnListar = new JButton("Listar Pedidos");
        JButton btnAsignar = new JButton("Asignar Repartidor / Iniciar");
        JButton btnRegistrarRepartidor = new JButton("Registrar Repartidor");
        JButton btnListarRepartidores = new JButton("Listar Repartidores");
        JButton btnGestionEntregas = new JButton("Gestión de Entregas");

        add(btnRegistrar);
        add(btnListar);
        add(btnAsignar);
        add(btnRegistrarRepartidor);
        add(btnListarRepartidores);
        add(btnGestionEntregas);

        btnRegistrar.addActionListener(e -> new VentanaRegistroPedido().setVisible(true));
        btnListar.addActionListener(e -> new VentanaListaPedidos().setVisible(true));
        btnAsignar.addActionListener(e -> new VentanaAsignarRepartidor().setVisible(true));
        btnRegistrarRepartidor.addActionListener(e -> new VentanaRegistroRepartidor().setVisible(true));
        btnListarRepartidores.addActionListener(e -> new VentanaListaRepartidores().setVisible(true));
        btnGestionEntregas.addActionListener(e -> new VentanaGestionEntregas().setVisible(true));
    }
}
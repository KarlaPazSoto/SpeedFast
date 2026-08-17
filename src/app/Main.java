package app;

import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;

public class Main {

    public static void main(String[] args) {

        // Crear un pedido de cada tipo
        PedidoComida pedidoComida = new PedidoComida(
                1,
                "Av. Providencia 123"
        );

        PedidoEncomienda pedidoEncomienda = new PedidoEncomienda(
                2,
                "Av. Las Condes 456"
        );

        PedidoExpress pedidoExpress = new PedidoExpress(
                3,
                "Av. Apoquindo 789"
        );

        // Sobrecarga de asignarRepartidor()
        pedidoComida.asignarRepartidor("Juan Pérez");

        pedidoEncomienda.asignarRepartidor("Camila Soto");

        pedidoExpress.asignarRepartidor("Luis Díaz");
    }
}
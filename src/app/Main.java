package app;

import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;

public class Main {

    public static void main(String[] args) {

        PedidoComida pedidoComida = new PedidoComida(
                1,
                "Av. Providencia 123",
                5
        );

        PedidoEncomienda pedidoEncomienda = new PedidoEncomienda(
                2,
                "Av. Las Condes 456",
                8
        );

        PedidoExpress pedidoExpress = new PedidoExpress(
                3,
                "Av. Apoquindo 789",
                6
        );

        System.out.println("===== PEDIDO COMIDA =====");
        pedidoComida.mostrarResumen();
        System.out.println("Tiempo estimado: "
                + pedidoComida.calcularTiempoEntrega() + " minutos");

        System.out.println();

        System.out.println("===== PEDIDO ENCOMIENDA =====");
        pedidoEncomienda.mostrarResumen();
        System.out.println("Tiempo estimado: "
                + pedidoEncomienda.calcularTiempoEntrega() + " minutos");

        System.out.println();

        System.out.println("===== PEDIDO EXPRESS =====");
        pedidoExpress.mostrarResumen();
        System.out.println("Tiempo estimado: "
                + pedidoExpress.calcularTiempoEntrega() + " minutos");
    }
}
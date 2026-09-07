package app;

import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;
import model.Repartidor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        // ==========================================
        // CREACIÓN DE PEDIDOS
        // ==========================================

        PedidoComida pedidoComida1 = new PedidoComida(
                1,
                "Av. Providencia 123",
                5
        );

        PedidoComida pedidoComida2 = new PedidoComida(
                2,
                "Av. Vitacura 456",
                3
        );

        PedidoEncomienda pedidoEncomienda1 = new PedidoEncomienda(
                3,
                "Av. Las Condes 789",
                8
        );

        PedidoEncomienda pedidoEncomienda2 = new PedidoEncomienda(
                4,
                "Av. Apoquindo 321",
                4
        );

        PedidoExpress pedidoExpress1 = new PedidoExpress(
                5,
                "Av. Macul 654",
                6
        );

        PedidoExpress pedidoExpress2 = new PedidoExpress(
                6,
                "Av. Grecia 987",
                2
        );


        // ==========================================
        // CREACIÓN DE REPARTIDORES
        // ==========================================

        Repartidor juan = new Repartidor("Juan Pérez");
        Repartidor camila = new Repartidor("Camila Soto");
        Repartidor luis = new Repartidor("Luis Díaz");


        // ==========================================
        // ASIGNACIÓN DE PEDIDOS
        // ==========================================

        juan.agregarPedido(pedidoComida1);
        juan.agregarPedido(pedidoExpress1);

        camila.agregarPedido(pedidoEncomienda1);
        camila.agregarPedido(pedidoComida2);

        luis.agregarPedido(pedidoExpress2);
        luis.agregarPedido(pedidoEncomienda2);


        // ==========================================
        // EJECUCIÓN CONCURRENTE
        // ==========================================

        ExecutorService executor = Executors.newFixedThreadPool(3);

        System.out.println("========================================");
        System.out.println("INICIANDO SIMULACIÓN DE ENTREGAS");
        System.out.println("========================================");
        System.out.println();

        executor.submit(juan);
        executor.submit(camila);
        executor.submit(luis);

        executor.shutdown();

        System.out.println();
        System.out.println("Todos los repartidores han sido enviados a trabajar.");
    }
}
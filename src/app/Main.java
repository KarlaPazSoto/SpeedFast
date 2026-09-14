package app;

import model.Pedido;
import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;
import model.Repartidor;
import model.ZonaDeCarga;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        // Crear la zona de carga compartida
        ZonaDeCarga zonaDeCarga = new ZonaDeCarga();

        // Crear pedidos
        Pedido pedido1 = new PedidoComida(
                1,
                "Av. Providencia 123",
                5.2
        );

        Pedido pedido2 = new PedidoEncomienda(
                2,
                "Av. Las Condes 456",
                3.8
        );

        Pedido pedido3 = new PedidoExpress(
                3,
                "Av. Macul 789",
                7.5
        );

        Pedido pedido4 = new PedidoComida(
                4,
                "Av. Grecia 321",
                2.4
        );

        Pedido pedido5 = new PedidoEncomienda(
                5,
                "Av. Apoquindo 654",
                6.1
        );

        // Agregar pedidos a la zona de carga
        zonaDeCarga.agregarPedido(pedido1);
        zonaDeCarga.agregarPedido(pedido2);
        zonaDeCarga.agregarPedido(pedido3);
        zonaDeCarga.agregarPedido(pedido4);
        zonaDeCarga.agregarPedido(pedido5);

        // Crear repartidores
        Repartidor juan = new Repartidor(
                "Juan Pérez",
                zonaDeCarga
        );

        Repartidor camila = new Repartidor(
                "Camila Soto",
                zonaDeCarga
        );

        Repartidor luis = new Repartidor(
                "Luis Díaz",
                zonaDeCarga
        );

        // Crear ExecutorService con 3 hilos
        ExecutorService executor = Executors.newFixedThreadPool(3);

        System.out.println();
        System.out.println("======================================");
        System.out.println("   INICIANDO ENTREGAS SPEEDFAST");
        System.out.println("======================================");
        System.out.println();

        // Ejecutar los 3 repartidores en paralelo
        executor.submit(juan);
        executor.submit(camila);
        executor.submit(luis);

        // No aceptar nuevas tareas
        executor.shutdown();

        // Esperar hasta que todos los repartidores terminen
        try {

            executor.awaitTermination(
                    1,
                    TimeUnit.MINUTES
            );

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            System.out.println(
                    "La ejecución fue interrumpida."
            );
        }

        System.out.println();
        System.out.println("======================================");
        System.out.println(
                "Todos los pedidos han sido entregados correctamente"
        );
        System.out.println("======================================");
    }
}
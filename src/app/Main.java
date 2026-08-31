package app;

import model.Pedido;
import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;
import strategy.ControladorEnvios;

public class Main {

    public static void main(String[] args) {

        // Crear pedidos
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

        // Crear controlador de envíos
        ControladorEnvios controlador = new ControladorEnvios();

        // ==========================================
        // RESUMEN Y TIEMPO DE ENTREGA
        // ==========================================

        System.out.println("===== PEDIDOS =====");

        pedidoComida.mostrarResumen();
        System.out.println("Tiempo estimado: "
                + pedidoComida.calcularTiempoEntrega() + " minutos");
        System.out.println();

        pedidoEncomienda.mostrarResumen();
        System.out.println("Tiempo estimado: "
                + pedidoEncomienda.calcularTiempoEntrega() + " minutos");
        System.out.println();

        pedidoExpress.mostrarResumen();
        System.out.println("Tiempo estimado: "
                + pedidoExpress.calcularTiempoEntrega() + " minutos");

        // ==========================================
        // ASIGNACIÓN DE REPARTIDORES
        // ==========================================

        System.out.println();
        System.out.println("===== ASIGNACIÓN DE REPARTIDORES =====");

        pedidoComida.asignarRepartidor();
        pedidoComida.asignarRepartidor("Juan Pérez");

        System.out.println();

        pedidoEncomienda.asignarRepartidor();
        pedidoEncomienda.asignarRepartidor("Camila Soto");

        System.out.println();

        pedidoExpress.asignarRepartidor();
        pedidoExpress.asignarRepartidor("Luis Díaz");

        // ==========================================
        // DESPACHO
        // ==========================================

        System.out.println();
        System.out.println("===== DESPACHO =====");

        controlador.despachar();

        // Agregamos los pedidos despachados al historial
        controlador.agregarAlHistorial(pedidoComida);
        controlador.agregarAlHistorial(pedidoEncomienda);

        // ==========================================
        // CANCELACIÓN
        // ==========================================

        System.out.println();
        System.out.println("===== CANCELACIÓN =====");

        controlador.cancelar();

        // ==========================================
        // HISTORIAL
        // ==========================================

        System.out.println();

        controlador.verHistorial();
    }
}
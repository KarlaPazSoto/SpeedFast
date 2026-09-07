package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Repartidor implements Runnable {

    private String nombre;
    private List<Pedido> pedidosAsignados;

    public Repartidor(String nombre) {
        this.nombre = nombre;
        this.pedidosAsignados = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Pedido> getPedidosAsignados() {
        return pedidosAsignados;
    }

    public void agregarPedido(Pedido pedido) {
        pedidosAsignados.add(pedido);
    }

    @Override
    public void run() {

        Random random = new Random();

        System.out.println("========================================");
        System.out.println("Repartidor " + nombre + " inició su jornada.");
        System.out.println("Pedidos asignados: " + pedidosAsignados.size());
        System.out.println("========================================");

        for (Pedido pedido : pedidosAsignados) {

            System.out.println(
                    "[" + nombre + "] Iniciando entrega del pedido #"
                            + pedido.getIdPedido()
            );

            System.out.println(
                    "[" + nombre + "] Dirección: "
                            + pedido.getDireccionEntrega()
            );

            System.out.println(
                    "[" + nombre + "] Distancia: "
                            + pedido.getDistanciaKm() + " km"
            );

            System.out.println(
                    "[" + nombre + "] Tiempo estimado: "
                            + pedido.calcularTiempoEntrega()
                            + " minutos"
            );

            try {

                // Tiempo aleatorio entre 1 y 3 segundos
                int tiempoEspera = 1000 + random.nextInt(2001);

                System.out.println(
                        "[" + nombre + "] Entregando pedido..."
                );

                Thread.sleep(tiempoEspera);

                System.out.println(
                        "[" + nombre + "] Pedido #"
                                + pedido.getIdPedido()
                                + " entregado correctamente."
                );

            } catch (InterruptedException e) {

                System.out.println(
                        "[" + nombre + "] La entrega fue interrumpida."
                );

                Thread.currentThread().interrupt();
            }

            System.out.println();
        }

        System.out.println(
                "[" + nombre + "] Finalizó todas sus entregas."
        );
        System.out.println();
    }
}
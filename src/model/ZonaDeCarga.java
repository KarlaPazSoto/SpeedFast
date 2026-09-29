package model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ZonaDeCarga {
    private BlockingQueue<Pedido> pedidosPendientes = new LinkedBlockingQueue<>();

    public void insertarPedido(Pedido p) {
        pedidosPendientes.add(p);
        System.out.println("Pedido agregado: " + p);
    }

    public Pedido retirarPedido() {
        try {
            return pedidosPendientes.take(); // bloquea hasta que haya un pedido
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public boolean estaVacia() {
        return pedidosPendientes.isEmpty();
    }
}

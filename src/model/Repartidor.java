package model;

public class Repartidor implements Runnable {
    private String nombre;
    private ZonaDeCarga zonaDeCarga;

    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga) {
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    @Override
    public void run() {
        while (!zonaDeCarga.estaVacia()) {
            Pedido pedido = zonaDeCarga.retirarPedido();
            if (pedido != null && pedido.getEstado() == EstadoPedido.PENDIENTE) {
                pedido.setEstado(EstadoPedido.EN_REPARTO);
                System.out.println(nombre + " retiró el pedido " + pedido.getIdPedido() + " y está en reparto.");
                try {
                    Thread.sleep(2000); // simula entrega
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                pedido.setEstado(EstadoPedido.ENTREGADO);
                System.out.println(nombre + " entregó el pedido " + pedido.getIdPedido());
            }
        }
    }
}

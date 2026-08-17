package model;

public class PedidoExpress extends Pedido {

    public PedidoExpress(int idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega, "Express");
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("---[Pedido Express]---");
        System.out.println("Asignando repartidor");
        System.out.println("- Repartidor más cercano con disponibilidad inmediata encontrado");
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("---[Pedido Express]---");
        System.out.println("Asignando repartidor");
        System.out.println("- Repartidor más cercano con disponibilidad inmediata encontrado");
        System.out.println("- Pedido asignado a: " + nombreRepartidor);
    }
}

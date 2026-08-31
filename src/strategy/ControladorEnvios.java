package strategy;


import model.Pedido;

import java.util.ArrayList;

public class ControladorEnvios implements Despachable, Cancelable, Rastreable {

    private ArrayList<Pedido> historial;

    public ControladorEnvios() {
        historial = new ArrayList<>();
    }

    @Override
    public void despachar() {
        System.out.println("→ Pedido despachado correctamente.");
    }

    @Override
    public void cancelar() {
        System.out.println("→ Pedido cancelado correctamente.");
    }

    @Override
    public void verHistorial() {

        System.out.println("===== HISTORIAL DE ENTREGAS =====");

        if (historial.isEmpty()) {
            System.out.println("No existen entregas registradas.");
            return;
        }

        for (Pedido pedido : historial) {
            System.out.println(
                    "Pedido #" + pedido.getIdPedido()
                            + " - "
                            + pedido.getClass().getSimpleName()
                            + " - "
                            + pedido.getDistanciaKm() + " km"
            );
        }
    }

    public void agregarAlHistorial(Pedido pedido) {
        historial.add(pedido);
    }
}
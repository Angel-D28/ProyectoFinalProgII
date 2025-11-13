package co.edu.uniquindio.poo.neodelivery.model;

public class DeliveredState implements ShipmentState {
    @Override
    public void assignerDriver(Shipment shipment, Admin admin, DeliveryDriver driver) {
        System.out.println("⚠El envío ya fue entregado. No se puede reasignar.");
    }

    @Override
    public void collect(Shipment shipment, DeliveryDriver driver) {
        System.out.println("El envío ya fue entregado. No se puede recoger.");
    }

    @Override
    public void deliver(Shipment shipment, DeliveryDriver driver) {
        System.out.println("El envío ya está completado.");
    }

    @Override
    public String getStatusName() { return "DELIVERED"; }
}

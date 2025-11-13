package co.edu.uniquindio.poo.neodelivery.model;

public class PendingState implements ShipmentState{

    @Override
    public void assignerDriver(Shipment shipment, Admin admin, DeliveryDriver driver) {
        shipment.setAssignedDriver(driver);
        shipment.setStatus(Status.DELIVERASSIGNED);
        shipment.setState(new DeliverAssignedState());
        System.out.println(" Envío asignado al repartidor " + driver.getName() + " por el admin " + admin.getName());
    }

    @Override
    public void collect(Shipment shipment, DeliveryDriver driver) {
        System.out.println("No se puede recoger un pedido que aún no tiene repartidor asignado.");
    }

    @Override
    public void deliver(Shipment shipment, DeliveryDriver driver) {
        System.out.println(" No se puede entregar un pedido sin recogerlo primero.");
    }

    @Override
    public String getStatusName() { return "PENDING"; }
}

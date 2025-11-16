package co.edu.uniquindio.poo.neodelivery.model.State;

import co.edu.uniquindio.poo.neodelivery.model.Admin;
import co.edu.uniquindio.poo.neodelivery.model.DeliveryDriver;
import co.edu.uniquindio.poo.neodelivery.model.Shipment;
import co.edu.uniquindio.poo.neodelivery.model.Status;

public class DeliverAssignedState implements ShipmentState{

    @Override
    public void assignerDriver(Shipment shipment, Admin admin, DeliveryDriver driver) {
        System.out.println(" El envío ya tiene un repartidor asignado.");
    }

    @Override
    public void collect(Shipment shipment, DeliveryDriver driver) {
        if (shipment.getAssignedDriver().equals(driver)) {
            shipment.setStatus(Status.DELIVERING);
            shipment.setState(new DeliveringState());
            System.out.println(" El repartidor " + driver.getName() + " ha recogido el envío.");
        } else {
            System.out.println("Solo el repartidor asignado puede recoger el envío.");
        }
    }

    @Override
    public void deliver(Shipment shipment, DeliveryDriver driver) {
        System.out.println(" No se puede entregar un pedido que aún no ha sido recogido.");
    }

    @Override
    public String getStatusName() { return "DELIVERASSIGNED"; }
}

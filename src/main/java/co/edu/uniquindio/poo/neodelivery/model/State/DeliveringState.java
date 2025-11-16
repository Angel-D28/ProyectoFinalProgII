package co.edu.uniquindio.poo.neodelivery.model.State;

import co.edu.uniquindio.poo.neodelivery.model.Admin;
import co.edu.uniquindio.poo.neodelivery.model.DeliveryDriver;
import co.edu.uniquindio.poo.neodelivery.model.Shipment;
import co.edu.uniquindio.poo.neodelivery.model.Status;

public class DeliveringState implements ShipmentState{

    @Override
    public void assignerDriver(Shipment shipment, Admin admin, DeliveryDriver driver) {
        System.out.println(" El envío ya está siendo entregado, no se puede reasignar.");
    }

    @Override
    public void collect(Shipment shipment, DeliveryDriver driver) {
        System.out.println(" El envío ya fue recogido.");
    }

    @Override
    public void deliver(Shipment shipment, DeliveryDriver driver) {
        if (shipment.getAssignedDriver().equals(driver)) {
            shipment.setStatus(Status.DELIVERED);
            shipment.setState(new DeliveredState());
            System.out.println(" El envío ha sido entregado exitosamente por " + driver.getName());
        } else {
            System.out.println(" Solo el repartidor asignado puede marcar el envío como entregado.");
        }
    }

    @Override
    public String getStatusName() { return "DELIVERING"; }
}

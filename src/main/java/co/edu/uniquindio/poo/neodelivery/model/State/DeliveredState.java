package co.edu.uniquindio.poo.neodelivery.model.State;

import co.edu.uniquindio.poo.neodelivery.model.Admin;
import co.edu.uniquindio.poo.neodelivery.model.DeliveryDriver;
import co.edu.uniquindio.poo.neodelivery.model.Shipment;
import co.edu.uniquindio.poo.neodelivery.model.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliveredState implements ShipmentState {

    public DeliveredState(){}

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
        shipment.setStatus(Status.DELIVERED);
        shipment.setState(new DeliveredState());

        driver.setAvalibility(true);
        driver.setShipmentAssigned(null);

        System.out.println("El envío ya está completado.");
    }

    @Override
    public String getStatusName() { return "DELIVERED"; }
}

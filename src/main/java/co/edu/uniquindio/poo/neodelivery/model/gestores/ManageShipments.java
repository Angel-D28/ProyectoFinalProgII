package co.edu.uniquindio.poo.neodelivery.model.gestores;

import co.edu.uniquindio.poo.neodelivery.model.Admin;
import co.edu.uniquindio.poo.neodelivery.model.DeliveryDriver;
import co.edu.uniquindio.poo.neodelivery.model.Shipment;
import co.edu.uniquindio.poo.neodelivery.model.Status;

public class ManageShipments {

    public void assignDriver(Admin admin, Shipment shipment, DeliveryDriver driver) {
        if (shipment.getStatus() == Status.PENDING) {
            shipment.setAssignedDriver(driver);
            shipment.setStatus(Status.DELIVERASSIGNED);

            System.out.println("Admin " + admin.getName() +
                    " asignó el envío " + shipment.getId() +
                    " al repartidor " + driver.getName());
        } else {
            System.out.println("No se puede asignar el envío (Estado actual: " + shipment.getStatus() + ")");
        }
    }

    public void collectShipment(DeliveryDriver driver, Shipment shipment) {
        if (shipment.getStatus() == Status.DELIVERASSIGNED &&
                driver.equals(shipment.getAssignedDriver())) {

            shipment.setStatus(Status.DELIVERING);
            System.out.println("El repartidor " + driver.getName() +
                    " recogió el envío con ID: " + shipment.getId());
        } else {
            System.out.println(" El envío no está listo o el repartidor no coincide.");
        }
    }

}

package co.edu.uniquindio.poo.neodelivery.model.State;


import co.edu.uniquindio.poo.neodelivery.model.Admin;
import co.edu.uniquindio.poo.neodelivery.model.DeliveryDriver;
import co.edu.uniquindio.poo.neodelivery.model.Shipment;

public interface ShipmentState {
    void assignerDriver(Shipment shipment , Admin admin , DeliveryDriver driver);
    void collect(Shipment shipment , DeliveryDriver driver);
    void deliver(Shipment shipment , DeliveryDriver driver);
    String getStatusName();
}

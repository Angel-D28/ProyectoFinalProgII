package co.edu.uniquindio.poo.neodelivery.model;


public interface ShipmentState {
    void assignerDriver(Shipment shipment , Admin admin , DeliveryDriver driver);
    void collect(Shipment shipment , DeliveryDriver driver);
    void deliver(Shipment shipment , DeliveryDriver driver);
    String getStatusName();
}

package co.edu.uniquindio.poo.neodelivery.model;


public class DeliveryDriver {
    private String id;
    private String name;
    private Shipment shipmentAssigned;

    public DeliveryDriver(String id, String name) {
        this.id = id;
        this.name = name;
        this.shipmentAssigned = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Shipment getShipmentAssigned() {
        return shipmentAssigned;
    }

    public void setShipmentAssigned(Shipment shipmentAssigned) {
        this.shipmentAssigned = shipmentAssigned;
    }

    public String getName() { return name; }
}

package co.edu.uniquindio.poo.neodelivery.model;

public abstract class ShipmentDecorator implements IShipment {
    protected IShipment shipment;

    public ShipmentDecorator(IShipment shipment) {
        this.shipment = shipment;
    }

    @Override
    public abstract double getCost();

    @Override
    public abstract String getDescription();
}
